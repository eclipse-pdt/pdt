<?php

/**
 * Return the translation of msgid for the current domain, or msgid unaltered if a translation does not exist 
 *
 * @return string
 * @param  msgid string
 */
function _($msgid) {}

/**
 * Return the absolute value of the number 
 *
 * @return int
 * @param  number int
 */
function abs($number) {}

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
 * 
 *
 * @return void
 * @param  obj object
 * @param  class string
 */
function aggregate($obj, $class) {}

/**
 * 
 *
 * @return void
 * @param  obj object
 * @param  class string
 */
function aggregate_methods($obj, $class) {}

/**
 * 
 *
 * @return void
 * @param  obj object
 * @param  class string
 * @param  method_list array
 * @param  exclude bool[optional]
 */
function aggregate_methods_by_list($obj, $class, $method_list, $exclude = null) {}

/**
 * 
 *
 * @return void
 * @param  obj object
 * @param  class string
 * @param  regexp string
 * @param  exclude bool[optional]
 */
function aggregate_methods_by_regexp($obj, $class, $regexp, $exclude = null) {}

/**
 * 
 *
 * @return void
 * @param  obj object
 * @param  class string
 */
function aggregate_properties($obj, $class) {}

/**
 * 
 *
 * @return void
 * @param  obj object
 * @param  class string
 * @param  props_list array
 * @param  exclude bool[optional]
 */
function aggregate_properties_by_list($obj, $class, $props_list, $exclude = null) {}

/**
 * 
 *
 * @return void
 * @param  obj object
 * @param  class string
 * @param  regexp string
 * @param  exclude bool[optional]
 */
function aggregate_properties_by_regexp($obj, $class, $regexp, $exclude = null) {}

/**
 * 
 *
 * @return array
 * @param  obj object
 */
function aggregation_info($obj) {}

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
 * @return bool
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
 * Return the value as key and the frequency of that value in input as value 
 *
 * @return array
 * @param  input array
 */
function array_count_values($input) {}

/**
 * Returns the entries of arr1 that have values which are not present in any of the others arguments 
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
 * Returns the entries of arr1 that have values which are present in all the other arguments. Keys are used to do more restrctive check 
 *
 * @return array
 * @param  arr1 array
 * @param  arr2 array
 * @vararg ... array
 */
function array_intersect_assoc($arr1, $arr2) {}

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
 */
function array_keys($input, $search_value = null) {}

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
 * @return mixed
 * @param  input array
 */
function array_sum($input) {}

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
 * @return int
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
 * @return int
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
 * @param  pass sting
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
 * @return int
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
 */
function class_exists($classname) {}

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
 * Increases the reference counter on a COM object 
 *
 * @return mixed
 * @param  module int
 */
function com_addref($module) {}

/**
 * Connect events from a COM object to a PHP object 
 *
 * @return bool
 * @param  comobject mixed
 * @param  sinkobject object
 * @param  sinkinterface mixed[optional]
 */
function com_event_sink($comobject, $sinkobject, $sinkinterface = null) {}

/**
 * Gets properties from a COM module 
 *
 * @return mixed
 * @param  module int
 * @param  property_name string
 * @vararg ... mixed
 */
function com_get($module, $property_name) {}

/**
 * Invokes a COM module 
 *
 * @return mixed
 * @param  module int
 * @param  handler_name string
 * @param  arg mixed[optional]
 * @vararg ... mixed
 */
function com_invoke($module, $handler_name, $arg = null) {}

/**
 * Invokes a COM module 
 *
 * @return mixed
 * @param  module int
 * @param  invokeflags int
 * @param  handler_name string
 * @param  arg mixed[optional]
 * @vararg ... mixed
 */
function com_invoke_ex($module, $invokeflags, $handler_name, $arg = null) {}

/**
 * Grabs an IEnumVariant 
 *
 * @return bool
 * @param  com_module object
 */
function com_isenum($com_module) {}

/**
 * Loads a COM module 
 *
 * @return int
 * @param  module_name string
 * @param  remote_host string[optional]
 * @param  codepage int[optional]
 * @param  typelib string[optional]
 */
function com_load($module_name, $remote_host = null, $codepage = null, $typelib = null) {}

/**
 * Loads a Typelib 
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
 * @param  comobject_|_string_typelib mixed
 * @param  dispinterface string
 * @param  wantsink bool
 */
function com_print_typeinfo($comobject_, $dispinterface, $wantsink) {}

/**
 * Gets properties from a COM module 
 *
 * @return mixed
 * @param  module int
 * @param  property_name string
 * @vararg ... mixed
 */
function com_propget($module, $property_name) {}

/**
 * Puts the properties for a module 
 *
 * @return bool
 * @param  module int
 * @param  property_name string
 * @param  value mixed
 * @vararg ...
 */
function com_propput($module, $property_name, $value) {}

/**
 * Puts the properties for a module 
 *
 * @return bool
 * @param  module int
 * @param  property_name string
 * @param  value mixed
 * @vararg ...
 */
function com_propset($module, $property_name, $value) {}

/**
 * Releases a COM object 
 *
 * @return mixed
 * @param  module int
 */
function com_release($module) {}

/**
 * Puts the properties for a module 
 *
 * @return bool
 * @param  module int
 * @param  property_name string
 * @param  value mixed
 * @vararg ...
 */
function com_set($module, $property_name, $value) {}

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
 * Sets annotation 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  xll float
 * @param  yll float
 * @param  xur float
 * @param  xur float
 * @param  title string
 * @param  text string
 * @param  mode int[optional]
 */
function cpdf_add_annotation($pdfdoc, $xll, $yll, $xur, $xur, $title, $text, $mode = null) {}

/**
 * Adds outline 
 *
 * @return int
 * @param  pdfdoc int
 * @param  lastoutline int
 * @param  sublevel int
 * @param  open int
 * @param  pagenr int
 * @param  title string
 */
function cpdf_add_outline($pdfdoc, $lastoutline, $sublevel, $open, $pagenr, $title) {}

/**
 * Draws an arc 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  x float
 * @param  y float
 * @param  radius float
 * @param  start float
 * @param  end float
 * @param  mode int[optional]
 */
function cpdf_arc($pdfdoc, $x, $y, $radius, $start, $end, $mode = null) {}

/**
 * Starts text section 
 *
 * @return bool
 * @param  pdfdoc int
 */
function cpdf_begin_text($pdfdoc) {}

/**
 * Draws a circle 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  x float
 * @param  y float
 * @param  radius float
 * @param  mode int[optional]
 */
function cpdf_circle($pdfdoc, $x, $y, $radius, $mode = null) {}

/**
 * Clips to current path 
 *
 * @return bool
 * @param  pdfdoc int
 */
function cpdf_clip($pdfdoc) {}

/**
 * Closes the pdf document 
 *
 * @return void
 * @param  pdfdoc int
 */
function cpdf_close($pdfdoc) {}

/**
 * Close path 
 *
 * @return bool
 * @param  pdfdoc int
 */
function cpdf_closepath($pdfdoc) {}

/**
 * Close, fill and stroke current path 
 *
 * @return bool
 * @param  pdfdoc int
 */
function cpdf_closepath_fill_stroke($pdfdoc) {}

/**
 * Close path and draw line along path 
 *
 * @return bool
 * @param  pdfdoc int
 */
function cpdf_closepath_stroke($pdfdoc) {}

/**
 * Outputs text in next line 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  text string
 */
function cpdf_continue_text($pdfdoc, $text) {}

/**
 * Draws a curve 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  x1 float
 * @param  y1 float
 * @param  x2 float
 * @param  y2 float
 * @param  x3 float
 * @param  y3 float
 * @param  mode int[optional]
 */
function cpdf_curveto($pdfdoc, $x1, $y1, $x2, $y2, $x3, $y3, $mode = null) {}

/**
 * Ends text section 
 *
 * @return bool
 * @param  pdfdoc int
 */
function cpdf_end_text($pdfdoc) {}

/**
 * Fills current path 
 *
 * @return bool
 * @param  pdfdoc int
 */
function cpdf_fill($pdfdoc) {}

/**
 * Fills and stroke current path 
 *
 * @return bool
 * @param  pdfdoc int
 */
function cpdf_fill_stroke($pdfdoc) {}

/**
 * Creates PDF doc in memory 
 *
 * @return bool
 * @param  pdfdoc int
 */
function cpdf_finalize($pdfdoc) {}

/**
 * Ends the page to save memory 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  pagenr int
 */
function cpdf_finalize_page($pdfdoc, $pagenr) {}

/**
 * Sets document settings for all documents 
 *
 * @return bool
 * @param  maxPages int
 * @param  maxFonts int
 * @param  maxImages int
 * @param  maxAnnots int
 * @param  maxObjects int
 */
function cpdf_global_set_document_limits($maxPages, $maxFonts, $maxImages, $maxAnnots, $maxObjects) {}

/**
 * Includes JPEG image 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  filename string
 * @param  x float
 * @param  y float
 * @param  angle float
 * @param  width float
 * @param  height float
 * @param  x_scale float
 * @param  y_scale float
 * @param  gsave int
 * @param  mode int[optional]
 */
function cpdf_import_jpeg($pdfdoc, $filename, $x, $y, $angle, $width, $height, $x_scale, $y_scale, $gsave, $mode = null) {}

/**
 * Draws a line 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  x float
 * @param  y float
 * @param  mode int[optional]
 */
function cpdf_lineto($pdfdoc, $x, $y, $mode = null) {}

/**
 * Sets current point 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  x float
 * @param  y float
 * @param  mode int[optional]
 */
function cpdf_moveto($pdfdoc, $x, $y, $mode = null) {}

/**
 * Starts new path 
 *
 * @return bool
 * @param  pdfdoc int
 */
function cpdf_newpath($pdfdoc) {}

/**
 * Opens a new pdf document 
 *
 * @return int
 * @param  compression int
 * @param  filename string[optional]
 * @param  doc_limits array[optional]
 */
function cpdf_open($compression, $filename = null, $doc_limits = null) {}

/**
 * Returns the internal memory stream as string 
 *
 * @return bool
 * @param  pdfdoc int
 */
function cpdf_output_buffer($pdfdoc) {}

/**
 * Starts page 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  pagenr int
 * @param  orientation int
 * @param  height int
 * @param  width int
 * @param  unit float[optional]
 */
function cpdf_page_init($pdfdoc, $pagenr, $orientation, $height, $width, $unit = null) {}

/**
 * Includes image 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  gdimage int
 * @param  x float
 * @param  y float
 * @param  angle float
 * @param  width fload
 * @param  height float
 * @param  gsave int
 * @param  mode int[optional]
 */
function cpdf_place_inline_image($pdfdoc, $gdimage, $x, $y, $angle, $width, $height, $gsave, $mode = null) {}

/**
 * Draws a rectangle 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  x float
 * @param  y float
 * @param  width float
 * @param  height float
 * @param  mode int[optional]
 */
function cpdf_rect($pdfdoc, $x, $y, $width, $height, $mode = null) {}

/**
 * Restores formerly saved enviroment 
 *
 * @return bool
 * @param  pdfdoc int
 */
function cpdf_restore($pdfdoc) {}

/**
 * Draws a line relative to current point 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  x float
 * @param  y float
 * @param  mode int[optional]
 */
function cpdf_rlineto($pdfdoc, $x, $y, $mode = null) {}

/**
 * Sets current point 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  x float
 * @param  y float
 * @param  mode int[optional]
 */
function cpdf_rmoveto($pdfdoc, $x, $y, $mode = null) {}

/**
 * Sets rotation 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  angle float
 */
function cpdf_rotate($pdfdoc, $angle) {}

/**
 * Sets text rotation angle 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  angle float
 */
function cpdf_rotate_text($pdfdoc, $angle) {}

/**
 * Saves current enviroment 
 *
 * @return bool
 * @param  pdfdoc int
 */
function cpdf_save($pdfdoc) {}

/**
 * Saves the internal memory stream to a file 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  filename string
 */
function cpdf_save_to_file($pdfdoc, $filename) {}

/**
 * Sets scaling 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  x_scale float
 * @param  y_scale float
 */
function cpdf_scale($pdfdoc, $x_scale, $y_scale) {}

/**
 * Sets hyperlink 
 *
 * @return void
 * @param  pdfdoc int
 * @param  xll float
 * @param  yll float
 * @param  xur float
 * @param  xur float
 * @param  url string
 * @param  mode int[optional]
 */
function cpdf_set_action_url($pdfdoc, $xll, $yll, $xur, $xur, $url, $mode = null) {}

/**
 * Sets character spacing 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  space float
 */
function cpdf_set_char_spacing($pdfdoc, $space) {}

/**
 * Sets the creator field 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  creator string
 */
function cpdf_set_creator($pdfdoc, $creator) {}

/**
 * Sets page for output 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  pagenr int
 */
function cpdf_set_current_page($pdfdoc, $pagenr) {}

/**
 * Selects the current font face, size and encoding 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  font string
 * @param  size float
 * @param  encoding string
 */
function cpdf_set_font($pdfdoc, $font, $size, $encoding) {}

/**
 * Sets directories to search when using external fonts 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  pfmdir string
 * @param  pfbdir string
 */
function cpdf_set_font_directories($pdfdoc, $pfmdir, $pfbdir) {}

/**
 * Sets fontname to filename translation map when using external fonts 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  filename string
 */
function cpdf_set_font_map_file($pdfdoc, $filename) {}

/**
 * Sets horizontal scaling of text 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  scale float
 */
function cpdf_set_horiz_scaling($pdfdoc, $scale) {}

/**
 * Fills the keywords field of the info structure 
 *
 * @return bool
 * @param  pdfptr int
 * @param  keywords string
 */
function cpdf_set_keywords($pdfptr, $keywords) {}

/**
 * Sets distance between text lines 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  distance float
 */
function cpdf_set_leading($pdfdoc, $distance) {}

/**
 * Sets transition between pages 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  transition int
 * @param  duration float
 * @param  direction float
 * @param  orientation int
 * @param  inout int
 */
function cpdf_set_page_animation($pdfdoc, $transition, $duration, $direction, $orientation, $inout) {}

/**
 * Fills the subject field of the info structure 
 *
 * @return bool
 * @param  pdfptr int
 * @param  subject string
 */
function cpdf_set_subject($pdfptr, $subject) {}

/**
 * Sets the text matrix 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  matrix arry
 */
function cpdf_set_text_matrix($pdfdoc, $matrix) {}

/**
 * Sets the position of text for the next cpdf_show call 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  x float
 * @param  y float
 * @param  mode int[optional]
 */
function cpdf_set_text_pos($pdfdoc, $x, $y, $mode = null) {}

/**
 * Determines how text is rendered 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  rendermode int
 */
function cpdf_set_text_rendering($pdfdoc, $rendermode) {}

/**
 * Sets the text rise 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  value float
 */
function cpdf_set_text_rise($pdfdoc, $value) {}

/**
 * Fills the title field of the info structure 
 *
 * @return bool
 * @param  pdfptr int
 * @param  title string
 */
function cpdf_set_title($pdfptr, $title) {}

/**
 * How to show the document in the viewer 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  preferences array
 */
function cpdf_set_viewer_preferences($pdfdoc, $preferences) {}

/**
 * Sets spacing between words 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  space float
 */
function cpdf_set_word_spacing($pdfdoc, $space) {}

/**
 * Sets dash pattern 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  white long
 * @param  black long
 */
function cpdf_setdash($pdfdoc, $white, $black) {}

/**
 * Sets flatness 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  value float
 */
function cpdf_setflat($pdfdoc, $value) {}

/**
 * Sets drawing and filling color to gray value 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  value float
 */
function cpdf_setgray($pdfdoc, $value) {}

/**
 * Sets filling color to gray value 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  value float
 */
function cpdf_setgray_fill($pdfdoc, $value) {}

/**
 * Sets drawing color to gray value 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  value float
 */
function cpdf_setgray_stroke($pdfdoc, $value) {}

/**
 * Sets linecap parameter 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  value int
 */
function cpdf_setlinecap($pdfdoc, $value) {}

/**
 * Sets linejoin parameter 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  value int
 */
function cpdf_setlinejoin($pdfdoc, $value) {}

/**
 * Sets line width 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  width float
 */
function cpdf_setlinewidth($pdfdoc, $width) {}

/**
 * Sets miter limit 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  value float
 */
function cpdf_setmiterlimit($pdfdoc, $value) {}

/**
 * Sets drawing and filling color to RGB color value 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  red float
 * @param  green float
 * @param  blue float
 */
function cpdf_setrgbcolor($pdfdoc, $red, $green, $blue) {}

/**
 * Sets filling color to rgb color value 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  red float
 * @param  green float
 * @param  blue float
 */
function cpdf_setrgbcolor_fill($pdfdoc, $red, $green, $blue) {}

/**
 * Sets drawing color to RGB color value 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  red float
 * @param  green float
 * @param  blue float
 */
function cpdf_setrgbcolor_stroke($pdfdoc, $red, $green, $blue) {}

/**
 * Output text at current position 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  text string
 */
function cpdf_show($pdfdoc, $text) {}

/**
 * Output text at position 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  text string
 * @param  x_koor float
 * @param  y_koor float
 * @param  mode int[optional]
 */
function cpdf_show_xy($pdfdoc, $text, $x_koor, $y_koor, $mode = null) {}

/**
 * Returns width of text in current font 
 *
 * @return float
 * @param  pdfdoc int
 * @param  text string
 */
function cpdf_stringwidth($pdfdoc, $text) {}

/**
 * Draws line along path path 
 *
 * @return bool
 * @param  pdfdoc int
 */
function cpdf_stroke($pdfdoc) {}

/**
 * Outputs text 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  text string
 * @param  x_koor float[optional]
 * @param  y_koor float
 * @param  mode int[optional]
 * @param  orientation float[optional]
 * @param  alignmode int[optional]
 */
function cpdf_text($pdfdoc, $text, $x_koor = null, $y_koor, $mode = null, $orientation = null, $alignmode = null) {}

/**
 * Sets origin of coordinate system 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  x float
 * @param  y float
 */
function cpdf_translate($pdfdoc, $x, $y) {}

/**
 * Performs an obscure check with the given password 
 *
 * @return bool
 * @param  dictionary resource[optional]
 * @param  password string
 */
function crack_check($dictionary = null, $password) {}

/**
 * Closes an open cracklib dictionary 
 *
 * @return bool
 * @param  dictionary resource[optional]
 */
function crack_closedict($dictionary = null) {}

/**
 * Returns the message from the last obscure check 
 *
 * @return string
 */
function crack_getlastmessage() {}

/**
 * Opens a new cracklib dictionary 
 *
 * @return resource
 * @param  dictionary string
 */
function crack_opendict($dictionary) {}

/**
 * Calculate the crc32 polynomial of a string 
 *
 * @return string
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
 * @return bool
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
 * Set an option for a CURL transfer 
 *
 * @return bool
 * @param  ch resource
 * @param  option string
 * @param  value mixed
 */
function curl_setopt($ch, $option, $value) {}

/**
 * Return cURL version information. 
 *
 * @return string
 */
function curl_version() {}

/**
 * Return the element currently pointed to by the internal array pointer 
 *
 * @return mixed
 * @param  array_arg array
 */
function current($array_arg) {}

/**
 * Authenticate agaings a Cyrus IMAP server 
 *
 * @return void
 * @param  connection resource
 * @param  mechlist string[optional]
 * @param  service string[optional]
 * @param  user string[optional]
 * @param  minssf int[optional]
 * @param  maxssf int[optional]
 */
function cyrus_authenticate($connection, $mechlist = null, $service = null, $user = null, $minssf = null, $maxssf = null) {}

/**
 * Bind callbacks to a Cyrus IMAP connection 
 *
 * @return bool
 * @param  connection resource
 * @param  callbacks array
 */
function cyrus_bind($connection, $callbacks) {}

/**
 * Close connection to a cyrus server 
 *
 * @return bool
 * @param  connection resource
 */
function cyrus_close($connection) {}

/**
 * Connect to a Cyrus IMAP server 
 *
 * @return resource
 * @param  host string[optional]
 * @param  port string[optional]
 * @param  flags int[optional]
 */
function cyrus_connect($host = null, $port = null, $flags = null) {}

/**
 * Send a query to a Cyrus IMAP server 
 *
 * @return bool
 * @param  connection resource
 * @param  query string
 */
function cyrus_query($connection, $query) {}

/**
 * Unbind ... 
 *
 * @return bool
 * @param  connection resource
 * @param  trigger_name string
 */
function cyrus_unbind($connection, $trigger_name) {}

/**
 * Format a local time/date 
 *
 * @return string
 * @param  format string
 * @param  timestamp int[optional]
 */
function date($format, $timestamp = null) {}

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
 * @param  handle int
 */
function dba_delete($key, $handle) {}

/**
 * Checks, if the specified key exists 
 *
 * @return bool
 * @param  key string
 * @param  handle int
 */
function dba_exists($key, $handle) {}

/**
 * Fetches the data associated with key 
 *
 * @return string
 * @param  key string
 * @param  skip int[optional]
 * @param  handle int
 */
function dba_fetch($key, $skip = null, $handle) {}

/**
 * Resets the internal key pointer and returns the first key 
 *
 * @return string
 * @param  handle int
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
 * @param  handle int
 */
function dba_insert($key, $value, $handle) {}

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
 * @param  handle int
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
 * @param  handle int
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
 * @param  handle int
 */
function dba_replace($key, $value, $handle) {}

/**
 * Synchronizes database 
 *
 * @return bool
 * @param  handle int
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
 * @return bool
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
 * Describes the dbm-compatible library being used 
 *
 * @return string
 */
function dblist() {}

/**
 * Closes a dbm database 
 *
 * @return bool
 * @param  dbm_identifier int
 */
function dbmclose($dbm_identifier) {}

/**
 * Deletes the value for a key from a dbm database 
 *
 * @return int
 * @param  dbm_identifier int
 * @param  key string
 */
function dbmdelete($dbm_identifier, $key) {}

/**
 * Tells if a value exists for a key in a dbm database 
 *
 * @return int
 * @param  dbm_identifier int
 * @param  key string
 */
function dbmexists($dbm_identifier, $key) {}

/**
 * Fetches a value for a key from a dbm database 
 *
 * @return string
 * @param  dbm_identifier int
 * @param  key string
 */
function dbmfetch($dbm_identifier, $key) {}

/**
 * Retrieves the first key from a dbm database 
 *
 * @return string
 * @param  dbm_identifier int
 */
function dbmfirstkey($dbm_identifier) {}

/**
 * Inserts a value for a key in a dbm database 
 *
 * @return int
 * @param  dbm_identifier int
 * @param  key string
 * @param  value string
 */
function dbminsert($dbm_identifier, $key, $value) {}

/**
 * Retrieves the next key from a dbm database 
 *
 * @return string
 * @param  dbm_identifier int
 * @param  key string
 */
function dbmnextkey($dbm_identifier, $key) {}

/**
 * Opens a dbm database 
 *
 * @return int
 * @param  filename string
 * @param  mode string
 */
function dbmopen($filename, $mode) {}

/**
 * Replaces the value for a key in a dbm database 
 *
 * @return int
 * @param  dbm_identifier int
 * @param  key string
 * @param  value string
 */
function dbmreplace($dbm_identifier, $key, $value) {}

/**
 * 
 *
 * @return bool
 * @param  dbx_link dbx_link_object
 */
function dbx_close($dbx_link) {}

/**
 * Returns row_y[columnname] - row_x[columnname], converted to -1, 0 or 1 
 *
 * @return int
 * @param  row_x array
 * @param  row_y array
 * @param  columnname string
 * @param  flags int[optional]
 */
function dbx_compare($row_x, $row_y, $columnname, $flags = null) {}

/**
 * Returns a dbx_link_object on success and returns 0 on failure 
 *
 * @return dbx_link_object
 * @param  module_name string
 * @param  host string
 * @param  db string
 * @param  username string
 * @param  password string
 * @param  persistent bool[optional]
 */
function dbx_connect($module_name, $host, $db, $username, $password, $persistent = null) {}

/**
 * 
 *
 * @return void
 * @param  dbx_link dbx_link_object
 */
function dbx_error($dbx_link) {}

/**
 * 
 *
 * @return string
 * @param  dbx_link dbx_link_object
 * @param  sz string
 */
function dbx_esc($dbx_link, $sz) {}

/**
 * Returns a dbx_link_object on success and returns 0 on failure 
 *
 * @return dbx_result_object
 * @param  dbx_link dbx_link_object
 * @param  sql_statement string
 * @param  flags long[optional]
 */
function dbx_query($dbx_link, $sql_statement, $flags = null) {}

/**
 * Returns 0 on failure, 1 on success 
 *
 * @return int
 * @param  dbx_result object
 * @param  compare_function_name string
 */
function dbx_sort($dbx_result, $compare_function_name) {}

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
 * 
 *
 * @return void
 * @param  obj object
 * @param  class string[optional]
 */
function deaggregate($obj, $class = null) {}

/**
 * Prints out a backtrace 
 *
 * @return void
 */
function debug_backtrace() {}

/**
 * Dumps a string representation of an internal zend value to output. 
 *
 * @return void
 * @param  var mixed
 */
function debug_zval_dump($var) {}

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
 * @param  case_sensitive=true unknown
 */
function define($constant_name, $value, $case_sensitive=true) {}

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
 * Close the file descriptor given by fd 
 *
 * @return void
 * @param  fd resource
 */
function dio_close($fd) {}

/**
 * Perform a c library fcntl on fd 
 *
 * @return mixed
 * @param  fd resource
 * @param  cmd int
 * @param  arg mixed
 */
function dio_fcntl($fd, $cmd, $arg) {}

/**
 * Open a new filename with specified permissions of flags and creation permissions of mode 
 *
 * @return resource
 * @param  filename string
 * @param  flags int
 * @param  mode int
 */
function dio_open($filename, $flags, $mode) {}

/**
 * Read n bytes from fd and return them, if n is not specified, read 1k 
 *
 * @return string
 * @param  fd resource
 * @param  n int
 */
function dio_read($fd, $n) {}

/**
 * Seek to pos on fd from whence 
 *
 * @return int
 * @param  fd resource
 * @param  pos int
 * @param  whence int
 */
function dio_seek($fd, $pos, $whence) {}

/**
 * Get stat information about the file descriptor fd 
 *
 * @return array
 * @param  fd resource
 */
function dio_stat($fd) {}

/**
 * Perform a c library tcsetattr on fd 
 *
 * @return mixed
 * @param  fd resource
 * @param  args array
 */
function dio_tcsetattr($fd, $args) {}

/**
 * Truncate file descriptor fd to offset bytes 
 *
 * @return bool
 * @param  fd resource
 * @param  offset int
 */
function dio_truncate($fd, $offset) {}

/**
 * Write data to fd with optional truncation at length 
 *
 * @return int
 * @param  fd resource
 * @param  data string
 * @param  len int
 */
function dio_write($fd, $data, $len) {}

/**
 * Directory class with properties, handle and class and methods read, rewind and close 
 *
 * @return object
 * @param  directory string
 */
function dir($directory) {}

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
 * Adds root node to document 
 *
 * @return object
 * @param  name string
 */
function domxml_add_root($name) {}


/**
 * Set value of attribute 
 *
 * @return bool
 * @param  content string
 */
function domxml_attr_set_value($content) {}



/**
 * Returns list of attributes of node 
 *
 * @return array
 */
function domxml_attributes() {}


/**
 * Returns list of children nodes 
 *
 * @return array
 */
function domxml_children() {}















/**
 * Returns root node of document 
 *
 * @return object
 * @param  domnode int
 */
function domxml_doc_get_root($domnode) {}











/**
 * Returns DTD of document 
 *
 * @return object
 */
function domxml_dtd() {}




/**
 * Dumps document into string and optionally formats it 
 *
 * @return string
 * @param  doc_handle object
 * @param  format int[optional]
 * @param  encoding unknown[optional]
 */
function domxml_dumpmem($doc_handle, $format = null, $encoding = null) {}









/**
 * Constructor of DomElement 
 *
 * @return object
 * @param  name string
 */
function domxml_element($name) {}

/**
 * Returns value of given attribute 
 *
 * @return string
 * @param  attrname string
 */
function domxml_get_attribute($attrname) {}

/**
 * Returns value of given attribute 
 *
 * @return string
 * @param  attrname string
 */
function domxml_getattr($attrname) {}



/**
 * Adds child node to parent node 
 *
 * @return object
 * @param  name string
 * @param  content string
 */
function domxml_new_child($name, $content) {}

/**
 * Creates new xmldoc 
 *
 * @return object
 * @param  version string
 */
function domxml_new_doc($version) {}

/**
 * Creates new xmldoc 
 *
 * @return object
 * @param  version string
 */
function domxml_new_xmldoc($version) {}

































/**
 * Creates DOM object of XML document in file 
 *
 * @return object
 * @param  filename string
 * @param  mode int
 * @param  error array
 */
function domxml_open_file($filename, $mode, $error) {}

/**
 * Creates DOM object of XML document 
 *
 * @return object
 * @param  xmldoc string
 * @param  mode int
 * @param  error array
 */
function domxml_open_mem($xmldoc, $mode, $error) {}

/**
 * Creates new xmlparser 
 *
 * @return object
 * @param  buf string[optional]
 * @param  filename string
 */
function domxml_parser($buf = null, $filename) {}

















/**
 * Returns root node of document 
 *
 * @return object
 * @param  domnode int
 */
function domxml_root($domnode) {}

/**
 * Sets value of given attribute 
 *
 * @return bool
 * @param  attrname string
 * @param  value string
 */
function domxml_set_attribute($attrname, $value) {}

/**
 * Sets root node of document 
 *
 * @return bool
 * @param  domnode int
 */
function domxml_set_root($domnode) {}

/**
 * Sets value of given attribute 
 *
 * @return bool
 * @param  attrname string
 * @param  value string
 */
function domxml_setattr($attrname, $value) {}

/**
 * Set and return the previous value for default entity support 
 *
 * @return bool
 * @param  enable bool
 */
function domxml_substitute_entities_default($enable) {}

/**
 * Unity function for testing 
 *
 * @return int
 * @param  id int
 */
function domxml_test($id) {}

/**
 * Deletes the node from tree, but not from memory
 *
 * @return void
 * @param  node object[optional]
 */
function domxml_unlink_node($node = null) {}

/**
 * Get XML library version 
 *
 * @return string
 */
function domxml_version() {}

/**
 * Creates a tree of PHP objects from an XML document 
 *
 * @return object
 * @param  xmltree string
 */
function domxml_xmltree($xmltree) {}




/**
 * Creates XSLT Stylesheet object from string 
 *
 * @return object
 * @param  xsltstylesheet string
 */
function domxml_xslt_stylesheet($xsltstylesheet) {}

/**
 * Creates XSLT Stylesheet object from DOM Document object 
 *
 * @return object
 * @param  xmldoc object
 */
function domxml_xslt_stylesheet_doc($xmldoc) {}

/**
 * Creates XSLT Stylesheet object from file 
 *
 * @return object
 * @param  filename string
 */
function domxml_xslt_stylesheet_file($filename) {}

/**
 * Get XSLT library version 
 *
 * @return string
 */
function domxml_xslt_version() {}

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
 * Execute an external program 
 *
 * @return string
 * @param  command string
 * @param  output array[optional]
 * @param  return_value int[optional]
 */
function exec($command, $output = null, $return_value = null) {}

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
 * @return array|false
 * @param  filename string
 * @param  sections_needed unknown[optional]
 * @param  sub_arrays unknown[optional]
 * @param  read_thumbnail unknown
 */
function exif_read_data($filename, $sections_needed = null, $sub_arrays = null, $read_thumbnail) {}

/**
 * Get headername for index or false if not defined 
 *
 * @return string|false
 * @param  index unknown
 */
function exif_tagname($index) {}

/**
 * Reads the embedded thumbnail 
 *
 * @return string|false
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
 * Splits a string on string separator and return array of components 
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
 * @return string
 * @param  blob_handle string
 * @param  link_identifier resource[optional]
 */
function fbsql_blob_size($blob_handle, $link_identifier = null) {}

/**
 * Change the user for a session 
 *
 * @return int
 * @param  user string
 * @param  password string
 * @param  database string[optional]
 * @param  link_identifier resource[optional]
 */
function fbsql_change_user($user, $password, $database = null, $link_identifier = null) {}

/**
 * Get the size of a CLOB identified by clob_handle 
 *
 * @return string
 * @param  clob_handle string
 * @param  link_identifier resource[optional]
 */
function fbsql_clob_size($clob_handle, $link_identifier = null) {}

/**
 * Close a connection to a database server 
 *
 * @return int
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
 * @return int
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
 * @return int
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
 * @return object
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
 * @return string
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
 * @return int
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
 */
function fbsql_query($query, $link_identifier = null) {}

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
 * @return int
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
 * Sets the mode for how LOB data re retreived (actual data or a handle) 
 *
 * @return bool
 * @param  result resource
 * @param  lob_mode int
 */
function fbsql_set_lob_mode($result, $lob_mode) {}

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
 */
function fbsql_start_db($database_name, $link_identifier = null) {}

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
 * @return bool
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
 * @return string
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
 * @return mixed
 * @param  fdfdoc resource
 * @param  filename string[optional]
 */
function fdf_save($fdfdoc, $filename = null) {}

/**
 * Returns the FDF file as a string 
 *
 * @return mixed
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
 * @param  length int
 * @param  delimiter string[optional]
 * @param  enclosure string[optional]
 */
function fgetcsv($fp, $length, $delimiter = null, $enclosure = null) {}

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
 * @param  length int
 * @param  allowable_tags string[optional]
 */
function fgetss($fp, $length, $allowable_tags = null) {}

/**
 * Read entire file into an array 
 *
 * @return array
 * @param  filename string
 * @param  use_include_path bool[optional]
 */
function file($filename, $use_include_path = null) {}

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
 */
function file_get_contents($filename, $use_include_path = null) {}

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
 * Returns an array containing information about the specified charset 
 *
 * @return array
 * @param  charset int
 */
function fribidi_charset_info($charset) {}

/**
 * Returns an array containing available charsets 
 *
 * @return array
 */
function fribidi_get_charsets() {}

/**
 * Convert a logical string to a visual one 
 *
 * @return string
 * @param  logical_str string
 * @param  direction long
 * @param  charset long
 */
function fribidi_log2vis($logical_str, $direction, $charset) {}

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
 * @return int
 * @param  hostname string
 * @param  port int
 * @param  errno int[optional]
 * @param  errstr string[optional]
 * @param  timeout float[optional]
 * @param  context resource[optional]
 */
function fsockopen($hostname, $port, $errno = null, $errstr = null, $timeout = null, $context = null) {}

/**
 * Stat() on a filehandle 
 *
 * @return int
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
 * Closes the FTP stream 
 *
 * @return void
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
 * @return bool
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
 * @return bool
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
 * @return bool
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
 * @return void
 * @param  stream resource
 */
function ftp_quit($stream) {}

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
 * @return int
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
 * Get information about the capabilities of a browser 
 *
 * @return object
 * @param  browser_name string
 */
function get_browser($browser_name) {}

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
 * @param  object object
 */
function get_class($object) {}

/**
 * Returns an array of method names for class or class instance. 
 *
 * @return array
 * @param  class mixed
 */
function get_class_methods($class) {}

/**
 * Returns an array of default properties of the class 
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
 * Retrieves the parent class name for object or class. 
 *
 * @return string
 * @param  object mixed
 */
function get_parent_class($object) {}

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
 * @return mixed
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
 * @return int
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
 * @return array
 */
function gettimeofday() {}

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
 * Format a GMT/UTC date/time 
 *
 * @return string
 * @param  format string
 * @param  timestamp int[optional]
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
 * @param  length int
 * @param  allowable_tags string[optional]
 */
function gzgetss($fp, $length, $allowable_tags = null) {}

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
 * @return int
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
 * Sends a raw HTTP header 
 *
 * @return void
 * @param  header string
 * @param  replace bool[optional]
 * @param  http_response_code int[optional]
 */
function header($header, $replace = null, $http_response_code = null) {}

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
 * @return int
 * @param  hexadecimal_number string
 */
function hexdec($hexadecimal_number) {}

/**
 * Syntax highlight a source file 
 *
 * @return bool
 * @param  file_name string
 * @param  return bool[optional]
 */
function highlight_file($file_name, $return = null) {}

/**
 * Syntax highlight a string or optionally return it 
 *
 * @return bool
 * @param  string string
 * @param  return bool[optional]
 */
function highlight_string($string, $return = null) {}

/**
 * Creates DOM object of HTML document 
 *
 * @return object
 * @param  html_doc string
 * @param  from_file bool[optional]
 */
function html_doc($html_doc, $from_file = null) {}

/**
 * Creates DOM object of HTML document in file 
 *
 * @return object
 * @param  filename string
 */
function html_doc_file($filename) {}

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
 * Convert special characters to HTML entities 
 *
 * @return string
 * @param  string string
 * @param  quote_style int[optional]
 * @param  charset string[optional]
 */
function htmlspecialchars($string, $quote_style = null, $charset = null) {}

/**
 * Returns object record of object array 
 *
 * @return string
 * @param  objarr array
 */
function hw_array2objrec($objarr) {}

/**
 * Changes attributes of an object (obsolete) 
 *
 * @return void
 * @param  link int
 * @param  objid int
 * @param  attributes array
 */
function hw_changeobject($link, $objid, $attributes) {}

/**
 * Returns array of children object ids 
 *
 * @return array
 * @param  link int
 * @param  objid int
 */
function hw_children($link, $objid) {}

/**
 * Returns array of children object records 
 *
 * @return array
 * @param  link int
 * @param  objid int
 */
function hw_childrenobj($link, $objid) {}

/**
 * Close connection to Hyperwave server 
 *
 * @return void
 * @param  link int
 */
function hw_close($link) {}

/**
 * Connect to the Hyperwave server 
 *
 * @return int
 * @param  host string
 * @param  port_ int
 * @param  password string[optional]
 */
function hw_connect($host, $port_, $password = null) {}

/**
 * Prints information about the connection to Hyperwave server 
 *
 * @return void
 * @param  link int
 */
function hw_connection_info($link) {}

/**
 * Copies object 
 *
 * @return void
 * @param  link int
 * @param  objrec array
 * @param  dest int
 */
function hw_cp($link, $objrec, $dest) {}

