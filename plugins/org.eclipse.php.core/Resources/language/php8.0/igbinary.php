<?php

// Start of igbinary v.3.2.14

/**
 * Generates a compact, storable binary representation of a value
 * @link http://www.php.net/manual/en/function.igbinary-serialize.php
 * @param mixed $value The value to be serialized. igbinary_serialize
 * handles all types, except the resource-type and some objects (see note below).
 * Even arrays that contain references to itself can be igbinary_serialized.
 * Circular references inside the array or object that is being serializend will also be stored.
 * Any other reference will be lost.
 * <p>When serializing objects, igbinary will attempt to call the member functions
 * __serialize() or
 * __sleep() prior to serialization.
 * This is to allow the object to do any last minute clean-up, etc. prior
 * to being serialized. Likewise, when the object is restored using
 * igbinary_unserialize the __unserialize() or
 * __wakeup() member function is called.</p>
 * <p>Private members of objects have the class name prepended to the member
 * name; protected members have a '&#42;' prepended to the member name.
 * These prepended values have null bytes on either side.</p>
 * @return string|false Returns a string containing a byte-stream representation of
 * value that can be stored anywhere.
 * <p>Note that this is a binary string which can include any byte value, and needs
 * to be stored and handled as such. For example,
 * igbinary_serialize output should generally be stored in a BLOB
 * field in a database, rather than a CHAR or TEXT field.</p>
 */
function igbinary_serialize (mixed $value): string|int {}

/**
 * Creates a PHP value from a stored representation from igbinary_serialize
 * @link http://www.php.net/manual/en/function.igbinary-unserialize.php
 * @param string $str The serialized string generated by igbinary_serialize.
 * <p>If the value being unserialized is an object, after successfully
 * reconstructing the object igbinary will automatically attempt to call the
 * __unserialize() or
 * __wakeup() methods (if one exists).</p>
 * <p>unserialize_callback_func directive
 * <p>
 * It is possible to set a callback function which will be called,
 * if an undefined class should be instantiated during unserializing.
 * (to prevent getting an incomplete object __PHP_Incomplete_Class.)
 * The php.ini, ini_set or .htaccess can be used
 * to define unserialize_callback_func.
 * Everytime an undefined class should be instantiated, it will be called.
 * To disable this feature this setting should be emptied.
 * </p></p>
 * <p>It is possible to set a callback function which will be called,
 * if an undefined class should be instantiated during unserializing.
 * (to prevent getting an incomplete object __PHP_Incomplete_Class.)
 * The php.ini, ini_set or .htaccess can be used
 * to define unserialize_callback_func.
 * Everytime an undefined class should be instantiated, it will be called.
 * To disable this feature this setting should be emptied.</p>
 * @return mixed The converted value is returned, and can be a bool,
 * int, float, string,
 * array, object, or null.
 * <p>In case the passed string is not unserializeable, false is returned and
 * E_NOTICE or E_WARNING is issued.</p>
 */
function igbinary_unserialize (string $str): mixed {}

// End of igbinary v.3.2.14