/**
 * Deletes object 
 *
 * @return void
 * @param  link int
 * @param  objid int
 */
function hw_deleteobject($link, $objid) {}

/**
 * Returns objid of document belonging to anchorid 
 *
 * @return int
 * @param  link int
 * @param  anchorid int
 */
function hw_docbyanchor($link, $anchorid) {}

/**
 * Returns object record of document belonging to anchorid 
 *
 * @return array
 * @param  link int
 * @param  anchorid int
 */
function hw_docbyanchorobj($link, $anchorid) {}

/**
 * Returns object record of document 
 *
 * @return string
 * @param  doc hwdoc
 */
function hw_document_attributes($doc) {}

/**
 * Return bodytag prefixed by prefix 
 *
 * @return string
 * @param  doc hwdoc
 * @param  prefix string[optional]
 */
function hw_document_bodytag($doc, $prefix = null) {}

/**
 * Returns content of document 
 *
 * @return string
 * @param  doc hwdoc
 */
function hw_document_content($doc) {}

/**
 * Sets/replaces content of document 
 *
 * @return int
 * @param  doc hwdoc
 * @param  content string
 */
function hw_document_setcontent($doc, $content) {}

/**
 * Returns size of document 
 *
 * @return int
 * @param  doc hwdoc
 */
function hw_document_size($doc) {}

/**
 * An alias for hw_document_attributes 
 *
 * @return string
 * @param  doc hwdoc
 */
function hw_documentattributes($doc) {}

/**
 * An alias for hw_document_bodytag 
 *
 * @return string
 * @param  doc hwdoc
 * @param  prefix string[optional]
 */
function hw_documentbodytag($doc, $prefix = null) {}

/**
 * An alias for hw_document_size 
 *
 * @return int
 * @param  doc hwdoc
 */
function hw_documentsize($doc) {}

/**
 * Hyperwave dummy function 
 *
 * @return string
 * @param  link int
 * @param  id int
 * @param  msgid int
 */
function hw_dummy($link, $id, $msgid) {}

/**
 * Modifies text document 
 *
 * @return void
 * @param  link int
 * @param  doc hwdoc
 */
function hw_edittext($link, $doc) {}

/**
 * Returns last error number 
 *
 * @return int
 * @param  link int
 */
function hw_error($link) {}

/**
 * Returns last error message 
 *
 * @return string
 * @param  link int
 */
function hw_errormsg($link) {}

/**
 * Frees memory of document 
 *
 * @return void
 * @param  doc hwdoc
 */
function hw_free_document($doc) {}

/**
 * Return all anchors of object 
 *
 * @return array
 * @param  link int
 * @param  objid int
 */
function hw_getanchors($link, $objid) {}

/**
 * Return all object records of anchors of object 
 *
 * @return array
 * @param  link int
 * @param  objid int
 */
function hw_getanchorsobj($link, $objid) {}

/**
 * Returns object record and locks object 
 *
 * @return string
 * @param  link int
 * @param  objid int
 */
function hw_getandlock($link, $objid) {}

/**
 * Returns the output of a CGI script 
 *
 * @return hwdoc
 * @param  link int
 * @param  objid int
 */
function hw_getcgi($link, $objid) {}

/**
 * Returns array of child collection object ids 
 *
 * @return array
 * @param  link int
 * @param  objid int
 */
function hw_getchildcoll($link, $objid) {}

/**
 * Returns array of child collection object records 
 *
 * @return array
 * @param  link int
 * @param  objid int
 */
function hw_getchildcollobj($link, $objid) {}

/**
 * Returns all children ids which are documents 
 *
 * @return array
 * @param  link int
 * @param  objid int
 */
function hw_getchilddoccoll($link, $objid) {}

/**
 * Returns all children object records which are documents 
 *
 * @return array
 * @param  link int
 * @param  objid int
 */
function hw_getchilddoccollobj($link, $objid) {}

/**
 * Returns object record  
 *
 * @return string
 * @param  link int
 * @param  objid int
 * @param  query string[optional]
 */
function hw_getobject($link, $objid, $query = null) {}

/**
 * Search for query as fulltext and return maxhits objids 
 *
 * @return array
 * @param  link int
 * @param  query string
 * @param  maxhits int
 */
function hw_getobjectbyftquery($link, $query, $maxhits) {}

/**
 * Search for fulltext query in collection and return maxhits objids 
 *
 * @return array
 * @param  link int
 * @param  collid int
 * @param  query string
 * @param  maxhits int
 */
function hw_getobjectbyftquerycoll($link, $collid, $query, $maxhits) {}

/**
 * Search for fulltext query in collection and return maxhits object records 
 *
 * @return array
 * @param  link int
 * @param  collid int
 * @param  query string
 * @param  maxhits int
 */
function hw_getobjectbyftquerycollobj($link, $collid, $query, $maxhits) {}

/**
 * Search for query as fulltext and return maxhits object records 
 *
 * @return array
 * @param  link int
 * @param  query string
 * @param  maxhits int
 */
function hw_getobjectbyftqueryobj($link, $query, $maxhits) {}

/**
 * Search for query and return maxhits objids 
 *
 * @return array
 * @param  link int
 * @param  query string
 * @param  maxhits int
 */
function hw_getobjectbyquery($link, $query, $maxhits) {}

/**
 * Search for query in collection and return maxhits objids 
 *
 * @return array
 * @param  link int
 * @param  collid int
 * @param  query string
 * @param  maxhits int
 */
function hw_getobjectbyquerycoll($link, $collid, $query, $maxhits) {}

/**
 * Search for query in collection and return maxhits object records 
 *
 * @return array
 * @param  link int
 * @param  collid int
 * @param  query string
 * @param  maxhits int
 */
function hw_getobjectbyquerycollobj($link, $collid, $query, $maxhits) {}

/**
 * Search for query and return maxhits object records 
 *
 * @return array
 * @param  link int
 * @param  query string
 * @param  maxhits int
 */
function hw_getobjectbyqueryobj($link, $query, $maxhits) {}

/**
 * Returns array of parent object ids 
 *
 * @return array
 * @param  link int
 * @param  objid int
 */
function hw_getparents($link, $objid) {}

/**
 * Returns array of parent object records 
 *
 * @return array
 * @param  link int
 * @param  objid int
 */
function hw_getparentsobj($link, $objid) {}

/**
 * Get link from source to dest relative to rootid 
 *
 * @return string
 * @param  link int
 * @param  rootid int
 * @param  sourceid int
 * @param  destid int
 */
function hw_getrellink($link, $rootid, $sourceid, $destid) {}

/**
 * Returns the content of a remote document 
 *
 * @return int
 * @param  link int
 * @param  objid int
 */
function hw_getremote($link, $objid) {}

/**
 * Returns the remote document or an array of object records 
 *
 * @return [array|int]
 * @param  link int
 * @param  objrec string
 */
function hw_getremotechildren($link, $objrec) {}

/**
 * Returns object id of source docuent by destination anchor 
 *
 * @return int
 * @param  link int
 * @param  destid int
 */
function hw_getsrcbydestobj($link, $destid) {}

/**
 * Returns text document. Links are relative to rootid if given 
 *
 * @return hwdoc
 * @param  link int
 * @param  objid int
 * @param  rootid int[optional]
 */
function hw_gettext($link, $objid, $rootid = null) {}

/**
 * Returns the current user name 
 *
 * @return string
 * @param  link int
 */
function hw_getusername($link) {}

/**
 * Identifies at Hyperwave server 
 *
 * @return void
 * @param  link int
 * @param  username string
 * @param  password string
 */
function hw_identify($link, $username, $password) {}

/**
 * Returns object ids which are in collections 
 *
 * @return array
 * @param  link int
 * @param  objids array
 * @param  collids array
 * @param  para int
 */
function hw_incollections($link, $objids, $collids, $para) {}

/**
 * Outputs info string 
 *
 * @return void
 * @param  link int
 */
function hw_info($link) {}

/**
 * Inserts collection 
 *
 * @return void
 * @param  link int
 * @param  parentid int
 * @param  objarr array
 */
function hw_inscoll($link, $parentid, $objarr) {}

/**
 * Inserts document 
 *
 * @return void
 * @param  link int
 * @param  parentid int
 * @param  objrec string
 * @param  text string[optional]
 */
function hw_insdoc($link, $parentid, $objrec, $text = null) {}

/**
 * Inserts only anchors into text 
 *
 * @return string
 * @param  hwdoc int
 * @param  anchorecs array
 * @param  dest array
 * @param  urlprefixes array[optional]
 */
function hw_insertanchors($hwdoc, $anchorecs, $dest, $urlprefixes = null) {}

/**
 * Insert new document 
 *
 * @return void
 * @param  link int
 * @param  parentid int
 * @param  doc hwdoc
 */
function hw_insertdocument($link, $parentid, $doc) {}

/**
 * Inserts an object 
 *
 * @return int
 * @param  link int
 * @param  objrec string
 * @param  parms string
 */
function hw_insertobject($link, $objrec, $parms) {}

/**
 * Returns virtual object id of document on remote Hyperwave server 
 *
 * @return int
 * @param  link int
 * @param  serverid int
 * @param  destid int
 */
function hw_mapid($link, $serverid, $destid) {}

/**
 * Modifies attributes of an object 
 *
 * @return void
 * @param  link int
 * @param  objid int
 * @param  remattributes array
 * @param  addattributes array
 * @param  mode int[optional]
 */
function hw_modifyobject($link, $objid, $remattributes, $addattributes, $mode = null) {}

/**
 * Moves object 
 *
 * @return void
 * @param  link int
 * @param  objrec array
 * @param  from int
 * @param  dest int
 */
function hw_mv($link, $objrec, $from, $dest) {}

/**
 * Create a new document 
 *
 * @return hwdoc
 * @param  objrec string
 * @param  data string
 * @param  size int
 */
function hw_new_document($objrec, $data, $size) {}

/**
 * Create a new document from a file 
 *
 * @return hwdoc
 * @param  objrec string
 * @param  filename string
 */
function hw_new_document_from_file($objrec, $filename) {}

/**
 * Returns object array of object record 
 *
 * @return array
 * @param  objrec string
 * @param  format array[optional]
 */
function hw_objrec2array($objrec, $format = null) {}

/**
 * Prints document 
 *
 * @return void
 * @param  doc hwdoc
 */
function hw_output_document($doc) {}

/**
 * An alias for hw_output_document 
 *
 * @return void
 * @param  doc hwdoc
 */
function hw_outputdocument($doc) {}

/**
 * Connect to the Hyperwave server persistent 
 *
 * @return int
 * @param  host string
 * @param  port int
 * @param  username string[optional]
 * @param  password string[optional]
 */
function hw_pconnect($host, $port, $username = null, $password = null) {}

/**
 * Returns output of CGI script 
 *
 * @return hwdoc
 * @param  link int
 * @param  objid int
 */
function hw_pipecgi($link, $objid) {}

/**
 * Returns document 
 *
 * @return hwdoc
 * @param  link int
 * @param  objid int
 */
function hw_pipedocument($link, $objid) {}

/**
 * Returns object id of root collection 
 *
 * @return int
 */
function hw_root() {}

/**
 * Set the id to which links are calculated 
 *
 * @return void
 * @param  link int
 * @param  rootid int
 */
function hw_setlinkroot($link, $rootid) {}

/**
 * Returns status string 
 *
 * @return string
 * @param  link int
 */
function hw_stat($link) {}

/**
 * Unlocks object 
 *
 * @return void
 * @param  link int
 * @param  objid int
 */
function hw_unlock($link, $objid) {}

/**
 * Returns names and info of users loged in 
 *
 * @return array
 * @param  link int
 */
function hw_who($link) {}

/**
 * Returns sqrt(num1*num1 + num2*num2) 
 *
 * @return float
 * @param  num1 float
 * @param  num2 float
 */
function hypot($num1, $num2) {}

/**
 * Returns converted string in desired encoding 
 *
 * @return string
 * @param  str string
 * @param  to_encoding string
 * @param  from_encoding mixed[optional]
 */
function i18n_convert($str, $to_encoding, $from_encoding = null) {}

/**
 * Encodings of the given string is returned (as a string) 
 *
 * @return string
 * @param  str string
 * @param  encoding_list mixed[optional]
 */
function i18n_discover_encoding($str, $encoding_list = null) {}

/**
 * Returns the input encoding 
 *
 * @return mixed
 * @param  type string[optional]
 */
function i18n_http_input($type = null) {}

/**
 * Sets the current output_encoding or returns the current output_encoding as a string 
 *
 * @return string
 * @param  encoding string[optional]
 */
function i18n_http_output($encoding = null) {}

/**
 * Sets the current internal encoding or Returns the current internal encoding as a string 
 *
 * @return string
 * @param  encoding string[optional]
 */
function i18n_internal_encoding($encoding = null) {}

/**
 * Conversion between full-width character and half-width character (Japanese) 
 *
 * @return string
 * @param  str string
 * @param  option string[optional]
 * @param  encoding string[optional]
 */
function i18n_ja_jp_hantozen($str, $option = null, $encoding = null) {}

/**
 * Decodes the MIME "encoded-word" in the string 
 *
 * @return string
 * @param  string string
 */
function i18n_mime_header_decode($string) {}

/**
 * Converts the string to MIME "encoded-word" in the format of =?charset?(B|Q)?encoded_string?= 
 *
 * @return string
 * @param  str string
 * @param  charset string[optional]
 * @param  transfer_encoding string[optional]
 * @param  linefeed string[optional]
 */
function i18n_mime_header_encode($str, $charset = null, $transfer_encoding = null, $linefeed = null) {}

/**
 * Add an user to security database (only for IB6 or later) 
 *
 * @return bool
 * @param  server string
 * @param  dba_user_name string
 * @param  dba_password string
 * @param  user_name string
 * @param  password string
 * @param  first_name string[optional]
 * @param  middle_name string[optional]
 * @param  last_name string[optional]
 */
function ibase_add_user($server, $dba_user_name, $dba_password, $user_name, $password, $first_name = null, $middle_name = null, $last_name = null) {}

/**
 * Add data into created blob 
 *
 * @return bool
 * @param  blob_id int
 * @param  data string
 */
function ibase_blob_add($blob_id, $data) {}

/**
 * Cancel creating blob 
 *
 * @return bool
 * @param  blob_id int
 */
function ibase_blob_cancel($blob_id) {}

/**
 * Close blob 
 *
 * @return bool
 * @param  blob_id int
 */
function ibase_blob_close($blob_id) {}

/**
 * Create blob for adding data 
 *
 * @return int
 * @param  link_identifier resource[optional]
 */
function ibase_blob_create($link_identifier = null) {}

/**
 * Output blob contents to browser 
 *
 * @return bool
 * @param  blob_id_str string
 */
function ibase_blob_echo($blob_id_str) {}

/**
 * Get len bytes data from open blob 
 *
 * @return string
 * @param  blob_id int
 * @param  len int
 */
function ibase_blob_get($blob_id, $len) {}

/**
 * Create blob, copy file in it, and close it 
 *
 * @return string
 * @param  link_identifier resource[optional]
 * @param  file_id int
 */
function ibase_blob_import($link_identifier = null, $file_id) {}

/**
 * Return blob length and other useful info 
 *
 * @return object
 * @param  blob_id_str string
 */
function ibase_blob_info($blob_id_str) {}

/**
 * Open blob for retriving data parts 
 *
 * @return int
 * @param  blob_id string
 */
function ibase_blob_open($blob_id) {}

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
 * Delete an user from security database (only for IB6 or later) 
 *
 * @return bool
 * @param  server string
 * @param  dba_user_name string
 * @param  dba_password string
 * @param  username string
 */
function ibase_delete_user($server, $dba_user_name, $dba_password, $username) {}

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
 * @param  bind_args int[optional]
 * @vararg ... int
 */
function ibase_execute($query, $bind_args = null) {}

/**
 * Fetch a row  from the results of a query 
 *
 * @return array
 * @param  result resource
 * @param  blob_flag int[optional]
 */
function ibase_fetch_assoc($result, $blob_flag = null) {}

/**
 * Fetch a object from the results of a query 
 *
 * @return object
 * @param  result resource
 * @param  blob_flag int[optional]
 */
function ibase_fetch_object($result, $blob_flag = null) {}

/**
 * Fetch a row  from the results of a query 
 *
 * @return array
 * @param  result resource
 * @param  blob_flag int[optional]
 */
function ibase_fetch_row($result, $blob_flag = null) {}

/**
 * Get information about a field 
 *
 * @return array
 * @param  result resource
 * @param  field_number int
 */
function ibase_field_info($result, $field_number) {}

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
 * Modify an user in security database (only for IB6 or later) 
 *
 * @return bool
 * @param  server string
 * @param  dba_user_name string
 * @param  dba_password string
 * @param  user_name string
 * @param  password string
 * @param  first_name string[optional]
 * @param  middle_name string[optional]
 * @param  last_name string[optional]
 */
function ibase_modify_user($server, $dba_user_name, $dba_password, $user_name, $password, $first_name = null, $middle_name = null, $last_name = null) {}

/**
 * Get the number of fields in result 
 *
 * @return int
 * @param  result resource
 */
function ibase_num_fields($result) {}

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
 * @param  query string[optional]
 * @param  bind_args int[optional]
 */
function ibase_query($link_identifier = null, $query = null, $bind_args = null) {}

/**
 * Rollback transaction 
 *
 * @return bool
 * @param  link_identifier resource
 */
function ibase_rollback($link_identifier) {}

/**
 * Sets the format of timestamp, date and time columns returned from queries 
 *
 * @return int
 * @param  format string
 */
function ibase_timefmt($format) {}

/**
 * Start transaction 
 *
 * @return resource
 * @param  trans_args int[optional]
 * @param  link_identifier resource[optional]
 */
function ibase_trans($trans_args = null, $link_identifier = null) {}

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
 * @return array
 * @param  type string[optional]
 */
function iconv_get_encoding($type = null) {}

/**
 * Sets internal encoding and output encoding for ob_iconv_handler() 
 *
 * @return bool
 * @param  type string
 * @param  charset string
 */
function iconv_set_encoding($type, $charset) {}

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
 * @return int
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
 * @return int
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
 * @param  im int
 * @param  filename string[optional]
 * @param  threshold int[optional]
 */
function image2wbmp($im, $filename = null, $threshold = null) {}

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
 * @return void
 * @param  im resource
 * @param  on bool
 */
function imagealphablending($im, $on) {}

/**
 * Draw a partial ellipse 
 *
 * @return int
 * @param  im int
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
 * @return int
 * @param  im int
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
 * @return int
 * @param  im int
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
 * @param  im int
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
 * @param  im int
 * @param  x int
 * @param  y int
 */
function imagecolorat($im, $x, $y) {}

/**
 * Get the index of the closest color to the specified color 
 *
 * @return int
 * @param  im int
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
 * @param  im int
 * @param  red int
 * @param  green int
 * @param  blue int
 */
function imagecolorclosesthwb($im, $red, $green, $blue) {}

/**
 * De-allocate a color for an image 
 *
 * @return int
 * @param  im int
 * @param  index int
 */
function imagecolordeallocate($im, $index) {}

/**
 * Get the index of the specified color 
 *
 * @return int
 * @param  im int
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
 * @return void
 * @param  im1 resource
 * @param  im2 resource
 */
function imagecolormatch($im1, $im2) {}

/**
 * Get the index of the specified color or its closest possible alternative 
 *
 * @return int
 * @param  im int
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
 * @return int
 * @param  im int
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
 * @param  im int
 * @param  col int
 */
function imagecolorsforindex($im, $col) {}

/**
 * Find out the number of colors in an image's palette 
 *
 * @return int
 * @param  im int
 */
function imagecolorstotal($im) {}

/**
 * Define a color as transparent 
 *
 * @return int
 * @param  im int
 * @param  col int[optional]
 */
function imagecolortransparent($im, $col = null) {}

/**
 * Copy part of an image 
 *
 * @return int
 * @param  dst_im int
 * @param  src_im int
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
 * @return int
 * @param  src_im int
 * @param  dst_im int
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
 * @return int
 * @param  src_im int
 * @param  dst_im int
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
 * @return int
 * @param  dst_im int
 * @param  src_im int
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
 * @return int
 * @param  dst_im int
 * @param  src_im int
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
 * @return int
 * @param  x_size int
 * @param  y_size int
 */
function imagecreate($x_size, $y_size) {}

/**
 * Create a new image from GD file or URL 
 *
 * @return int
 * @param  filename string
 */
function imagecreatefromgd($filename) {}

/**
 * Create a new image from GD2 file or URL 
 *
 * @return int
 * @param  filename string
 */
function imagecreatefromgd2($filename) {}

/**
 * Create a new image from a given part of GD2 file or URL 
 *
 * @return int
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
 * @return int
 * @param  filename string
 */
function imagecreatefromgif($filename) {}

/**
 * Create a new image from JPEG file or URL 
 *
 * @return int
 * @param  filename string
 */
function imagecreatefromjpeg($filename) {}

/**
 * Create a new image from PNG file or URL 
 *
 * @return int
 * @param  filename string
 */
function imagecreatefrompng($filename) {}

/**
 * Create a new image from the image stream in the string 
 *
 * @return int
 * @param  image string
 */
function imagecreatefromstring($image) {}

/**
 * Create a new image from WBMP file or URL 
 *
 * @return int
 * @param  filename string
 */
function imagecreatefromwbmp($filename) {}

/**
 * Create a new image from XBM file or URL 
 *
 * @return int
 * @param  filename string
 */
function imagecreatefromxbm($filename) {}

/**
 * Create a new image from XPM file or URL 
 *
 * @return int
 * @param  filename string
 */
function imagecreatefromxpm($filename) {}

/**
 * Create a new true color image 
 *
 * @return int
 * @param  x_size int
 * @param  y_size int
 */
function imagecreatetruecolor($x_size, $y_size) {}

/**
 * Draw a dashed line 
 *
 * @return int
 * @param  im int
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
 * @return int
 * @param  im int
 */
function imagedestroy($im) {}

/**
 * Draw an ellipse 
 *
 * @return void
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
 * @return int
 * @param  im int
 * @param  x int
 * @param  y int
 * @param  col int
 */
function imagefill($im, $x, $y, $col) {}

/**
 * Draw a filled partial ellipse 
 *
 * @return int
 * @param  im int
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
 * @return void
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
 * @return int
 * @param  im int
 * @param  point array
 * @param  num_points int
 * @param  col int
 */
function imagefilledpolygon($im, $point, $num_points, $col) {}

/**
 * Draw a filled rectangle 
 *
 * @return int
 * @param  im int
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
 * @return int
 * @param  im int
 * @param  x int
 * @param  y int
 * @param  border int
 * @param  col int
 */
function imagefilltoborder($im, $x, $y, $border, $col) {}

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
 * @param  size int
 * @param  angle int
 * @param  font_file string
 * @param  text string
 * @param  extrainfo array
 */
function imageftbbox($size, $angle, $font_file, $text, $extrainfo) {}

/**
 * Write text to the image using fonts via freetype2 
 *
 * @return array
 * @param  im int
 * @param  size int
 * @param  angle int
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
 * @return int
 * @param  im int
 * @param  inputgamma float
 * @param  outputgamma float
 */
function imagegammacorrect($im, $inputgamma, $outputgamma) {}

/**
 * Output GD image to browser or file 
 *
 * @return int
 * @param  im int
 * @param  filename string[optional]
 */
function imagegd($im, $filename = null) {}

/**
 * Output GD2 image to browser or file 
 *
 * @return int
 * @param  im int
 * @param  filename string[optional]
 * @param  chunk_size int[optional]
 * @param  type int[optional]
 */
function imagegd2($im, $filename = null, $chunk_size = null, $type = null) {}

/**
 * Output GIF image to browser or file 
 *
 * @return int
 * @param  im int
 * @param  filename string[optional]
 */
function imagegif($im, $filename = null) {}

/**
 * Enable or disable interlace 
 *
 * @return int
 * @param  im int
 * @param  interlace int[optional]
 */
function imageinterlace($im, $interlace = null) {}

/**
 * return true if the image uses truecolor 
 *
 * @return int
 * @param  im int
 */
function imageistruecolor($im) {}

/**
 * Output JPEG image to browser or file 
 *
 * @return int
 * @param  im int
 * @param  filename string[optional]
 * @param  quality int[optional]
 */
function imagejpeg($im, $filename = null, $quality = null) {}

/**
 * Set the alpha blending flag to use the bundled libgd layering effects 
 *
 * @return void
 * @param  im resource
 * @param  effect int
 */
function imagelayereffect($im, $effect) {}

/**
 * Draw a line 
 *
 * @return int
 * @param  im int
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
 * @return int
 * @param  dst int
 * @param  src int
 */
function imagepalettecopy($dst, $src) {}

/**
 * Output PNG image to browser or file 
 *
 * @return int
 * @param  im int
 * @param  filename string[optional]
 */
function imagepng($im, $filename = null) {}

/**
 * Draw a polygon 
 *
 * @return int
 * @param  im int
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
 * @param  font int
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
 * @param  font_index int
 * @param  filename string
 */
function imagepsencodefont($font_index, $filename) {}

/**
 * Extend or or condense (if extend < 1) a font 
 *
 * @return bool
 * @param  font_index int
 * @param  extend float
 */
function imagepsextendfont($font_index, $extend) {}

/**
 * Free memory used by a font 
 *
 * @return bool
 * @param  font_index int
 */
function imagepsfreefont($font_index) {}

/**
 * Load a new font from specified file 
 *
 * @return int
 * @param  pathname string
 */
function imagepsloadfont($pathname) {}

/**
 * Slant a font 
 *
 * @return bool
 * @param  font_index int
 * @param  slant float
 */
function imagepsslantfont($font_index, $slant) {}

/**
 * Rasterize a string over an image 
 *
 * @return array
 * @param  image int
 * @param  text string
 * @param  font int
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
 * @return int
 * @param  im int
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
 * @return int
 * @param  src_im int
 * @param  angle float
 * @param  bgdcolor int
 */
function imagerotate($src_im, $angle, $bgdcolor) {}

/**
 * Include alpha channel to a saved image 
 *
 * @return void
 * @param  im resource
 * @param  on bool
 */
function imagesavealpha($im, $on) {}

/**
 * Should antialiased functions used or not
 *
 * @return unknown
 * @param  im int
 * @param  on bool
 */
function imagesetantialias($im, $on) {}

/**
 * Set the brush image to $brush when filling $image with the "IMG_COLOR_BRUSHED" color 
 *
 * @return int
 * @param  image resource
 * @param  brush resource
 */
function imagesetbrush($image, $brush) {}

/**
 * Set a single pixel 
 *
 * @return int
 * @param  im int
 * @param  x int
 * @param  y int
 * @param  col int
 */
function imagesetpixel($im, $x, $y, $col) {}

/**
 * Set the line drawing styles for use with imageline and IMG_COLOR_STYLED. 
 *
 * @return void
 * @param  im resource
 * @param  styles array
 */
function imagesetstyle($im, $styles) {}

/**
 * Set line thickness for drawing lines, ellipses, rectangles, polygons etc. 
 *
 * @return void
 * @param  im resource
 * @param  thickness int
 */
function imagesetthickness($im, $thickness) {}

/**
 * Set the tile image to $tile when filling $image with the "IMG_COLOR_TILED" color 
 *
 * @return int
 * @param  image resource
 * @param  tile resource
 */
function imagesettile($image, $tile) {}

/**
 * Draw a string horizontally 
 *
 * @return int
 * @param  im int
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
 * @return int
 * @param  im int
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
 * @param  im int
 */
function imagesx($im) {}

/**
 * Get image height 
 *
 * @return int
 * @param  im int
 */
function imagesy($im) {}

/**
 * Convert a true colour image to a palette based image with a number of colours, optionally using dithering. 
 *
 * @return void
 * @param  im resource
 * @param  ditherFlag bool
 * @param  colorsWanted int
 */
function imagetruecolortopalette($im, $ditherFlag, $colorsWanted) {}

/**
 * Give the bounding box of a text using TrueType fonts 
 *
 * @return array
 * @param  size int
 * @param  angle int
 * @param  font_file string
 * @param  text string
 */
function imagettfbbox($size, $angle, $font_file, $text) {}

/**
 * Write text to the image using a TrueType font 
 *
 * @return array
 * @param  im int
 * @param  size int
 * @param  angle int
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
 * @return int
 * @param  im int
 * @param  filename string[optional]
 * @param  foreground int[optional]
 */
function imagewbmp($im, $filename = null, $foreground = null) {}

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
 * @param  section int
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
 * @param  section int
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
 * @return int
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
 * Switch autocommit on or off 
 *
 * @return bool
 * @param  link resource[optional]
 */
function ingres_autocommit($link = null) {}

/**
 * Close an Ingres II database connection 
 *
 * @return bool
 * @param  link resource[optional]
 */
function ingres_close($link = null) {}

/**
 * Commit a transaction 
 *
 * @return bool
 * @param  link resource[optional]
 */
function ingres_commit($link = null) {}

/**
 * Open a connection to an Ingres II database the syntax of database is [node_id::]dbname[/svr_class] 
 *
 * @return resource
 * @param  database string[optional]
 * @param  username string[optional]
 * @param  password string[optional]
 */
function ingres_connect($database = null, $username = null, $password = null) {}

/**
 * Fetch a row of result into an array result_type can be II_NUM for enumerated array, II_ASSOC for associative array, or II_BOTH (default) 
 *
 * @return array
 * @param  result_type int[optional]
 * @param  link resource[optional]
 */
function ingres_fetch_array($result_type = null, $link = null) {}

/**
 * Fetch a row of result into an object result_type can be II_NUM for enumerated object, II_ASSOC for associative object, or II_BOTH (default) 
 *
 * @return array
 * @param  result_type int[optional]
 * @param  link resource[optional]
 */
function ingres_fetch_object($result_type = null, $link = null) {}

/**
 * Fetch a row of result into an enumerated array 
 *
 * @return array
 * @param  link resource[optional]
 */
function ingres_fetch_row($link = null) {}

/**
 * Return the length of a field in a query result index must be >0 and <= ingres_num_fields() 
 *
 * @return string
 * @param  index int
 * @param  link resource[optional]
 */
function ingres_field_length($index, $link = null) {}

/**
 * Return the name of a field in a query result index must be >0 and <= ingres_num_fields() 
 *
 * @return string
 * @param  index int
 * @param  link resource[optional]
 */
function ingres_field_name($index, $link = null) {}

/**
 * Return true if the field is nullable and false otherwise index must be >0 and <= ingres_num_fields() 
 *
 * @return string
 * @param  index int
 * @param  link resource[optional]
 */
function ingres_field_nullable($index, $link = null) {}

/**
 * Return the precision of a field in a query result index must be >0 and <= ingres_num_fields() 
 *
 * @return string
 * @param  index int
 * @param  link resource[optional]
 */
function ingres_field_precision($index, $link = null) {}

/**
 * Return the scale of a field in a query result index must be >0 and <= ingres_num_fields() 
 *
 * @return string
 * @param  index int
 * @param  link resource[optional]
 */
function ingres_field_scale($index, $link = null) {}

/**
 * Return the type of a field in a query result index must be >0 and <= ingres_num_fields() 
 *
 * @return string
 * @param  index int
 * @param  link resource[optional]
 */
function ingres_field_type($index, $link = null) {}

/**
 * Return the number of fields returned by the last query 
 *
 * @return int
 * @param  link resource[optional]
 */
function ingres_num_fields($link = null) {}

/**
 * Return the number of rows affected/returned by the last query 
 *
 * @return int
 * @param  link resource[optional]
 */
function ingres_num_rows($link = null) {}

/**
 * Open a persistent connection to an Ingres II database the syntax of database is [node_id::]dbname[/svr_class] 
 *
 * @return resource
 * @param  database string[optional]
 * @param  username string[optional]
 * @param  password string[optional]
 */
function ingres_pconnect($database = null, $username = null, $password = null) {}

/**
 * Send a SQL query to Ingres II 
 *
 * @return bool
 * @param  query string
 * @param  link resource[optional]
 */
function ingres_query($query, $link = null) {}

/**
 * Roll back a transaction 
 *
 * @return bool
 * @param  link resource[optional]
 */
function ingres_rollback($link = null) {}

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
 * @return array
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
 * Checks if given value is a SoapFault object 
 *
 * @return bool
 * @param  object mixed
 */
function is_soap_fault($object) {}

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
 */
function jdtojewish($juliandaycount) {}

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
 * @return void
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
 * @return bool
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
 * Create a hard link 
 *
 * @return int
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
 * @return int
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
 */
function mb_detect_encoding($str, $encoding_list = null) {}

/**
 * Sets the current detect_order or Return the current detect_order as a array 
 *
 * @return bool|array
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
 */
function mb_encode_mimeheader($str, $charset = null, $transfer_encoding = null, $linefeed = null) {}

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
 * @return string
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
 * @return string
 * @param  encoding string[optional]
 */
function mb_http_output($encoding = null) {}

/**
 * Sets the current internal encoding or Returns the current internal encoding as a string 
 *
 * @return string
 * @param  encoding string[optional]
 */
function mb_internal_encoding($encoding = null) {}

/**
 * Sets the current language or Returns the current language as a string 
 *
 * @return string
 * @param  language string[optional]
 */
function mb_language($language = null) {}

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
 * @return string
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
 * @return int
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
 * @return string
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
 * Returns part of a string 
 *
 * @return string
 * @param  str string
 * @param  start int
 * @param  length int[optional]
 * @param  encoding string[optional]
 */
function mbstrcut($str, $start, $length = null, $encoding = null) {}

/**
 * Get character numbers of a string 
 *
 * @return int
 * @param  str string
 * @param  encoding string[optional]
 */
function mbstrlen($str, $encoding = null) {}

/**
 * Find position of first occurrence of a string within another 
 *
 * @return int
 * @param  haystack string
 * @param  needle string
 * @param  offset int[optional]
 * @param  encoding string[optional]
 */
function mbstrpos($haystack, $needle, $offset = null, $encoding = null) {}

/**
 * Find the last occurrence of a character in a string within another 
 *
 * @return int
 * @param  haystack string
 * @param  needle string
 * @param  encoding string[optional]
 */
function mbstrrpos($haystack, $needle, $encoding = null) {}

/**
 * Returns part of a string 
 *
 * @return string
 * @param  str string
 * @param  start int
 * @param  length int[optional]
 * @param  encoding string[optional]
 */
function mbsubstr($str, $start, $length = null, $encoding = null) {}

/**
 * Append a new event to the calendar stream 
 *
 * @return string
 * @param  stream_id int
 */
function mcal_append_event($stream_id) {}

/**
 * Close an MCAL stream 
 *
 * @return int
 * @param  stream_id int
 * @param  options int[optional]
 */
function mcal_close($stream_id, $options = null) {}

/**
 * Create a new calendar 
 *
 * @return string
 * @param  stream_id int
 * @param  calendar string
 */
function mcal_create_calendar($stream_id, $calendar) {}

/**
 * Returns <0, 0, >0 if a<b, a==b, a>b respectively 
 *
 * @return int
 * @param  ayear int
 * @param  amonth int
 * @param  aday int
 * @param  byear int
 * @param  bmonth int
 * @param  bday int
 */
function mcal_date_compare($ayear, $amonth, $aday, $byear, $bmonth, $bday) {}

/**
 * Returns true if the date is a valid date 
 *
 * @return bool
 * @param  year int
 * @param  month int
 * @param  day int
 */
function mcal_date_valid($year, $month, $day) {}

/**
 * Returns the day of the week of the given date 
 *
 * @return int
 * @param  year int
 * @param  month int
 * @param  day int
 */
function mcal_day_of_week($year, $month, $day) {}

/**
 * Returns the day of the year of the given date 
 *
 * @return int
 * @param  year int
 * @param  month int
 * @param  day int
 */
function mcal_day_of_year($year, $month, $day) {}

/**
 * Returns the number of days in the given month, needs to know if the year is a leap year or not 
 *
 * @return int
 * @param  month int
 * @param  leap_year bool
 */
function mcal_days_in_month($month, $leap_year) {}

/**
 * Delete calendar 
 *
 * @return string
 * @param  stream_id int
 * @param  calendar string
 */
function mcal_delete_calendar($stream_id, $calendar) {}

/**
 * Delete an event 
 *
 * @return string
 * @param  stream_id int
 * @param  event_id int
 */
function mcal_delete_event($stream_id, $event_id) {}

/**
 * Add an attribute and value to an event 
 *
 * @return string
 * @param  stream_id int
 * @param  attribute string
 * @param  value string
 */
function mcal_event_add_attribute($stream_id, $attribute, $value) {}

/**
 * Initialize a streams global event 
 *
 * @return int
 * @param  stream_id int
 */
function mcal_event_init($stream_id) {}

/**
 * Add an alarm to the streams global event 
 *
 * @return int
 * @param  stream_id int
 * @param  alarm int
 */
function mcal_event_set_alarm($stream_id, $alarm) {}

/**
 * Attach a category to an event 
 *
 * @return string
 * @param  stream_id int
 * @param  category string
 */
function mcal_event_set_category($stream_id, $category) {}

/**
 * Add an class to the streams global event 
 *
 * @return int
 * @param  stream_id int
 * @param  class int
 */
function mcal_event_set_class($stream_id, $class) {}

/**
 * Attach a description to an event 
 *
 * @return string
 * @param  stream_id int
 * @param  description string
 */
function mcal_event_set_description($stream_id, $description) {}

/**
 * Attach an end datetime to an event 
 *
 * @return string
 * @param  stream_id int
 * @param  year int
 * @param  month int
 * @param  day int
 * @param  hour int[optional]
 * @param  min int
 * @param  sec int
 */
function mcal_event_set_end($stream_id, $year, $month, $day, $hour = null, $min, $sec) {}

/**
 * Create a daily recurrence 
 *
 * @return string
 * @param  stream_id int
 * @param  year int
 * @param  month int
 * @param  day int
 * @param  interval int
 */
function mcal_event_set_recur_daily($stream_id, $year, $month, $day, $interval) {}

/**
 * Create a monthly by day recurrence 
 *
 * @return string
 * @param  stream_id int
 * @param  year int
 * @param  month int
 * @param  day int
 * @param  interval int
 */
function mcal_event_set_recur_monthly_mday($stream_id, $year, $month, $day, $interval) {}

/**
 * Create a monthly by week recurrence 
 *
 * @return string
 * @param  stream_id int
 * @param  year int
 * @param  month int
 * @param  day int
 * @param  interval int
 */
function mcal_event_set_recur_monthly_wday($stream_id, $year, $month, $day, $interval) {}

/**
 * Create a daily recurrence 
 *
 * @return string
 * @param  stream_id int
 */
function mcal_event_set_recur_none($stream_id) {}

/**
 * Create a weekly recurrence 
 *
 * @return string
 * @param  stream_id int
 * @param  year int
 * @param  month int
 * @param  day int
 * @param  interval int
 * @param  weekdays int
 */
function mcal_event_set_recur_weekly($stream_id, $year, $month, $day, $interval, $weekdays) {}

/**
 * Create a yearly recurrence 
 *
 * @return string
 * @param  stream_id int
 * @param  year int
 * @param  month int
 * @param  day int
 * @param  interval int
 */
function mcal_event_set_recur_yearly($stream_id, $year, $month, $day, $interval) {}

/**
 * Attach a start datetime to an event 
 *
 * @return string
 * @param  stream_id int
 * @param  year int
 * @param  month int
 * @param  day int
 * @param  hour int[optional]
 * @param  min int
 * @param  sec int
 */
function mcal_event_set_start($stream_id, $year, $month, $day, $hour = null, $min, $sec) {}

/**
 * Attach a title to an event 
 *
 * @return string
 * @param  stream_id int
 * @param  title string
 */
function mcal_event_set_title($stream_id, $title) {}

/**
 * Delete all events marked for deletion 
 *
 * @return int
 * @param  stream_id int
 */
function mcal_expunge($stream_id) {}

/**
 * Fetch the current event stored in the stream's event structure 
 *
 * @return object
 * @param  stream_id int
 */
function mcal_fetch_current_stream_event($stream_id) {}

/**
 * Fetch an event 
 *
 * @return int
 * @param  stream_id int
 * @param  eventid int
 * @param  options int[optional]
 */
function mcal_fetch_event($stream_id, $eventid, $options = null) {}

/**
 * Returns true if year is a leap year, false if not 
 *
 * @return bool
 * @param  year int
 */
function mcal_is_leap_year($year) {}

/**
 * List alarms for a given time 
 *
 * @return bool
 * @param  stream_id int
 * @param  year int
 * @param  month int
 * @param  day int
 * @param  hour int
 * @param  min int
 * @param  sec int
 */
function mcal_list_alarms($stream_id, $year, $month, $day, $hour, $min, $sec) {}

/**
 * Returns list of UIDs for that day or range of days 
 *
 * @return array
 * @param  stream_id int
 * @param  begindate object
 * @param  enddate object[optional]
 */
function mcal_list_events($stream_id, $begindate, $enddate = null) {}

/**
 * Returns an object filled with the next date the event occurs, on or after the supplied date.  Returns empty date field if event does not occur or something is invalid. 
 *
 * @return object
 * @param  stream_id int
 * @param  weekstart int
 * @param  next array
 */
function mcal_next_recurrence($stream_id, $weekstart, $next) {}

/**
 * Open an MCAL stream to a calendar 
 *
 * @return int
 * @param  calendar string
 * @param  user string
 * @param  password string
 * @param  options int[optional]
 */
function mcal_open($calendar, $user, $password, $options = null) {}

/**
 * Open a persistent MCAL stream to a calendar 
 *
 * @return string
 * @param  calendar string
 * @param  user string
 * @param  password string
 * @param  options int[optional]
 */
function mcal_popen($calendar, $user, $password, $options = null) {}

/**
 * Rename a calendar 
 *
 * @return string
 * @param  stream_id int
 * @param  src_calendar string
 * @param  dest_calendar string
 */
function mcal_rename_calendar($stream_id, $src_calendar, $dest_calendar) {}

/**
 * Reopen MCAL stream to a new calendar 
 *
 * @return int
 * @param  stream_id int
 * @param  calendar string
 * @param  options int[optional]
 */
function mcal_reopen($stream_id, $calendar, $options = null) {}

/**
 * Snooze an alarm 
 *
 * @return string
 * @param  stream_id int
 * @param  uid int
 */
function mcal_snooze($stream_id, $uid) {}

/**
 * Store changes to an event 
 *
 * @return string
 * @param  stream_id int
 */
function mcal_store_event($stream_id) {}

/**
 * Returns true if the time is a valid time 
 *
 * @return bool
 * @param  hour int
 * @param  min int
 * @param  sec int
 */
function mcal_time_valid($hour, $min, $sec) {}

/**
 * Returns the week number of the given date 
 *
 * @return int
 * @param  day int
 * @param  month int
 * @param  year int
 */
function mcal_week_of_year($day, $month, $year) {}

/**
 * CBC crypt/decrypt data using key key with cipher cipher using optional iv 
 *
 * @return string
 * @param  cipher int
 * @param  key string
 * @param  data string
 * @param  mode int
 * @param  iv string[optional]
 */
function mcrypt_cbc($cipher, $key, $data, $mode, $iv = null) {}

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
 * ECB crypt/decrypt data using key key with cipher cipher 
 *
 * @return string
 * @param  cipher int
 * @param  key string
 * @param  data string
 * @param  mode int
 */
function mcrypt_ecb($cipher, $key, $data, $mode) {}

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
 * Get the block size of cipher 
 *
 * @return int
 * @param  cipher int
 */
function mcrypt_get_block_size($cipher) {}

/**
 * Get the name of cipher 
 *
 * @return string
 * @param  cipher int
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
 * @param  cipher int
 */
function mcrypt_get_key_size($cipher) {}

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
 * Add an MCVE user using usersetup structure 
 *
 * @return int
 * @param  conn resource
 * @param  admin_password string
 * @param  usersetup int
 */
function mcve_adduser($conn, $admin_password, $usersetup) {}

/**
 * Add a value to user configuration structure 
 *
 * @return int
 * @param  usersetup resource
 * @param  argtype int
 * @param  argval string
 */
function mcve_adduserarg($usersetup, $argtype, $argval) {}

/**
 * Get unsettled batch totals 
 *
 * @return int
 * @param  conn resource
 * @param  username string
 * @param  password string
 */
function mcve_bt($conn, $username, $password) {}

/**
 * Check to see if a transaction has completed 
 *
 * @return int
 * @param  conn resource
 * @param  identifier int
 */
function mcve_checkstatus($conn, $identifier) {}

/**
 * Verify Password 
 *
 * @return int
 * @param  conn resource
 * @param  username string
 * @param  password string
 */
function mcve_chkpwd($conn, $username, $password) {}

/**
 * Change the system administrator's password 
 *
 * @return int
 * @param  conn resource
 * @param  admin_password string
 * @param  new_password string
 */
function mcve_chngpwd($conn, $admin_password, $new_password) {}

/**
 * Number of complete authorizations in queue, returning an array of their identifiers 
 *
 * @return int
 * @param  conn resource
 * @param  array int
 */
function mcve_completeauthorizations($conn, &$array) {}

/**
 * Establish the connection to MCVE 
 *
 * @return int
 * @param  conn resource
 */
function mcve_connect($conn) {}

/**
 * Get a textual representation of why a connection failed 
 *
 * @return string
 * @param  conn resource
 */
function mcve_connectionerror($conn) {}

/**
 * Delete specified transaction from MCVE_CONN structure 
 *
 * @return bool
 * @param  conn resource
 * @param  identifier int
 */
function mcve_deleteresponse($conn, $identifier) {}

/**
 * Delete specified transaction from MCVE_CONN structure 
 *
 * @return bool
 * @param  conn resource
 * @param  identifier int
 */
function mcve_deletetrans($conn, $identifier) {}

/**
 * Deallocate data associated with usersetup structure 
 *
 * @return void
 * @param  usersetup resource
 */
function mcve_deleteusersetup($usersetup) {}

/**
 * Delete an MCVE user account 
 *
 * @return int
 * @param  conn resource
 * @param  admin_password string
 * @param  username string
 */
function mcve_deluser($conn, $admin_password, $username) {}

/**
 * Destroy the connection and MCVE_CONN structure 
 *
 * @return void
 * @param  conn resource
 */
function mcve_destroyconn($conn) {}

/**
 * Free memory associated with IP/SSL connectivity 
 *
 * @return void
 */
function mcve_destroyengine() {}

/**
 * Disable an active MCVE user account 
 *
 * @return int
 * @param  conn resource
 * @param  admin_password string
 * @param  username string
 */
function mcve_disableuser($conn, $admin_password, $username) {}

/**
 * Edit MCVE user using usersetup structure 
 *
 * @return int
 * @param  conn resource
 * @param  admin_password string
 * @param  usersetup int
 */
function mcve_edituser($conn, $admin_password, $usersetup) {}

/**
 * Enable an inactive MCVE user account 
 *
 * @return int
 * @param  conn resource
 * @param  admin_password string
 * @param  username string
 */
function mcve_enableuser($conn, $admin_password, $username) {}

/**
 * Send a FORCE to MCVE.  (typically, a phone-authorization) 
 *
 * @return int
 * @param  conn resiurce
 * @param  username string
 * @param  password string
 * @param  trackdata string
 * @param  account string
 * @param  expdate string
 * @param  amount float
 * @param  authcode string
 * @param  comments string
 * @param  clerkid string
 * @param  stationid string
 * @param  ptrannum int
 */
function mcve_force($conn, $username, $password, $trackdata, $account, $expdate, $amount, $authcode, $comments, $clerkid, $stationid, $ptrannum) {}

/**
 * Get a specific cell from a comma delimited response by column name 
 *
 * @return string
 * @param  conn resource
 * @param  identifier int
 * @param  column string
 * @param  row int
 */
function mcve_getcell($conn, $identifier, $column, $row) {}

/**
 * Get a specific cell from a comma delimited response by column number 
 *
 * @return string
 * @param  conn resource
 * @param  identifier int
 * @param  column int
 * @param  row int
 */
function mcve_getcellbynum($conn, $identifier, $column, $row) {}

/**
 * Get the RAW comma delimited data returned from MCVE 
 *
 * @return string
 * @param  conn resource
 * @param  identifier int
 */
function mcve_getcommadelimited($conn, $identifier) {}

/**
 * Get the name of the column in a comma-delimited response 
 *
 * @return string
 * @param  conn resource
 * @param  identifier int
 * @param  column_num int
 */
function mcve_getheader($conn, $identifier, $column_num) {}

/**
 * Grab a value from usersetup structure 
 *
 * @return string
 * @param  usersetup resource
 * @param  argtype int
 */
function mcve_getuserarg($usersetup, $argtype) {}

/**
 * Get a user response parameter 
 *
 * @return string
 * @param  conn resource
 * @param  identifier long
 * @param  key int
 */
function mcve_getuserparam($conn, $identifier, $key) {}

/**
 * 
 *
 * @return int
 * @param  conn resource
 * @param  username string
 * @param  password string
 * @param  type int
 * @param  account string
 * @param  clerkid string
 * @param  stationid string
 * @param  comments string
 * @param  ptrannum int
 * @param  startdate string
 * @param  enddate string
 */
function mcve_gft($conn, $username, $password, $type, $account, $clerkid, $stationid, $comments, $ptrannum, $startdate, $enddate) {}

/**
 * Audit MCVE for settled transactions 
 *
 * @return int
 * @param  conn int
 * @param  username string
 * @param  password string
 * @param  type int
 * @param  account string
 * @param  batch string
 * @param  clerkid string
 * @param  stationid string
 * @param  comments string
 * @param  ptrannum int
 * @param  startdate string
 * @param  enddate string
 */
function mcve_gl($conn, $username, $password, $type, $account, $batch, $clerkid, $stationid, $comments, $ptrannum, $startdate, $enddate) {}

/**
 * Audit MCVE for Unsettled Transactions 
 *
 * @return int
 * @param  conn resource
 * @param  username string
 * @param  password string
 * @param  type int
 * @param  account string
 * @param  clerkid string
 * @param  stationid string
 * @param  comments string
 * @param  ptrannum int
 * @param  startdate string
 * @param  enddate string
 */
function mcve_gut($conn, $username, $password, $type, $account, $clerkid, $stationid, $comments, $ptrannum, $startdate, $enddate) {}

/**
 * Create and initialize an MCVE_CONN structure 
 *
 * @return resource
 */
function mcve_initconn() {}

/**
 * Ready the client for IP/SSL Communication 
 *
 * @return int
 * @param  location string
 */
function mcve_initengine($location) {}

/**
 * Initialize structure to store user data 
 *
 * @return resource
 */
function mcve_initusersetup() {}

/**
 * Checks to see if response is comma delimited 
 *
 * @return int
 * @param  conn resource
 * @param  identifier int
 */
function mcve_iscommadelimited($conn, $identifier) {}

/**
 * List statistics for all users on MCVE system 
 *
 * @return int
 * @param  conn resource
 * @param  admin_password string
 */
function mcve_liststats($conn, $admin_password) {}

/**
 * List all users on MCVE system 
 *
 * @return int
 * @param  conn resource
 * @param  admin_password string
 */
function mcve_listusers($conn, $admin_password) {}

/**
 * 
 *
 * @return bool
 * @param  conn resource
 * @param  secs int
 */
function mcve_maxconntimeout($conn, $secs) {}

/**
 * Perform communication with MCVE (send/receive data)   Non-blocking 
 *
 * @return int
 * @param  conn resource
 */
function mcve_monitor($conn) {}

/**
 * Number of columns returned in a comma delimited response 
 *
 * @return int
 * @param  conn resource
 * @param  identifier int
 */
function mcve_numcolumns($conn, $identifier) {}

/**
 * Number of rows returned in a comma delimited response 
 *
 * @return int
 * @param  conn resource
 * @param  identifier int
 */
function mcve_numrows($conn, $identifier) {}

/**
 * Send an OVERRIDE to MCVE 
 *
 * @return int
 * @param  conn resource
 * @param  username string
 * @param  password string
 * @param  trackdata string
 * @param  account string
 * @param  expdate string
 * @param  amount float
 * @param  street string
 * @param  zip string
 * @param  cv string
 * @param  comments string
 * @param  clerkid string
 * @param  stationid string
 * @param  ptrannum int
 */
function mcve_override($conn, $username, $password, $trackdata, $account, $expdate, $amount, $street, $zip, $cv, $comments, $clerkid, $stationid, $ptrannum) {}

/**
 * Parse the comma delimited response so mcve_getcell, etc will work 
 *
 * @return int
 * @param  conn resource
 * @param  identifier int
 */
function mcve_parsecommadelimited($conn, $identifier) {}

/**
 * Send a ping request to MCVE 
 *
 * @return int
 * @param  conn resource
 */
function mcve_ping($conn) {}

/**
 * Send a PREAUTHORIZATION to MCVE 
 *
 * @return int
 * @param  conn resource
 * @param  username string
 * @param  password string
 * @param  trackdata string
 * @param  account string
 * @param  expdate string
 * @param  amount float
 * @param  street string
 * @param  zip string
 * @param  cv string
 * @param  comments string
 * @param  clerkid string
 * @param  stationid string
 * @param  ptrannum int
 */
function mcve_preauth($conn, $username, $password, $trackdata, $account, $expdate, $amount, $street, $zip, $cv, $comments, $clerkid, $stationid, $ptrannum) {}

/**
 * Complete a PREAUTHORIZATION... Ready it for settlement 
 *
 * @return int
 * @param  conn resource
 * @param  username string
 * @param  password string
 * @param  finalamount float
 * @param  sid int
 * @param  ptrannum int
 */
function mcve_preauthcompletion($conn, $username, $password, $finalamount, $sid, $ptrannum) {}

/**
 * Audit MCVE for a list of transactions in the outgoing queue 
 *
 * @return int
 * @param  conn resource
 * @param  username string
 * @param  password string
 * @param  clerkid string
 * @param  stationid string
 * @param  comments string
 * @param  ptrannum int
 */
function mcve_qc($conn, $username, $password, $clerkid, $stationid, $comments, $ptrannum) {}

/**
 * Get a custom response parameter 
 *
 * @return string
 * @param  conn resource
 * @param  identifier long
 * @param  key string
 */
function mcve_responseparam($conn, $identifier, $key) {}

/**
 * Issue a RETURN or CREDIT to MCVE 
 *
 * @return int
 * @param  conn int
 * @param  username string
 * @param  password string
 * @param  trackdata string
 * @param  account string
 * @param  expdate string
 * @param  amount float
 * @param  comments string
 * @param  clerkid string
 * @param  stationid string
 * @param  ptrannum int
 */
function mcve_return($conn, $username, $password, $trackdata, $account, $expdate, $amount, $comments, $clerkid, $stationid, $ptrannum) {}

/**
 * Grab the exact return code from the transaction 
 *
 * @return int
 * @param  conn resource
 * @param  identifier int
 */
function mcve_returncode($conn, $identifier) {}

/**
 * Check to see if the transaction was successful 
 *
 * @return int
 * @param  conn resource
 * @param  identifier int
 */
function mcve_returnstatus($conn, $identifier) {}

/**
 * Send a SALE to MCVE 
 *
 * @return int
 * @param  conn resource
 * @param  username string
 * @param  password string
 * @param  trackdata string
 * @param  account string
 * @param  expdate string
 * @param  amount float
 * @param  street string
 * @param  zip string
 * @param  cv string
 * @param  comments string
 * @param  clerkid string
 * @param  stationid string
 * @param  ptrannum int
 */
function mcve_sale($conn, $username, $password, $trackdata, $account, $expdate, $amount, $street, $zip, $cv, $comments, $clerkid, $stationid, $ptrannum) {}

/**
 * 
 *
 * @return int
 * @param  conn resource
 * @param  tf int
 */
function mcve_setblocking($conn, $tf) {}

/**
 * Set the connection method to Drop-File 
 *
 * @return int
 * @param  conn resource
 * @param  directory string
 */
function mcve_setdropfile($conn, $directory) {}

/**
 * Set the connection method to IP 
 *
 * @return int
 * @param  conn resource
 * @param  host string
 * @param  port int
 */
function mcve_setip($conn, $host, $port) {}

/**
 * Set the connection method to SSL 
 *
 * @return int
 * @param  conn resource
 * @param  host string
 * @param  port int
 */
function mcve_setssl($conn, $host, $port) {}

/**
 * 
 *
 * @return int
 * @param  conn resource
 * @param  seconds int
 */
function mcve_settimeout($conn, $seconds) {}

/**
 * Issue a settlement command to do a batch deposit 
 *
 * @return int
 * @param  conn resource
 * @param  username string
 * @param  password string
 * @param  batch string
 */
function mcve_settle($conn, $username, $password, $batch) {}

/**
 * Get a textual representation of the return_avs 
 *
 * @return string
 * @param  code string
 */
function mcve_text_avs($code) {}

/**
 * Get a textual representation of the return_code 
 *
 * @return string
 * @param  code string
 */
function mcve_text_code($code) {}

/**
 * Get a textual representation of the return_cv 
 *
 * @return string
 * @param  code int
 */
function mcve_text_cv($code) {}

/**
 * Get the authorization number returned for the transaction (alpha-numeric) 
 *
 * @return string
 * @param  conn resource
 * @param  identifier int
 */
function mcve_transactionauth($conn, $identifier) {}

/**
 * Get the Address Verification return status 
 *
 * @return int
 * @param  conn resource
 * @param  identifier int
 */
function mcve_transactionavs($conn, $identifier) {}

/**
 * Get the batch number associated with the transaction 
 *
 * @return int
 * @param  conn resource
 * @param  identifier int
 */
function mcve_transactionbatch($conn, $identifier) {}

/**
 * Get the CVC2/CVV2/CID return status 
 *
 * @return int
 * @param  conn resource
 * @param  identifier int
 */
function mcve_transactioncv($conn, $identifier) {}

/**
 * 
 *
 * @return int
 * @param  conn resource
 * @param  identifier int
 */
function mcve_transactionid($conn, $identifier) {}

/**
 * Get the ITEM number in the associated batch for this transaction 
 *
 * @return int
 * @param  conn resource
 * @param  identifier int
 */
function mcve_transactionitem($conn, $identifier) {}

/**
 * Check to see if outgoing buffer is clear 
 *
 * @return int
 * @param  conn resource
 */
function mcve_transactionssent($conn) {}

/**
 * Get verbiage (text) return from MCVE or processing institution 
 *
 * @return string
 * @param  conn resource
 * @param  identifier int
 */
function mcve_transactiontext($conn, $identifier) {}

/**
 * Number of transactions in client-queue 
 *
 * @return int
 * @param  conn resource
 */
function mcve_transinqueue($conn) {}

/**
 * Start a new transaction 
 *
 * @return int
 * @param  conn resource
 */
function mcve_transnew($conn) {}

/**
 * Add a parameter to a transaction 
 *
 * @return int
 * @param  conn resource
 * @param  identifier long
 * @param  key int
 * @vararg ...
 */
function mcve_transparam($conn, $identifier, $key) {}

/**
 * Finalize and send the transaction 
 *
 * @return int
 * @param  conn resource
 * @param  identifier long
 */
function mcve_transsend($conn, $identifier) {}

/**
 * Get a list of all Unsettled batches 
 *
 * @return int
 * @param  conn resource
 * @param  username string
 * @param  password string
 */
function mcve_ub($conn, $username, $password) {}

/**
 * Wait x microsecs 
 *
 * @return int
 * @param  microsecs long
 */
function mcve_uwait($microsecs) {}

/**
 * 
 *
 * @return bool
 * @param  conn resource
 * @param  tf int
 */
function mcve_verifyconnection($conn, $tf) {}

/**
 * 
 *
 * @return bool
 * @param  conn resource
 * @param  tf int
 */
function mcve_verifysslcert($conn, $tf) {}

/**
 * VOID a transaction in the settlement queue 
 *
 * @return int
 * @param  conn resource
 * @param  username string
 * @param  password string
 * @param  sid int
 * @param  ptrannum int
 */
function mcve_void($conn, $username, $password, $sid, $ptrannum) {}

/**
 * Calculate the md5 hash of a string 
 *
 * @return string
 * @param  str string
 */
function md5($str) {}

/**
 * Calculate the md5 hash of given filename 
 *
 * @return string
 * @param  filename string
 */
function md5_file($filename) {}

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
 * Returns a string containing the current time in seconds and microseconds 
 *
 * @return string
 */
function microtime() {}

/**
 * Return content-type for file 
 *
 * @return string
 * @param  filename string
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
 * @param  mode int
 */
function mkdir($pathname, $mode) {}

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
 * Move a file if and only if it was created by an upload 
 *
 * @return bool
 * @param  path string
 * @param  new_path string
 */
function move_uploaded_file($path, $new_path) {}

/**
 * Call the plugin function named fn_name 
 *
 * @return string
 * @param  fn_name string
 * @param  param1 string[optional]
 * @vararg ...
 * @param  param4 string[optional]
 */
function msession_call($fn_name, $param1 = null, $param4 = null) {}

/**
 * Connect to msession sever 
 *
 * @return bool
 * @param  host string
 * @param  port string
 */
function msession_connect($host, $port) {}

/**
 * Get session count 
 *
 * @return int
 */
function msession_count() {}

/**
 * Create a session 
 *
 * @return bool
 * @param  session string
 */
function msession_create($session) {}

/**
 * Destroy a session 
 *
 * @return bool
 * @param  name string
 */
function msession_destroy($name) {}

/**
 * Disconnect from msession server 
 *
 * @return void
 */
function msession_disconnect() {}

/**
 * Find all sessions with name and value 
 *
 * @return array
 * @param  name string
 * @param  value string
 */
function msession_find($name, $value) {}

/**
 * Get value from session 
 *
 * @return string
 * @param  session string
 * @param  name string
 * @param  default_value string
 */
function msession_get($session, $name, $default_value) {}

/**
 * Get array of msession variables  
 *
 * @return array
 * @param  session string
 */
function msession_get_array($session) {}

/**
 * Get data session unstructured data. (PHP sessions use this)  
 *
 * @return string
 * @param  session string
 */
function msession_get_data($session) {}

/**
 * Increment value in session 
 *
 * @return string
 * @param  session string
 * @param  name string
 */
function msession_inc($session, $name) {}

/**
 * List all sessions  
 *
 * @return array
 */
function msession_list() {}

/**
 * return associative array of value:session for all sessions with a variable named 'name' 
 *
 * @return array
 * @param  name string
 */
function msession_listvar($name) {}

/**
 * Lock a session 
 *
 * @return int
 * @param  name string
 */
function msession_lock($name) {}

/**
 * Call the personality plugin escape function 
 *
 * @return string
 * @param  session string
 * @param  val string
 * @param  param string[optional]
 */
function msession_plugin($session, $val, $param = null) {}

/**
 * Get random string 
 *
 * @return string
 * @param  num_chars int
 */
function msession_randstr($num_chars) {}

/**
 * Set value in session 
 *
 * @return bool
 * @param  session string
 * @param  name string
 * @param  value string
 */
function msession_set($session, $name, $value) {}

/**
 * Set msession variables from an array
 *
 * @return bool
 * @param  session string
 * @param  tuples array
 */
function msession_set_array($session, $tuples) {}

/**
 * Set data session unstructured data. (PHP sessions use this)  
 *
 * @return bool
 * @param  session string
 * @param  value string
 */
function msession_set_data($session, $value) {}

/**
 * Lock a session 
 *
 * @return int
 * @param  name string
 */
function msession_stat($name) {}

/**
 * Set/get session timeout 
 *
 * @return int
 * @param  session string
 * @param  param int[optional]
 */
function msession_timeout($session, $param = null) {}

/**
 * Get uniq id 
 *
 * @return string
 * @param  num_chars int
 */
function msession_uniq($num_chars) {}

/**
 * Unlock a session 
 *
 * @return int
 * @param  session string
 * @param  key int
 */
function msession_unlock($session, $key) {}

/**
 * Attach to a message queue 
 *
 * @return resource
 * @param  key long
 * @param  perms long[optional]
 */
function msg_get_queue($key, $perms = null) {}

/**
 * Send a message of type msgtype (must be > 0) to a message queue 
 *
 * @return mixed
 */
function msg_receive() {}

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
 * @param  msgtype long
 * @param  message mixed
 * @param  serialize bool[optional]
 * @param  blocking bool[optional]
 * @param  errorcode long[optional]
 */
function msg_send($queue, $msgtype, $message, $serialize = true, $blocking = true, $errorcode = null) {}

/**
 * Set information for a message queue 
 *
 * @return array
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
 * @return int
 * @param  database_name string
 * @param  query string
 * @param  link_identifier int[optional]
 */
function msql($database_name, $query, $link_identifier = null) {}

/**
 * Return number of affected rows 
 *
 * @return int
 * @param  query int
 */
function msql_affected_rows($query) {}

/**
 * Close an mSQL connection 
 *
 * @return int
 * @param  link_identifier int[optional]
 */
function msql_close($link_identifier = null) {}

/**
 * Open a connection to an mSQL Server 
 *
 * @return int
 * @param  hostname string[optional]
 * @param  username string[optional]
 * @param  password string[optional]
 */
function msql_connect($hostname = null, $username = null, $password = null) {}

/**
 * Create an mSQL database 
 *
 * @return int
 * @param  database_name string
 * @param  link_identifier int[optional]
 */
function msql_create_db($database_name, $link_identifier = null) {}

/**
 * Create an mSQL database 
 *
 * @return int
 * @param  database_name string
 * @param  link_identifier int[optional]
 */
function msql_createdb($database_name, $link_identifier = null) {}

/**
 * Move internal result pointer 
 *
 * @return int
 * @param  query int
 * @param  row_number int
 */
function msql_data_seek($query, $row_number) {}

/**
 * Send an SQL query to mSQL 
 *
 * @return int
 * @param  database_name string
 * @param  query string
 * @param  link_identifier int[optional]
 */
function msql_db_query($database_name, $query, $link_identifier = null) {}

/**
 * Get result data 
 *
 * @return int
 * @param  query int
 * @param  row int
 * @param  field mixed[optional]
 */
function msql_dbname($query, $row, $field = null) {}

/**
 * Drop (delete) an mSQL database 
 *
 * @return int
 * @param  database_name string
 * @param  link_identifier int[optional]
 */
function msql_drop_db($database_name, $link_identifier = null) {}

/**
 * Drop (delete) an mSQL database 
 *
 * @return int
 * @param  database_name string
 * @param  link_identifier int[optional]
 */
function msql_dropdb($database_name, $link_identifier = null) {}

/**
 * Returns the text of the error message from previous mSQL operation 
 *
 * @return string
 * @param  link_identifier int[optional]
 */
function msql_error($link_identifier = null) {}

/**
 * Fetch a result row as an associative array 
 *
 * @return array
 * @param  query int
 * @param  result_type int[optional]
 */
function msql_fetch_array($query, $result_type = null) {}

/**
 * Get column information from a result and return as an object 
 *
 * @return object
 * @param  query int
 * @param  field_offset int[optional]
 */
function msql_fetch_field($query, $field_offset = null) {}

/**
 * Fetch a result row as an object 
 *
 * @return object
 * @param  query int
 * @param  result_type int[optional]
 */
function msql_fetch_object($query, $result_type = null) {}

/**
 * Get a result row as an enumerated array 
 *
 * @return array
 * @param  query int
 */
function msql_fetch_row($query) {}

/**
 * Get the flags associated with the specified field in a result 
 *
 * @return string
 * @param  query int
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
 * @param  query int
 * @param  field_index int
 */
function msql_field_name($query, $field_index) {}

/**
 * Set result pointer to a specific field offset 
 *
 * @return int
 * @param  query int
 * @param  field_offset int
 */
function msql_field_seek($query, $field_offset) {}

/**
 * Get name of the table the specified field is in 
 *
 * @return string
 * @param  query int
 * @param  field_offset int
 */
function msql_field_table($query, $field_offset) {}

/**
 * Get the type of the specified field in a result 
 *
 * @return string
 * @param  query int
 * @param  field_offset int
 */
function msql_field_type($query, $field_offset) {}

/**
 * Get the flags associated with the specified field in a result 
 *
 * @return string
 * @param  query int
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
 * @param  query int
 * @param  field_index int
 */
function msql_fieldname($query, $field_index) {}

/**
 * Get name of the table the specified field is in 
 *
 * @return string
 * @param  query int
 * @param  field_offset int
 */
function msql_fieldtable($query, $field_offset) {}

/**
 * Get the type of the specified field in a result 
 *
 * @return string
 * @param  query int
 * @param  field_offset int
 */
function msql_fieldtype($query, $field_offset) {}

/**
 * Free result memory 
 *
 * @return int
 * @param  query int
 */
function msql_free_result($query) {}

/**
 * Free result memory 
 *
 * @return int
 * @param  query int
 */
function msql_freeresult($query) {}

/**
 * List databases available on an mSQL server 
 *
 * @return int
 * @param  link_identifier int[optional]
 */
function msql_list_dbs($link_identifier = null) {}

/**
 * List mSQL result fields 
 *
 * @return int
 * @param  database_name string
 * @param  table_name string
 * @param  link_identifier int[optional]
 */
function msql_list_fields($database_name, $table_name, $link_identifier = null) {}

/**
 * List tables in an mSQL database 
 *
 * @return int
 * @param  database_name string
 * @param  link_identifier int[optional]
 */
function msql_list_tables($database_name, $link_identifier = null) {}

/**
 * List databases available on an mSQL server 
 *
 * @return int
 * @param  link_identifier int[optional]
 */
function msql_listdbs($link_identifier = null) {}

/**
 * List mSQL result fields 
 *
 * @return int
 * @param  database_name string
 * @param  table_name string
 * @param  link_identifier int[optional]
 */
function msql_listfields($database_name, $table_name, $link_identifier = null) {}

/**
 * List tables in an mSQL database 
 *
 * @return int
 * @param  database_name string
 * @param  link_identifier int[optional]
 */
function msql_listtables($database_name, $link_identifier = null) {}

/**
 * Get number of fields in a result 
 *
 * @return int
 * @param  query int
 */
function msql_num_fields($query) {}

/**
 * Get number of rows in a result 
 *
 * @return int
 * @param  query int
 */
function msql_num_rows($query) {}

/**
 * Get number of fields in a result 
 *
 * @return int
 * @param  query int
 */
function msql_numfields($query) {}

/**
 * Get number of rows in a result 
 *
 * @return int
 * @param  query int
 */
function msql_numrows($query) {}

/**
 * Open a persistent connection to an mSQL Server 
 *
 * @return int
 * @param  hostname string[optional]
 * @param  username string[optional]
 * @param  password string[optional]
 */
function msql_pconnect($hostname = null, $username = null, $password = null) {}

/**
 * Send an SQL query to mSQL 
 *
 * @return int
 * @param  query string
 * @param  link_identifier int[optional]
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
 * @return int
 * @param  query int
 * @param  row int
 * @param  field mixed[optional]
 */
function msql_result($query, $row, $field = null) {}

/**
 * Select an mSQL database 
 *
 * @return int
 * @param  database_name string
 * @param  link_identifier int[optional]
 */
function msql_select_db($database_name, $link_identifier = null) {}

/**
 * Select an mSQL database 
 *
 * @return int
 * @param  database_name string
 * @param  link_identifier int[optional]
 */
function msql_selectdb($database_name, $link_identifier = null) {}

/**
 * Get result data 
 *
 * @return int
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
 * 
 *
 * @return bool
 */
function mssql_bind() {}

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
 * @return int
 * @param  servername string[optional]
 * @param  username string[optional]
 * @param  password string[optional]
 */
function mssql_connect($servername = null, $username = null, $password = null) {}

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
 * @param  result_type int[optional]
 */
function mssql_fetch_assoc($result_id, $result_type = null) {}

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
 * @param  result_type int[optional]
 */
function mssql_fetch_row($result_id, $result_type = null) {}

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
 * @return int
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
 * @return int
 * @param  servername string[optional]
 * @param  username string[optional]
 * @param  password string[optional]
 */
function mssql_pconnect($servername = null, $username = null, $password = null) {}

/**
 * Perform an SQL query on a MS-SQL server database 
 *
 * @return resource
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
 */
function mssql_set_message_handler($error_func) {}

/**
 * Send Sybase query 
 *
 * @return int
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
 * @return mixed
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
 * @return mixed
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
 * @param  result_type int[optional]
 */
function mysql_fetch_object($result, $result_type = null) {}

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
 * @return mixed
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
 * @return mixed
 * @param  result resource
 * @param  row int
 * @param  field mixed[optional]
 */
function mysql_table_name($result, $row, $field = null) {}

/**
 * Gets result data 
 *
 * @return mixed
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
 * Sort an array using case-insensitive natural sort 
 *
 * @return void
 * @param  array_arg array
 */
function natcasesort($array_arg) {}

/**
 * Sort an array using natural sort 
 *
 * @return void
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
 * @return int
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
 * @return int
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
 * @return int
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
 * Reads mouse event from queue 
 *
 * @return bool
 * @param  mevent array
 */
function ncurses_getmouse($mevent) {}

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
 * @return int
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
function ncurses_instr($buffer) {}

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
 * @param  enable bool
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
 * @return long
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
function ncurses_mouse_trafo($y, $x, $toscreen) {}

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
 * @return int
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
 * @return int
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
 * @return int
 * @param  panel resource
 */
function ncurses_panel_above($panel) {}

/**
 * Returns the panel below panel. If panel is null, returns the top panel in the stack 
 *
 * @return int
 * @param  panel resource
 */
function ncurses_panel_below($panel) {}

/**
 * Returns the window associated with panel 
 *
 * @return int
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
 * @return int
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
 * @return int
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
 * @return int
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
 * @param  flag bool
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
 * @return long
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
function ncurses_wmouse_trafo($window, $y, $x, $toscreen) {}

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
 * Draws a vertical line in a window at current position using an attributed character and max. n characters long 
 *
 * @return int
 * @param  window resource
 * @param  charattr int
 * @param  n int
 */
function ncurses_wvline($window, $charattr, $n) {}

/**
 * Make key from pass phrase in the snmpv3 session 
 *
 * @return int
 * @param  snmp_session_*s struct
 * @param  *pass char
 */
function netsnmp_session_gen_auth_key($snmp_session_s, $pass) {}

/**
 * Make key from pass phrase in the snmpv3 session 
 *
 * @return int
 * @param  snmp_session_*s struct
 * @param  *pass u_char
 */
function netsnmp_session_gen_sec_key($snmp_session_s, $pass) {}

/**
 * Set the authentication protocol in the snmpv3 session 
 *
 * @return int
 * @param  snmp_session_*s struct
 * @param  *prot char
 */
function netsnmp_session_set_auth_protocol($snmp_session_s, $prot) {}

/**
 * Set the security level in the snmpv3 session 
 *
 * @return int
 * @param  snmp_session_*s struct
 * @param  *level char
 */
function netsnmp_session_set_sec_level($snmp_session_s, $level) {}

/**
 * Set the security name in the snmpv3 session 
 *
 * @return int
 * @param  snmp_session_*s struct
 * @param  *name char
 */
function netsnmp_session_set_sec_name($snmp_session_s, $name) {}

/**
 * Set the security protocol in the snmpv3 session 
 *
 * @return int
 * @param  snmp_session_*s struct
 * @param  *prot char
 */
function netsnmp_session_set_sec_protocol($snmp_session_s, $prot) {}

/**
 * Creates new xmldoc 
 *
 * @return object
 * @param  version string
 */
function new_xmldoc($version) {}

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
 * Returns list of children nodes 
 *
 * @return int
 * @param  **attributes zval
 * @param  node int
 */
function node_attributes($attributes, $node) {}

/**
 * Returns list of children nodes 
 *
 * @return int
 * @param  node int[optional]
 */
function node_children($node = null) {}

/**
 * Returns list of namespaces 
 *
 * @return int
 * @param  node int[optional]
 */
function node_namespace($node = null) {}

/**
 * Opens the message msg_number in the specified mailbox on the specified server (leave server blank for local) and returns an array of body text lines 
 *
 * @return array
 * @param  server string
 * @param  mailbox string
 * @param  msg_number int
 */
function notes_body($server, $mailbox, $msg_number) {}

/**
 * Creates a note using form form_name 
 *
 * @return string
 * @param  from_database_name string
 * @param  to_database_name string
 * @param  title string[optional]
 */
function notes_copy_db($from_database_name, $to_database_name, $title = null) {}

/**
 * Creates a Lotus Notes database 
 *
 * @return bool
 * @param  database_name string
 */
function notes_create_db($database_name) {}

/**
 * Creates a note using form form_name 
 *
 * @return string
 * @param  database_name string
 * @param  form_name string
 */
function notes_create_note($database_name, $form_name) {}

/**
 * Drops a Lotus Notes database 
 *
 * @return bool
 * @param  database_name string
 */
function notes_drop_db($database_name) {}

/**
 * Returns a note id found in database_name 
 *
 * @return bool
 * @param  database_name string
 * @param  name string
 * @param  type string[optional]
 */
function notes_find_note($database_name, $name, $type = null) {}

/**
 * Opens the message msg_number in the specified mailbox on the specified server (leave server blank for local) 
 *
 * @return object
 * @param  server string
 * @param  mailbox string
 * @param  msg_number int
 */
function notes_header_info($server, $mailbox, $msg_number) {}

/**
 * ??? 
 *
 * @return bool
 * @param  db string
 */
function notes_list_msgs($db) {}

/**
 * Marks a note_id as read for the User user_name.  Note: user_name must be fully distinguished user name 
 *
 * @return string
 * @param  database_name string
 * @param  user_name string
 * @param  note_id string
 */
function notes_mark_read($database_name, $user_name, $note_id) {}

/**
 * Marks a note_id as unread for the User user_name.  Note: user_name must be fully distinguished user name 
 *
 * @return string
 * @param  database_name string
 * @param  user_name string
 * @param  note_id string
 */
function notes_mark_unread($database_name, $user_name, $note_id) {}

/**
 * Creates a navigator name, in database_name 
 *
 * @return bool
 * @param  database_name string
 * @param  name string
 */
function notes_nav_create($database_name, $name) {}

/**
 * Finds notes that match keywords in database_name.  The note(s) that are returned must be converted to base 16. Example base_convert($note_id, "10", "16") 
 *
 * @return string
 * @param  database_name string
 * @param  keywords string
 */
function notes_search($database_name, $keywords) {}

/**
 * Returns the unread note id's for the current User user_name.  Note: user_name must be fully distinguished user name 
 *
 * @return string
 * @param  database_name string
 * @param  user_name string
 */
function notes_unread($database_name, $user_name) {}

/**
 * Gets the Lotus Notes version 
 *
 * @return string
 * @param  database_name string
 */
function notes_version($database_name) {}

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
 * @return bool
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
 * @return bool
 */
function ob_flush() {}

/**
 * Get current buffer contents and delete current output buffer 
 *
 * @return bool
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
 * @return bool
 */
function ob_get_flush() {}

/**
 * Return the length of the output buffer 
 *
 * @return string
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
 * @return false|array
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
 * @return false|array
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
 * Bind a PHP variable to an Oracle placeholder by name 
 *
 * @return bool
 * @param  stmt int
 * @param  name string
 * @param  var mixed
 * @param  maxlength int[optional]
 * @param  type int[optional]
 */
function ocibindbyname($stmt, $name, &$var, $maxlength = null, $type = null) {}

/**
 * Prepare a new row of data for reading 
 *
 * @return bool
 * @param  stmt int
 */
function ocicancel($stmt) {}









/**
 * Tell whether a column is NULL 
 *
 * @return bool
 * @param  stmt int
 * @param  col int
 */
function ocicolumnisnull($stmt, $col) {}

/**
 * Tell the name of a column 
 *
 * @return string
 * @param  stmt int
 * @param  col int
 */
function ocicolumnname($stmt, $col) {}

/**
 * Tell the precision of a column 
 *
 * @return int
 * @param  stmt int
 * @param  col int
 */
function ocicolumnprecision($stmt, $col) {}

/**
 * Tell the scale of a column 
 *
 * @return int
 * @param  stmt int
 * @param  col int
 */
function ocicolumnscale($stmt, $col) {}

/**
 * Tell the maximum data size of a column 
 *
 * @return int
 * @param  stmt int
 * @param  col int
 */
function ocicolumnsize($stmt, $col) {}

/**
 * Tell the data type of a column 
 *
 * @return mixed
 * @param  stmt int
 * @param  col int
 */
function ocicolumntype($stmt, $col) {}

/**
 * Tell the raw oracle data type of a column 
 *
 * @return mixed
 * @param  stmt int
 * @param  col int
 */
function ocicolumntyperaw($stmt, $col) {}

/**
 * Commit the current context 
 *
 * @return bool
 * @param  conn int
 */
function ocicommit($conn) {}

/**
 * Define a PHP variable to an Oracle column by name 
 *
 * @return bool
 * @param  stmt int
 * @param  name string
 * @param  var mixed
 * @param  type int[optional]
 */
function ocidefinebyname($stmt, $name, &$var, $type = null) {}

/**
 * Return the last error of stmt|conn|global. If no error happened returns false. 
 *
 * @return array
 * @param  stmt|conn|global int[optional]
 */
function ocierror($stmt = null) {}

/**
 * Execute a parsed statement 
 *
 * @return bool
 * @param  stmt int
 * @param  mode int[optional]
 */
function ociexecute($stmt, $mode = null) {}

/**
 * Prepare a new row of data for reading 
 *
 * @return bool
 * @param  stmt int
 */
function ocifetch($stmt) {}

/**
 * Fetch a row of result data into an array 
 *
 * @return int
 * @param  stmt int
 * @param  output array
 * @param  mode int[optional]
 */
function ocifetchinto($stmt, &$output, $mode = null) {}

/**
 * Fetch all rows of result data into an array 
 *
 * @return int
 * @param  stmt int
 * @param  output array
 * @param  skip int
 * @param  maxrows int[optional]
 * @param  flags int[optional]
 */
function ocifetchstatement($stmt, &$output, $skip, $maxrows = null, $flags = null) {}


/**
 * Free all resources associated with a statement 
 *
 * @return bool
 * @param  stmt int
 */
function ocifreecursor($stmt) {}


/**
 * Free all resources associated with a statement 
 *
 * @return bool
 * @param  stmt int
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
 * Disconnect from database 
 *
 * @return bool
 * @param  conn int
 */
function ocilogoff($conn) {}

/**
 * 
 *
 * @return int
 * @param  user string
 * @param  pass string
 * @param  db string[optional]
 */
function ocilogon($user, $pass, $db = null) {}

/**
 * Initialize a new collection 
 *
 * @return bool
 * @param  connection int
 * @param  tdo string
 * @param  schema string[optional]
 */
function ocinewcollection($connection, $tdo, $schema = null) {}

/**
 * Return a new cursor (Statement-Handle) - use this to bind ref-cursors! 
 *
 * @return int
 * @param  conn int
 */
function ocinewcursor($conn) {}

/**
 * Initialize a new empty descriptor LOB/FILE (LOB is default) 
 *
 * @return string
 * @param  connection int
 * @param  type int[optional]
 */
function ocinewdescriptor($connection, $type = null) {}

/**
 * Connect to an Oracle database and log on. returns a new session 
 *
 * @return int
 * @param  user string
 * @param  pass string
 * @param  db string[optional]
 */
function ocinlogon($user, $pass, $db = null) {}

/**
 * Return the number of result columns in a statement 
 *
 * @return int
 * @param  stmt int
 */
function ocinumcols($stmt) {}

/**
 * Parse a query and return a statement 
 *
 * @return int
 * @param  conn int
 * @param  query string
 */
function ociparse($conn, $query) {}

/**
 * changes the password of an account
 *
 * @return bool
 * @param  conn int
 * @param  username string
 * @param  old_password string
 * @param  new_password string
 */
function ocipasswordchange($conn, $username, $old_password, $new_password) {}

/**
 * Connect to an Oracle database using a persistent connection and log on. Returns a new session. 
 *
 * @return int
 * @param  user string
 * @param  pass string
 * @param  db string[optional]
 */
function ociplogon($user, $pass, $db = null) {}

/**
 * Return a single column of result data 
 *
 * @return string
 * @param  stmt int
 * @param  column mixed
 */
function ociresult($stmt, $column) {}

/**
 * Rollback the current context 
 *
 * @return bool
 * @param  conn int
 */
function ocirollback($conn) {}

/**
 * Return the row count of an OCI statement 
 *
 * @return int
 * @param  stmt int
 */
function ocirowcount($stmt) {}



/**
 * Return a string containing server version information 
 *
 * @return string
 * @param  conn int
 */
function ociserverversion($conn) {}

/**
 * sets the number of rows to be prefetched on execute to prefetch_rows for stmt 
 *
 * @return int
 * @param  stmt int
 * @param  prefetch_rows int
 */
function ocisetprefetch($stmt, $prefetch_rows) {}

/**
 * Return the query type of an OCI statement 
 *
 * @return string
 * @param  stmt int
 */
function ocistatementtype($stmt) {}



/**
 * Returns the decimal equivalent of an octal string 
 *
 * @return int
 * @param  octal_number string
 */
function octdec($octal_number) {}

/**
 * Toggle autocommit mode or get status 
 *
 * @return int
 * @param  connection_id int
 * @param  OnOff int[optional]
 */
function odbc_autocommit($connection_id, $OnOff = null) {}

/**
 * Handle binary column data 
 *
 * @return int
 * @param  result_id int
 * @param  mode int
 */
function odbc_binmode($result_id, $mode) {}

/**
 * Close an ODBC connection 
 *
 * @return void
 * @param  connection_id int
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
 * @return int
 * @param  connection_id int
 * @param  catalog string
 * @param  schema string
 * @param  table string
 * @param  column string
 */
function odbc_columnprivileges($connection_id, $catalog, $schema, $table, $column) {}

/**
 * Returns a result identifier that can be used to fetch a list of column names in specified tables 
 *
 * @return int
 * @param  connection_id int
 * @param  qualifier string
 * @param  owner string
 * @param  table_name string
 * @param  column_name string
 */
function odbc_columns($connection_id, $qualifier, $owner, $table_name, $column_name) {}

/**
 * Commit an ODBC transaction 
 *
 * @return int
 * @param  connection_id int
 */
function odbc_commit($connection_id) {}

/**
 * Connect to a datasource 
 *
 * @return int
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
 * @param  result_id int
 */
function odbc_cursor($result_id) {}

/**
 * Return information about the currently connected data source 
 *
 * @return array
 * @param  connection_id int
 * @param  fetch_type int
 */
function odbc_data_source($connection_id, $fetch_type) {}

/**
 * Prepare and execute an SQL statement 
 *
 * @return int
 * @param  connection_id int
 * @param  query string
 * @param  flags int[optional]
 */
function odbc_do($connection_id, $query, $flags = null) {}

/**
 * Get the last error code 
 *
 * @return string
 * @param  connection_id int[optional]
 */
function odbc_error($connection_id = null) {}

/**
 * Get the last error message 
 *
 * @return string
 * @param  connection_id int[optional]
 */
function odbc_errormsg($connection_id = null) {}

/**
 * Prepare and execute an SQL statement 
 *
 * @return int
 * @param  connection_id int
 * @param  query string
 * @param  flags int[optional]
 */
function odbc_exec($connection_id, $query, $flags = null) {}

/**
 * Execute a prepared statement 
 *
 * @return int
 * @param  result_id int
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
 * @return int
 * @param  result_id int
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
 * @return int
 * @param  result_id int
 * @param  row_number int[optional]
 */
function odbc_fetch_row($result_id, $row_number = null) {}

/**
 * Get the length (precision) of a column 
 *
 * @return int
 * @param  result_id int
 * @param  field_number int
 */
function odbc_field_len($result_id, $field_number) {}

/**
 * Get a column name 
 *
 * @return string
 * @param  result_id int
 * @param  field_number int
 */
function odbc_field_name($result_id, $field_number) {}

/**
 * Return column number 
 *
 * @return int
 * @param  result_id int
 * @param  field_name string
 */
function odbc_field_num($result_id, $field_name) {}

/**
 * Get the length (precision) of a column 
 *
 * @return int
 * @param  result_id int
 * @param  field_number int
 */
function odbc_field_precision($result_id, $field_number) {}

/**
 * Get the scale of a column 
 *
 * @return int
 * @param  result_id int
 * @param  field_number int
 */
function odbc_field_scale($result_id, $field_number) {}

/**
 * Get the datatype of a column 
 *
 * @return string
 * @param  result_id int
 * @param  field_number int
 */
function odbc_field_type($result_id, $field_number) {}

/**
 * Returns a result identifier to either a list of foreign keys in the specified table or a list of foreign keys in other tables that refer to the primary key in the specified table 
 *
 * @return int
 * @param  connection_id int
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
 * @return int
 * @param  result_id int
 */
function odbc_free_result($result_id) {}

/**
 * Returns a result identifier containing information about data types supported by the data source 
 *
 * @return int
 * @param  connection_id int
 * @param  data_type int[optional]
 */
function odbc_gettypeinfo($connection_id, $data_type = null) {}

/**
 * Handle LONG columns 
 *
 * @return int
 * @param  result_id int
 * @param  length int
 */
function odbc_longreadlen($result_id, $length) {}

/**
 * Checks if multiple results are avaiable 
 *
 * @return bool
 * @param  result_id int
 */
function odbc_next_result($result_id) {}

/**
 * Get number of columns in a result 
 *
 * @return int
 * @param  result_id int
 */
function odbc_num_fields($result_id) {}

/**
 * Get number of rows in a result 
 *
 * @return int
 * @param  result_id int
 */
function odbc_num_rows($result_id) {}

/**
 * Establish a persistent connection to a datasource 
 *
 * @return int
 * @param  DSN string
 * @param  user string
 * @param  password string
 * @param  cursor_option int[optional]
 */
function odbc_pconnect($DSN, $user, $password, $cursor_option = null) {}

/**
 * Prepares a statement for execution 
 *
 * @return int
 * @param  connection_id int
 * @param  query string
 */
function odbc_prepare($connection_id, $query) {}

/**
 * Returns a result identifier listing the column names that comprise the primary key for a table 
 *
 * @return int
 * @param  connection_id int
 * @param  qualifier string
 * @param  owner string
 * @param  table string
 */
function odbc_primarykeys($connection_id, $qualifier, $owner, $table) {}

/**
 * Returns a result identifier containing the list of input and output parameters, as well as the columns that make up the result set for the specified procedures 
 *
 * @return int
 * @param  connection_id int
 * @param  qualifier string[optional]
 * @param  owner string
 * @param  proc string
 * @param  column string
 */
function odbc_procedurecolumns($connection_id, $qualifier = null, $owner, $proc, $column) {}

/**
 * Returns a result identifier containg the list of procedure names in a datasource 
 *
 * @return int
 * @param  connection_id int
 * @param  qualifier string[optional]
 * @param  owner string
 * @param  name string
 */
function odbc_procedures($connection_id, $qualifier = null, $owner, $name) {}

/**
 * Get result data 
 *
 * @return string
 * @param  result_id int
 * @param  field mixed
 */
function odbc_result($result_id, $field) {}

/**
 * Print result as HTML table 
 *
 * @return int
 * @param  result_id int
 * @param  format string[optional]
 */
function odbc_result_all($result_id, $format = null) {}

/**
 * Rollback a transaction 
 *
 * @return int
 * @param  connection_id int
 */
function odbc_rollback($connection_id) {}

/**
 * Sets connection or statement options 
 *
 * @return int
 * @param  conn_id|result_id int
 * @param  which int
 * @param  option int
 * @param  value int
 */
function odbc_setoption($conn_id, $which, $option, $value) {}

/**
 * Returns a result identifier containing either the optimal set of columns that uniquely identifies a row in the table or columns that are automatically updated when any value in the row is updated by a transaction 
 *
 * @return int
 * @param  connection_id int
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
 * @return int
 * @param  connection_id int
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
 * @return int
 * @param  connection_id int
 * @param  qualifier string
 * @param  owner string
 * @param  name string
 */
function odbc_tableprivileges($connection_id, $qualifier, $owner, $name) {}

/**
 * Call the SQLTables function 
 *
 * @return int
 * @param  connection_id int
 * @param  qualifier string[optional]
 * @param  owner string
 * @param  name string
 * @param  table_types string
 */
function odbc_tables($connection_id, $qualifier = null, $owner, $name, $table_types) {}

/**
 * Open a directory and return a dir_handle 
 *
 * @return mixed
 * @param  path string
 */
function opendir($path) {}

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
 * @return bool
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
 */
function openssl_csr_sign($csr, $x509, $priv_key, $days) {}

/**
 * Returns a description of the last error, and alters the index of the error messages. Returns false when the are no more messages 
 *
 * @return mixed
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
 * @return int
 * @param  key string
 * @param  passphrase string[optional]
 */
function openssl_get_privatekey($key, $passphrase = null) {}

/**
 * Gets public key from X.509 certificate 
 *
 * @return int
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
 */
function openssl_pkcs7_encrypt($infile, $outfile, $recipcerts, $headers, $flags = null) {}

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
 * @return bool
 * @param  filename string
 * @param  flags long
 * @param  signerscerts string[optional]
 * @param  cainfo array[optional]
 * @param  extracerts string[optional]
 */
function openssl_pkcs7_verify($filename, $flags, $signerscerts = null, $cainfo = null, $extracerts = null) {}

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
 * @return int
 * @param  key string
 * @param  passphrase string[optional]
 */
function openssl_pkey_get_private($key, $passphrase = null) {}

/**
 * Gets public key from X.509 certificate 
 *
 * @return int
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
function openssl_private_decrypt($data, &$decrypted, $key, $padding = null) {}

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
 * Bind a PHP variable to an Oracle parameter 
 *
 * @return int
 * @param  cursor int
 * @param  php_variable_name string
 * @param  sql_parameter_name string
 * @param  length int
 * @param  type int[optional]
 */
function ora_bind($cursor, $php_variable_name, $sql_parameter_name, $length, $type = null) {}

/**
 * Close an Oracle cursor 
 *
 * @return int
 * @param  cursor int
 */
function ora_close($cursor) {}

/**
 * Get the name of an Oracle result column 
 *
 * @return string
 * @param  cursor int
 * @param  column int
 */
function ora_columnname($cursor, $column) {}

/**
 * Return the size of the column 
 *
 * @return int
 * @param  cursor int
 * @param  column int
 */
function ora_columnsize($cursor, $column) {}

/**
 * Get the type of an Oracle result column 
 *
 * @return string
 * @param  cursor int
 * @param  column int
 */
function ora_columntype($cursor, $column) {}

/**
 * Commit an Oracle transaction 
 *
 * @return int
 * @param  connection int
 */
function ora_commit($connection) {}

/**
 * Disable automatic commit 
 *
 * @return int
 * @param  connection int
 */
function ora_commitoff($connection) {}

/**
 * Enable automatic commit 
 *
 * @return int
 * @param  connection int
 */
function ora_commiton($connection) {}

/**
 * Parse and execute a statement and fetch first result row 
 *
 * @return int
 * @param  connection int
 * @param  cursor int
 */
function ora_do($connection, $cursor) {}

/**
 * Get an Oracle error message 
 *
 * @return string
 * @param  cursor_or_connection int
 */
function ora_error($cursor_or_connection) {}

/**
 * Get an Oracle error code 
 *
 * @return int
 * @param  cursor_or_connection int
 */
function ora_errorcode($cursor_or_connection) {}

/**
 * Execute a parsed statement 
 *
 * @return int
 * @param  cursor int
 */
function ora_exec($cursor) {}

/**
 * Fetch a row of result data from a cursor 
 *
 * @return int
 * @param  cursor int
 */
function ora_fetch($cursor) {}

/**
 * Fetch a row into the specified result array 
 *
 * @return int
 * @param  cursor int
 * @param  result array
 * @param  flags int[optional]
 */
function ora_fetch_into($cursor, $result, $flags = null) {}

/**
 * Get data from a fetched row 
 *
 * @return mixed
 * @param  cursor int
 * @param  column int
 */
function ora_getcolumn($cursor, $column) {}

/**
 * Close an Oracle connection 
 *
 * @return int
 * @param  connection int
 */
function ora_logoff($connection) {}

/**
 * Open an Oracle connection 
 *
 * @return int
 * @param  user string
 * @param  password string
 */
function ora_logon($user, $password) {}

/**
 * Returns the numbers of columns in a result 
 *
 * @return int
 * @param  cursor int
 */
function ora_numcols($cursor) {}

/**
 * Returns the number of rows in a result 
 *
 * @return int
 * @param  cursor int
 */
function ora_numrows($cursor) {}

/**
 * Open an Oracle cursor 
 *
 * @return int
 * @param  connection int
 */
function ora_open($connection) {}

/**
 * Parse an Oracle SQL statement 
 *
 * @return int
 * @param  cursor int
 * @param  sql_statement string
 * @param  defer int[optional]
 */
function ora_parse($cursor, $sql_statement, $defer = null) {}

/**
 * Open a persistent Oracle connection 
 *
 * @return int
 * @param  user string
 * @param  password string
 */
function ora_plogon($user, $password) {}

/**
 * Roll back an Oracle transaction 
 *
 * @return int
 * @param  connection int
 */
function ora_rollback($connection) {}

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
 * Reset(clear) URL rewriter values 
 *
 * @return bool
 */
function output_reset_rewrite_vars() {}

/**
 * Enables property and method call overloading for a class. 
 *
 * @return void
 * @param  class_entry string
 */
function overload($class_entry) {}

/**
 * 
 *
 * @return int
 * @param  connection_id int
 * @param  OnOff int
 */
function ovrimos_autocommit($connection_id, $OnOff) {}

/**
 * Close a connection 
 *
 * @return void
 * @param  connection int
 */
function ovrimos_close($connection) {}

/**
 * Commit an ovrimos transaction 
 *
 * @return int
 * @param  connection_id int
 */
function ovrimos_commit($connection_id) {}

/**
 * Connect to an Ovrimos database 
 *
 * @return int
 * @param  host string
 * @param  db string
 * @param  user string
 * @param  password string
 */
function ovrimos_connect($host, $db, $user, $password) {}

/**
 * Get cursor name 
 *
 * @return string
 * @param  result_id int
 */
function ovrimos_cursor($result_id) {}

/**
 * Prepare and execute an SQL statement 
 *
 * @return int
 * @param  connection_id int
 * @param  query string
 */
function ovrimos_do($connection_id, $query) {}

/**
 * Prepare and execute an SQL statement 
 *
 * @return int
 * @param  connection_id int
 * @param  query string
 */
function ovrimos_exec($connection_id, $query) {}

/**
 * Execute a prepared statement 
 *
 * @return int
 * @param  result_id int
 * @param  parameters_array array[optional]
 */
function ovrimos_execute($result_id, $parameters_array = null) {}

/**
 * 
 *
 * @return int
 * @param  result_id int
 * @param  result_array array
 * @param  how string[optional]
 * @param  rownumber int[optional]
 */
function ovrimos_fetch_into($result_id, $result_array, $how = null, $rownumber = null) {}

/**
 * 
 *
 * @return int
 * @param  result_id int
 * @param  how int[optional]
 * @param  row_number int[optional]
 */
function ovrimos_fetch_row($result_id, $how = null, $row_number = null) {}

/**
 * Get the length of a column 
 *
 * @return int
 * @param  result_id int
 * @param  field_number int
 */
function ovrimos_field_len($result_id, $field_number) {}

/**
 * Get a column name 
 *
 * @return string
 * @param  result_id int
 * @param  field_number int
 */
function ovrimos_field_name($result_id, $field_number) {}

/**
 * Return column number 
 *
 * @return int
 * @param  result_id int
 * @param  field_name string
 */
function ovrimos_field_num($result_id, $field_name) {}

/**
 * Get the datatype of a column 
 *
 * @return string
 * @param  result_id int
 * @param  field_number int
 */
function ovrimos_field_type($result_id, $field_number) {}

/**
 * Free resources associated with a result 
 *
 * @return int
 * @param  result_id int
 */
function ovrimos_free_result($result_id) {}

/**
 * Handle LONG columns 
 *
 * @return int
 * @param  result_id int
 * @param  length int
 */
function ovrimos_longreadlen($result_id, $length) {}

/**
 * Get number of columns in a result 
 *
 * @return int
 * @param  result_id int
 */
function ovrimos_num_fields($result_id) {}

/**
 * Get number of rows in a result 
 *
 * @return int
 * @param  result_id int
 */
function ovrimos_num_rows($result_id) {}

/**
 * Prepares a statement for execution 
 *
 * @return int
 * @param  connection_id int
 * @param  query string
 */
function ovrimos_prepare($connection_id, $query) {}

/**
 * Get result data 
 *
 * @return string
 * @param  result_id int
 * @param  field mixed
 */
function ovrimos_result($result_id, $field) {}

/**
 * Print result as HTML table 
 *
 * @return int
 * @param  result_id int
 * @param  format string[optional]
 */
function ovrimos_result_all($result_id, $format = null) {}

/**
 * Rollback a transaction 
 *
 * @return int
 * @param  connection_id int
 */
function ovrimos_rollback($connection_id) {}

/**
 * Sets connection or statement options 
 *
 * @return int
 * @param  conn_id|result_id int
 * @param  which int
 * @param  option int
 * @param  value int
 */
function ovrimos_setoption($conn_id, $which, $option, $value) {}

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
 */
function parse_url($url) {}

/**
 * Execute an external program and display raw output 
 *
 * @return void
 * @param  command string
 * @param  return_value int[optional]
 */
function passthru($command, $return_value = null) {}

/**
 * Returns information about a certain string 
 *
 * @return array
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
 * @return bool
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
 * Assigns a system signal handler to a PHP function 
 *
 * @return bool
 * @param  signo long
 * @param  handle mixed
 * @param  restart_syscalls bool[optional]
 */
function pcntl_signal($signo, $handle, $restart_syscalls = null) {}

/**
 * Waits on or returns the status of a forked child as defined by the waitpid() system call 
 *
 * @return int
 * @param  pid long
 * @param  status long
 * @param  options long
 */
function pcntl_waitpid($pid, $status, $options) {}

/**
 * Returns the status code of a child's exit 
 *
 * @return int
 * @param  status long
 */
function pcntl_wexitstatus($status) {}

/**
 * Returns true if the child status code represents a successful exit 
 *
 * @return bool
 * @param  status long
 */
function pcntl_wifexited($status) {}

/**
 * Returns true if the child status code represents a process that was terminated due to a signal 
 *
 * @return bool
 * @param  status long
 */
function pcntl_wifsignaled($status) {}

/**
 * Returns true if the child status code represents a stopped process (WUNTRACED must have been used with waitpid) 
 *
 * @return bool
 * @param  status long
 */
function pcntl_wifstopped($status) {}

/**
 * Returns the number of the signal that caused the process to stop who's status code is passed 
 *
 * @return int
 * @param  status long
 */
function pcntl_wstopsig($status) {}

/**
 * Returns the number of the signal that terminated the process who's status code is passed  
 *
 * @return int
 * @param  status long
 */
function pcntl_wtermsig($status) {}

/**
 * Sets annotation (depreciated use pdf_add_note instead) 
 *
 * @return void
 * @param  pdfdoc int
 * @param  xll float
 * @param  yll float
 * @param  xur float
 * @param  xur float
 * @param  title string
 * @param  text string
 */
function pdf_add_annotation($pdfdoc, $xll, $yll, $xur, $xur, $title, $text) {}

/**
 * Adds bookmark for current page 
 *
 * @return int
 * @param  pdfdoc int
 * @param  text string
 * @param  parent int[optional]
 * @param  open int
 */
function pdf_add_bookmark($pdfdoc, $text, $parent = null, $open) {}

/**
 * Adds link to web resource 
 *
 * @return void
 * @param  pdfdoc int
 * @param  llx float
 * @param  lly float
 * @param  urx float
 * @param  ury float
 * @param  filename string
 */
function pdf_add_launchlink($pdfdoc, $llx, $lly, $urx, $ury, $filename) {}

/**
 * Adds link to web resource 
 *
 * @return void
 * @param  pdfdoc int
 * @param  llx float
 * @param  lly float
 * @param  urx float
 * @param  ury float
 * @param  page int
 * @param  dest string
 */
function pdf_add_locallink($pdfdoc, $llx, $lly, $urx, $ury, $page, $dest) {}

/**
 * Sets annotation 
 *
 * @return void
 * @param  pdfdoc int
 * @param  llx float
 * @param  lly float
 * @param  urx float
 * @param  ury float
 * @param  contents string
 * @param  title string
 * @param  icon string
 * @param  open int
 */
function pdf_add_note($pdfdoc, $llx, $lly, $urx, $ury, $contents, $title, $icon, $open) {}

/**
 * Adds bookmark for current page 
 *
 * @return int
 * @param  pdfdoc int
 * @param  text string
 * @param  parent int[optional]
 * @param  open int
 */
function pdf_add_outline($pdfdoc, $text, $parent = null, $open) {}

/**
 * Adds link to PDF document 
 *
 * @return void
 * @param  pdfdoc int
 * @param  llx float
 * @param  lly float
 * @param  urx float
 * @param  ury float
 * @param  filename string
 * @param  page int
 * @param  dest string
 */
function pdf_add_pdflink($pdfdoc, $llx, $lly, $urx, $ury, $filename, $page, $dest) {}

/**
 * * Add an existing image as thumbnail for the current page. 
 *
 * @return void
 * @param  pdf int
 * @param  image int
 */
function pdf_add_thumbnail($pdf, $image) {}

/**
 * Adds link to web resource 
 *
 * @return void
 * @param  pdfdoc int
 * @param  llx float
 * @param  lly float
 * @param  urx float
 * @param  ury float
 * @param  url string
 */
function pdf_add_weblink($pdfdoc, $llx, $lly, $urx, $ury, $url) {}

/**
 * Draws an arc 
 *
 * @return void
 * @param  pdfdoc int
 * @param  x float
 * @param  y float
 * @param  radius float
 * @param  start float
 * @param  end float
 */
function pdf_arc($pdfdoc, $x, $y, $radius, $start, $end) {}

/**
 * * Draw a clockwise circular arc from alpha to beta degrees. 
 *
 * @return void
 * @param  pdf int
 * @param  x float
 * @param  y float
 * @param  r float
 * @param  alpha float
 * @param  beta float
 */
function pdf_arcn($pdf, $x, $y, $r, $alpha, $beta) {}

/**
 * Adds a file attachment annotation at the rectangle specified by his lower left and upper right corners 
 *
 * @return void
 * @param  pdf int
 * @param  lly float
 * @param  lly float
 * @param  urx float
 * @param  ury float
 * @param  filename string
 * @param  description string
 * @param  author string
 * @param  mimetype string
 * @param  icon string
 */
function pdf_attach_file($pdf, $lly, $lly, $urx, $ury, $filename, $description, $author, $mimetype, $icon) {}

/**
 * Starts page 
 *
 * @return void
 * @param  pdfdoc int
 * @param  width float
 * @param  height float
 */
function pdf_begin_page($pdfdoc, $width, $height) {}

/**
 * * Start a new pattern definition. 
 *
 * @return int
 * @param  pdf int
 * @param  width float
 * @param  height float
 * @param  xstep float
 * @param  ystep float
 * @param  painttype int
 */
function pdf_begin_pattern($pdf, $width, $height, $xstep, $ystep, $painttype) {}

/**
 * * Start a new template definition. 
 *
 * @return int
 * @param  pdf int
 * @param  width float
 * @param  height float
 */
function pdf_begin_template($pdf, $width, $height) {}

/**
 * Draws a circle 
 *
 * @return void
 * @param  pdfdoc int
 * @param  x float
 * @param  y float
 * @param  radius float
 */
function pdf_circle($pdfdoc, $x, $y, $radius) {}

/**
 * Clips to current path 
 *
 * @return void
 * @param  pdfdoc int
 */
function pdf_clip($pdfdoc) {}

/**
 * Closes the pdf document 
 *
 * @return void
 * @param  pdfdoc int
 */
function pdf_close($pdfdoc) {}

/**
 * Closes the PDF image 
 *
 * @return void
 * @param  pdf int
 * @param  pdfimage int
 */
function pdf_close_image($pdf, $pdfimage) {}

/**
 * * Close all open page handles, and close the input PDF document. 
 *
 * @return void
 * @param  pdf int
 * @param  doc int
 */
function pdf_close_pdi($pdf, $doc) {}

/**
 * * Close the page handle, and free all page-related resources. 
 *
 * @return void
 * @param  pdf int
 * @param  page int
 */
function pdf_close_pdi_page($pdf, $page) {}

/**
 * Close path 
 *
 * @return void
 * @param  pdfdoc int
 */
function pdf_closepath($pdfdoc) {}

/**
 * Close, fill and stroke current path 
 *
 * @return void
 * @param  pdfdoc int
 */
function pdf_closepath_fill_stroke($pdfdoc) {}

/**
 * Close path and draw line along path 
 *
 * @return void
 * @param  pdfdoc int
 */
function pdf_closepath_stroke($pdfdoc) {}

/**
 * Concatenates a matrix to the current transformation matrix for text and graphics 
 *
 * @return void
 * @param  pdf int
 * @param  a float
 * @param  b float
 * @param  c float
 * @param  d float
 * @param  e float
 * @param  f float
 */
function pdf_concat($pdf, $a, $b, $c, $d, $e, $f) {}

/**
 * Output text in next line 
 *
 * @return void
 * @param  pdfdoc int
 * @param  text string
 */
function pdf_continue_text($pdfdoc, $text) {}

/**
 * Draws a curve 
 *
 * @return void
 * @param  pdfdoc int
 * @param  x1 float
 * @param  y1 float
 * @param  x2 float
 * @param  y2 float
 * @param  x3 float
 * @param  y3 float
 */
function pdf_curveto($pdfdoc, $x1, $y1, $x2, $y2, $x3, $y3) {}

/**
 * Deletes the PDF object 
 *
 * @return bool
 * @param  pdfdoc int
 */
function pdf_delete($pdfdoc) {}

/**
 * Ends page 
 *
 * @return void
 * @param  pdfdoc int
 */
function pdf_end_page($pdfdoc) {}

/**
 * * Finish the pattern definition. 
 *
 * @return void
 * @param  pdf int
 */
function pdf_end_pattern($pdf) {}

/**
 * * Finish the template definition. 
 *
 * @return void
 * @param  pdf int
 */
function pdf_end_template($pdf) {}

/**
 * Ends current path 
 *
 * @return void
 * @param  pdfdoc int
 */
function pdf_endpath($pdfdoc) {}

/**
 * Fill current path 
 *
 * @return void
 * @param  pdfdoc int
 */
function pdf_fill($pdfdoc) {}

/**
 * Fill and stroke current path 
 *
 * @return void
 * @param  pdfdoc int
 */
function pdf_fill_stroke($pdfdoc) {}

/**
 * Prepares the font fontname for later use with pdf_setfont() 
 *
 * @return int
 * @param  pdfdoc int
 * @param  fontname string
 * @param  encoding string
 * @param  embed int[optional]
 */
function pdf_findfont($pdfdoc, $fontname, $encoding, $embed = null) {}

/**
 * Fetches the full buffer containig the generated PDF data 
 *
 * @return int
 * @param  pdfdoc int
 */
function pdf_get_buffer($pdfdoc) {}

/**
 * Gets the current font 
 *
 * @return int
 * @param  pdfdoc int
 */
function pdf_get_font($pdfdoc) {}

/**
 * Gets the current font name 
 *
 * @return string
 * @param  pdfdoc int
 */
function pdf_get_fontname($pdfdoc) {}

/**
 * Gets the current font size 
 *
 * @return float
 * @param  pdfdoc int
 */
function pdf_get_fontsize($pdfdoc) {}

/**
 * Returns the height of an image 
 *
 * @return int
 * @param  pdf int
 * @param  pdfimage int
 */
function pdf_get_image_height($pdf, $pdfimage) {}

/**
 * Returns the width of an image 
 *
 * @return int
 * @param  pdf int
 * @param  pdfimage int
 */
function pdf_get_image_width($pdf, $pdfimage) {}

/**
 * Returns the major version number of the PDFlib 
 *
 * @return int
 */
function pdf_get_majorversion() {}

/**
 * Returns the minor version number of the PDFlib 
 *
 * @return int
 */
function pdf_get_minorversion() {}

/**
 * Gets arbitrary parameters 
 *
 * @return string
 * @param  pdfdoc int
 * @param  key string
 * @param  modifier mixed
 */
function pdf_get_parameter($pdfdoc, $key, $modifier) {}

/**
 * * Get the contents of some PDI document parameter with string type. 
 *
 * @return string
 * @param  pdf int
 * @param  key string
 * @param  doc int
 * @param  page int
 * @param  index int
 */
function pdf_get_pdi_parameter($pdf, $key, $doc, $page, $index) {}

/**
 * * Get the contents of some PDI document parameter with numerical type. 
 *
 * @return float
 * @param  pdf int
 * @param  key string
 * @param  doc int
 * @param  page int
 * @param  index int
 */
function pdf_get_pdi_value($pdf, $key, $doc, $page, $index) {}

/**
 * Gets arbitrary value 
 *
 * @return float
 * @param  pdfdoc int
 * @param  key string
 * @param  modifier float
 */
function pdf_get_value($pdfdoc, $key, $modifier) {}

/**
 * * Reset all implicit color and graphics state parameters to their defaults. 
 *
 * @return void
 * @param  pdf int
 */
function pdf_initgraphics($pdf) {}

/**
 * Draws a line 
 *
 * @return void
 * @param  pdfdoc int
 * @param  x float
 * @param  y float
 */
function pdf_lineto($pdfdoc, $x, $y) {}

/**
 * * Make a named spot color from the current color. 
 *
 * @return int
 * @param  pdf int
 * @param  spotname string
 */
function pdf_makespotcolor($pdf, $spotname) {}

/**
 * Sets current point 
 *
 * @return void
 * @param  pdfdoc int
 * @param  x float
 * @param  y float
 */
function pdf_moveto($pdfdoc, $x, $y) {}

/**
 * Creates a new PDF object 
 *
 * @return int
 */
function pdf_new() {}

/**
 * Opens a new pdf document. If filedesc is NULL, document is created in memory. This is the old interface, only for compatibility use pdf_new + pdf_open_file instead 
 *
 * @return int
 * @param  filedesc int[optional]
 */
function pdf_open($filedesc = null) {}

/**
 * Opens an image file with raw CCITT G3 or G4 compresed bitmap data 
 *
 * @return int
 * @param  pdf int
 * @param  filename string
 * @param  width int
 * @param  height int
 * @param  bitreverse int
 * @param  k int
 * @param  blackls1 int
 */
function pdf_open_ccitt($pdf, $filename, $width, $height, $bitreverse, $k, $blackls1) {}

/**
 * Opens a new PDF document. If filename is NULL, document is created in memory. This is not yet fully supported 
 *
 * @return int
 * @param  pdfdoc int
 * @param  filename char[optional]
 */
function pdf_open_file($pdfdoc, $filename = null) {}

/**
 * Opens a GIF file and returns an image for placement in a pdf object 
 *
 * @return int
 * @param  pdf int
 * @param  giffile string
 */
function pdf_open_gif($pdf, $giffile) {}

/**
 * Opens an image of the given type and returns an image for placement in a PDF document 
 *
 * @return int
 * @param  pdf int
 * @param  type string
 * @param  source string
 * @param  data string
 * @param  length long
 * @param  width int
 * @param  height int
 * @param  components int
 * @param  bpc int
 * @param  params string
 */
function pdf_open_image($pdf, $type, $source, $data, $length, $width, $height, $components, $bpc, $params) {}

/**
 * Opens an image file of the given type and returns an image for placement in a PDF document 
 *
 * @return int
 * @param  pdf int
 * @param  type string
 * @param  file string
 * @param  stringparam string
 * @param  intparam int
 */
function pdf_open_image_file($pdf, $type, $file, $stringparam, $intparam) {}

/**
 * Opens a JPEG file and returns an image for placement in a PDF document 
 *
 * @return int
 * @param  pdf int
 * @param  jpegfile string
 */
function pdf_open_jpeg($pdf, $jpegfile) {}

/**
 * Takes an GD image and returns an image for placement in a PDF document 
 *
 * @return int
 * @param  pdf int
 * @param  image int
 */
function pdf_open_memory_image($pdf, $image) {}

/**
 * * Open an existing PDF document and prepare it for later use. 
 *
 * @return int
 * @param  pdf int
 * @param  filename string
 * @param  stringparam string
 * @param  intparam int
 */
function pdf_open_pdi($pdf, $filename, $stringparam, $intparam) {}

/**
 * * Prepare a page for later use with PDF_place_image(). 
 *
 * @return int
 * @param  pdf int
 * @param  doc int
 * @param  page int
 * @param  label string
 */
function pdf_open_pdi_page($pdf, $doc, $page, $label) {}

/**
 * Opens a PNG file and returns an image for placement in a PDF document 
 *
 * @return int
 * @param  pdf int
 * @param  pngfile string
 */
function pdf_open_png($pdf, $pngfile) {}

/**
 * Opens a TIFF file and returns an image for placement in a PDF document 
 *
 * @return int
 * @param  pdf int
 * @param  tifffile string
 */
function pdf_open_tiff($pdf, $tifffile) {}

/**
 * Places image in the PDF document 
 *
 * @return void
 * @param  pdf int
 * @param  pdfimage int
 * @param  x float
 * @param  y float
 * @param  scale float
 */
function pdf_place_image($pdf, $pdfimage, $x, $y, $scale) {}

/**
 * * Place a PDF page with the lower left corner at (x, y), and scale it. 
 *
 * @return void
 * @param  pdf int
 * @param  page int
 * @param  x float
 * @param  y float
 * @param  sx float
 * @param  sy float
 */
function pdf_place_pdi_page($pdf, $page, $x, $y, $sx, $sy) {}

/**
 * Draws a rectangle 
 *
 * @return void
 * @param  pdfdoc int
 * @param  x float
 * @param  y float
 * @param  width float
 * @param  height float
 */
function pdf_rect($pdfdoc, $x, $y, $width, $height) {}

/**
 * Restores formerly saved enviroment 
 *
 * @return void
 * @param  pdfdoc int
 */
function pdf_restore($pdfdoc) {}

/**
 * Sets rotation 
 *
 * @return void
 * @param  pdfdoc int
 * @param  angle float
 */
function pdf_rotate($pdfdoc, $angle) {}

/**
 * Saves current enviroment 
 *
 * @return void
 * @param  pdfdoc int
 */
function pdf_save($pdfdoc) {}

/**
 * Sets scaling 
 *
 * @return void
 * @param  pdfdoc int
 * @param  x_scale float
 * @param  y_scale float
 */
function pdf_scale($pdfdoc, $x_scale, $y_scale) {}

/**
 * Sets color of box surounded all kinds of annotations and links 
 *
 * @return void
 * @param  pdfdoc int
 * @param  red float
 * @param  green float
 * @param  blue float
 */
function pdf_set_border_color($pdfdoc, $red, $green, $blue) {}

/**
 * Sets the border dash style of all kinds of annotations and links 
 *
 * @return void
 * @param  pdfdoc int
 * @param  black float
 * @param  white float
 */
function pdf_set_border_dash($pdfdoc, $black, $white) {}

/**
 * Sets style of box surounding all kinds of annotations and link 
 *
 * @return void
 * @param  pdfdoc int
 * @param  style string
 * @param  width float
 */
function pdf_set_border_style($pdfdoc, $style, $width) {}

/**
 * Sets character spacing 
 *
 * @return void
 * @param  pdfdoc int
 * @param  space float
 */
function pdf_set_char_spacing($pdfdoc, $space) {}

/**
 * Sets duration between pages 
 *
 * @return void
 * @param  pdfdoc int
 * @param  duration float
 */
function pdf_set_duration($pdfdoc, $duration) {}

/**
 * Select the current font face, size and encoding 
 *
 * @return void
 * @param  pdfdoc int
 * @param  font string
 * @param  size float
 * @param  encoding string
 * @param  embed int[optional]
 */
function pdf_set_font($pdfdoc, $font, $size, $encoding, $embed = null) {}

/**
 * Sets horizontal scaling of text 
 *
 * @return void
 * @param  pdfdoc int
 * @param  scale float
 */
function pdf_set_horiz_scaling($pdfdoc, $scale) {}

/**
 * Fills an info field of the document 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  fieldname string
 * @param  value string
 */
function pdf_set_info($pdfdoc, $fieldname, $value) {}

/**
 * Fills the author field of the document 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  author string
 */
function pdf_set_info_author($pdfdoc, $author) {}

/**
 * Fills the creator field of the document 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  creator string
 */
function pdf_set_info_creator($pdfdoc, $creator) {}

/**
 * Fills the keywords field of the document 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  keywords string
 */
function pdf_set_info_keywords($pdfdoc, $keywords) {}

/**
 * Fills the subject field of the document 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  subject string
 */
function pdf_set_info_subject($pdfdoc, $subject) {}

/**
 * Fills the title field of the document 
 *
 * @return bool
 * @param  pdfdoc int
 * @param  title string
 */
function pdf_set_info_title($pdfdoc, $title) {}

/**
 * Sets distance between text lines 
 *
 * @return void
 * @param  pdfdoc int
 * @param  distance float
 */
function pdf_set_leading($pdfdoc, $distance) {}

/**
 * Sets arbitrary parameters 
 *
 * @return void
 * @param  pdfdoc int
 * @param  key string
 * @param  value string
 */
function pdf_set_parameter($pdfdoc, $key, $value) {}

/**
 * Sets the position of text for the next pdf_show call 
 *
 * @return void
 * @param  pdfdoc int
 * @param  x float
 * @param  y float
 */
function pdf_set_text_pos($pdfdoc, $x, $y) {}

/**
 * Determines how text is rendered 
 *
 * @return void
 * @param  pdfdoc int
 * @param  mode int
 */
function pdf_set_text_rendering($pdfdoc, $mode) {}

/**
 * Sets the text rise 
 *
 * @return void
 * @param  pdfdoc int
 * @param  value float
 */
function pdf_set_text_rise($pdfdoc, $value) {}

/**
 * Sets transition between pages 
 *
 * @return void
 * @param  pdfdoc int
 * @param  transition int
 */
function pdf_set_transition($pdfdoc, $transition) {}

/**
 * Sets arbitrary value 
 *
 * @return void
 * @param  pdfdoc int
 * @param  key string
 * @param  value float
 */
function pdf_set_value($pdfdoc, $key, $value) {}

/**
 * Sets spacing between words 
 *
 * @return void
 * @param  pdfdoc int
 * @param  space float
 */
function pdf_set_word_spacing($pdfdoc, $space) {}

/**
 * * Set the current color space and color. 
 *
 * @return void
 * @param  pdf int
 * @param  type string
 * @param  colorspace string
 * @param  c1 float
 * @param  c2 float[optional]
 * @param  c3 float[optional]
 * @param  c4 float[optional]
 */
function pdf_setcolor($pdf, $type, $colorspace, $c1, $c2 = null, $c3 = null, $c4 = null) {}

/**
 * Sets dash pattern 
 *
 * @return void
 * @param  pdfdoc int
 * @param  black float
 * @param  white float
 */
function pdf_setdash($pdfdoc, $black, $white) {}

/**
 * Sets flatness 
 *
 * @return void
 * @param  pdfdoc int
 * @param  value float
 */
function pdf_setflat($pdfdoc, $value) {}

/**
 * Sets the current font in the fiven fontsize 
 *
 * @return void
 * @param  pdfdoc int
 * @param  font int
 * @param  fontsize float
 */
function pdf_setfont($pdfdoc, $font, $fontsize) {}

/**
 * Sets drawing and filling color to gray value 
 *
 * @return void
 * @param  pdfdoc int
 * @param  value float
 */
function pdf_setgray($pdfdoc, $value) {}

/**
 * Sets filling color to gray value 
 *
 * @return void
 * @param  pdfdoc int
 * @param  value float
 */
function pdf_setgray_fill($pdfdoc, $value) {}

/**
 * Sets drawing color to gray value 
 *
 * @return void
 * @param  pdfdoc int
 * @param  value float
 */
function pdf_setgray_stroke($pdfdoc, $value) {}

/**
 * Sets linecap parameter 
 *
 * @return void
 * @param  pdfdoc int
 * @param  value int
 */
function pdf_setlinecap($pdfdoc, $value) {}

/**
 * Sets linejoin parameter 
 *
 * @return void
 * @param  pdfdoc int
 * @param  value int
 */
function pdf_setlinejoin($pdfdoc, $value) {}

/**
 * Sets line width 
 *
 * @return void
 * @param  pdfdoc int
 * @param  width float
 */
function pdf_setlinewidth($pdfdoc, $width) {}

/**
 * Explicitly set the current transformation matrix. 
 *
 * @return void
 * @param  pdf int
 * @param  a float
 * @param  b float
 * @param  c float
 * @param  d float
 * @param  e float
 * @param  f float
 */
function pdf_setmatrix($pdf, $a, $b, $c, $d, $e, $f) {}

/**
 * Sets miter limit 
 *
 * @return void
 * @param  pdfdoc int
 * @param  value float
 */
function pdf_setmiterlimit($pdfdoc, $value) {}

/**
 * Sets more complicated dash pattern 
 *
 * @return void
 * @param  pdfdoc int
 * @param  darray float
 */
function pdf_setpolydash($pdfdoc, $darray) {}

/**
 * Sets drawing and filling color to RGB color value 
 *
 * @return void
 * @param  pdfdoc int
 * @param  red float
 * @param  green float
 * @param  blue float
 */
function pdf_setrgbcolor($pdfdoc, $red, $green, $blue) {}

/**
 * Sets filling color to RGB color value 
 *
 * @return void
 * @param  pdfdoc int
 * @param  red float
 * @param  green float
 * @param  blue float
 */
function pdf_setrgbcolor_fill($pdfdoc, $red, $green, $blue) {}

/**
 * Sets drawing color to RGB color value 
 *
 * @return void
 * @param  pdfdoc int
 * @param  red float
 * @param  green float
 * @param  blue float
 */
function pdf_setrgbcolor_stroke($pdfdoc, $red, $green, $blue) {}

/**
 * Output text at current position 
 *
 * @return void
 * @param  pdfdoc int
 * @param  text string
 */
function pdf_show($pdfdoc, $text) {}

/**
 * Output text formated in a boxed 
 *
 * @return int
 * @param  pdfdoc int
 * @param  text string
 * @param  x_koor float
 * @param  y_koor float
 * @param  width float
 * @param  height float
 * @param  mode string
 * @param  feature string[optional]
 */
function pdf_show_boxed($pdfdoc, $text, $x_koor, $y_koor, $width, $height, $mode, $feature = null) {}

/**
 * Output text at position 
 *
 * @return void
 * @param  pdfdoc int
 * @param  text string
 * @param  x_koor float
 * @param  y_koor float
 */
function pdf_show_xy($pdfdoc, $text, $x_koor, $y_koor) {}

/**
 * Skew the coordinate system 
 *
 * @return void
 * @param  pdfdoc int
 * @param  xangle float
 * @param  yangle float
 */
function pdf_skew($pdfdoc, $xangle, $yangle) {}

/**
 * Returns width of text in current font 
 *
 * @return float
 * @param  pdfdoc int
 * @param  text string
 * @param  font int[optional]
 * @param  size float
 */
function pdf_stringwidth($pdfdoc, $text, $font = null, $size) {}

/**
 * Draw line along path path 
 *
 * @return void
 * @param  pdfdoc int
 */
function pdf_stroke($pdfdoc) {}

/**
 * Sets origin of coordinate system 
 *
 * @return void
 * @param  pdfdoc int
 * @param  x float
 * @param  y float
 */
function pdf_translate($pdfdoc, $x, $y) {}

/**
 * Shuts down the Payflow Pro library 
 *
 * @return void
 */
function pfpro_cleanup() {}

/**
 * Initializes the Payflow Pro library 
 *
 * @return void
 */
function pfpro_init() {}

/**
 * Payflow Pro transaction processing using arrays 
 *
 * @return array
 * @param  parmlist array
 * @param  hostaddress string[optional]
 * @param  port int[optional]
 * @param  timeout int[optional]
 * @param  proxyAddress string[optional]
 * @param  proxyPort int[optional]
 * @param  proxyLogon string[optional]
 * @param  proxyPassword string[optional]
 */
function pfpro_process($parmlist, $hostaddress = null, $port = null, $timeout = null, $proxyAddress = null, $proxyPort = null, $proxyLogon = null, $proxyPassword = null) {}

/**
 * Raw Payflow Pro transaction processing 
 *
 * @return string
 * @param  parmlist string
 * @param  hostaddress string[optional]
 * @param  port int[optional]
 * @param  timeout int[optional]
 * @param  proxyAddress string[optional]
 * @param  proxyPort int[optional]
 * @param  proxyLogon string[optional]
 * @param  proxyPassword string[optional]
 */
function pfpro_process_raw($parmlist, $hostaddress = null, $port = null, $timeout = null, $proxyAddress = null, $proxyPort = null, $proxyLogon = null, $proxyPassword = null) {}

/**
 * Returns the version of the Payflow Pro library 
 *
 * @return string
 */
function pfpro_version() {}

/**
 * Open persistent Internet or Unix domain socket connection 
 *
 * @return int
 * @param  hostname string
 * @param  port int
 * @param  errno int[optional]
 * @param  errstr string[optional]
 * @param  timeout float[optional]
 * @param  context resource[optional]
 */
function pfsockopen($hostname, $port, $errno = null, $errstr = null, $timeout = null, $context = null) {}

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
 * Fetch all rows into array 
 *
 * @return array
 * @param  result resource
 */
function pg_fetch_all($result) {}

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
 */
function pg_fetch_object($result, $row = null) {}

/**
 * Returns values from a result identifier 
 *
 * @return mixed
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
 * @return resource
 * @param  connection resource[optional]
 * @param  result_type unknown
 */
function pg_get_notify($connection = null, $result_type) {}

/**
 * Get backend(server) pid 
 *
 * @return resource
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
 * @param  connection resource
 */
function pg_ping($connection) {}

/**
 * Return the port number associated with the connection 
 *
 * @return int
 * @param  connection resource[optional]
 */
function pg_port($connection = null) {}

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
 * Returns values from a result identifier 
 *
 * @return mixed
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
 * Set internal row offset 
 *
 * @return mixed
 * @param  result resource
 * @param  offset int
 */
function pg_result_seek($result, $offset) {}

/**
 * Get status of query result 
 *
 * @return int
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
 * Send asynchronous query 
 *
 * @return bool
 * @param  connection resource
 * @param  qeury string
 */
function pg_send_query($connection, $qeury) {}

/**
 * Set client encoding 
 *
 * @return int
 * @param  connection resource[optional]
 * @param  encoding string
 */
function pg_set_client_encoding($connection = null, $encoding) {}

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
 * Return information about the system PHP was built on 
 *
 * @return string
 */
function php_uname() {}

/**
 * Prints the list of people who've contributed to the PHP project 
 *
 * @return void
 * @param  flag int[optional]
 */
function phpcredits($flag = null) {}

/**
 * Output a page of useful information about PHP and the current request 
 *
 * @return void
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
 * Convert PNG image to WBMP image 
 *
 * @return void
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
 * @return int
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
 */
function preg_match($pattern, $subject, $subpatterns = null, $flags = null) {}

/**
 * Perform a Perl-style global regular expression match 
 *
 * @return int
 * @param  pattern string
 * @param  subject string
 * @param  subpatterns array
 * @param  flags int[optional]
 */
function preg_match_all($pattern, $subject, $subpatterns, $flags = null) {}

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
 * @return string
 * @param  regex mixed
 * @param  replace mixed
 * @param  subject mixed
 * @param  limit int[optional]
 */
function preg_replace($regex, $replace, $subject, $limit = null) {}

/**
 * Perform Perl-style regular expression replacement using replacement callback. 
 *
 * @return string
 * @param  regex mixed
 * @param  callback mixed
 * @param  subject mixed
 * @param  limit int[optional]
 */
function preg_replace_callback($regex, $callback, $subject, $limit = null) {}

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
 * Run a process with more control over it's file descriptors 
 *
 * @return resource
 * @param  command string
 * @param  descriptorspec array
 * @param  pipes array
 */
function proc_open($command, $descriptorspec, &$pipes) {}

/**
 * Adds a word to a personal list 
 *
 * @return int
 * @param  pspell int
 * @param  word string
 */
function pspell_add_to_personal($pspell, $word) {}

/**
 * Adds a word to the current session 
 *
 * @return int
 * @param  pspell int
 * @param  word string
 */
function pspell_add_to_session($pspell, $word) {}

/**
 * Returns true if word is valid 
 *
 * @return int
 * @param  pspell int
 * @param  word string
 */
function pspell_check($pspell, $word) {}

/**
 * Clears the current session 
 *
 * @return int
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
 * Ignore words <= n chars 
 *
 * @return int
 * @param  conf int
 * @param  ignore int
 */
function pspell_config_ignore($conf, $ignore) {}

/**
 * Select mode for config (PSPELL_FAST, PSPELL_NORMAL or PSPELL_BAD_SPELLERS) 
 *
 * @return int
 * @param  conf int
 * @param  mode long
 */
function pspell_config_mode($conf, $mode) {}

/**
 * Use a personal dictionary for this config 
 *
 * @return int
 * @param  conf int
 * @param  personal string
 */
function pspell_config_personal($conf, $personal) {}

/**
 * Use a personal dictionary with replacement pairs for this config 
 *
 * @return int
 * @param  conf int
 * @param  repl string
 */
function pspell_config_repl($conf, $repl) {}

/**
 * Consider run-together words as valid components 
 *
 * @return int
 * @param  conf int
 * @param  runtogether bool
 */
function pspell_config_runtogether($conf, $runtogether) {}

/**
 * Save replacement pairs when personal list is saved for this config 
 *
 * @return int
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
 * @return int
 * @param  pspell int
 */
function pspell_save_wordlist($pspell) {}

/**
 * Notify the dictionary of a user-selected replacement 
 *
 * @return int
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
 * Returns the error string from the last QDOM operation or FALSE if no errors occured.
 *
 * @return string
 */
function qdom_error() {}

/**
 * creates a tree of an xml string 
 *
 * @return object
 * @param  string unknown
 */
function qdom_tree($string) {}

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
 */
function range($low, $high) {}

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
 * @return array|false
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
 * @param  use_include_path int[optional]
 */
function readfile($filename, $use_include_path = null) {}

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
 * @return void
 * @param  prompt string[optional]
 */
function readline_add_history($prompt = null) {}

/**
 * Clears the history 
 *
 * @return void
 */
function readline_clear_history() {}

/**
 * Readline completion function? 
 *
 * @return void
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
 * Reads the history 
 *
 * @return int
 * @param  filename string[optional]
 * @param  from int[optional]
 * @param  to int[optional]
 */
function readline_read_history($filename = null, $from = null, $to = null) {}

/**
 * Writes the history 
 *
 * @return int
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
 * Rename a file 
 *
 * @return bool
 * @param  old_name string
 * @param  new_name string
 */
function rename($old_name, $new_name) {}

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
 * @return void
 */
function restore_error_handler() {}

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
 */
function rmdir($dirname) {}

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
 * Acquires the semaphore with the given id, blocking if necessary 
 *
 * @return int
 * @param  id int
 */
function sem_acquire($id) {}

/**
 * Return an id for the semaphore with the given key, and allow max_acquire (default 1) processes to acquire it simultaneously 
 *
 * @return int
 * @param  key int
 * @param  max_acquire int[optional]
 * @param  perm int[optional]
 * @param  auto_release int[optional]
 */
function sem_get($key, $max_acquire = null, $perm = null, $auto_release = null) {}

/**
 * Releases the semaphore with the given id 
 *
 * @return int
 * @param  id int
 */
function sem_release($id) {}

/**
 * Removes semaphore from Unix systems 
 *
 * @return int
 * @param  id int
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
 * Update the current session id with a newly generated one. 
 *
 * @return bool
 */
function session_regenerate_id() {}

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
 * @return void
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
 * Sets a user-defined error handler function.  Returns the previously defined error handler, or false on error 
 *
 * @return string
 * @param  error_handler string
 */
function set_error_handler($error_handler) {}

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
 * @param  varname string
 * @param  newvalue string
 */
function set_include_path($varname, $newvalue) {}

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
 * @return bool
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
 */
function sha1($str) {}

/**
 * Calculate the sha1 hash of given filename 
 *
 * @return string
 * @param  filename string
 */
function sha1_file($filename) {}

/**
 * Use pclose() for FILE* that has been opened via popen() 
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
 * @return int
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
 * @return int
 * @param  shm_identifier int
 * @param  variable_key int
 * @param  variable mixed
 */
function shm_put_var($shm_identifier, $variable_key, $variable) {}

/**
 * Removes shared memory from Unix systems 
 *
 * @return int
 * @param  shm_identifier int
 */
function shm_remove($shm_identifier) {}

/**
 * Removes variable from shared memory 
 *
 * @return int
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
 * @return bool
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
 * @return void
 * @param  seconds int
 */
function sleep($seconds) {}

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
 * @return int
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
 * @return int
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
 * @return resource
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
 * Adds a new vector to the scatter/gather array 
 *
 * @return bool
 * @param  iovec resource
 * @param  iov_len int
 */
function socket_iovec_add($iovec, $iov_len) {}

/**
 * Builds a 'struct iovec' for use with sendmsg, recvmsg, writev, and readv 
 *
 * @return resource
 * @param  num_vectors int
 * @vararg ... int
 */
function socket_iovec_alloc($num_vectors) {}

/**
 * Deletes a vector from an array of vectors 
 *
 * @return bool
 * @param  iovec resource
 * @param  iov_pos int
 */
function socket_iovec_delete($iovec, $iov_pos) {}

/**
 * Returns the data held in the iovec specified by iovec_id[iovec_position] 
 *
 * @return string
 * @param  iovec resource
 * @param  iovec_position int
 */
function socket_iovec_fetch($iovec, $iovec_position) {}

/**
 * Frees the iovec specified by iovec_id 
 *
 * @return bool
 * @param  iovec resource
 */
function socket_iovec_free($iovec) {}

/**
 * Sets the data held in iovec_id[iovec_position] to new_val 
 *
 * @return bool
 * @param  iovec resource
 * @param  iovec_position int
 * @param  new_val string
 */
function socket_iovec_set($iovec, $iovec_position, $new_val) {}

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
 * Reads from an fd, using the scatter-gather array defined by iovec_id 
 *
 * @return bool
 * @param  socket resource
 * @param  iovec_id resource
 */
function socket_readv($socket, $iovec_id) {}

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
 * Used to receive messages on a socket, whether connection-oriented or not 
 *
 * @return bool
 * @param  socket resource
 * @param  iovec resource
 * @param  control array
 * @param  controllen int
 * @param  flags int
 * @param  addr string
 * @param  port int[optional]
 */
function socket_recvmsg($socket, $iovec, &$control, &$controllen, &$flags, &$addr, &$port) {}

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
 * Sends a message to a socket, regardless of whether it is connection-oriented or not 
 *
 * @return bool
 * @param  socket resource
 * @param  iovec resource
 * @param  flags int
 * @param  addr string
 * @param  port int[optional]
 */
function socket_sendmsg($socket, $iovec, $flags, $addr, $port = null) {}

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
 * Writes to a file descriptor, fd, using the scatter-gather array defined by iovec_id 
 *
 * @return bool
 * @param  socket resource
 * @param  iovec_id resource
 */
function socket_writev($socket, $iovec_id) {}

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
 * @return bool
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
 * @return bool
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
 * @return boolean
 * @param  query string
 * @param  db resource
 * @param  error_message string
 */
function sqlite_exec($query, $db, &$error_message) {}

/**
 * Opens a SQLite database and creates an object for it. Will create the database if it does not exist. 
 *
 * @return object
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
 * @return resource
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
 */
function str_replace($search, $replace, $subject) {}

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
 * @return void
 * @param  str string
 */
function str_shuffle($str) {}

/**
 * 
 *
 * @return mixed
 * @param  str string
 * @param  format int[optional]
 */
function str_word_count($str, $format = null) {}

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
 * Create a file context and optionally set parameters 
 *
 * @return resource
 * @param  options array[optional]
 */
function stream_context_create($options = null) {}

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
 * Append a filter to a stream 
 *
 * @return bool
 * @param  stream resource
 * @param  filtername string
 * @param  filterparams string
 */
function stream_filter_append($stream, $filtername, $filterparams) {}

/**
 * Prepend a filter to a stream 
 *
 * @return bool
 * @param  stream resource
 * @param  filtername string
 * @param  filterparams string
 */
function stream_filter_prepend($stream, $filtername, $filterparams) {}

/**
 * Retrieves header/meta data from streams/file pointers 
 *
 * @return resource
 * @param  fp resource
 */
function stream_get_meta_data($fp) {}

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
 * Registers a custom URL protocol handler class 
 *
 * @return bool
 * @param  protocol string
 * @param  classname string
 */
function stream_wrapper_register($protocol, $classname) {}

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
 * Finds position of first occurrence of a string within another 
 *
 * @return int
 * @param  haystack string
 * @param  needle string
 * @param  offset int[optional]
 */
function strpos($haystack, $needle, $offset = null) {}

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
 * Finds position of last occurrence of a character in a string within another 
 *
 * @return int
 * @param  haystack string
 * @param  needle string
 */
function strrpos($haystack, $needle) {}

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
 * @param  now int
 */
function strtotime($time, $now) {}

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
 * Returns the number of times a substring occurs in the string 
 *
 * @return int
 * @param  haystack string
 * @param  needle string
 */
function substr_count($haystack, $needle) {}

/**
 * Replaces part of a string with another string 
 *
 * @return string
 * @param  str string
 * @param  repl string
 * @param  start int
 * @param  length int[optional]
 */
function substr_replace($str, $repl, $start, $length = null) {}

/**
 * Gets the specified url 
 *
 * @return void
 * @param  url string
 * @param  target string
 */
function swf_actiongeturl($url, $target) {}

/**
 * Causes the Flash movie to display the specified frame, frame_number, and then stop. 
 *
 * @return void
 * @param  frame_number int
 */
function swf_actiongotoframe($frame_number) {}

/**
 * Causes the flash movie to display the frame with the given label and then stop 
 *
 * @return void
 * @param  label string
 */
function swf_actiongotolabel($label) {}

/**
 * Goes foward one frame 
 *
 * @return void
 */
function swf_actionnextframe() {}

/**
 * Starts playing the Flash movie from the current frame 
 *
 * @return void
 */
function swf_actionplay() {}

/**
 * Goes backward one frame 
 *
 * @return void
 */
function swf_actionprevframe() {}

/**
 * Sets the context for actions 
 *
 * @return void
 * @param  target string
 */
function swf_actionsettarget($target) {}

/**
 * Stops playing the Flash movie at the current frame 
 *
 * @return void
 */
function swf_actionstop() {}

/**
 * Toggles between high and low quality 
 *
 * @return void
 */
function swf_actiontogglequality() {}

/**
 * If the specified frame has not been loaded, skip the specified number of actions in the action list 
 *
 * @return void
 * @param  frame int
 * @param  skipcount int
 */
function swf_actionwaitforframe($frame, $skipcount) {}

/**
 * Controls the location, appearance and active area of the current button 
 *
 * @return void
 * @param  state int
 * @param  objid int
 * @param  depth int
 */
function swf_addbuttonrecord($state, $objid, $depth) {}

/**
 * Set the global add color to the rgba value specified 
 *
 * @return void
 * @param  r float
 * @param  g float
 * @param  b float
 * @param  a float
 */
function swf_addcolor($r, $g, $b, $a) {}

/**
 * Close a Shockwave flash file that was opened with swf_openfile 
 *
 * @return void
 */
function swf_closefile() {}

/**
 * Defines a bitmap given the name of a .gif .rgb .jpeg or .fi image. The image will be converted into Flash jpeg or Flash color map format 
 *
 * @return void
 * @param  objid int
 * @param  imgname string
 */
function swf_definebitmap($objid, $imgname) {}

/**
 * Defines a font. name specifies the PostScript name of the font to use. This font also becomes the current font.  
 *
 * @return void
 * @param  fontid int
 * @param  name string
 */
function swf_definefont($fontid, $name) {}

/**
 * Create a line with object id, objid, starting from x1, y1 and going to x2, y2 with width, width 
 *
 * @return void
 * @param  objid int
 * @param  x1 float
 * @param  y1 float
 * @param  x2 float
 * @param  y2 float
 * @param  width float
 */
function swf_defineline($objid, $x1, $y1, $x2, $y2, $width) {}

/**
 * Define a Polygon from an array of x,y coordinates, coords. 
 *
 * @return void
 * @param  obj_id int
 * @param  coords array
 * @param  npoints int
 * @param  width float
 */
function swf_definepoly($obj_id, $coords, $npoints, $width) {}

/**
 * Create a rectangle with object id, objid, the upper lefthand coordinate is given by x1, y1 the bottom right coordinate is x2, y2 and with is the width of the line 
 *
 * @return void
 * @param  objid int
 * @param  x1 float
 * @param  y1 float
 * @param  x2 float
 * @param  y2 float
 * @param  width float
 */
function swf_definerect($objid, $x1, $y1, $x2, $y2, $width) {}

/**
 * defines a text string using the current font, current fontsize and current font slant. If docCenter is 1, the word is centered in x 
 *
 * @return void
 * @param  objid int
 * @param  str string
 * @param  docCenter int
 */
function swf_definetext($objid, $str, $docCenter) {}

/**
 * Complete the definition of the current button 
 *
 * @return void
 */
function swf_endbutton() {}

/**
 * Ends the list of actions to perform for the current frame 
 *
 * @return void
 */
function swf_enddoaction() {}

/**
 * Completes the definition of the current shape 
 *
 * @return void
 */
function swf_endshape() {}

/**
 * End the current symbol 
 *
 * @return void
 */
function swf_endsymbol() {}

/**
 * Sets the current font's height to the value specified by height 
 *
 * @return void
 * @param  height float
 */
function swf_fontsize($height) {}

/**
 * Set the current font slant to the angle indicated by slant 
 *
 * @return void
 * @param  slant float
 */
function swf_fontslant($slant) {}

/**
 * Sets the current font tracking to the specified value, track 
 *
 * @return void
 * @param  track unknown
 */
function swf_fonttracking($track) {}

/**
 * Returns an array of information about a bitmap specified by bitmapid 
 *
 * @return array
 * @param  bitmapid int
 */
function swf_getbitmapinfo($bitmapid) {}

/**
 * Get information about the current font 
 *
 * @return array
 */
function swf_getfontinfo() {}

/**
 * Returns the current frame 
 *
 * @return int
 */
function swf_getframe() {}

/**
 * Adds string name to the current frame 
 *
 * @return void
 * @param  name string
 */
function swf_labelframe($name) {}

/**
 * Defines a viewing transformation by giving the view position vx, vy, vz, and the coordinates of a reference point in the scene at px, py, pz. Twist controls a rotation along the viewer's z axis 
 *
 * @return void
 * @param  vx float
 * @param  vy float
 * @param  vz float
 * @param  px float
 * @param  py float
 * @param  pz float
 * @param  twist float
 */
function swf_lookat($vx, $vy, $vz, $px, $py, $pz, $twist) {}

/**
 * Updates the position and/or color of the object 
 *
 * @return void
 * @param  depth int
 * @param  how int
 */
function swf_modifyobject($depth, $how) {}

/**
 * Sets the global multiply color to the rgba value specified 
 *
 * @return void
 * @param  r float
 * @param  g float
 * @param  b float
 * @param  a float
 */
function swf_mulcolor($r, $g, $b, $a) {}

/**
 * Returns a free objid 
 *
 * @return int
 */
function swf_nextid() {}

/**
 * Describes a transition used to trigger an action list 
 *
 * @return void
 * @param  transitions int
 */
function swf_oncondition($transitions) {}

/**
 * Create a Shockwave Flash file given by name, with width xsize and height ysize at a frame rate of framerate and a background color specified by a red value of r, green value of g and a blue value of b 
 *
 * @return void
 * @param  name string
 * @param  xsize float
 * @param  ysize float
 * @param  framerate float
 * @param  r float
 * @param  g float
 * @param  b float
 */
function swf_openfile($name, $xsize, $ysize, $framerate, $r, $g, $b) {}

/**
 * Defines an orthographic mapping of user coordinates onto the current viewport 
 *
 * @return void
 * @param  xmin float
 * @param  xmax float
 * @param  ymin float
 * @param  ymax float
 * @param  zmin float
 * @param  zmax float
 */
function swf_ortho($xmin, $xmax, $ymin, $ymax, $zmin, $zmax) {}

/**
 * Defines a 2-D orthographic mapping of user coordinates onto the current viewport 
 *
 * @return void
 * @param  xmin float
 * @param  xmax float
 * @param  ymin float
 * @param  ymax float
 */
function swf_ortho2($xmin, $xmax, $ymin, $ymax) {}

/**
 * Define a perspective projection transformation. 
 *
 * @return void
 * @param  fovy float
 * @param  aspect float
 * @param  near float
 * @param  far float
 */
function swf_perspective($fovy, $aspect, $near, $far) {}

/**
 * Places the object, objid, in the current frame at depth, depth 
 *
 * @return void
 * @param  objid int
 * @param  depth int
 */
function swf_placeobject($objid, $depth) {}

/**
 * Defines he viewer's position in polar coordinates 
 *
 * @return void
 * @param  dist float
 * @param  azimuth float
 * @param  incidence float
 * @param  twist float
 */
function swf_polarview($dist, $azimuth, $incidence, $twist) {}

/**
 * Restore a previous transformation matrix 
 *
 * @return void
 */
function swf_popmatrix() {}

/**
 * This enables or disables rounding of the translation when objects are places or moved 
 *
 * @return void
 * @param  doit int
 */
function swf_posround($doit) {}

/**
 * Push the current transformation matrix onto the stack 
 *
 * @return void
 */
function swf_pushmatrix() {}

/**
 * Removes the object at the specified depth 
 *
 * @return void
 * @param  depth int
 */
function swf_removeobject($depth) {}

/**
 * Rotate the current transformation by the given angle about x, y, or z axis. The axis may be 'x', 'y', or 'z' 
 *
 * @return void
 * @param  angle float
 * @param  axis string
 */
function swf_rotate($angle, $axis) {}

/**
 * Scale the current transformation 
 *
 * @return void
 * @param  x float
 * @param  y float
 * @param  z float
 */
function swf_scale($x, $y, $z) {}

/**
 * Sets fontid to the current font 
 *
 * @return void
 * @param  fontid int
 */
function swf_setfont($fontid) {}

/**
 * Set the current frame number to the number given by frame_number 
 *
 * @return void
 * @param  frame_number int
 */
function swf_setframe($frame_number) {}

/**
 * Draws a circular arc from ang1 to ang2. The center of the circle is given by x, and y. r specifies the radius of the arc 
 *
 * @return void
 * @param  x float
 * @param  y float
 * @param  r float
 * @param  ang1 float
 * @param  ang2 float
 */
function swf_shapearc($x, $y, $r, $ang1, $ang2) {}

/**
 * Draws a quadratic bezier curve starting at the current position using x1, y1 as an off curve control point and using x2, y2 as the end point. The current position is then set to x2, y2. 
 *
 * @return void
 * @param  x1 float
 * @param  y1 float
 * @param  x2 float
 * @param  y2 float
 */
function swf_shapecurveto($x1, $y1, $x2, $y2) {}

/**
 * Draws a cubic bezier curve starting at the current position using x1, y1 and x2, y2 as off curve control points and using x3,y3 as the end point.  The current position is then sent to x3, y3 
 *
 * @return void
 * @param  x1 float
 * @param  y1 float
 * @param  x2 float
 * @param  y2 float
 * @param  x3 float
 * @param  y3 float
 */
function swf_shapecurveto3($x1, $y1, $x2, $y2, $x3, $y3) {}

/**
 * Sets the current fill mode to clipped bitmap fill. Pixels from the previously defined bitmapid will be used to fill areas 
 *
 * @return void
 * @param  bitmapid int
 */
function swf_shapefillbitmapclip($bitmapid) {}

/**
 * Sets the current fill mode to tiled bitmap fill. Pixels from the previously defined bitmapid will be used to fill areas 
 *
 * @return void
 * @param  bitmapid int
 */
function swf_shapefillbitmaptile($bitmapid) {}

/**
 * Turns off filling 
 *
 * @return void
 */
function swf_shapefilloff() {}

/**
 * Sets the current fill style to a solid fill with the specified rgba color 
 *
 * @return void
 * @param  r float
 * @param  g float
 * @param  b float
 * @param  a float
 */
function swf_shapefillsolid($r, $g, $b, $a) {}

/**
 * Create a line with color defined by rgba, and a width of width 
 *
 * @return void
 * @param  r float
 * @param  g float
 * @param  b float
 * @param  a float
 * @param  width float
 */
function swf_shapelinesolid($r, $g, $b, $a, $width) {}

/**
 * Draws a line from the current position to x,y, the current position is then set to x,y 
 *
 * @return void
 * @param  x float
 * @param  y float
 */
function swf_shapelineto($x, $y) {}

/**
 * swf_shapemoveto moves the current position to the given x,y. 
 *
 * @return void
 * @param  x float
 * @param  y float
 */
function swf_shapemoveto($x, $y) {}

/**
 * Finish the current frame 
 *
 * @return void
 */
function swf_showframe() {}

/**
 * Start a button with an object id, objid and a type of either TYPE_MENUBUTTON or TYPE_PUSHBUTTON 
 *
 * @return void
 * @param  objid int
 * @param  type int
 */
function swf_startbutton($objid, $type) {}

/**
 * Starts the description of an action list for the current frame 
 *
 * @return void
 */
function swf_startdoaction() {}

/**
 * Initialize a new shape with object id, objid 
 *
 * @return void
 * @param  objid int
 */
function swf_startshape($objid) {}

/**
 * Create a new symbol with object id, objid 
 *
 * @return void
 * @param  objid int
 */
function swf_startsymbol($objid) {}

/**
 * Calculates the width of a string, str, using the current fontsize & current font 
 *
 * @return void
 * @param  str string
 */
function swf_textwidth($str) {}

/**
 * Translate the current transformation 
 *
 * @return void
 * @param  x float
 * @param  y float
 * @param  z float
 */
function swf_translate($x, $y, $z) {}

/**
 * Selects an area on the drawing surface for future drawing 
 *
 * @return void
 * @param  xmin float
 * @param  xmax float
 * @param  ymin float
 * @param  ymax float
 */
function swf_viewport($xmin, $xmax, $ymin, $ymax) {}








/**
 * Returns the action flag for keyPress(char) 
 *
 * @return int
 * @param  str string
 */
function swfbutton_keypress($str) {}



















































































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
 * @return int
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
 * @return int
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
 * @return int
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
 */
function sybase_set_message_handler($error_func) {}

/**
 * Send Sybase query 
 *
 * @return int
 * @param  query string
 * @param  link_id int[optional]
 */
function sybase_unbuffered_query($query, $link_id = null) {}

/**
 * Create a symbolic link 
 *
 * @return int
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
 * @return int
 * @param  command string
 * @param  return_value int[optional]
 */
function system($command, $return_value = null) {}

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
 * Return current UNIX timestamp 
 *
 * @return int
 */
function time() {}

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
 * @return void
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
 * Add mnoGoSearch search restrictions 
 *
 * @return int
 * @param  agent int
 * @param  var int
 * @param  val string
 */
function udm_add_search_limit($agent, $var, $val) {}

/**
 * Allocate mnoGoSearch session 
 *
 * @return int
 * @param  dbaddr string
 * @param  dbmode string[optional]
 */
function udm_alloc_agent($dbaddr, $dbmode = null) {}

/**
 * Allocate mnoGoSearch session 
 *
 * @return int
 * @param  dbaddr array
 */
function udm_alloc_agent_array($dbaddr) {}

/**
 * Get mnoGoSearch API version 
 *
 * @return int
 */
function udm_api_version() {}

/**
 * Get mnoGoSearch categories list with the same root 
 *
 * @return array
 * @param  agent int
 * @param  category string
 */
function udm_cat_list($agent, $category) {}

/**
 * Get mnoGoSearch categories path from the root to the given catgory 
 *
 * @return array
 * @param  agent int
 * @param  category string
 */
function udm_cat_path($agent, $category) {}

/**
 * Check if the given charset is known to mnogosearch 
 *
 * @return int
 * @param  agent int
 * @param  charset string
 */
function udm_check_charset($agent, $charset) {}

/**
 * Open connection to stored  
 *
 * @return int
 * @param  agent int
 * @param  link int
 * @param  doc_id string
 */
function udm_check_stored($agent, $link, $doc_id) {}

/**
 * Clear all mnoGoSearch search restrictions 
 *
 * @return int
 * @param  agent int
 */
function udm_clear_search_limits($agent) {}

/**
 * Open connection to stored  
 *
 * @return int
 * @param  agent int
 * @param  link int
 */
function udm_close_stored($agent, $link) {}

/**
 * Return CRC32 checksum of gived string 
 *
 * @return int
 * @param  agent int
 * @param  str string
 */
function udm_crc32($agent, $str) {}

/**
 * Get mnoGoSearch error number 
 *
 * @return int
 * @param  agent int
 */
function udm_errno($agent) {}

/**
 * Get mnoGoSearch error message 
 *
 * @return string
 * @param  agent int
 */
function udm_error($agent) {}

/**
 * Perform search 
 *
 * @return int
 * @param  agent int
 * @param  query string
 */
function udm_find($agent, $query) {}

/**
 * Free mnoGoSearch session 
 *
 * @return int
 * @param  agent int
 */
function udm_free_agent($agent) {}

/**
 * Free memory allocated for ispell data 
 *
 * @return int
 * @param  agent int
 */
function udm_free_ispell_data($agent) {}

/**
 * mnoGoSearch free result 
 *
 * @return int
 * @param  res int
 */
function udm_free_res($res) {}

/**
 * Get total number of documents in database 
 *
 * @return int
 * @param  agent int
 */
function udm_get_doc_count($agent) {}

/**
 * Fetch mnoGoSearch result field 
 *
 * @return string
 * @param  res int
 * @param  row int
 * @param  field int
 */
function udm_get_res_field($res, $row, $field) {}

/**
 * Fetch mnoGoSearch result field 
 *
 * @return string
 * @param  res int
 * @param  row int
 * @param  field string
 */
function udm_get_res_field_ex($res, $row, $field) {}

/**
 * Get mnoGoSearch result parameters 
 *
 * @return string
 * @param  res int
 * @param  param int
 */
function udm_get_res_param($res, $param) {}

/**
 * Return Hash32 checksum of gived string 
 *
 * @return int
 * @param  agent int
 * @param  str string
 */
function udm_hash32($agent, $str) {}

/**
 * Load ispell data 
 *
 * @return int
 * @param  agent int
 * @param  var int
 * @param  val1 string
 * @param  charset string[optional]
 * @param  val2 string
 * @param  flag int
 */
function udm_load_ispell_data($agent, $var, $val1, $charset = null, $val2, $flag) {}

/**
 * Perform search 
 *
 * @return int
 * @param  agent int
 * @param  res int
 * @param  row int
 */
function udm_make_excerpt($agent, $res, $row) {}

/**
 * Open connection to stored  
 *
 * @return int
 * @param  agent int
 * @param  storedaddr string
 */
function udm_open_stored($agent, $storedaddr) {}

/**
 * Parses query string, initialises variables and search limits taken from it 
 *
 * @return int
 * @param  agent int
 * @param  str string
 */
function udm_parse_query_string($agent, $str) {}

/**
 * Set mnoGoSearch agent session parameters 
 *
 * @return int
 * @param  agent int
 * @param  var int
 * @param  val string
 */
function udm_set_agent_param($agent, $var, $val) {}

/**
 * Set mnoGoSearch agent session parameters extended 
 *
 * @return int
 * @param  agent int
 * @param  var string
 * @param  val string
 */
function udm_set_agent_param_ex($agent, $var, $val) {}

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
 * @param  prefix string
 * @param  more_entropy bool[optional]
 */
function uniqid($prefix, $more_entropy = null) {}

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
 */
function unlink($filename) {}

/**
 * Unpack binary string into named array elements according to format argument 
 *
 * @return array
 * @param  format string
 * @param  input string
 */
function unpack($format, $input) {}

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
 * 
 *
 * @return bool
 * @param  on bool[optional]
 */
function use_soap_error_handler($on = null) {}

/**
 * Generates a user-level error/warning/notice message 
 *
 * @return void
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
 * @param  pass sting
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
 * @return int
 * @param  ver1 string
 * @param  ver2 string
 * @param  oper string[optional]
 */
function version_compare($ver1, $ver2, $oper = null) {}

/**
 * Perform an Apache sub-request 
 *
 * @return bool
 * @param  filename string
 */
function virtual($filename) {}

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
 * Turns off attributes for a window 
 *
 * @return int
 * @param  window resource
 * @param  attrs int
 */
function wattroff($window, $attrs) {}

/**
 * Turns on attributes for a window 
 *
 * @return int
 * @param  window resource
 * @param  attrs int
 */
function wattron($window, $attrs) {}

/**
 * Set the attributes for a window 
 *
 * @return int
 * @param  window resource
 * @param  attrs int
 */
function wattrset($window, $attrs) {}

/**
 * Serializes given variables and adds them to packet given by packet_id 
 *
 * @return int
 * @param  packet_id int
 * @param  var_names mixed
 * @vararg ... mixed
 */
function wddx_add_vars($packet_id, $var_names) {}

/**
 * Deserializes given packet and returns a PHP value 
 *
 * @return mixed
 * @param  packet string
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
 * @return int
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
 * @param  cut int[optional]
 */
function wordwrap($str, $width = null, $break = null, $cut = null) {}

/**
 * End standout mode for a window 
 *
 * @return int
 * @param  window resource
 */
function wstandend($window) {}

/**
 * Enter standout mode for a window 
 *
 * @return int
 * @param  window resource
 */
function wstandout($window) {}

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
 * @return int
 * @param  parser resource
 */
function xml_parser_free($parser) {}

/**
 * Get options from an XML parser 
 *
 * @return int
 * @param  parser resource
 * @param  option int
 */
function xml_parser_get_option($parser, $option) {}

/**
 * Set options in an XML parser 
 *
 * @return int
 * @param  parser resource
 * @param  option int
 * @param  value mixed
 */
function xml_parser_set_option($parser, $option, $value) {}

/**
 * Set up character data handler 
 *
 * @return int
 * @param  parser resource
 * @param  hdl string
 */
function xml_set_character_data_handler($parser, $hdl) {}

/**
 * Set up default handler 
 *
 * @return int
 * @param  parser resource
 * @param  hdl string
 */
function xml_set_default_handler($parser, $hdl) {}

/**
 * Set up start and end element handlers 
 *
 * @return int
 * @param  parser resource
 * @param  shdl string
 * @param  ehdl string
 */
function xml_set_element_handler($parser, $shdl, $ehdl) {}

/**
 * Set up character data handler 
 *
 * @return int
 * @param  parser resource
 * @param  hdl string
 */
function xml_set_end_namespace_decl_handler($parser, $hdl) {}

/**
 * Set up external entity reference handler 
 *
 * @return int
 * @param  parser resource
 * @param  hdl string
 */
function xml_set_external_entity_ref_handler($parser, $hdl) {}

/**
 * Set up notation declaration handler 
 *
 * @return int
 * @param  parser resource
 * @param  hdl string
 */
function xml_set_notation_decl_handler($parser, $hdl) {}

/**
 * Set up object which should be used for callbacks 
 *
 * @return int
 * @param  parser resource
 * @param  obj object
 */
function xml_set_object($parser, &$obj) {}

/**
 * Set up processing instruction (PI) handler 
 *
 * @return int
 * @param  parser resource
 * @param  hdl string
 */
function xml_set_processing_instruction_handler($parser, $hdl) {}

/**
 * Set up character data handler 
 *
 * @return int
 * @param  parser resource
 * @param  hdl string
 */
function xml_set_start_namespace_decl_handler($parser, $hdl) {}

/**
 * Set up unparsed entity declaration handler 
 *
 * @return int
 * @param  parser resource
 * @param  hdl string
 */
function xml_set_unparsed_entity_decl_handler($parser, $hdl) {}

/**
 * Creates DOM object of XML document 
 *
 * @return object
 * @param  xmldoc string
 * @param  mode int
 * @param  error array
 */
function xmldoc($xmldoc, $mode, $error) {}

/**
 * Creates DOM object of XML document in file 
 *
 * @return object
 * @param  filename string
 * @param  mode int
 * @param  error array
 */
function xmldocfile($filename, $mode, $error) {}

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
 * @return string
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
 * @param  server handle
 * @param  desc array
 */
function xmlrpc_server_add_introspection_data($server, $desc) {}

/**
 * Parses XML requests and call methods 
 *
 * @return mixed
 * @param  server handle
 * @param  xml string
 * @param  user_data mixed
 * @param  output_options array[optional]
 */
function xmlrpc_server_call_method($server, $xml, $user_data, $output_options = null) {}

/**
 * Creates an xmlrpc server 
 *
 * @return handle
 */
function xmlrpc_server_create() {}

/**
 * Destroys server resources 
 *
 * @return void
 * @param  server handle
 */
function xmlrpc_server_destroy($server) {}

/**
 * Register a PHP function to generate documentation 
 *
 * @return bool
 * @param  server handle
 * @param  function string
 */
function xmlrpc_server_register_introspection_callback($server, $function) {}

/**
 * Register a PHP function to handle method matching method_name 
 *
 * @return bool
 * @param  server handle
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
 * Creates a tree of PHP objects from an XML document 
 *
 * @return object
 * @param  xmltree string
 */
function xmltree($xmltree) {}

/**
 * Evaluates the XPath Location Path in the given string 
 *
 * @return object
 * @param  xpathctx_handle object[optional]
 * @param  str string
 */
function xpath_eval($xpathctx_handle = null, $str) {}

/**
 * Evaluates the XPath expression in the given string 
 *
 * @return object
 * @param  xpathctx_handle object[optional]
 * @param  str string
 */
function xpath_eval_expression($xpathctx_handle = null, $str) {}

/**
 * Initializing XPath environment 
 *
 * @return bool
 */
function xpath_init() {}

/**
 * Creates new XPath context 
 *
 * @return object
 * @param  doc_handle int[optional]
 */
function xpath_new_context($doc_handle = null) {}

/**
 * Registeres the given namespace in the passed XPath context 
 *
 * @return bool
 * @param  xpathctx_handle object[optional]
 * @param  namespace_prefix string
 * @param  namespace_uri string
 */
function xpath_register_ns($xpathctx_handle = null, $namespace_prefix, $namespace_uri) {}

/**
 * Evaluates the XPtr Location Path in the given string 
 *
 * @return int
 * @param  xpathctx_handle int[optional]
 * @param  str string
 */
function xptr_eval($xpathctx_handle = null, $str) {}

/**
 * Creates new XPath context 
 *
 * @return object
 * @param  doc_handle int[optional]
 */
function xptr_new_context($doc_handle = null) {}

/**
 * Returns the information on the compilation settings of the backend 
 *
 * @return string
 */
function xslt_backend_info() {}

/**
 * Returns the name of the Backend (here "Sablotron")
 *
 * @return string
 */
function xslt_backend_name() {}

/**
 * Returns the version number of Sablotron (if available) 
 *
 * @return string
 */
function xslt_backend_version() {}

/**
 * Create a new XSLT processor 
 *
 * @return resource
 */
function xslt_create() {}

/**
 * Error number 
 *
 * @return int
 * @param  processor resource
 */
function xslt_errno($processor) {}

/**
 * Error string 
 *
 * @return string
 * @param  processor resource
 */
function xslt_error($processor) {}

/**
 * Free the xslt processor up 
 *
 * @return void
 * @param  processor resource
 */
function xslt_free($processor) {}

/**
 * Get options on a given xsl processor 
 *
 * @return int
 * @param  processor resource
 */
function xslt_getopt($processor) {}

/**
 * Perform the xslt transformation 
 *
 * @return string
 * @param  processor resource
 * @param  xml string
 * @param  xslt string
 * @param  result mixed
 * @param  args array
 * @param  params array
 */
function xslt_process($processor, $xml, $xslt, $result, $args, $params) {}

/**
 * Sets the base URI for all XSLT transformations 
 *
 * @return void
 * @param  processor resource
 * @param  base string
 */
function xslt_set_base($processor, $base) {}

/**
 * Set the output encoding for the current stylesheet 
 *
 * @return void
 * @param  processor resource
 * @param  encoding string
 */
function xslt_set_encoding($processor, $encoding) {}

/**
 * Set the error handler, to be called when an XSLT error happens 
 *
 * @return void
 * @param  processor resource
 * @param  error_func mixed
 */
function xslt_set_error_handler($processor, $error_func) {}

/**
 * Set the log file to write the errors to (defaults to stderr) 
 *
 * @return void
 * @param  processor resource
 * @param  logfile string
 */
function xslt_set_log($processor, $logfile) {}

/**
 * sets the object in which to resolve callback functions 
 *
 * @return int
 * @param  parser resource
 * @param  obj object
 */
function xslt_set_object($parser, $obj) {}

/**
 * Set the SAX handlers to be called when the XML document gets processed 
 *
 * @return void
 * @param  processor resource
 * @param  handlers array
 */
function xslt_set_sax_handlers($processor, $handlers) {}

/**
 * Set the scheme handlers for the XSLT processor 
 *
 * @return void
 * @param  processor resource
 * @param  handlers array
 */
function xslt_set_scheme_handlers($processor, $handlers) {}

/**
 * Set options on a given xsl processor 
 *
 * @return int
 * @param  processor resource
 * @param  newmask int
 */
function xslt_setopt($processor, $newmask) {}

/**
 * Return additional info for last error (empty string if none) 
 *
 * @return string
 * @param  id int
 */
function yaz_addinfo($id) {}

/**
 * Configure CCL package 
 *
 * @return int
 * @param  id int
 * @param  package array
 */
function yaz_ccl_conf($id, $package) {}

/**
 * Parse a CCL query 
 *
 * @return int
 * @param  id int
 * @param  query string
 * @param  res array
 */
function yaz_ccl_parse($id, $query, $res) {}

/**
 * Destory and close target 
 *
 * @return int
 * @param  id int
 */
function yaz_close($id) {}

/**
 * Create target with given zurl. Returns positive id if successful. 
 *
 * @return int
 * @param  zurl_ string
 */
function yaz_connect($zurl_) {}

/**
 * Specify the databases within a session 
 *
 * @return int
 * @param  id int
 * @param  databases string
 */
function yaz_database($id, $databases) {}

/**
 * Set Element-Set-Name for retrieval 
 *
 * @return int
 * @param  id int
 * @param  elementsetname string
 */
function yaz_element($id, $elementsetname) {}

/**
 * Return last error number (>0 for bib-1 diagnostic, <0 for other error, 0 for no error 
 *
 * @return int
 * @param  id int
 */
function yaz_errno($id) {}

/**
 * Return last error message 
 *
 * @return string
 * @param  id int
 */
function yaz_error($id) {}

/**
 * Inspects Extended Services Result 
 *
 * @return int
 * @param  id int
 */
function yaz_es_result($id) {}

/**
 * Return number of hits (result count) for last search 
 *
 * @return int
 * @param  id int
 */
function yaz_hits($id) {}

/**
 * Sends Item Order request 
 *
 * @return int
 * @param  id int
 * @param  package array
 */
function yaz_itemorder($id, $package) {}

/**
 * Retrieve records 
 *
 * @return int
 * @param  id int
 */
function yaz_present($id) {}

/**
 * Set result set start point and number of records to request 
 *
 * @return int
 * @param  id int
 * @param  start int
 * @param  number int
 */
function yaz_range($id, $start, $number) {}

/**
 * Return record information at given result set position 
 *
 * @return string
 * @param  id int
 * @param  pos int
 * @param  type string
 */
function yaz_record($id, $pos, $type) {}

/**
 * Sends Scan Request 
 *
 * @return int
 * @param  id int
 * @param  type unknown
 * @param  query unknown
 * @param  flags unknown[optional]
 */
function yaz_scan($id, $type, $query, $flags = null) {}

/**
 * Inspects Scan Result 
 *
 * @return int
 * @param  id int
 * @param  options array
 */
function yaz_scan_result($id, $options) {}

/**
 * Set Schema for retrieval 
 *
 * @return int
 * @param  id int
 * @param  schema string
 */
function yaz_schema($id, $schema) {}

/**
 * Specify query of type for search - returns true if successful 
 *
 * @return int
 * @param  id int
 * @param  type string
 * @param  query string
 */
function yaz_search($id, $type, $query) {}

/**
 * Set result set sorting criteria 
 *
 * @return int
 * @param  id int
 * @param  sortspec string
 */
function yaz_sort($id, $sortspec) {}

/**
 * Set record syntax for retrieval 
 *
 * @return int
 * @param  id int
 * @param  syntax string
 */
function yaz_syntax($id, $syntax) {}

/**
 * Process events. 
 *
 * @return int
 * @param  options array[optional]
 */
function yaz_wait($options = null) {}

/**
 * Traverse the map and call a function on each entry 
 *
 * @return void
 * @param  domain string
 * @param  map string
 * @param  callback string
 */
function yp_all($domain, $map, $callback) {}

/**
 * Return an array containing the entire map 
 *
 * @return array
 * @param  domain string
 * @param  map string
 */
function yp_cat($domain, $map) {}

/**
 * Returns the corresponding error string for the given error code 
 *
 * @return string
 * @param  errorcode int
 */
function yp_err_string($errorcode) {}

/**
 * Returns the error code from the last call or 0 if no error occured 
 *
 * @return int
 */
function yp_errno() {}

/**
 * Returns the first key as array with $var[$key] and the the line as the value 
 *
 * @return array
 * @param  domain string
 * @param  map string
 */
function yp_first($domain, $map) {}

/**
 * Returns the domain or false 
 *
 * @return string
 */
function yp_get_default_domain() {}

/**
 * Returns the machine name of the master 
 *
 * @return string
 * @param  domain string
 * @param  map string
 */
function yp_master($domain, $map) {}

/**
 * Returns the matched line or false 
 *
 * @return string
 * @param  domain string
 * @param  map string
 * @param  key string
 */
function yp_match($domain, $map, $key) {}

/**
 * Returns an array with $var[$key] and the the line as the value 
 *
 * @return array
 * @param  domain string
 * @param  map string
 * @param  key string
 */
function yp_next($domain, $map, $key) {}

/**
 * Returns the order number or false 
 *
 * @return int
 * @param  domain string
 * @param  map string
 */
function yp_order($domain, $map) {}

/**
 * Return the special ID used to request the Zend logo in phpinfo screens
 *
 * @return string
 */
function zend_logo_guid() {}

/**
 * Get the version of the Zend Engine 
 *
 * @return string
 */
function zend_version() {}

/**
 * Close a Zip archive 
 *
 * @return void
 * @param  zip resource
 */
function zip_close($zip) {}

/**
 * Close a zip entry 
 *
 * @return void
 * @param  zip_ent resource
 */
function zip_entry_close($zip_ent) {}

/**
 * Return the compressed size of a ZZip entry 
 *
 * @return int
 * @param  zip_entry resource
 */
function zip_entry_compressedsize($zip_entry) {}

/**
 * Return a string containing the compression method used on a particular entry 
 *
 * @return string
 * @param  zip_entry resource
 */
function zip_entry_compressionmethod($zip_entry) {}

/**
 * Return the actual filesize of a ZZip entry 
 *
 * @return int
 * @param  zip_entry resource
 */
function zip_entry_filesize($zip_entry) {}

/**
 * Return the name given a ZZip entry 
 *
 * @return string
 * @param  zip_entry resource
 */
function zip_entry_name($zip_entry) {}

/**
 * Open a Zip File, pointed by the resource entry 
 *
 * @return bool
 * @param  zip_dp resource
 * @param  zip_entry resource
 * @param  mode string
 */
function zip_entry_open($zip_dp, $zip_entry, $mode) {}

/**
 * Read X bytes from an opened zip entry 
 *
 * @return string
 * @param  zip_ent resource
 */
function zip_entry_read($zip_ent) {}

/**
 * Open a new zip archive for reading 
 *
 * @return resource
 * @param  filename string
 */
function zip_open($filename) {}

/**
 * Returns the next file in the archive 
 *
 * @return resource
 * @param  zip resource
 */
function zip_read($zip) {}

/**
 * Returns the coding type used for output compression 
 *
 * @return unknown
 */
function zlib_get_coding_type() {}

class OCI_Lob {
    /**
     * Loads a large object 
     *
     * @return string
     * @param  lob object
     */
    function load($lob) {}

    /**
     * Writes a large object into a file 
     *
     * @return bool
     * @param  lob object
     * @param  filename string[optional]
     * @param  start int[optional]
     * @param  length int[optional]
     */
    function writetofile($lob, $filename = null, $start = null, $length = null) {}

    /**
     * Return the row count of an OCI statement 
     *
     * @return bool
     * @param  stmt int
     * @param  loc int
     * @param  var string
     */
    function writetemporary($stmt, $loc, $var) {}

    /**
     * Closes lob descriptor 
     *
     * @return bool
     * @param  lob object
     */
    function close($lob) {}

    /**
     * Saves a large object 
     *
     * @return bool
     * @param  lob object
     */
    function save($lob) {}

    /**
     * Saves a large object file 
     *
     * @return bool
     * @param  lob object
     */
    function savefile($lob) {}

    /**
     * Deletes large object description 
     *
     * @return bool
     * @param  lob object
     */
    function free($lob) {}

};

class OCI_Collection {
    /**
     * Append an object to the collection 
     *
     * @return bool
     * @param  collection object
     * @param  value unknown
     */
    function append($collection, $value) {}

    /**
     * Retrieve the value at collection index ndx 
     *
     * @return string
     * @param  collection object
     * @param  ndx unknown
     */
    function getelem($collection, $ndx) {}

    /**
     * Assign element val to collection at index ndx 
     *
     * @return bool
     * @param  collection object
     * @param  ndx unknown
     * @param  val unknown
     */
    function assignelem($collection, $ndx, $val) {}

    /**
     * Assign a collection from another existing collection 
     *
     * @return bool
     * @param  collection object
     * @param  object unknown
     */
    function assign($collection, $object) {}

    /**
     * Return the size of a collection 
     *
     * @return int
     * @param  collection object
     */
    function size($collection) {}

    /**
     * Return the max value of a collection.  For a varray this is the maximum length of the array 
     *
     * @return int
     * @param  collection object
     */
    function max($collection) {}

    /**
     * Trim num elements from the end of a collection 
     *
     * @return bool
     * @param  collection object
     * @param  num unknown
     */
    function trim($collection, $num) {}

    /**
     * Deletes collection object
     *
     * @return bool
     * @param  lob object
     */
    function free($lob) {}

};

class swfshape {
    /**
     * Returns a new SWFShape object 
     *
     * @return class
     */
    function swfshape() {}

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
     * Returns a fill object, for use with swfshape_setleftfill and swfshape_setrightfill 
     *
     * @return int
     * @param  fill int
     * @param  flags int
     */
    function addfill($fill, $flags) {}

    /**
     * Sets the left side fill style to fill 
     *
     * @return void
     * @param  fill int
     */
    function setleftfill($fill) {}

    /**
     * Sets the right side fill style to fill 
     *
     * @return void
     * @param  fill int
     */
    function setrightfill($fill) {}

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
     * @return void
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
     * @return void
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
     * @param  r int
     */
    function drawcircle($r) {}

    /**
     * Draws an arc of radius r centered at the current location, from angle startAngle to angle endAngle measured counterclockwise from 12 o'clock 
     *
     * @return void
     * @param  r int
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

class swffill {
    /**
     * Returns a new SWFFill object 
     *
     * @return class
     */
    function swffill() {}

    /**
     * Moves this SWFFill to shape coordinates (x,y) 
     *
     * @return void
     * @param  x int
     * @param  y int
     */
    function moveto($x, $y) {}

    /**
     * Scales this SWFFill by xScale in the x direction, yScale in the y, or both to xScale if only one arg 
     *
     * @return void
     * @param  xScale float
     * @param  yScale float[optional]
     */
    function scaleto($xScale, $yScale = null) {}

    /**
     * Rotates this SWFFill the given (clockwise) degrees from its original orientation 
     *
     * @return void
     * @param  degrees float
     */
    function rotateto($degrees) {}

    /**
     * Sets this SWFFill's x skew value to xSkew 
     *
     * @return void
     * @param  xSkew float
     */
    function skewxto($xSkew) {}

    /**
     * Sets this SWFFill's y skew value to ySkew 
     *
     * @return void
     * @param  ySkew float
     */
    function skewyto($ySkew) {}

};

class swfgradient {
    /**
     * Returns a new SWFGradient object 
     *
     * @return class
     */
    function swfgradient() {}

    /**
     * Adds given entry to the gradient 
     *
     * @return void
     * @param  ratio float
     * @param  r string
     * @param  g string
     * @param  b string
     * @param  a string[optional]
     */
    function addentry($ratio, $r, $g, $b, $a = null) {}

};

class swfbitmap {
    /**
     * Returns a new SWFBitmap object from jpg (with optional mask) or dbl file 
     *
     * @return class
     * @param  file unknown
     * @param  maskfile unknown[optional]
     */
    function swfbitmap($file, $maskfile = null) {}

    /**
     * Returns the width of this bitmap 
     *
     * @return void
     */
    function getwidth() {}

    /**
     * Returns the height of this bitmap 
     *
     * @return void
     */
    function getheight() {}

};

class swftext {
    /**
     * Returns new SWFText object 
     *
     * @return class
     */
    function swftext() {}

    /**
     * Sets this SWFText object's current font to given font 
     *
     * @return void
     * @param  font class
     */
    function setfont($font) {}

    /**
     * Sets this SWFText object's current height to given height 
     *
     * @return void
     * @param  height float
     */
    function setheight($height) {}

    /**
     * Sets this SWFText object's current letterspacing to given spacing 
     *
     * @return void
     * @param  spacing float
     */
    function setspacing($spacing) {}

    /**
     * Sets this SWFText object's current color to the given color 
     *
     * @return void
     * @param  r int
     * @param  g int
     * @param  b int
     * @param  a int[optional]
     */
    function setcolor($r, $g, $b, $a = null) {}

    /**
     * Moves this SWFText object's current pen position to (x, y) in text coordinates 
     *
     * @return void
     * @param  x float
     * @param  y float
     */
    function moveto($x, $y) {}

    /**
     * Writes the given text into this SWFText object at the current pen position, using the current font, height, spacing, and color 
     *
     * @return void
     * @param  text string
     */
    function addstring($text) {}

    /**
     * Calculates the width of the given string in this text objects current font and size 
     *
     * @return float
     * @param  str string
     */
    function getwidth($str) {}

    /**
     * Returns the ascent of the current font at its current size, or 0 if not available 
     *
     * @return float
     */
    function getascent() {}

    /**
     * Returns the descent of the current font at its current size, or 0 if not available 
     *
     * @return float
     */
    function getdescent() {}

    /**
     * Returns the leading of the current font at its current size, or 0 if not available 
     *
     * @return float
     */
    function getleading() {}

};

class swftextfield {
    /**
     * Returns a new SWFTextField object 
     *
     * @return object
     * @param  flags int[optional]
     */
    function swftextfield($flags = null) {}

    /**
     * Sets the font for this textfield 
     *
     * @return void
     * @param  font int
     */
    function setfont($font) {}

    /**
     * Sets the width and height of this textfield 
     *
     * @return void
     * @param  width float
     * @param  height float
     */
    function setbounds($width, $height) {}

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
    function setheight($height) {}

    /**
     * Sets the left margin of this textfield 
     *
     * @return void
     * @param  float unknown
     */
    function setleftmargin($float) {}

    /**
     * Sets the right margin of this textfield 
     *
     * @return void
     * @param  margin float
     */
    function setrightmargin($margin) {}

    /**
     * Sets both margins of this textfield 
     *
     * @return void
     * @param  left float
     * @param  right float
     */
    function setmargins($left, $right) {}

    /**
     * Sets the indentation of the first line of this textfield 
     *
     * @return void
     * @param  indentation float
     */
    function setindentation($indentation) {}

    /**
     * Sets the line spacing of this textfield 
     *
     * @return void
     * @param  space float
     */
    function setlinespacing($space) {}

    /**
     * Sets the color of this textfield 
     *
     * @return void
     * @param  r int
     * @param  g int
     * @param  b int
     * @param  a int[optional]
     */
    function setcolor($r, $g, $b, $a = null) {}

    /**
     * Sets the variable name of this textfield 
     *
     * @return void
     * @param  var_name string
     */
    function setname($var_name) {}

    /**
     * Adds the given string to this textfield 
     *
     * @return void
     * @param  str string
     */
    function addstring($str) {}

};

class swffont {
    /**
     * Returns a new SWFFont object from given file 
     *
     * @return class
     * @param  filename string
     */
    function swffont($filename) {}

    /**
     * Calculates the width of the given string in this font at full height 
     *
     * @return int
     * @param  string unknown
     */
    function getwidth($string) {}

    /**
     * Returns the ascent of the font, or 0 if not available 
     *
     * @return int
     */
    function getascent() {}

    /**
     * Returns the descent of the font, or 0 if not available 
     *
     * @return int
     */
    function getdescent() {}

    /**
     * Returns the leading of the font, or 0 if not available 
     *
     * @return int
     */
    function getleading() {}

};

class swfdisplayitem {
    /**
     * Moves this SWFDisplayItem to movie coordinates (x, y) 
     *
     * @return void
     * @param  x int
     * @param  y int
     */
    function moveto($x, $y) {}

    /**
     * Displaces this SWFDisplayItem by (dx, dy) in movie coordinates 
     *
     * @return void
     * @param  dx int
     * @param  dy int
     */
    function move($dx, $dy) {}

    /**
     * Scales this SWFDisplayItem by xScale in the x direction, yScale in the y, or both to xScale if only one arg 
     *
     * @return void
     * @param  xScale float
     * @param  yScale float[optional]
     */
    function scaleto($xScale, $yScale = null) {}

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
    function rotateto($degrees) {}

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
    function skewxto($xSkew) {}

    /**
     * Adds xSkew to this SWFDisplayItem's x skew value 
     *
     * @return void
     * @param  xSkew float
     */
    function skewx($xSkew) {}

    /**
     * Sets this SWFDisplayItem's y skew value to ySkew 
     *
     * @return void
     * @param  ySkew float
     */
    function skewyto($ySkew) {}

    /**
     * Adds ySkew to this SWFDisplayItem's y skew value 
     *
     * @return void
     * @param  ySkew float
     */
    function skewy($ySkew) {}

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
    function setmatrix($a, $b, $c, $d, $x, $y) {}

    /**
     * Sets this SWFDisplayItem's z-depth to depth.  Items with higher depth values are drawn on top of those with lower values 
     *
     * @return void
     * @param  depth int
     */
    function setdepth($depth) {}

    /**
     * Sets this SWFDisplayItem's ratio to ratio.  Obviously only does anything if displayitem was created from an SWFMorph 
     *
     * @return void
     * @param  ratio float
     */
    function setratio($ratio) {}

    /**
     * Sets the add color part of this SWFDisplayItem's CXform to (r, g, b [, a]), a defaults to 0 
     *
     * @return void
     * @param  r int
     * @param  g int
     * @param  b int
     * @param  a int[optional]
     */
    function addcolor($r, $g, $b, $a = null) {}

    /**
     * Sets the multiply color part of this SWFDisplayItem's CXform to (r, g, b [, a]), a defaults to 1.0 
     *
     * @return void
     * @param  r float
     * @param  g float
     * @param  b float
     * @param  a float[optional]
     */
    function multcolor($r, $g, $b, $a = null) {}

    /**
     * Sets this SWFDisplayItem's name to name 
     *
     * @return void
     * @param  name string
     */
    function setname($name) {}

    /**
     * Adds this SWFAction to the given SWFSprite instance 
     *
     * @return void
     * @param  action SWFAction
     * @param  flags int
     */
    function addaction($action, $flags) {}

};

class swfbutton {
    /**
     * Returns a new SWFButton object 
     *
     * @return object
     */
    function swfbutton() {}

    /**
     * Sets the character for this button's hit test state 
     *
     * @return void
     * @param  SWFCharacter unknown
     */
    function sethit($SWFCharacter) {}

    /**
     * Sets the character for this button's over state 
     *
     * @return void
     * @param  SWFCharacter unknown
     */
    function setover($SWFCharacter) {}

    /**
     * Sets the character for this button's up state 
     *
     * @return void
     * @param  SWFCharacter unknown
     */
    function setup($SWFCharacter) {}

    /**
     * Sets the character for this button's down state 
     *
     * @return void
     * @param  SWFCharacter unknown
     */
    function setdown($SWFCharacter) {}

    /**
     * Sets the action to perform when button is pressed 
     *
     * @return void
     * @param  SWFAction unknown
     */
    function setaction($SWFAction) {}

    /**
     * Sets the character to display for the condition described in flags 
     *
     * @return void
     * @param  character SWFCharacter
     * @param  flags int
     */
    function addshape($character, $flags) {}

    /**
     * Sets the action to perform when conditions described in flags is met 
     *
     * @return void
     * @param  action SWFAction
     * @param  flags int
     */
    function addaction($action, $flags) {}

};

class swfaction {
    /**
     * Returns a new SWFAction object, compiling the given script 
     *
     * @return object
     * @param  string unknown
     */
    function swfaction($string) {}

};

class swfmorph {
    /**
     * Returns a new SWFMorph object 
     *
     * @return object
     */
    function swfmorph() {}

    /**
     * Return's this SWFMorph's start shape object 
     *
     * @return object
     */
    function getshape1() {}

    /**
     * Return's this SWFMorph's start shape object 
     *
     * @return object
     */
    function getshape2() {}

};

class swfsprite {
    /**
     * Returns a new SWFSprite object 
     *
     * @return class
     */
    function swfsprite() {}

    /**
     * Adds the character to the sprite, returns a displayitem object 
     *
     * @return object
     * @param  SWFCharacter unknown
     */
    function add($SWFCharacter) {}

    /**
     * Remove the named character from the sprite's display list 
     *
     * @return void
     * @param  SWFDisplayItem unknown
     */
    function remove($SWFDisplayItem) {}

    /**
     * Moves the sprite to the next frame 
     *
     * @return void
     */
    function nextframe() {}

    /**
     * Sets the number of frames in this SWFSprite 
     *
     * @return void
     * @param  frames int
     */
    function setframes($frames) {}

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

};

class domnode {
    /**
     * Creates node 
     *
     * @return object
     * @param  name string
     */
    function domnode($name) {}

    /**
     * Returns name of node 
     *
     * @return object
     */
    function node_name() {}

    /**
     * Returns the type of the node 
     *
     * @return int
     */
    function node_type() {}

    /**
     * Returns name of value 
     *
     * @return object
     */
    function node_value() {}

    /**
     * Returns first child from list of children 
     *
     * @return object
     */
    function first_child() {}

    /**
     * Returns last child from list of children 
     *
     * @return object
     */
    function last_child() {}

    /**
     * Returns list of children nodes 
     *
     * @return array
     */
    function children() {}

    /**
     * Returns list of children nodes 
     *
     * @return array
     */
    function child_nodes() {}

    /**
     * Returns previous child from list of children 
     *
     * @return object
     */
    function previous_sibling() {}

    /**
     * Returns next child from list of children 
     *
     * @return object
     */
    function next_sibling() {}

    /**
     * Returns true if node has children 
     *
     * @return object
     */
    function has_child_nodes() {}

    /**
     * Returns parent of node 
     *
     * @return object
     */
    function parent() {}

    /**
     * Returns parent of node 
     *
     * @return object
     */
    function parent_node() {}

    /**
     * Adds node in list of nodes before given node 
     *
     * @return object
     * @param  newnode object
     * @param  refnode object
     */
    function insert_before($newnode, $refnode) {}

    /**
     * Adds node to list of children 
     *
     * @return object
     * @param  domnode object
     */
    function append_child($domnode) {}

    /**
     * Removes node from list of children 
     *
     * @return object
     * @param  domnode object
     */
    function remove_child($domnode) {}

    /**
     * Replaces node in list of children 
     *
     * @return object
     * @param  newnode object
     * @param  oldnode object
     */
    function replace_child($newnode, $oldnode) {}

    /**
     * Returns document this node belongs to 
     *
     * @return object
     */
    function owner_document() {}

    /**
     * Adds child node to parent node 
     *
     * @return object
     * @param  name string
     * @param  content string
     */
    function new_child($name, $content) {}

    /**
     * Returns list of attributes of node 
     *
     * @return array
     */
    function attributes() {}

    /**
     * Returns true if node has attributes 
     *
     * @return object
     */
    function has_attributes() {}

    /**
     * Returns namespace prefix of node 
     *
     * @return string
     */
    function prefix() {}

    /**
     * Returns namespace uri of node 
     *
     * @return string
     */
    function namespace_uri() {}

    /**
     * Clones a node 
     *
     * @return object
     * @param  deep bool[optional]
     */
    function clone_node($deep = null) {}

    /**
     * Adds a namespace declaration to a node 
     *
     * @return bool
     * @param  uri string
     * @param  prefix string
     */
    function add_namespace($uri, $prefix) {}

    /**
     * Sets the namespace of a node 
     *
     * @return void
     * @param  uri string
     * @param  prefix string[optional]
     */
    function set_namespace($uri, $prefix = null) {}

    /**
     * Adds node to list of children 
     *
     * @return object
     * @param  domnode object
     */
    function add_child($domnode) {}

    /**
     * Adds node to list of siblings 
     *
     * @return object
     * @param  domnode object
     */
    function append_sibling($domnode) {}

    /**
     * Creates node 
     *
     * @return object
     * @param  name string
     */
    function node($name) {}

    /**
     * Deletes the node from tree, but not from memory
     *
     * @return void
     * @param  node object[optional]
     */
    function unlink($node = null) {}

    /**
     * Deletes the node from tree, but not from memory
     *
     * @return void
     * @param  node object[optional]
     */
    function unlink_node($node = null) {}

    /**
     * Replaces one node with another node 
     *
     * @return object
     * @param  domnode object
     */
    function replace_node($domnode) {}

    /**
     * Sets content of a node 
     *
     * @return bool
     * @param  content string
     */
    function set_content($content) {}

    /**
     * 
     *
     * @return string
     */
    function get_content() {}

    /**
     * Add string tocontent of a node 
     *
     * @return bool
     * @param  content string
     */
    function text_concat($content) {}

    /**
     * Sets name of a node 
     *
     * @return bool
     * @param  name string
     */
    function set_name($name) {}

    /**
     * Returns true if node is blank 
     *
     * @return bool
     */
    function is_blank_node() {}

    /**
     * Dumps node into string 
     *
     * @return string
     * @param  doc_handle object
     * @param  node_handle object
     * @param  format int[optional]
     * @param  level int[optional]
     */
    function dump_node($doc_handle, $node_handle, $format = null, $level = null) {}

};

class domdocument {
    /**
     * Creates DOM object of XML document 
     *
     * @return object
     * @param  xmldoc string
     * @param  mode int
     * @param  error array
     */
    function domdocument($xmldoc, $mode, $error) {}

    /**
     * Returns DomDocumentType 
     *
     * @return object
     */
    function doctype() {}

    /**
     * Returns DomeDOMImplementation 
     *
     * @return object
     */
    function implementation() {}

    /**
     * Returns root node of document 
     *
     * @return object
     * @param  domnode int
     */
    function document_element($domnode) {}

    /**
     * Creates new element node 
     *
     * @return object
     * @param  name string
     */
    function create_element($name) {}

    /**
     * Creates new element node with a namespace 
     *
     * @return object
     * @param  uri string
     * @param  name string
     * @param  prefix string[optional]
     */
    function create_element_ns($uri, $name, $prefix = null) {}

    /**
     * Creates new text node 
     *
     * @return object
     * @param  content string
     */
    function create_text_node($content) {}

    /**
     * Creates new comment node 
     *
     * @return object
     * @param  content string
     */
    function create_comment($content) {}

    /**
     * Creates new attribute node 
     *
     * @return object
     * @param  name string
     * @param  value string
     */
    function create_attribute($name, $value) {}

    /**
     * Creates new cdata node 
     *
     * @return object
     * @param  content string
     */
    function create_cdata_section($content) {}

    /**
     * Creates new cdata node 
     *
     * @return object
     * @param  name string
     */
    function create_entity_reference($name) {}

    /**
     * Creates new processing_instruction node 
     *
     * @return object
     * @param  name string
     */
    function create_processing_instruction($name) {}

    /**
     * Returns array with nodes with given tagname in document or empty array, if not found
     *
     * @return string
     * @param  tagname string
     * @param  xpathctx_handle object[optional]
     */
    function get_elements_by_tagname($tagname, $xpathctx_handle = null) {}

    /**
     * Returns element for given id or false if not found 
     *
     * @return string
     * @param  id string
     */
    function get_element_by_id($id) {}

    /**
     * Frees xmldoc and removed objects from hash 
     *
     * @return bool
     */
    function free() {}

    /**
     * Returns list of children nodes 
     *
     * @return array
     */
    function children() {}

    /**
     * Returns root node of document 
     *
     * @return object
     * @param  domnode int
     */
    function get_root($domnode) {}

    /**
     * Returns root node of document 
     *
     * @return object
     * @param  domnode int
     */
    function root($domnode) {}

    /**
     * Creates new element node 
     *
     * @return object
     * @param  node object
     * @param  recursive bool
     */
    function imported_node($node, $recursive) {}

    /**
     * Returns array of ids 
     *
     * @return string
     * @param  doc_handle object
     */
    function ids($doc_handle) {}

    /**
     * Dumps document into string and optionally formats it 
     *
     * @return string
     * @param  doc_handle object
     * @param  format int[optional]
     * @param  encoding unknown[optional]
     */
    function dumpmem($doc_handle, $format = null, $encoding = null) {}

    /**
     * Dumps document into string and optionally formats it 
     *
     * @return string
     * @param  doc_handle object
     * @param  format int[optional]
     * @param  encoding unknown[optional]
     */
    function dump_mem($doc_handle, $format = null, $encoding = null) {}

    /**
     * Dumps document into file and uses compression if specified. Returns false on error, otherwise the length of the xml-document (uncompressed) 
     *
     * @return int
     * @param  filename string
     * @param  compressmode int[optional]
     * @param  format int[optional]
     */
    function dump_mem_file($filename, $compressmode = null, $format = null) {}

    /**
     * Dumps document into file and uses compression if specified. Returns false on error, otherwise the length of the xml-document (uncompressed) 
     *
     * @return int
     * @param  filename string
     * @param  compressmode int[optional]
     * @param  format int[optional]
     */
    function dump_file($filename, $compressmode = null, $format = null) {}

    /**
     * Dumps document into string as HTML 
     *
     * @return string
     * @param  doc_handle int[optional]
     */
    function html_dump_mem($doc_handle = null) {}

    /**
     * Initializing XPath environment 
     *
     * @return bool
     */
    function xpath_init() {}

    /**
     * Creates new XPath context 
     *
     * @return object
     * @param  doc_handle int[optional]
     */
    function xpath_new_context($doc_handle = null) {}

    /**
     * Creates new XPath context 
     *
     * @return object
     * @param  doc_handle int[optional]
     */
    function xptr_new_context($doc_handle = null) {}

    /**
     * Validates a DomDocument according to his DTD
     *
     * @return bool
     * @param  error array
     */
    function validate(&$error) {}

    /**
     * Substitutues xincludes in a DomDocument 
     *
     * @return int
     */
    function xinclude() {}

};

class domparser {
    /**
     * adds xml-chunk to parser 
     *
     * @return bool
     * @param  chunk string
     */
    function add_chunk($chunk) {}

    /**
     * Ends parsing and returns DomDocument
     *
     * @return object
     * @param  chunk string[optional]
     */
    function end($chunk = null) {}

    /**
     * Determines how to handle blanks 
     *
     * @return bool
     * @param  mode bool
     */
    function set_keep_blanks($mode) {}

    /**
     * Starts an element and adds attributes
     *
     * @return bool
     * @param  tagname string
     * @param  attributes array
     */
    function start_element($tagname, $attributes) {}

    /**
     * Ends an element 
     *
     * @return bool
     * @param  tagname string
     */
    function end_element($tagname) {}

    /**
     * Adds characters 
     *
     * @return bool
     * @param  characters string
     */
    function characters($characters) {}

    /**
     * Adds entity reference 
     *
     * @return bool
     * @param  reference string
     */
    function entity_reference($reference) {}

    /**
     * Adds processing instruction 
     *
     * @return bool
     * @param  target string
     * @param  data string
     */
    function processing_instruction($target, $data) {}

    /**
     * adds a cdata block 
     *
     * @return bool
     * @param  chunk string
     */
    function cdata_section($chunk) {}

    /**
     * Adds a comment 
     *
     * @return bool
     * @param  comment string
     */
    function comment($comment) {}

    /**
     * Adds namespace declaration 
     *
     * @return bool
     * @param  href string
     * @param  prefix string
     */
    function namespace_decl($href, $prefix) {}

    /**
     * starts a document
     *
     * @return bool
     */
    function start_document() {}

    /**
     * ends a document 
     *
     * @return bool
     */
    function end_document() {}

    /**
     * Returns DomDocument from parser 
     *
     * @return object
     */
    function get_document() {}

};

class domdocumenttype {
    /**
     * Returns name of DocumentType 
     *
     * @return array
     */
    function name() {}

    /**
     * Returns list of entities 
     *
     * @return array
     */
    function entities() {}

    /**
     * Returns list of notations 
     *
     * @return array
     */
    function notations() {}

    /**
     * Returns system id of DocumentType 
     *
     * @return array
     */
    function system_id() {}

    /**
     * Returns public id of DocumentType 
     *
     * @return array
     */
    function public_id() {}

};

class domelement {
    /**
     * Creates new element node 
     *
     * @return object
     * @param  name string
     */
    function domelement($name) {}

    /**
     * Returns tag name of element node 
     *
     * @return string
     */
    function name() {}

    /**
     * Returns tag name of element node 
     *
     * @return string
     */
    function tagname() {}

    /**
     * Returns value of given attribute 
     *
     * @return string
     * @param  attrname string
     */
    function get_attribute($attrname) {}

    /**
     * Sets value of given attribute 
     *
     * @return bool
     * @param  attrname string
     * @param  value string
     */
    function set_attribute($attrname, $value) {}

    /**
     * Removes given attribute 
     *
     * @return string
     * @param  attrname string
     */
    function remove_attribute($attrname) {}

    /**
     * Returns value of given attribute 
     *
     * @return string
     * @param  attrname string
     */
    function get_attribute_node($attrname) {}

    /**
     * Sets value of given attribute 
     *
     * @return bool
     * @param  attr object
     */
    function set_attribute_node($attr) {}

    /**
     * Returns array with nodes with given tagname in element or empty array, if not found 
     *
     * @return string
     * @param  tagname string
     */
    function get_elements_by_tagname($tagname) {}

    /**
     * Checks for existenz given attribute 
     *
     * @return string
     * @param  attrname string
     */
    function has_attribute($attrname) {}

};

class domattribute {
    /**
     * Creates new attribute node 
     *
     * @return object
     * @param  name string
     * @param  value string
     */
    function domattribute($name, $value) {}

    /**
     * 
     *
     * @return array
     */
    function node_name() {}

    /**
     * Returns list of attribute names 
     *
     * @return array
     */
    function node_value() {}

    /**
     * Returns list of attribute names 
     *
     * @return array
     */
    function node_specified() {}

    /**
     * 
     *
     * @return array
     */
    function name() {}

    /**
     * Returns list of attribute names 
     *
     * @return array
     */
    function value() {}

    /**
     * Returns list of attribute names 
     *
     * @return array
     */
    function specified() {}

};

class domcdata {
    /**
     * Creates new cdata node 
     *
     * @return object
     * @param  content string
     */
    function domcdata($content) {}

    /**
     * Returns list of attribute names 
     *
     * @return array
     */
    function length() {}

};

class domtext {
    /**
     * Creates new text node 
     *
     * @return object
     * @param  content string
     */
    function domtext($content) {}

};

class domcomment {
    /**
     * Creates new comment node 
     *
     * @return object
     * @param  content string
     */
    function domcomment($content) {}

};

class domprocessinginstruction {
    /**
     * Creates new processing_instruction node 
     *
     * @return object
     * @param  name string
     */
    function domprocessinginstruction($name) {}

    /**
     * Returns target of pi 
     *
     * @return array
     */
    function target() {}

    /**
     * Returns data of pi 
     *
     * @return array
     */
    function data() {}

};

class domnotation {
    /**
     * Returns public id of notation node 
     *
     * @return string
     */
    function public_id() {}

    /**
     * Returns system ID of notation node 
     *
     * @return string
     */
    function system_id() {}

};

class domentityreference {
    /**
     * Creates new cdata node 
     *
     * @return object
     * @param  name string
     */
    function domentityreference($name) {}

};

class XPathContext {
    /**
     * Evaluates the XPath Location Path in the given string 
     *
     * @return object
     * @param  xpathctx_handle object[optional]
     * @param  str string
     */
    function xpath_eval($xpathctx_handle = null, $str) {}

    /**
     * Evaluates the XPath expression in the given string 
     *
     * @return object
     * @param  xpathctx_handle object[optional]
     * @param  str string
     */
    function xpath_eval_expression($xpathctx_handle = null, $str) {}

    /**
     * Registeres the given namespace in the passed XPath context 
     *
     * @return bool
     * @param  xpathctx_handle object[optional]
     * @param  namespace_prefix string
     * @param  namespace_uri string
     */
    function xpath_register_ns($xpathctx_handle = null, $namespace_prefix, $namespace_uri) {}

};

class XsltStylesheet {
    /**
     * Perform an XSLT transformation 
     *
     * @return object
     * @param  xslstylesheet object
     * @param  xmldoc object
     * @param  xslt_parameters array[optional]
     * @param  xpath_parameters bool[optional]
     * @param  profileFilename string[optional]
     */
    function process($xslstylesheet, $xmldoc, $xslt_parameters = null, $xpath_parameters = null, $profileFilename = null) {}

    /**
     * output XSLT result to memory 
     *
     * @return string
     * @param  xslstylesheet object
     * @param  xmldoc object
     */
    function result_dump_mem($xslstylesheet, $xmldoc) {}

    /**
     * output XSLT result to File 
     *
     * @return int
     * @param  xslstylesheet object
     * @param  xmldoc object
     * @param  filename string
     * @param  compression int
     */
    function result_dump_file($xslstylesheet, $xmldoc, $filename, $compression) {}

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
     * @return boolean
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
     * @return bool
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
     * @return bool
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
     * @return resource
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
     * @return object
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
     * Sets object which will handle SOAP requests 
     *
     * @return void
     * @param  object unknown
     */
    function setObject($object) {}

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
     * @return unknown
     * @param  code staring
     * @param  string string
     * @param  actor string[optional]
     * @param  details mixed[optional]
     * @param  name string[optional]
     */
    function fault($code, $string, $actor = null, $details = null, $name = null) {}

    /**
     * Adds one SOAP header into response 
     *
     * @return unknown
     * @param  header SoapHeader
     */
    function addSoapHeader($header) {}

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
     * @param  arguments array
     * @param  options array[optional]
     * @param  input_headers array[optional]
     * @param  output_headers array[optional]
     */
    function __call($function_name, $arguments, $options = null, $input_headers = null, $output_headers = null) {}

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
     * @return object
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
     * Returns array of cookies. 
     *
     * @return array
     */
    function __getCookies() {}

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
define("AF_UNIX", 0);
define("ALT_DIGITS", 0);
define("AM_STR", 0);
define("APACHE_MAP", 0);
define("ASSERT_ACTIVE", 0);
define("ASSERT_BAIL", 0);
define("ASSERT_CALLBACK", 0);
define("ASSERT_QUIET_EVAL", 0);
define("ASSERT_WARNING", 0);
define("BSDown", 0);
define("BSHitTest", 0);
define("BSOver", 0);
define("BSUp", 0);
define("ButtonEnter", 0);
define("ButtonExit", 0);
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
define("CLSCTX_ALL", 0);
define("CLSCTX_INPROC_HANDLER", 0);
define("CLSCTX_INPROC_SERVER", 0);
define("CLSCTX_LOCAL_SERVER", 0);
define("CLSCTX_REMOTE_SERVER", 0);
define("CLSCTX_SERVER", 0);
define("CL_EXPUNGE", 0);
define("CODESET", 0);
define("CONNECTION_ABORTED", 0);
define("CONNECTION_NORMAL", 0);
define("CONNECTION_TIMEOUT", 0);
define("COUNT_NORMAL", 0);
define("COUNT_RECURSIVE", 0);
define("CPDF_PL_1COLUMN", 0);
define("CPDF_PL_2LCOLUMN", 0);
define("CPDF_PL_2RCOLUMN", 0);
define("CPDF_PL_SINGLE", 0);
define("CPDF_PM_FULLSCREEN", 0);
define("CPDF_PM_NONE", 0);
define("CPDF_PM_OUTLINES", 0);
define("CPDF_PM_THUMBS", 0);
define("CP_ACP", 0);
define("CP_MACCP", 0);
define("CP_MOVE", 0);
define("CP_OEMCP", 0);
define("CP_SYMBOL", 0);
define("CP_THREAD_ACP", 0);
define("CP_UID", 0);
define("CP_UTF7", 0);
define("CP_UTF8", 0);
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
define("CURLCLOSEPOLICY_CALLBACK", 0);
define("CURLCLOSEPOLICY_LEAST_RECENTLY_USED", 0);
define("CURLCLOSEPOLICY_LEAST_TRAFFIC", 0);
define("CURLCLOSEPOLICY_OLDEST", 0);
define("CURLCLOSEPOLICY_SLOWEST", 0);
define("CURLE_ABORTED_BY_CALLBACK", 0);
define("CURLE_BAD_CALLING_ORDER", 0);
define("CURLE_BAD_FUNCTION_ARGUMENT", 0);
define("CURLE_BAD_PASSWORD_ENTERED", 0);
define("CURLE_COULDNT_CONNECT", 0);
define("CURLE_COULDNT_RESOLVE_HOST", 0);
define("CURLE_COULDNT_RESOLVE_PROXY", 0);
define("CURLE_FAILED_INIT", 0);
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
define("CURLE_FTP_USER_PASSWORD_INCORRECT", 0);
define("CURLE_FTP_WEIRD_227_FORMAT", 0);
define("CURLE_FTP_WEIRD_PASS_REPLY", 0);
define("CURLE_FTP_WEIRD_PASV_REPLY", 0);
define("CURLE_FTP_WEIRD_SERVER_REPLY", 0);
define("CURLE_FTP_WEIRD_USER_REPLY", 0);
define("CURLE_FTP_WRITE_ERROR", 0);
define("CURLE_FUNCTION_NOT_FOUND", 0);
define("CURLE_HTTP_NOT_FOUND", 0);
define("CURLE_HTTP_PORT_FAILED", 0);
define("CURLE_HTTP_POST_ERROR", 0);
define("CURLE_HTTP_RANGE_ERROR", 0);
define("CURLE_LDAP_CANNOT_BIND", 0);
define("CURLE_LDAP_SEARCH_FAILED", 0);
define("CURLE_LIBRARY_NOT_FOUND", 0);
define("CURLE_MALFORMAT_USER", 0);
define("CURLE_OBSOLETE", 0);
define("CURLE_OK", 0);
define("CURLE_OPERATION_TIMEOUTED", 0);
define("CURLE_OUT_OF_MEMORY", 0);
define("CURLE_PARTIAL_FILE", 0);
define("CURLE_READ_ERROR", 0);
define("CURLE_SSL_CONNECT_ERROR", 0);
define("CURLE_SSL_PEER_CERTIFICATE", 0);
define("CURLE_TELNET_OPTION_SYNTAX", 0);
define("CURLE_TOO_MANY_REDIRECTS", 0);
define("CURLE_UNKNOWN_TELNET_OPTION", 0);
define("CURLE_UNSUPPORTED_PROTOCOL", 0);
define("CURLE_URL_MALFORMAT", 0);
define("CURLE_URL_MALFORMAT_USER", 0);
define("CURLE_WRITE_ERROR", 0);
define("CURLINFO_CONNECT_TIME", 0);
define("CURLINFO_CONTENT_LENGTH_DOWNLOAD", 0);
define("CURLINFO_CONTENT_LENGTH_UPLOAD", 0);
define("CURLINFO_CONTENT_TYPE", 0);
define("CURLINFO_EFFECTIVE_URL", 0);
define("CURLINFO_FILETIME", 0);
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
define("CURLOPT_BINARYTRANSFER", 0);
define("CURLOPT_CAINFO", 0);
define("CURLOPT_CAPATH", 0);
define("CURLOPT_CLOSEPOLICY", 0);
define("CURLOPT_CONNECTTIMEOUT", 0);
define("CURLOPT_COOKIE", 0);
define("CURLOPT_COOKIEFILE", 0);
define("CURLOPT_COOKIEJAR", 0);
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
define("CURLOPT_FTP_USE_EPSV", 0);
define("CURLOPT_HEADER", 0);
define("CURLOPT_HEADERFUNCTION", 0);
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
define("CURLOPT_PASSWDFUNCTION", 0);
define("CURLOPT_PORT", 0);
define("CURLOPT_POST", 0);
define("CURLOPT_POSTFIELDS", 0);
define("CURLOPT_POSTQUOTE", 0);
define("CURLOPT_PROXY", 0);
define("CURLOPT_PROXYUSERPWD", 0);
define("CURLOPT_PUT", 0);
define("CURLOPT_QUOTE", 0);
define("CURLOPT_RANDOM_FILE", 0);
define("CURLOPT_RANGE", 0);
define("CURLOPT_READFUNCTION", 0);
define("CURLOPT_REFERER", 0);
define("CURLOPT_RESUME_FROM", 0);
define("CURLOPT_RETURNTRANSFER", 0);
define("CURLOPT_SSLCERT", 0);
define("CURLOPT_SSLCERTPASSWD", 0);
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
define("CURLOPT_UPLOAD", 0);
define("CURLOPT_URL", 0);
define("CURLOPT_USERAGENT", 0);
define("CURLOPT_USERPWD", 0);
define("CURLOPT_VERBOSE", 0);
define("CURLOPT_WRITEFUNCTION", 0);
define("CURLOPT_WRITEHEADER", 0);
define("CURL_HTTP_VERSION_1_0", 0);
define("CURL_HTTP_VERSION_1_1", 0);
define("CURL_HTTP_VERSION_NONE", 0);
define("CURL_NETRC_IGNORED", 0);
define("CURL_NETRC_OPTIONAL", 0);
define("CURL_NETRC_REQUIRED", 0);
define("CURRENCY_SYMBOL", 0);
define("CYRUS_CALLBACK_NOLITERAL", 0);
define("CYRUS_CALLBACK_NUMBERED", 0);
define("CYRUS_CONN_INITIALRESPONSE", 0);
define("CYRUS_CONN_NONSYNCLITERAL", 0);
define("DAY_1", 0);
define("DAY_2", 0);
define("DAY_3", 0);
define("DAY_4", 0);
define("DAY_5", 0);
define("DAY_6", 0);
define("DAY_7", 0);
define("DBX_CMP_ASC", 0);
define("DBX_CMP_DESC", 0);
define("DBX_CMP_NATIVE", 0);
define("DBX_CMP_NUMBER", 0);
define("DBX_CMP_TEXT", 0);
define("DBX_COLNAMES_LOWERCASE", 0);
define("DBX_COLNAMES_UNCHANGED", 0);
define("DBX_COLNAMES_UPPERCASE", 0);
define("DBX_FBSQL", 0);
define("DBX_MSSQL", 0);
define("DBX_MYSQL", 0);
define("DBX_OCI8", 0);
define("DBX_ODBC", 0);
define("DBX_PERSISTENT", 0);
define("DBX_PGSQL", 0);
define("DBX_RESULT_ASSOC", 0);
define("DBX_RESULT_INDEX", 0);
define("DBX_RESULT_INFO", 0);
define("DBX_SYBASECT", 0);
define("DECIMAL_POINT", 0);
define("DEFAULT_INCLUDE_PATH", 0);
define("DIRECTORY_SEPARATOR", 0);
define("DISPATCH_METHOD", 0);
define("DISPATCH_PROPERTYGET", 0);
define("DISPATCH_PROPERTYPUT", 0);
define("DOMXML_LOAD_COMPLETE_ATTRS", 0);
define("DOMXML_LOAD_DONT_KEEP_BLANKS", 0);
define("DOMXML_LOAD_PARSING", 0);
define("DOMXML_LOAD_RECOVERING", 0);
define("DOMXML_LOAD_SUBSTITUTE_ENTITIES", 0);
define("DOMXML_LOAD_VALIDATING", 0);
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
define("E_USER_ERROR", 0);
define("E_USER_NOTICE", 0);
define("E_USER_WARNING", 0);
define("E_WARNING", 0);
define("FBSQL_ASSOC", 0);
define("FBSQL_BOTH", 0);
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
define("FNM_CASEFOLD", 0);
define("FNM_NOESCAPE", 0);
define("FNM_PATHNAME", 0);
define("FNM_PERIOD", 0);
define("FORCE_DEFLATE", 0);
define("FORCE_GZIP", 0);
define("FRAC_DIGITS", 0);
define("FRIBIDI_AUTO", 0);
define("FRIBIDI_CHARSET_8859_6", 0);
define("FRIBIDI_CHARSET_8859_8", 0);
define("FRIBIDI_CHARSET_CAP_RTL", 0);
define("FRIBIDI_CHARSET_CP1255", 0);
define("FRIBIDI_CHARSET_CP1256", 0);
define("FRIBIDI_CHARSET_ISIRI_3342", 0);
define("FRIBIDI_CHARSET_UTF8", 0);
define("FRIBIDI_LTR", 0);
define("FRIBIDI_RTL", 0);
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
define("HTML_ENTITIES", 0);
define("HTML_SPECIALCHARS", 0);
define("HW_ATTR_LANG", 0);
define("HW_ATTR_NONE", 0);
define("HW_ATTR_NR", 0);
define("IBASE_COMMITTED", 0);
define("IBASE_CONCURRENCY", 0);
define("IBASE_CONSISTENCY", 0);
define("IBASE_DATE", 0);
define("IBASE_DEFAULT", 0);
define("IBASE_NOWAIT", 0);
define("IBASE_READ", 0);
define("IBASE_REC_NO_VERSION", 0);
define("IBASE_REC_VERSION", 0);
define("IBASE_TEXT", 0);
define("IBASE_TIME", 0);
define("IBASE_TIMESTAMP", 0);
define("IBASE_UNIXTIME", 0);
define("IBASE_WAIT", 0);
define("IBASE_WRITE", 0);
define("ICONV_IMPL", 0);
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
define("IMG_GD2_COMPRESSED", 0);
define("IMG_GD2_RAW", 0);
define("IMG_GIF", 0);
define("IMG_JPEG", 0);
define("IMG_JPG", 0);
define("IMG_PNG", 0);
define("IMG_WBMP", 0);
define("IMG_XPM", 0);
define("INFO_ALL", 0);
define("INFO_CONFIGURATION", 0);
define("INFO_CREDITS", 0);
define("INFO_ENVIRONMENT", 0);
define("INFO_GENERAL", 0);
define("INFO_LICENSE", 0);
define("INFO_MODULES", 0);
define("INFO_VARIABLES", 0);
define("INGRES_ASSOC", 0);
define("INGRES_BOTH", 0);
define("INGRES_NUM", 0);
define("INI_ALL", 0);
define("INI_PERDIR", 0);
define("INI_SYSTEM", 0);
define("INI_USER", 0);
define("INT_CURR_SYMBOL", 0);
define("INT_FRAC_DIGITS", 0);
define("IdletoOverDown", 0);
define("IdletoOverUp", 0);
define("LATT_MARKED", 0);
define("LATT_NOINFERIORS", 0);
define("LATT_NOSELECT", 0);
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
define("MCAL_APRIL", 0);
define("MCAL_AUGUST", 0);
define("MCAL_FEBRUARY", 0);
define("MCAL_FRIDAY", 0);
define("MCAL_JANUARY", 0);
define("MCAL_JULY", 0);
define("MCAL_JUNE", 0);
define("MCAL_MARCH", 0);
define("MCAL_MAY", 0);
define("MCAL_MONDAY", 0);
define("MCAL_M_ALLDAYS", 0);
define("MCAL_M_FRIDAY", 0);
define("MCAL_M_MONDAY", 0);
define("MCAL_M_SATURDAY", 0);
define("MCAL_M_SUNDAY", 0);
define("MCAL_M_THURSDAY", 0);
define("MCAL_M_TUESDAY", 0);
define("MCAL_M_WEDNESDAY", 0);
define("MCAL_M_WEEKDAYS", 0);
define("MCAL_M_WEEKEND", 0);
define("MCAL_NOVEMBER", 0);
define("MCAL_OCTOBER", 0);
define("MCAL_RECUR_DAILY", 0);
define("MCAL_RECUR_MONTHLY_MDAY", 0);
define("MCAL_RECUR_MONTHLY_WDAY", 0);
define("MCAL_RECUR_NONE", 0);
define("MCAL_RECUR_WEEKLY", 0);
define("MCAL_RECUR_YEARLY", 0);
define("MCAL_SATURDAY", 0);
define("MCAL_SEPTEMBER", 0);
define("MCAL_SUNDAY", 0);
define("MCAL_THURSDAY", 0);
define("MCAL_TUESDAY", 0);
define("MCAL_WEDNESDAY", 0);
define("MCRYPT_DECRYPT", 0);
define("MCRYPT_DEV_RANDOM", 0);
define("MCRYPT_DEV_URANDOM", 0);
define("MCRYPT_ENCRYPT", 0);
define("MCRYPT_RAND", 0);
define("MCVE_AGENTID", 0);
define("MCVE_AUTH", 0);
define("MCVE_BAD", 0);
define("MCVE_BANKID", 0);
define("MCVE_CALL", 0);
define("MCVE_CHAINID", 0);
define("MCVE_CLIENTNUM", 0);
define("MCVE_DENY", 0);
define("MCVE_DONE", 0);
define("MCVE_DUPL", 0);
define("MCVE_ERROR", 0);
define("MCVE_FAIL", 0);
define("MCVE_FORCE", 0);
define("MCVE_GOOD", 0);
define("MCVE_INDCODE", 0);
define("MCVE_MERCHCAT", 0);
define("MCVE_MERCHID", 0);
define("MCVE_MERCHLOC", 0);
define("MCVE_MERNAME", 0);
define("MCVE_NEW", 0);
define("MCVE_OVERRIDE", 0);
define("MCVE_PENDING", 0);
define("MCVE_PKUP", 0);
define("MCVE_PREAUTH", 0);
define("MCVE_PROC", 0);
define("MCVE_PWD", 0);
define("MCVE_RETRY", 0);
define("MCVE_RETURN", 0);
define("MCVE_SALE", 0);
define("MCVE_SERVICEPHONE", 0);
define("MCVE_SETTLE", 0);
define("MCVE_SETUP", 0);
define("MCVE_STATECODE", 0);
define("MCVE_STOREID", 0);
define("MCVE_STREET", 0);
define("MCVE_SUCCESS", 0);
define("MCVE_TERMID", 0);
define("MCVE_TIMEZONE", 0);
define("MCVE_UNKNOWN", 0);
define("MCVE_UNUSED", 0);
define("MCVE_USER", 0);
define("MCVE_ZIP", 0);
define("MCVE_ZIPCODE", 0);
define("MC_ACCOUNT", 0);
define("MC_ACCT", 0);
define("MC_ADMIN", 0);
define("MC_ADMIN_BT", 0);
define("MC_ADMIN_CFH", 0);
define("MC_ADMIN_CTH", 0);
define("MC_ADMIN_FORCESETTLE", 0);
define("MC_ADMIN_GFT", 0);
define("MC_ADMIN_GL", 0);
define("MC_ADMIN_GUT", 0);
define("MC_ADMIN_QC", 0);
define("MC_ADMIN_RS", 0);
define("MC_ADMIN_SETBATCHNUM", 0);
define("MC_ADMIN_UB", 0);
define("MC_AMOUNT", 0);
define("MC_APPRCODE", 0);
define("MC_AUDITTYPE", 0);
define("MC_BATCH", 0);
define("MC_BDATE", 0);
define("MC_CLERKID", 0);
define("MC_COMMENTS", 0);
define("MC_CUSTOM", 0);
define("MC_CV", 0);
define("MC_EDATE", 0);
define("MC_EXPDATE", 0);
define("MC_FILE", 0);
define("MC_PASSWORD", 0);
define("MC_PTRANNUM", 0);
define("MC_PWD", 0);
define("MC_STATIONID", 0);
define("MC_STREET", 0);
define("MC_TRACKDATA", 0);
define("MC_TRANTYPE", 0);
define("MC_TRAN_ADDUSER", 0);
define("MC_TRAN_ADMIN", 0);
define("MC_TRAN_CHKPWD", 0);
define("MC_TRAN_CHNGPWD", 0);
define("MC_TRAN_DELUSER", 0);
define("MC_TRAN_DISABLEUSER", 0);
define("MC_TRAN_EDITUSER", 0);
define("MC_TRAN_ENABLEUSER", 0);
define("MC_TRAN_EXPORT", 0);
define("MC_TRAN_FORCE", 0);
define("MC_TRAN_GETUSERINFO", 0);
define("MC_TRAN_IMPORT", 0);
define("MC_TRAN_LISTSTATS", 0);
define("MC_TRAN_LISTUSERS", 0);
define("MC_TRAN_OVERRIDE", 0);
define("MC_TRAN_PING", 0);
define("MC_TRAN_PREAUTH", 0);
define("MC_TRAN_PREAUTHCOMPLETE", 0);
define("MC_TRAN_RETURN", 0);
define("MC_TRAN_SALE", 0);
define("MC_TRAN_SETTLE", 0);
define("MC_TRAN_VOID", 0);
define("MC_TTID", 0);
define("MC_USER", 0);
define("MC_USERNAME", 0);
define("MC_USER_AGENTID", 0);
define("MC_USER_BANKID", 0);
define("MC_USER_CHAINID", 0);
define("MC_USER_CLIENTNUM", 0);
define("MC_USER_INDCODE", 0);
define("MC_USER_MERCHCAT", 0);
define("MC_USER_MERCHID", 0);
define("MC_USER_MERCHLOC", 0);
define("MC_USER_MERNAME", 0);
define("MC_USER_PHONE", 0);
define("MC_USER_PROC", 0);
define("MC_USER_PWD", 0);
define("MC_USER_STATECODE", 0);
define("MC_USER_STOREID", 0);
define("MC_USER_TERMID", 0);
define("MC_USER_TIMEZONE", 0);
define("MC_USER_USER", 0);
define("MC_USER_ZIPCODE", 0);
define("MC_ZIP", 0);
define("MOD_COLOR", 0);
define("MOD_MATRIX", 0);
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
define("M_E", 0);
define("M_LN2", 0);
define("M_LN10", 0);
define("M_LOG2E", 0);
define("M_LOG10E", 0);
define("M_PI", 0);
define("M_PI_2", 0);
define("M_PI_4", 0);
define("M_SQRT1_2", 0);
define("M_SQRT2", 0);
define("MenuEnter", 0);
define("MenuExit", 0);
define("NEGATIVE_SIGN", 0);
define("NIL", 0);
define("NOEXPR", 0);
define("NOSTR", 0);
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
define("OCI_B_ROWID", 0);
define("OCI_B_SQLT_NTY", 0);
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
define("OCI_NUM", 0);
define("OCI_RETURN_LOBS", 0);
define("OCI_RETURN_NULLS", 0);
define("OCI_SYSDATE", 0);
define("ODBC_BINMODE_CONVERT", 0);
define("ODBC_BINMODE_PASSTHRU", 0);
define("ODBC_BINMODE_RETURN", 0);
define("ODBC_TYPE", 0);
define("OPENSSL_KEYTYPE_DH", 0);
define("OPENSSL_KEYTYPE_DSA", 0);
define("OPENSSL_KEYTYPE_RSA", 0);
define("OPENSSL_NO_PADDING", 0);
define("OPENSSL_PKCS1_OAEP_PADDING", 0);
define("OPENSSL_PKCS1_PADDING", 0);
define("OPENSSL_SSLV23_PADDING", 0);
define("OP_ANONYMOUS", 0);
define("OP_DEBUG", 0);
define("OP_EXPUNGE", 0);
define("OP_HALFOPEN", 0);
define("OP_PROTOTYPE", 0);
define("OP_READONLY", 0);
define("OP_SECURE", 0);
define("OP_SHORTCACHE", 0);
define("OP_SILENT", 0);
define("ORA_BIND_IN", 0);
define("ORA_BIND_INOUT", 0);
define("ORA_BIND_OUT", 0);
define("ORA_FETCHINTO_ASSOC", 0);
define("ORA_FETCHINTO_NULLS", 0);
define("OutDowntoIdle", 0);
define("OutDowntoOverDown", 0);
define("OverDowntoIdle", 0);
define("OverDowntoOutDown", 0);
define("OverUptoIdle", 0);
define("OverUptoOverDown", 0);
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
define("PGSQL_DML_ASYNC", 0);
define("PGSQL_DML_EXEC", 0);
define("PGSQL_DML_NO_CONV", 0);
define("PGSQL_DML_STRING", 0);
define("PGSQL_EMPTY_QUERY", 0);
define("PGSQL_FATAL_ERROR", 0);
define("PGSQL_NONFATAL_ERROR", 0);
define("PGSQL_NUM", 0);
define("PGSQL_SEEK_CUR", 0);
define("PGSQL_SEEK_END", 0);
define("PGSQL_SEEK_SET", 0);
define("PGSQL_STATUS_LONG", 0);
define("PGSQL_STATUS_STRING", 0);
define("PGSQL_TUPLES_OK", 0);
define("PHP_BINARY_READ", 0);
define("PHP_BINDIR", 0);
define("PHP_CONFIG_FILE_PATH", 0);
define("PHP_CONFIG_FILE_SCAN_DIR", 0);
define("PHP_DATADIR", 0);
define("PHP_EXTENSION_DIR", 0);
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
define("PM_STR", 0);
define("POSITIVE_SIGN", 0);
define("PREG_GREP_INVERT", 0);
define("PREG_OFFSET_CAPTURE", 0);
define("PREG_PATTERN_ORDER", 0);
define("PREG_SET_ORDER", 0);
define("PREG_SPLIT_DELIM_CAPTURE", 0);
define("PREG_SPLIT_NO_EMPTY", 0);
define("PREG_SPLIT_OFFSET_CAPTURE", 0);
define("PSPELL_BAD_SPELLERS", 0);
define("PSPELL_FAST", 0);
define("PSPELL_NORMAL", 0);
define("PSPELL_RUN_TOGETHER", 0);
define("P_CS_PRECEDES", 0);
define("P_SEP_BY_SPACE", 0);
define("P_SIGN_POSN", 0);
define("RADIXCHAR", 0);
define("SA_ALL", 0);
define("SA_MESSAGES", 0);
define("SA_RECENT", 0);
define("SA_UIDNEXT", 0);
define("SA_UIDVALIDITY", 0);
define("SA_UNSEEN", 0);
define("SEEK_CUR", 0);
define("SEEK_END", 0);
define("SEEK_SET", 0);
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
define("SOAP_USE_XSI_ARRAY_TYPE", 0);
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
define("SQLT_BFILEE", 0);
define("SQLT_BLOB", 0);
define("SQLT_CFILEE", 0);
define("SQLT_CLOB", 0);
define("SQLT_RDD", 0);
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
define("STREAM_ENFORCE_SAFE_MODE", 0);
define("STREAM_IGNORE_URL", 0);
define("STREAM_MUST_SEEK", 0);
define("STREAM_NOTIFY_AUTH_REQUIRED", 0);
define("STREAM_NOTIFY_AUTH_RESULT", 0);
define("STREAM_NOTIFY_CONNECT", 0);
define("STREAM_NOTIFY_FAILURE", 0);
define("STREAM_NOTIFY_FILE_SIZE_IS", 0);
define("STREAM_NOTIFY_MIME_TYPE_IS", 0);
define("STREAM_NOTIFY_PROGRESS", 0);
define("STREAM_NOTIFY_REDIRECTED", 0);
define("STREAM_NOTIFY_SEVERITY_ERR", 0);
define("STREAM_NOTIFY_SEVERITY_INFO", 0);
define("STREAM_NOTIFY_SEVERITY_WARN", 0);
define("STREAM_REPORT_ERRORS", 0);
define("STREAM_USE_PATH", 0);
define("STR_PAD_BOTH", 0);
define("STR_PAD_LEFT", 0);
define("STR_PAD_RIGHT", 0);
define("ST_SET", 0);
define("ST_SILENT", 0);
define("ST_UID", 0);
define("THOUSANDS_SEP", 0);
define("THOUSEP", 0);
define("TYPEAPPLICATION", 0);
define("TYPEAUDIO", 0);
define("TYPEIMAGE", 0);
define("TYPEMESSAGE", 0);
define("TYPEMULTIPART", 0);
define("TYPEOTHER", 0);
define("TYPETEXT", 0);
define("TYPEVIDEO", 0);
define("TYPE_MENUBUTTON", 0);
define("TYPE_PUSHBUTTON", 0);
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
define("T_CHARACTER", 0);
define("T_CLASS", 0);
define("T_CLASS_C", 0);
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
define("T_FMT", 0);
define("T_FMT_AMPM", 0);
define("T_FOR", 0);
define("T_FOREACH", 0);
define("T_FUNCTION", 0);
define("T_FUNC_C", 0);
define("T_GLOBAL", 0);
define("T_IF", 0);
define("T_INC", 0);
define("T_INCLUDE", 0);
define("T_INCLUDE_ONCE", 0);
define("T_INLINE_HTML", 0);
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
define("T_MINUS_EQUAL", 0);
define("T_ML_COMMENT", 0);
define("T_MOD_EQUAL", 0);
define("T_MUL_EQUAL", 0);
define("T_NEW", 0);
define("T_NUM_STRING", 0);
define("T_OBJECT_CAST", 0);
define("T_OBJECT_OPERATOR", 0);
define("T_OLD_FUNCTION", 0);
define("T_OPEN_TAG", 0);
define("T_OPEN_TAG_WITH_ECHO", 0);
define("T_OR_EQUAL", 0);
define("T_PAAMAYIM_NEKUDOTAYIM", 0);
define("T_PLUS_EQUAL", 0);
define("T_PRINT", 0);
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
define("T_UNSET", 0);
define("T_UNSET_CAST", 0);
define("T_USE", 0);
define("T_VAR", 0);
define("T_VARIABLE", 0);
define("T_WHILE", 0);
define("T_WHITESPACE", 0);
define("T_XOR_EQUAL", 0);
define("UDM_CACHE_DISABLED", 0);
define("UDM_CACHE_ENABLED", 0);
define("UDM_CROSSWORDS_DISABLED", 0);
define("UDM_CROSSWORDS_ENABLED", 0);
define("UDM_CROSS_WORDS_DISABLED", 0);
define("UDM_CROSS_WORDS_ENABLED", 0);
define("UDM_DISABLED", 0);
define("UDM_ENABLED", 0);
define("UDM_FIELD_CATEGORY", 0);
define("UDM_FIELD_CHARSET", 0);
define("UDM_FIELD_CONTENT", 0);
define("UDM_FIELD_CRC", 0);
define("UDM_FIELD_DESC", 0);
define("UDM_FIELD_DESCRIPTION", 0);
define("UDM_FIELD_KEYWORDS", 0);
define("UDM_FIELD_LANG", 0);
define("UDM_FIELD_MODIFIED", 0);
define("UDM_FIELD_ORDER", 0);
define("UDM_FIELD_ORIGINID", 0);
define("UDM_FIELD_POP_RANK", 0);
define("UDM_FIELD_RATING", 0);
define("UDM_FIELD_SCORE", 0);
define("UDM_FIELD_SITEID", 0);
define("UDM_FIELD_SIZE", 0);
define("UDM_FIELD_TEXT", 0);
define("UDM_FIELD_TITLE", 0);
define("UDM_FIELD_URL", 0);
define("UDM_FIELD_URLID", 0);
define("UDM_ISPELL_PREFIXES_DISABLED", 0);
define("UDM_ISPELL_PREFIXES_ENABLED", 0);
define("UDM_ISPELL_PREFIX_DISABLED", 0);
define("UDM_ISPELL_PREFIX_ENABLED", 0);
define("UDM_ISPELL_TYPE_AFFIX", 0);
define("UDM_ISPELL_TYPE_DB", 0);
define("UDM_ISPELL_TYPE_SERVER", 0);
define("UDM_ISPELL_TYPE_SPELL", 0);
define("UDM_LIMIT_CAT", 0);
define("UDM_LIMIT_DATE", 0);
define("UDM_LIMIT_LANG", 0);
define("UDM_LIMIT_TAG", 0);
define("UDM_LIMIT_TYPE", 0);
define("UDM_LIMIT_URL", 0);
define("UDM_MATCH_BEGIN", 0);
define("UDM_MATCH_END", 0);
define("UDM_MATCH_SUBSTR", 0);
define("UDM_MATCH_WORD", 0);
define("UDM_MODE_ALL", 0);
define("UDM_MODE_ANY", 0);
define("UDM_MODE_BOOL", 0);
define("UDM_MODE_PHRASE", 0);
define("UDM_PARAM_BROWSER_CHARSET", 0);
define("UDM_PARAM_CACHE_MODE", 0);
define("UDM_PARAM_CHARSET", 0);
define("UDM_PARAM_CROSSWORDS", 0);
define("UDM_PARAM_CROSS_WORDS", 0);
define("UDM_PARAM_DATADIR", 0);
define("UDM_PARAM_DETECT_CLONES", 0);
define("UDM_PARAM_FIRST_DOC", 0);
define("UDM_PARAM_FOUND", 0);
define("UDM_PARAM_GROUPBYSITE", 0);
define("UDM_PARAM_HLBEG", 0);
define("UDM_PARAM_HLEND", 0);
define("UDM_PARAM_ISPELL_PREFIX", 0);
define("UDM_PARAM_ISPELL_PREFIXES", 0);
define("UDM_PARAM_LAST_DOC", 0);
define("UDM_PARAM_LOCAL_CHARSET", 0);
define("UDM_PARAM_MAX_WORDLEN", 0);
define("UDM_PARAM_MAX_WORD_LEN", 0);
define("UDM_PARAM_MIN_WORDLEN", 0);
define("UDM_PARAM_MIN_WORD_LEN", 0);
define("UDM_PARAM_NUM_ROWS", 0);
define("UDM_PARAM_PAGE_NUM", 0);
define("UDM_PARAM_PAGE_SIZE", 0);
define("UDM_PARAM_PHRASE_MODE", 0);
define("UDM_PARAM_PREFIX", 0);
define("UDM_PARAM_PREFIXES", 0);
define("UDM_PARAM_QSTRING", 0);
define("UDM_PARAM_QUERY", 0);
define("UDM_PARAM_REMOTE_ADDR", 0);
define("UDM_PARAM_SEARCHD", 0);
define("UDM_PARAM_SEARCHTIME", 0);
define("UDM_PARAM_SEARCH_MODE", 0);
define("UDM_PARAM_SEARCH_TIME", 0);
define("UDM_PARAM_SITEID", 0);
define("UDM_PARAM_STOPFILE", 0);
define("UDM_PARAM_STOPTABLE", 0);
define("UDM_PARAM_STOP_FILE", 0);
define("UDM_PARAM_STOP_TABLE", 0);
define("UDM_PARAM_STORED", 0);
define("UDM_PARAM_SYNONYM", 0);
define("UDM_PARAM_TRACK_MODE", 0);
define("UDM_PARAM_VARDIR", 0);
define("UDM_PARAM_WEIGHT_FACTOR", 0);
define("UDM_PARAM_WORDINFO", 0);
define("UDM_PARAM_WORDINFO_ALL", 0);
define("UDM_PARAM_WORD_INFO", 0);
define("UDM_PARAM_WORD_MATCH", 0);
define("UDM_PHRASE_DISABLED", 0);
define("UDM_PHRASE_ENABLED", 0);
define("UDM_PREFIXES_DISABLED", 0);
define("UDM_PREFIXES_ENABLED", 0);
define("UDM_PREFIX_DISABLED", 0);
define("UDM_PREFIX_ENABLED", 0);
define("UDM_TRACK_DISABLED", 0);
define("UDM_TRACK_ENABLED", 0);
define("UNKNOWN_TYPE", 0);
define("UPLOAD_ERR_FORM_SIZE", 0);
define("UPLOAD_ERR_INI_SIZE", 0);
define("UPLOAD_ERR_NO_FILE", 0);
define("UPLOAD_ERR_OK", 0);
define("UPLOAD_ERR_PARTIAL", 0);
define("VT_ARRAY", 0);
define("VT_BOOL", 0);
define("VT_BSTR", 0);
define("VT_BYREF", 0);
define("VT_CY", 0);
define("VT_DATE", 0);
define("VT_DECIMAL", 0);
define("VT_DISPATCH", 0);
define("VT_EMPTY", 0);
define("VT_ERROR", 0);
define("VT_I1", 0);
define("VT_I2", 0);
define("VT_I4", 0);
define("VT_INT", 0);
define("VT_NULL", 0);
define("VT_R4", 0);
define("VT_R8", 0);
define("VT_UI1", 0);
define("VT_UI2", 0);
define("VT_UI4", 0);
define("VT_UINT", 0);
define("VT_UNKNOWN", 0);
define("VT_VARIANT", 0);
define("W32API_ARGPTR", 0);
define("W32API_BORLAND", 0);
define("W32API_CDECL", 0);
define("W32API_REAL4", 0);
define("W32API_REAL8", 0);
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
define("XML_TEXT_NODE", 0);
define("XPATH_BOOLEAN", 0);
define("XPATH_LOCATIONSET", 0);
define("XPATH_NODESET", 0);
define("XPATH_NUMBER", 0);
define("XPATH_POINT", 0);
define("XPATH_RANGE", 0);
define("XPATH_STRING", 0);
define("XPATH_UNDEFINED", 0);
define("XPATH_USERS", 0);
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
define("XSLT_ERR_UNSUPPORTED_SCHEME", 0);
define("XSLT_OPT_SILENT", 0);
define("XSLT_SABOPT_DISABLE_ADDING_META", 0);
define("XSLT_SABOPT_DISABLE_STRIPPING", 0);
define("XSLT_SABOPT_FILES_TO_HANDLER", 0);
define("XSLT_SABOPT_IGNORE_DOC_NOT_FOUND", 0);
define("XSLT_SABOPT_PARSE_PUBLIC_ENTITIES", 0);
define("YESEXPR", 0);
define("YESSTR", 0);
define("YPERR_BADARGS", 0);
define("YPERR_BADDB", 0);
define("YPERR_BUSY", 0);
define("YPERR_DOMAIN", 0);
define("YPERR_KEY", 0);
define("YPERR_MAP", 0);
define("YPERR_NODOM", 0);
define("YPERR_NOMORE", 0);
define("YPERR_PMAP", 0);
define("YPERR_RESRC", 0);
define("YPERR_RPC", 0);
define("YPERR_VERS", 0);
define("YPERR_YPBIND", 0);
define("YPERR_YPERR", 0);
define("YPERR_YPSERV", 0);
define("__CLASS__", 0);
define("__FILE__", 0);
define("__FUNCTION__", 0);
define("__LINE__", 0);
// Constants for jobs status
define('JOB_QUEUE_STATUS_SUCCESS', 1);             // Job was processed and succeeded
define('JOB_QUEUE_STATUS_WAITING', 2);             // Job is waiting for being processed (was not scheduled)
define('JOB_QUEUE_STATUS_SUSPENDED', 3);           // Job was suspended
define('JOB_QUEUE_STATUS_SCHEDULED', 4);           // Job is scheduled and waiting in queue
define('JOB_QUEUE_STATUS_WAITING_PREDECESSOR', 5); // Job is waiting for it's predecessor to be completed
define('JOB_QUEUE_STATUS_IN_PROCESS', 6);          // Job is in process in Queue
define('JOB_QUEUE_STATUS_EXECUTION_FAILED', 7);    // Job execution failed in the ZendEnabler
define('JOB_QUEUE_STATUS_LOGICALLY_FAILED', 8);    // Job was processed and failed logically either
                                                   // because of job_fail command or script parse or
                                                   // fatal error

// Constants for different priorities of jobs
define('JOB_QUEUE_PRIORITY_LOW', 0);
define('JOB_QUEUE_PRIORITY_NORMAL', 1);
define('JOB_QUEUE_PRIORITY_HIGH', 2);
define('JOB_QUEUE_PRIORITY_URGENT', 3);

// Constants for saving global variables bit mask
define('JOB_QUEUE_SAVE_POST', 1);
define('JOB_QUEUE_SAVE_GET', 2);
define('JOB_QUEUE_SAVE_COOKIE', 4);
define('JOB_QUEUE_SAVE_SESSION', 8);
define('JOB_QUEUE_SAVE_RAW_POST', 16);
define('JOB_QUEUE_SAVE_SERVER', 32);
define('JOB_QUEUE_SAVE_FILES', 64);
define('JOB_QUEUE_SAVE_ENV', 128);


/**
 * causes a job to fail logically
 * can be used to indicate an error in the script logic (e.g. database connection problem)
 * @param string $error_string the error string to display 
 */
function set_job_failed( $error_string ) { }



/**
 * returns array containing following fields:
 * "license_ok" - whether license allows use of JobQueue
 * "expires" - license expiration date 
 */
function jobqueue_license_info() { };


class ZendAPI_Queue {
    var $_jobqueue_url;
    
    /**
     * Constructor for a job queue connection
     *
     * @param string $jobqueue_url Full address where the queue is in the form host:port
     * @return zendapi_queue object
     */
    function zendapi_queue($queue_url) {}
    
    /**
     * Open a connection to a job queue
     *
     * @param string $password For authentication, password must be specified to connect to a queue
     * @param int $application_id Optional, if set, all subsequent calls to job related methods will use this application id (unless explicitly specified otherwise). I.e. When adding new job, 
        unless this job already set an application id, the job will be assigned the queue application id
     * @return bool Success
     */
    function login($password, $application_id=null) {}
    
    
    /**
     * Insert a new job to the queue, the Job is passed by reference because 
        its new job ID and status will be set in the Job object
         * If the returned job id is 0 it means the job could be added to the queue
         *
     * @param Job $job The Job we want to insert to the queue (by ref.)
     * @return int The inserted job id
     */
    function addJob(&$job) {}
    

    /**
     * Return a Job object that describing a job in the queue
         *
     * @param int $job_id The job id
     * @return Job Object describing a job in the queue
     */
    function getJob($job_id) {}

    /**
     * Update an existing job in the queue with it's new properties. If job doesn't exists, 
        a new job will be added. Job is passed by reference and it's updated from the queue.
     *
     * @param Job $job The Job object, the ID of the given job is the id of the job we try to update.
        If the given Job doesn't have an assigned ID, a new job will be added
     * @return int The id of the updated job
     */
    function updateJob(&$job) {}
    
    /**
     * Remove a job from the queue
     *
     * @param int|array $job_id The job id or array of job ids we want to remove from the queue
     * @return bool Success/Failure
     */
    function removeJob($job_id) {}

    
    /**
     * Suspend a job in the queue (without removing it)
     *
     * @param int|array $job_id The job id or array of job ids we want to suspend
     * @return bool Success/Failure
     */
    function suspendJob($job_id) {}


    /**
     * Resume a suspended job in the queue
     *
     * @param int|array $job_id The job id or array of job ids we want to resume
     * @return bool Success/Failure (if the job wasn't suspended, the function will return false)
     */
    function resumeJob($job_id) {}


    /**
     * Requeue failed job back to the queue.
     *
     * @param job $job  job object to re-query
     * @return bool - true or false.
     */
    function requeueJob($job) {}


    /**
     * returns job statistics
         * @return array with the following:
                         "total_complete_jobs"
                         "total_incomplete_jobs"
                         "average_time_in_queue"  [msec]
                         "average_waiting_time"   [sec]
                         "added_jobs_in_window"
                         "activated_jobs_in_window"
                         "completed_jobs_in_window"
         * moving window size can be set through ini file
         */
    function getStatistics() {}


    /**
     * Returns whether a script exists in the document root
     * @param string $path relative script path
     * @return bool - TRUE if script exists in the document root FALSE otherwise
     */
    function isScriptExists($path) {}


    /**
     * Returns whether the queue is suspended
     * @return bool - TRUE if job is suspended FALSE otherwise
     */
    function isSuspend() {}


    /**
     * Return a list of jobs in the queue according to the options given in the filter_options parameter, doesn't return jobs in "final states" (failed, complete)
     * If application id is set for this queue, only jobs with this application id will be returned
     *
     * @param array $filter_options Array of optional filter options to filter the jobs we want to get 
        from the queue. If not set, all jobs will be returned.<br>
     *     Options can be: priority, application_id, name, status, recurring.
     * @param int max_jobs  Maximum jobs to retrive. Default is -1, getting all jobs available.
     * @param bool with_globals_and_output. Whether gets the global variables dataand job output.
     *     Default is false.
     * @return array. Jobs that satisfies filter_options.
     */
    function getJobsInQueue($filter_options=null, $max_jobs=-1, $with_globals_and_output=false) {}
    

    /**
     * Return the number of jobs in the queue according to the options given in the filter_options parameter
     * @param array $filter_options Array of optional filter options to filter the jobs we want to get from the queue. If not set, all jobs will be counted.<br>
     *     Options can be: priority, application_id, host, name, status, recurring.
     * @return int. Number of jobs that satisfy filter_options.
     */
    function getNumOfJobsInQueue($filter_options=null) {}


    /**
     * Return all the hosts that jobs were submitted from.
     * @return array. 
     */
    function getAllhosts() {}


    /**
     * Return all the application ids exists in queue.
     * @return array.
     */
    function getAllApplicationIDs() {}



    /**
     * Return finished jobs (either failed or successed) between time range allowing paging.
     * Jobs are sorted by job id descending.
     *
     * @param int $status. Filter to jobs by status, 1-success, 0-failed either logical or execution.
     * @param UNIX timestamp $start_time. Get only jobs finished after $start_time.
     * @param UNIX timestamp $end_time. Get only jobs finished before $end_time.
     * @param int $index. Get jobs starting from the $index-th place.
     * @param int $count. Get only $count jobs.
     * @param int $total. Pass by reference. Return the total number of jobs statisifed the query criteria. 
     *
     * @return array of jobs.
     */
    function getHistoricJobs($status, $start_time, $end_time, $index, $count, &$total) {}


    /**
     * Suspends queue operation
     * @return bool - TRUE if successful FALSE otherwise
     */
    function suspendQueue() {}


    /**
     * Resumes queue operation
     * @return bool - TRUE if successful FALSE otherwise
     */
    function resumeQueue() {}


    /**
     * Return description of the last error occured in the queue object. After every
     *    method invoked an error string describing the error is store in the queue object.
     * @return string.
     */
    function getLastError() {}


    /**
     * Sets a new maximum time for keeping historic jobs
     * @return bool - TRUE if successful FALSE otherwise
     */
    function setMaxHistoryTime() {}
}

/**
 * Describing a job in a queue
 * In order to add/modify a job in the queue, a Job class must be created/retrieved and than saved in a queue
 *
 * For simplicity, a job can be added directly to a queue and without creating an instant of a Queue object
 */
class ZendAPI_Job {
    
    /**
     * Unique id of the Job in the job queue
     *
     * @var int
     */
    var $_id;
    
    /**
     * Full path of the script that this job calls when it's processed
     *
     * @var string
     */
    var $_script;
    
    /**
     * The host that the job was submit from
     *
     * @var string
     */
    var $_host;

    /**
     * A short string describing the job
     *
     * @var string
     */
    var $_name;


    /**
     * The job output after executing
     *
     * @var string
     */
    var $_output;

    /**
     * The status of the job
     * By default, the job status is waiting to being execute. 
     * The status is determent by the queue and can not be modify by the user.
     *
     * @var int
     */
    var $_status = JOB_QUEUE_STATUS_WAITING;

    /**
     * The application id of the job
     * If the application id is not set, this job may get an application id automatically from the queue 
     * (if the queue was assigned one). By default it is null (which indicates no application id is assigned)
     *
     * @var string
     */
    var $_application_id = null;
    
    /**
     * The priority of the job, options are the priority constants
     * By default the priority is set to normal (JOB_QUEUE_PRIORITY_NORMAL)
     *
     * @var int
     */
    var $_priority = JOB_QUEUE_PRIORITY_NORMAL;
    
    /**
     * Array holding all the variables that the user wants the job's script to have when it's called
     * The structure is variable_name => variable_value
        i.e. if the user_variables array is array('my_var' => 8), when the script is called,
        a global variable called $my_var will have the int value of 8
     * By default there are no variables that we want to add to the job's script
     *
     * @var array
     */
    var $_user_variables = array();
    
    /**
     * Bit mask holding the global variables that the user want the job's script to have when it's called
     * Options are prefixed with "JOB_QUEUE_SAVE_" and may be:
        POST|GET|COOKIE|SESSION|RAW_POST|SERVER|FILES|ENV
     * By default there are no global variables we want to add to the job's script
     * i.e. In order to save the current GET and COOKIE global variables,
        this property should be JOB_QUEUE_SAVE_GET|JOB_QUEUE_SAVE_COOKIE (or the integer 6)
        In that case (of GET and COOKIE), when the job is added, the current $_GET and 
        $_COOKIE variables  should be saved, and when the job's script is called,
        those global variables should be populated
     *
     * @var int
     */
    var $_global_variables = 0;
    
    /**
     * The job may have a dependency (another job that must be performed before this job)
     * This property hold the id of the job that must be performed. if this variable is an array of integers,
        it means that there are several jobs that must be performed before this job 
     * By default there are no dependencies
     *
     * @var mixed (int|array)
     */
    var $_predecessor = null;
    
    /**
     * The time that this job should be performed, this variables is the UNIX timestamp.
     * If set to 0, it means that the job should be performed now (or at least as soon as possible)
     * By default there is no scheduled time, which means we want to perform the job as soon as possible
     *
     * @var int
     */
    var $_scheduled_time = 0;
    
    /**
     * The job running frequency in seconds. The job should run every _internal seconds
     * This property applys only to recurrent job. 
     * By default, its value is 0 e.g. run it only once.
     *
     * @var int
     */
         var $_interval = 0;

    /**
     * UNIX timestamp that it's the last time this job should occurs. If _interval was set, and _end_time
     * was not, then this job will run forever.
     * By default there is no end_time, so recurrent job will run forever. If the job is not recurrent
     * (occurs only once) then the job will run at most once. If end_time has reached and the job was not
     * execute yet, it will not run.
     * 
     * @var int
     */
     var $_end_time = null;


    /**
     * A bit that determine whether job can be deleted from history. When set, removeJob will not
     * delete the job from history.
     *
     * @var int
     */
     var $_preserved = 0;

    
    /**
     * Instantiate a Job object, describe all the information and properties of a job
     *
     * @param script $script relative path (relative to document root supplied in ini file) of the script this job should call when it's executing
     * @return Job
     */
    function ZendAPI_Job($script) {}
    

    /**
     * Add the job the the specified queue (without instantiating a JobQueue object)
     * This function should be used for extreme simplicity of the user when adding a single job,
            when the user want to insert more than one job and/or manipulating other jobs (or job tasks) 
            he should create and use the JobQueue object
     * Actually what this function do is to create a new JobQueue, login to it (with the given parameters), 
            add this job to it and logout
     * 
     * @param string $jobqueue_url Full address of the queue we want to connect to
     * @param string $password For authentication, the queue password
     * @return int The added job id or false on failure
     */
    function addJobToQueue($jobqueue_url, $password) {}


    /**
     * Set a new priority to the job
     *
     * @param int $priority Priority options are constants with the "JOB_QUEUE_PRIORITY_" prefix
     */
    function setJobPriority($priority) {}
    
    // All properties SET functions
    function setJobName($name) {}
    function setScript($script) {}
    function setApplicationID($app_id) {}
    function setUserVariables($vars) {}
    function setGlobalVariables($vars) {}
    function setJobDependency($job_id) {}
    function setScheduledTime($timestamp) {}
    function setRecurrenceData($interval, $end_time=null) {}
    function setPreserved($preserved) { }
    
    /**
     * Get the job properties
     *
     * @return array The same format of job options array as in the Job constructor
     */
    function getProperties() {}

    /**
     * Get the job output
     *
     * @return An HTML representing the job output
     */
    function getOutput() {}
    
    // All properties GET functions
    function getID() {}
    function getHost() {}
    function getScript() {}
    function getJobPriority() {}
    function getJobName() {}
    function getApplicationID() {}
    function getUserVariables() {}
    function getGlobalVariables() {}
    function getJobDependency() {}
    function getScheduledTime() {}
    function getInterval() {}
    function getEndTime() {}
    function getPreserved() {}

    /**
     * Get the current status of the job
     * If this job was created and not returned from a queue (using the JobQueue::GetJob() function), 
     *  the function will return false
     * The status is one of the constants with the "JOB_QUEUE_STATUS_" prefix. 
     * E.g. job was performed and failed, job is waiting etc.
     *
     * @return int
     */
    function getJobStatus() {}

    /**
     * Get how much seconds there are until the next time the job will run. 
     * If the job is not recurrence or it past its end time, then return 0.
     *
     * @return int
     */
     function getTimeToNextRepeat() {}

    /**
     * For recurring job get the status of the last execution. For simple job,
     * getLastPerformedStatus is equivalent to getJobStatus.
     * jobs that haven't been executed yet will return STATUS_WAITING
     * @return int
     */
     function getLastPerformedStatus() {}

    
}

class stdClass {
};

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



/**
 * Disable/enable the Code Acceleration functionality at run time.
 * @param status bool If false, Acceleration is disabled, if true - enabled
 * @return void
 */ 
function accelerator_set_status($status) {}

/**
 * Disables output caching for currently running scripts.
 * @return void
 */
function output_cache_disable() {}

/**
 * Does not allow the cache to perform compression on the output of the current page.
 * This output will not be compressed, even if the global set tings would normally allow
 * compression on files of this type.
 * @return void
 */
function output_cache_disable_compression() {}

/**
 * Gets the code’s return value from the cache if it is there, if not - run function and cache the value.
 * @param key string cache key
 * @param function string PHP expression
 * @param lifetime int data lifetime in cache (seconds)
 * @return string function's return
 */
function output_cache_fetch($key, $function, $lifetime) {}

/**
 * If they cache for the key exists, output it, otherwise capture expression output, cache and pass it out.
 * @param key string cache key
 * @param function string PHP expression
 * @param lifetime int data lifetime in cache (seconds)
 * @return expression output
 */
function output_cache_output($key, $function, $lifetime) {}

/**
 * Removes all the cache data for the given filename.
 * @param filename string full script path on local filesystem
 * @return bool true if OK, false if something went wrong
 */
function output_cache_remove($filename) {}

/**
 * Remove cache data for the script with given URL (all dependent data is removed)
 * @param url string the local url for the script
 * @return bool true if OK
 */
function output_cache_remove_url($url) {}

/**
 * Remove item from PHP API cache by key
 * @param key string cache key as given to output_cache_get/output_cache_put
 * @return bool true if OK
 */
function output_cache_remove_key($key) {}

/**
 * Puts data in cache according to the assigned key.
 * @param key string cache key
 * @param data mixed cached data (must not contain objects or resources)
 * @return bool true if OK
 */
function output_cache_put($key, $data) {}

/**
 * Gets cached data according to the assigned key.
 * @param key string cache key
 * @param lifetime int cache validity time (seconds)
 * @return mixed cached data if cache exists, false otherwise
 */
function output_cache_get($key, $lifetime) {}

/**
 * If data for assigned key exists, this function outputs it and returns a value of true.
 * If not, it starts capturing the output. To be used in pair with output_cache_stop.
 * @param key string cache key
 * @param lifetime int cache validity time (seconds)
 * @return bool true if cached data exists
 */
function output_cache_exists($key, $lifetime) {}

/**
 * If output was captured by output_cache_exists, this function stops the output capture and stores
 * the data under the key that was given to output_cache_exists().
 * @return void
 */
function output_cache_stop() {}


/**
 * Should be called from a custom error handler to pass the error to the monitor.
 * The user function needs to accept two parameters: the error code, and a string describing the error.
 * Then there are two optional parameters that may be supplied: the filename in which the error occurred
 * and the line number  in which the error occurred.
 * @param errno int
 * @param errstr string
 * @param errfile string
 * @param errline integer
 * @return void
 */
function monitor_pass_error($errno, $errstr, $errfile, $errline) {}

/**
 * Limited in the database to 255 chars
 * @param hint string
 * @return void
 */
function monitor_set_aggregation_hint($hint) {}

/**
 * Creates a custom event with class $class, text $text and possibly severity and other user data
 * @param class string
 * @param text string
 * @param severe int[optional]
 * @param user_data mixed[optional]
 * @return void
 */
function monitor_custom_event($class, $text, $severe = null, $user_data = null) {}

/**
 * Create an HTTPERROR event
 * @param error_code int the http error code to be associated with this event
 * @param url string the URL to be associated with this event
 * @param severe int[optional] the severety of the event: 0 - not severe, 1 - severe
 * @return void
 */
function monitor_httperror_event($error_code, $url, $severe = null) {}

/**
 * Returns an array containing information about
 * <li>module loading status (and cause of error if module failed to load)
 * <li>module license status (and cause of error if license not valid)
 * @return array 
 */
function monitor_license_info() {}

/**
 * Allow you to register a user function as an event handler.When a monitor event is triggerd
 * all the user event handler are called and the return value from the handler is saved in
 * an array keyed by the name the event handler was registered under. The event handlers
 * results array is saved in the event_extra_data table.
 * @param event_handler_func string The callback function that will be call when the event is triggered, object methods may also be invoked statically using t
his function by passing array($objectname, $methodname) to the function parameter
 * @param handler_register_name string[optional] The name this function is registered under - if none is supplied, the function will be registerd under it's own name
 * @param event_type_mask int The mask of event types that the handler should be called on by default it's set to MONITOR_EVENT_ALL.
 * @return bool TRUE on sucess and FALSE if an error occurs.
 */
function register_event_handler($event_handler_func, $handler_register_name, $event_type_mask) {}

/**
 * Allow you to unregister an event handler.
 * @param handler_name string the name you registered with the handler you now wish to unregister.
 * @return bool TRUE on sucess and FALSE if no handler we registered under the given name.
 */
function unregister_event_handler($handler_name) {}

/**
 * Send a file using ZDS
 * @param filename string path to the file
 * @param mime_type string[optional] MIME type of the file, if omitted, taken from configured MIME types file
 * @param custom_headers string[optional] user defined headers that will be send instead of regular ZDS headers. few basic essential headers will be send anyway
 * @return bool FALSE if sending file failed, does not return otherwise
 */
function zend_send_file($filename, $mime_type, $custom_headers) {}

/**
 * Send a buffer using ZDS
 * @param buffer string the content that will be send
 * @param mime_type string[optional] MIME type of the buffer, if omitted, taken from configured MIME types file
 * @param custom_headers string[optional] user defined headers that will be send instead of regular ZDS headers. few basic essential headers will be send anyway
 * @return bool FALSE if sending file failed, does not return otherwise
 */
function zend_send_buffer($buffer, $mime_type, $custom_headers) {}


class java {
    /**
     * Create Java object
     *
     * @return java
     * @param  classname string
     * @vararg ...
     */
    function java($classname) {}

};

class JavaException {
    /**
     * Get Java exception that led to this exception
     *
     * @return object
     */
    function getCause() {}

};


/**
 * Create Java object
 *
 * @return object
 * @param  class string
 * @vararg ...
 */
function java($class) {}


/**
 * Return Java exception object for last exception
 * @return object Java Exception object, if there was an exception, false otherwise
 */
function java_last_exception_get() {}

/**
 * Clear last Java exception object record.
 * @return void
 */
function java_last_exception_clear() {}

/**
 * Set case sensitivity for Java calls.
 * @param ignore bool if set, Java attribute and method names would be resolved disregarding case. NOTE: this does not make any Java functions case insensi
tive, just things like $foo->bar and $foo->bar() would match Bar too.
 * @return void
 */
function java_set_ignore_case($ignore) {}

/**
 * Set encoding for strings received by Java from PHP. Default is UTF-8.
 * @param encoding string
 * @return array
 */
function java_set_encoding($encoding) {}

/**
 * Control if exceptions are thrown on Java exception. Only for PHP5.
 * @param throw bool If true, PHP exception is thrown when Java exception happens. If set to false, use java_last_exception_get() to check for exception.
 * @return void
 */
function java_throw_exceptions($throw) {}

/**
 * Reload Jar's that were dynamically loaded
 *
 * @return array
 * @param  new_jarpath string
 */
function java_reload($new_jarpath) {}

/**
 * Add to Java's classpath in runtime
 *
 * @return array
 * @param  new_classpath string
 */
function java_require($new_classpath) {}


/**
 * Shown if loader is enabled
 * @return bool
 */
function zend_loader_enabled() {}

/**
 * Returns true if the current file is a Zend-encoded file.
 * @return bool
 */
function zend_loader_file_encoded() {}

/**
 * Returns license (array with fields) if the current file has a valid license and is encoded, otherwise it returns false.
 * @return array 
 */
function zend_loader_file_licensed() {}

/**
 * Returns the name of the file currently being executed.
 * @return string
 */
function zend_loader_current_file() {}

/**
 * Dynamically loads a license for applications encoded with Zend SafeGuard. The Override controls if it will override old licenses for the same product.
 * @param license_file string
 * @param override bool[optional]
 * @return bool
 */
function zend_loader_install_license($license_file, $override) {}

/**
 * Obfuscate and return the given function name with the internal obfuscation function.
 * @param function_name string
 * @return string
 */
function zend_obfuscate_function_name($function_name) {}

/**
 * Obfuscate and return the given class name with the internal obfuscation function.
 * @param class_name string
 * @return string
 */
function zend_obfuscate_class_name($class_name) {}

/**
 * Returns the current obfuscation level support (set by zend_optimizer.obfuscation_level_support)
 * @return int
 */
function zend_current_obfuscation_level() {}

/**
 * Start runtime-obfuscation support that allows limited mixing of obfuscated and un-obfuscated code.
 * @return void
 */
function zend_runtime_obfuscate() {}

/**
 * Returns array of the host ids. If all_ids is true, then all IDs are returned, otehrwise only IDs considered "primary" are returned.
 * @param all_ids bool[optional]
 * @return array
 */
function zend_get_id($all_ids = false) {}

/**
 * Returns Optimizer version. Alias: zend_loader_version()
 * @return string
 */
function zend_optimizer_version() {}


?>
