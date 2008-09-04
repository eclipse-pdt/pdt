<?php

// Start of oci8 v.1.3.4

class OCI_Lob  {

	/**
	 * Returns large object's contents
	 * @link http://php.net/manual/en/function.oci-lob-load.php
	 * @return string the contents of the object, or false on errors.
	 */
	public function load () {}

	/**
	 * Returns current position of internal pointer of large object
	 * @link http://php.net/manual/en/function.oci-lob-tell.php
	 * @return int current position of a LOB's internal pointer or false if an
	 */
	public function tell () {}

	/**
	 * Truncates large object
	 * @link http://php.net/manual/en/function.oci-lob-truncate.php
	 * @param length int[optional]
	 * @return bool 
	 */
	public function truncate ($length = null) {}

	/**
	 * Erases a specified portion of the internal LOB data
	 * @link http://php.net/manual/en/function.oci-lob-erase.php
	 * @param offset int[optional]
	 * @param length int[optional]
	 * @return int the actual number of characters/bytes erased or false in case of
	 */
	public function erase ($offset = null, $length = null) {}

	/**
	 * Flushes/writes buffer of the LOB to the server
	 * @link http://php.net/manual/en/function.oci-lob-flush.php
	 * @param flag int[optional]
	 * @return bool false if buffering was not enabled or an error occurred.
	 */
	public function flush ($flag = null) {}

	/**
	 * Changes current state of buffering for the large object
	 * @link http://php.net/manual/en/function.oci-lob-setbuffering.php
	 * @param on_off bool
	 * @return bool 
	 */
	public function setbuffering ($on_off) {}

	/**
	 * Returns current state of buffering for the large object
	 * @link http://php.net/manual/en/function.oci-lob-getbuffering.php
	 * @return bool false if buffering for the large object is off and true if
	 */
	public function getbuffering () {}

	/**
	 * Moves the internal pointer to the beginning of the large object
	 * @link http://php.net/manual/en/function.oci-lob-rewind.php
	 * @return bool 
	 */
	public function rewind () {}

	/**
	 * Reads part of the large object
	 * @link http://php.net/manual/en/function.oci-lob-read.php
	 * @param length int
	 * @return string the contents as a string, or false in case of error.
	 */
	public function read ($length) {}

	/**
	 * Tests for end-of-file on a large object's descriptor
	 * @link http://php.net/manual/en/function.oci-lob-eof.php
	 * @return bool true if internal pointer of large object is at the end of LOB.
	 */
	public function eof () {}

	/**
	 * Sets the internal pointer of the large object
	 * @link http://php.net/manual/en/function.oci-lob-seek.php
	 * @param offset int
	 * @param whence int[optional]
	 * @return bool 
	 */
	public function seek ($offset, $whence = null) {}

	/**
	 * Writes data to the large object
	 * @link http://php.net/manual/en/function.oci-lob-write.php
	 * @param data string
	 * @param length int[optional]
	 * @return int the number of bytes written or false in case of error.
	 */
	public function write ($data, $length = null) {}

	/**
	 * Appends data from the large object to another large object
	 * @link http://php.net/manual/en/function.oci-lob-append.php
	 * @param lob_from OCI-Lob
	 * @return bool 
	 */
	public function append (OCI-Lob $lob_from) {}

	/**
	 * Returns size of large object
	 * @link http://php.net/manual/en/function.oci-lob-size.php
	 * @return int length of large object value or false in case of error.
	 */
	public function size () {}

	/**
	 * &Alias; <function>oci_lob_export</function>
	 * @link http://php.net/manual/en/function.oci-lob-writetofile.php
	 * @param filename
	 * @param start[optional]
	 * @param length[optional]
	 */
	public function writetofile ($filename, $start, $length) {}

	/**
	 * Exports LOB's contents to a file
	 * @link http://php.net/manual/en/function.oci-lob-export.php
	 * @param filename string
	 * @param start int[optional]
	 * @param length int[optional]
	 * @return bool 
	 */
	public function export ($filename, $start = null, $length = null) {}

	/**
	 * Imports file data to the LOB
	 * @link http://php.net/manual/en/function.oci-lob-import.php
	 * @param filename string
	 * @return bool 
	 */
	public function import ($filename) {}

	/**
	 * Writes temporary large object
	 * @link http://php.net/manual/en/function.oci-lob-writetemporary.php
	 * @param data string
	 * @param lob_type int[optional]
	 * @return bool 
	 */
	public function writetemporary ($data, $lob_type = null) {}

	/**
	 * Closes LOB descriptor
	 * @link http://php.net/manual/en/function.oci-lob-close.php
	 * @return bool 
	 */
	public function close () {}

	/**
	 * Saves data to the large object
	 * @link http://php.net/manual/en/function.oci-lob-save.php
	 * @param data string
	 * @param offset int[optional]
	 * @return bool 
	 */
	public function save ($data, $offset = null) {}

	/**
	 * &Alias; <function>oci_lob_import</function>
	 * @link http://php.net/manual/en/function.oci-lob-savefile.php
	 * @param filename
	 */
	public function savefile ($filename) {}

	/**
	 * Frees resources associated with the LOB descriptor
	 * @link http://php.net/manual/en/function.oci-lob-free.php
	 * @return bool 
	 */
	public function free () {}

}

class OCI_Collection  {

	/**
	 * Appends element to the collection
	 * @link http://php.net/manual/en/function.oci-collection-append.php
	 * @param value mixed
	 * @return bool 
	 */
	public function append ($value) {}

	/**
	 * Returns value of the element
	 * @link http://php.net/manual/en/function.oci-collection-element-get.php
	 * @param index int
	 * @return mixed false if such element doesn't exist; &null; if element is &null;;
	 */
	public function getelem ($index) {}

	/**
	 * Assigns a value to the element of the collection
	 * @link http://php.net/manual/en/function.oci-collection-element-assign.php
	 * @param index int
	 * @param value mixed
	 * @return bool 
	 */
	public function assignelem ($index, $value) {}

	/**
	 * Assigns a value to the collection from another existing collection
	 * @link http://php.net/manual/en/function.oci-collection-assign.php
	 * @param from OCI-Collection
	 * @return bool 
	 */
	public function assign (OCI-Collection $from) {}

	/**
	 * Returns size of the collection
	 * @link http://php.net/manual/en/function.oci-collection-size.php
	 * @return int the number of elements in the collection or false on error.
	 */
	public function size () {}

	/**
	 * Returns the maximum number of elements in the collection
	 * @link http://php.net/manual/en/function.oci-collection-max.php
	 * @return int the maximum number as an integer, or false on errors.
	 */
	public function max () {}

	/**
	 * Trims elements from the end of the collection
	 * @link http://php.net/manual/en/function.oci-collection-trim.php
	 * @param num int
	 * @return bool 
	 */
	public function trim ($num) {}

	/**
	 * Frees the resources associated with the collection object
	 * @link http://php.net/manual/en/function.oci-collection-free.php
	 * @return bool 
	 */
	public function free () {}

}

/**
 * Uses a PHP variable for the define-step during a SELECT
 * @link http://php.net/manual/en/function.oci-define-by-name.php
 * @param statement resource
 * @param column_name string
 * @param variable mixed
 * @param type int[optional]
 * @return bool 
 */
function oci_define_by_name ($statement, $column_name, &$variable, $type = null) {}

/**
 * Binds the PHP variable to the Oracle placeholder
 * @link http://php.net/manual/en/function.oci-bind-by-name.php
 * @param statement resource
 * @param ph_name string
 * @param variable mixed
 * @param maxlength int[optional]
 * @param type int[optional]
 * @return bool 
 */
function oci_bind_by_name ($statement, $ph_name, &$variable, $maxlength = null, $type = null) {}

/**
 * Binds PHP array to Oracle PL/SQL array by name
 * @link http://php.net/manual/en/function.oci-bind-array-by-name.php
 * @param statement resource
 * @param name string
 * @param var_array array
 * @param max_table_length int
 * @param max_item_length int[optional]
 * @param type int[optional]
 * @return bool 
 */
function oci_bind_array_by_name ($statement, $name, array &$var_array, $max_table_length, $max_item_length = null, $type = null) {}

/**
 * Checks if the field is &null;
 * @link http://php.net/manual/en/function.oci-field-is-null.php
 * @param statement resource
 * @param field mixed
 * @return bool true if field is &null;, false otherwise.
 */
function oci_field_is_null ($statement, $field) {}

/**
 * Returns the name of a field from the statement
 * @link http://php.net/manual/en/function.oci-field-name.php
 * @param statement resource
 * @param field int
 * @return string the name as a string, or false on errors.
 */
function oci_field_name ($statement, $field) {}

/**
 * Returns field's size
 * @link http://php.net/manual/en/function.oci-field-size.php
 * @param statement resource
 * @param field mixed
 * @return int the size of a field in bytes, or false on
 */
function oci_field_size ($statement, $field) {}

/**
 * Tell the scale of the field
 * @link http://php.net/manual/en/function.oci-field-scale.php
 * @param statement resource
 * @param field int
 * @return int the scale as an integer, or false on errors.
 */
function oci_field_scale ($statement, $field) {}

/**
 * Tell the precision of a field
 * @link http://php.net/manual/en/function.oci-field-precision.php
 * @param statement resource
 * @param field int
 * @return int the precision as an integer, or false on errors.
 */
function oci_field_precision ($statement, $field) {}

/**
 * Returns field's data type
 * @link http://php.net/manual/en/function.oci-field-type.php
 * @param statement resource
 * @param field int
 * @return mixed the field data type as a string, or false on errors.
 */
function oci_field_type ($statement, $field) {}

/**
 * Tell the raw Oracle data type of the field
 * @link http://php.net/manual/en/function.oci-field-type-raw.php
 * @param statement resource
 * @param field int
 * @return int Oracle's raw data type as a string, or false on errors.
 */
function oci_field_type_raw ($statement, $field) {}

/**
 * Executes a statement
 * @link http://php.net/manual/en/function.oci-execute.php
 * @param statement resource
 * @param mode int[optional]
 * @return bool 
 */
function oci_execute ($statement, $mode = null) {}

/**
 * Cancels reading from cursor
 * @link http://php.net/manual/en/function.oci-cancel.php
 * @param statement resource
 * @return bool 
 */
function oci_cancel ($statement) {}

/**
 * Fetches the next row into result-buffer
 * @link http://php.net/manual/en/function.oci-fetch.php
 * @param statement resource
 * @return bool 
 */
function oci_fetch ($statement) {}

/**
 * Returns the next row from the result data as an object
 * @link http://php.net/manual/en/function.oci-fetch-object.php
 * @param statement resource
 * @return object an object, which attributes correspond to fields in statement, or
 */
function oci_fetch_object ($statement) {}

/**
 * Returns the next row from the result data as a numeric array
 * @link http://php.net/manual/en/function.oci-fetch-row.php
 * @param statement resource
 * @return array an indexed array with the field information, or false if there
 */
function oci_fetch_row ($statement) {}

/**
 * Returns the next row from the result data as an associative array
 * @link http://php.net/manual/en/function.oci-fetch-assoc.php
 * @param statement resource
 * @return array an associative array, or false if there are no more rows in the
 */
function oci_fetch_assoc ($statement) {}

/**
 * Returns the next row from the result data as an associative or
   numeric array, or both
 * @link http://php.net/manual/en/function.oci-fetch-array.php
 * @param statement resource
 * @param mode int[optional]
 * @return array an array with both associative and numeric indices, or false if
 */
function oci_fetch_array ($statement, $mode = null) {}

/**
 * Fetches the next row into an array (deprecated)
 * @link http://php.net/manual/en/function.ocifetchinto.php
 * @param statement resource
 * @param result array
 * @param mode int[optional]
 * @return int 
 */
function ocifetchinto ($statement, array &$result, $mode = null) {}

/**
 * Fetches all rows of result data into an array
 * @link http://php.net/manual/en/function.oci-fetch-all.php
 * @param statement resource
 * @param output array
 * @param skip int[optional]
 * @param maxrows int[optional]
 * @param flags int[optional]
 * @return int the number of rows fetched or false in case of an error.
 */
function oci_fetch_all ($statement, array &$output, $skip = null, $maxrows = null, $flags = null) {}

/**
 * Frees all resources associated with statement or cursor
 * @link http://php.net/manual/en/function.oci-free-statement.php
 * @param statement resource
 * @return bool 
 */
function oci_free_statement ($statement) {}

/**
 * Enables or disables internal debug output
 * @link http://php.net/manual/en/function.oci-internal-debug.php
 * @param onoff bool
 * @return void 
 */
function oci_internal_debug ($onoff) {}

/**
 * Returns the number of result columns in a statement
 * @link http://php.net/manual/en/function.oci-num-fields.php
 * @param statement resource
 * @return int the number of columns as an integer, or false on errors.
 */
function oci_num_fields ($statement) {}

/**
 * Prepares Oracle statement for execution
 * @link http://php.net/manual/en/function.oci-parse.php
 * @param connection resource
 * @param query string
 * @return resource a statement handler on success, or false on error.
 */
function oci_parse ($connection, $query) {}

/**
 * Allocates and returns a new cursor (statement handle)
 * @link http://php.net/manual/en/function.oci-new-cursor.php
 * @param connection resource
 * @return resource a new statement handle, or false on error.
 */
function oci_new_cursor ($connection) {}

/**
 * Returns field's value from the fetched row
 * @link http://php.net/manual/en/function.oci-result.php
 * @param statement resource
 * @param field mixed
 * @return mixed everything as strings except for abstract types (ROWIDs, LOBs and
 */
function oci_result ($statement, $field) {}

/**
 * Returns server version
 * @link http://php.net/manual/en/function.oci-server-version.php
 * @param connection resource
 * @return string the version information as a string or false on error.
 */
function oci_server_version ($connection) {}

/**
 * Returns the type of an OCI statement
 * @link http://php.net/manual/en/function.oci-statement-type.php
 * @param statement resource
 * @return string the query type ofstatement as one of the
 */
function oci_statement_type ($statement) {}

/**
 * Returns number of rows affected during statement execution
 * @link http://php.net/manual/en/function.oci-num-rows.php
 * @param statement resource
 * @return int the number of rows affected as an integer, or false on errors.
 */
function oci_num_rows ($statement) {}

/**
 * Closes Oracle connection
 * @link http://php.net/manual/en/function.oci-close.php
 * @param connection resource
 * @return bool 
 */
function oci_close ($connection) {}

/**
 * Establishes a connection to the Oracle server
 * @link http://php.net/manual/en/function.oci-connect.php
 * @param username string
 * @param password string
 * @param db string[optional]
 * @param charset string[optional]
 * @param session_mode int[optional]
 * @return resource a connection identifier or false on error.
 */
function oci_connect ($username, $password, $db = null, $charset = null, $session_mode = null) {}

/**
 * Establishes a new connection to the Oracle server
 * @link http://php.net/manual/en/function.oci-new-connect.php
 * @param username string
 * @param password string
 * @param db string[optional]
 * @param charset string[optional]
 * @param session_mode int[optional]
 * @return resource a connection identifier or false on error.
 */
function oci_new_connect ($username, $password, $db = null, $charset = null, $session_mode = null) {}

/**
 * Connect to an Oracle database using a persistent connection
 * @link http://php.net/manual/en/function.oci-pconnect.php
 * @param username string
 * @param password string
 * @param db string[optional]
 * @param charset string[optional]
 * @param session_mode int[optional]
 * @return resource a connection identifier or false on error.
 */
function oci_pconnect ($username, $password, $db = null, $charset = null, $session_mode = null) {}

/**
 * Returns the last error found
 * @link http://php.net/manual/en/function.oci-error.php
 * @param source resource[optional]
 * @return array 
 */
function oci_error ($source = null) {}

/**
 * @param lob_descriptor
 */
function oci_free_descriptor ($lob_descriptor) {}

/**
 * @param lob_descriptor
 * @param data
 * @param offset[optional]
 */
function oci_lob_save ($lob_descriptor, $data, $offset) {}

/**
 * @param lob_descriptor
 * @param filename
 */
function oci_lob_import ($lob_descriptor, $filename) {}

/**
 * @param lob_descriptor
 */
function oci_lob_size ($lob_descriptor) {}

/**
 * @param lob_descriptor
 */
function oci_lob_load ($lob_descriptor) {}

/**
 * @param lob_descriptor
 * @param length
 */
function oci_lob_read ($lob_descriptor, $length) {}

/**
 * @param lob_descriptor
 */
function oci_lob_eof ($lob_descriptor) {}

/**
 * @param lob_descriptor
 */
function oci_lob_tell ($lob_descriptor) {}

/**
 * @param lob_descriptor
 * @param length[optional]
 */
function oci_lob_truncate ($lob_descriptor, $length) {}

/**
 * @param lob_descriptor
 * @param offset[optional]
 * @param length[optional]
 */
function oci_lob_erase ($lob_descriptor, $offset, $length) {}

/**
 * @param lob_descriptor
 * @param flag[optional]
 */
function oci_lob_flush ($lob_descriptor, $flag) {}

/**
 * @param lob_descriptor
 * @param mode
 */
function ocisetbufferinglob ($lob_descriptor, $mode) {}

/**
 * @param lob_descriptor
 */
function ocigetbufferinglob ($lob_descriptor) {}

/**
 * Compares two LOB/FILE locators for equality
 * @link http://php.net/manual/en/function.oci-lob-is-equal.php
 * @param lob1 OCI-Lob
 * @param lob2 OCI-Lob
 * @return bool true if these objects are equal, false otherwise.
 */
function oci_lob_is_equal (OCI-Lob $lob1, OCI-Lob $lob2) {}

/**
 * @param lob_descriptor
 */
function oci_lob_rewind ($lob_descriptor) {}

/**
 * @param lob_descriptor
 * @param string
 * @param length[optional]
 */
function oci_lob_write ($lob_descriptor, $string, $length) {}

/**
 * @param lob_descriptor_to
 * @param lob_descriptor_from
 */
function oci_lob_append ($lob_descriptor_to, $lob_descriptor_from) {}

/**
 * Copies large object
 * @link http://php.net/manual/en/function.oci-lob-copy.php
 * @param lob_to OCI-Lob
 * @param lob_from OCI-Lob
 * @param length int[optional]
 * @return bool 
 */
function oci_lob_copy (OCI-Lob $lob_to, OCI-Lob $lob_from, $length = null) {}

/**
 * @param lob_descriptor
 * @param filename
 * @param start[optional]
 * @param length[optional]
 */
function oci_lob_export ($lob_descriptor, $filename, $start, $length) {}

/**
 * @param lob_descriptor
 * @param offset
 * @param whence[optional]
 */
function oci_lob_seek ($lob_descriptor, $offset, $whence) {}

/**
 * Commits outstanding statements
 * @link http://php.net/manual/en/function.oci-commit.php
 * @param connection resource
 * @return bool 
 */
function oci_commit ($connection) {}

/**
 * Rolls back outstanding transaction
 * @link http://php.net/manual/en/function.oci-rollback.php
 * @param connection resource
 * @return bool 
 */
function oci_rollback ($connection) {}

/**
 * Initializes a new empty LOB or FILE descriptor
 * @link http://php.net/manual/en/function.oci-new-descriptor.php
 * @param connection resource
 * @param type int[optional]
 * @return OCI-Lob a new LOB or FILE descriptor on success, false on error.
 */
function oci_new_descriptor ($connection, $type = null) {}

/**
 * Sets number of rows to be prefetched
 * @link http://php.net/manual/en/function.oci-set-prefetch.php
 * @param statement resource
 * @param rows int
 * @return bool 
 */
function oci_set_prefetch ($statement, $rows) {}

/**
 * Changes password of Oracle's user
 * @link http://php.net/manual/en/function.oci-password-change.php
 * @param connection resource
 * @param username string
 * @param old_password string
 * @param new_password string
 * @return bool 
 */
function oci_password_change ($connection, $username, $old_password, $new_password) {}

/**
 * @param collection
 */
function oci_free_collection ($collection) {}

/**
 * @param collection
 * @param value
 */
function oci_collection_append ($collection, $value) {}

/**
 * @param collection
 * @param index
 */
function oci_collection_element_get ($collection, $index) {}

/**
 * @param collection
 * @param index
 * @param value
 */
function oci_collection_element_assign ($collection, $index, $value) {}

/**
 * @param collection_to
 * @param collection_from
 */
function oci_collection_assign ($collection_to, $collection_from) {}

/**
 * @param collection
 */
function oci_collection_size ($collection) {}

/**
 * @param collection
 */
function oci_collection_max ($collection) {}

/**
 * @param collection
 * @param number
 */
function oci_collection_trim ($collection, $number) {}

/**
 * Allocates new collection object
 * @link http://php.net/manual/en/function.oci-new-collection.php
 * @param connection resource
 * @param tdo string
 * @param schema string[optional]
 * @return OCI-Collection a new OCICollection object or false on
 */
function oci_new_collection ($connection, $tdo, $schema = null) {}

/**
 * @param statement_resource
 */
function oci_free_cursor ($statement_resource) {}

/**
 * &Alias; <function>oci_free_statement</function>
 * @link http://php.net/manual/en/function.ocifreecursor.php
 * @param statement_resource
 */
function ocifreecursor ($statement_resource) {}

/**
 * &Alias; <function>oci_bind_by_name</function>
 * @link http://php.net/manual/en/function.ocibindbyname.php
 * @param statement_resource
 * @param column_name
 * @param variable
 * @param maximum_length[optional]
 * @param type[optional]
 */
function ocibindbyname ($statement_resource, $column_name, &$variable, $maximum_length, $type) {}

/**
 * &Alias; <function>oci_define_by_name</function>
 * @link http://php.net/manual/en/function.ocidefinebyname.php
 * @param statement_resource
 * @param column_name
 * @param variable
 * @param type[optional]
 */
function ocidefinebyname ($statement_resource, $column_name, &$variable, $type) {}

/**
 * &Alias; <function>oci_field_is_null</function>
 * @link http://php.net/manual/en/function.ocicolumnisnull.php
 * @param statement_resource
 * @param column_number_or_name
 */
function ocicolumnisnull ($statement_resource, $column_number_or_name) {}

/**
 * &Alias; <function>oci_field_name</function>
 * @link http://php.net/manual/en/function.ocicolumnname.php
 * @param statement_resource
 * @param column_number
 */
function ocicolumnname ($statement_resource, $column_number) {}

/**
 * &Alias; <function>oci_field_size</function>
 * @link http://php.net/manual/en/function.ocicolumnsize.php
 * @param statement_resource
 * @param column_number_or_name
 */
function ocicolumnsize ($statement_resource, $column_number_or_name) {}

/**
 * &Alias; <function>oci_field_scale</function>
 * @link http://php.net/manual/en/function.ocicolumnscale.php
 * @param statement_resource
 * @param column_number
 */
function ocicolumnscale ($statement_resource, $column_number) {}

/**
 * &Alias; <function>oci_field_precision</function>
 * @link http://php.net/manual/en/function.ocicolumnprecision.php
 * @param statement_resource
 * @param column_number
 */
function ocicolumnprecision ($statement_resource, $column_number) {}

/**
 * &Alias; <function>oci_field_type</function>
 * @link http://php.net/manual/en/function.ocicolumntype.php
 * @param statement_resource
 * @param column_number
 */
function ocicolumntype ($statement_resource, $column_number) {}

/**
 * &Alias; <function>oci_field_type_raw</function>
 * @link http://php.net/manual/en/function.ocicolumntyperaw.php
 * @param statement_resource
 * @param column_number
 */
function ocicolumntyperaw ($statement_resource, $column_number) {}

/**
 * &Alias; <function>oci_execute</function>
 * @link http://php.net/manual/en/function.ociexecute.php
 * @param statement_resource
 * @param mode[optional]
 */
function ociexecute ($statement_resource, $mode) {}

/**
 * &Alias; <function>oci_cancel</function>
 * @link http://php.net/manual/en/function.ocicancel.php
 * @param statement_resource
 */
function ocicancel ($statement_resource) {}

/**
 * &Alias; <function>oci_fetch</function>
 * @link http://php.net/manual/en/function.ocifetch.php
 * @param statement_resource
 */
function ocifetch ($statement_resource) {}

/**
 * &Alias; <function>oci_fetch_all</function>
 * @link http://php.net/manual/en/function.ocifetchstatement.php
 * @param statement_resource
 * @param output
 * @param skip[optional]
 * @param maximum_rows[optional]
 * @param flags[optional]
 */
function ocifetchstatement ($statement_resource, &$output, $skip, $maximum_rows, $flags) {}

/**
 * &Alias; <function>oci_free_statement</function>
 * @link http://php.net/manual/en/function.ocifreestatement.php
 * @param statement_resource
 */
function ocifreestatement ($statement_resource) {}

/**
 * &Alias; <function>oci_internal_debug</function>
 * @link http://php.net/manual/en/function.ociinternaldebug.php
 * @param mode
 */
function ociinternaldebug ($mode) {}

/**
 * &Alias; <function>oci_num_fields</function>
 * @link http://php.net/manual/en/function.ocinumcols.php
 * @param statement_resource
 */
function ocinumcols ($statement_resource) {}

/**
 * &Alias; <function>oci_parse</function>
 * @link http://php.net/manual/en/function.ociparse.php
 * @param connection_resource
 * @param sql_text
 */
function ociparse ($connection_resource, $sql_text) {}

/**
 * &Alias; <function>oci_new_cursor</function>
 * @link http://php.net/manual/en/function.ocinewcursor.php
 * @param connection_resource
 */
function ocinewcursor ($connection_resource) {}

/**
 * &Alias; <function>oci_result</function>
 * @link http://php.net/manual/en/function.ociresult.php
 * @param statement_resource
 * @param column_number_or_name
 */
function ociresult ($statement_resource, $column_number_or_name) {}

/**
 * &Alias; <function>oci_server_version</function>
 * @link http://php.net/manual/en/function.ociserverversion.php
 * @param connection_resource
 */
function ociserverversion ($connection_resource) {}

/**
 * &Alias; <function>oci_statement_type</function>
 * @link http://php.net/manual/en/function.ocistatementtype.php
 * @param statement_resource
 */
function ocistatementtype ($statement_resource) {}

/**
 * &Alias; <function>oci_num_rows</function>
 * @link http://php.net/manual/en/function.ocirowcount.php
 * @param statement_resource
 */
function ocirowcount ($statement_resource) {}

/**
 * &Alias; <function>oci_close</function>
 * @link http://php.net/manual/en/function.ocilogoff.php
 * @param connection_resource
 */
function ocilogoff ($connection_resource) {}

/**
 * &Alias; <function>oci_connect</function>
 * @link http://php.net/manual/en/function.ocilogon.php
 * @param username
 * @param password
 * @param connection_string[optional]
 * @param character_set[optional]
 * @param session_mode[optional]
 */
function ocilogon ($username, $password, $connection_string, $character_set, $session_mode) {}

/**
 * &Alias; <function>oci_new_connect</function>
 * @link http://php.net/manual/en/function.ocinlogon.php
 * @param username
 * @param password
 * @param connection_string[optional]
 * @param character_set[optional]
 * @param session_mode[optional]
 */
function ocinlogon ($username, $password, $connection_string, $character_set, $session_mode) {}

/**
 * &Alias; <function>oci_pconnect</function>
 * @link http://php.net/manual/en/function.ociplogon.php
 * @param username
 * @param password
 * @param connection_string[optional]
 * @param character_set[optional]
 * @param session_mode[optional]
 */
function ociplogon ($username, $password, $connection_string, $character_set, $session_mode) {}

/**
 * &Alias; <function>oci_error</function>
 * @link http://php.net/manual/en/function.ocierror.php
 * @param connection_or_statement_resource[optional]
 */
function ocierror ($connection_or_statement_resource) {}

/**
 * &Alias; <xref linkend="function.oci-lob-free" />
 * @link http://php.net/manual/en/function.ocifreedesc.php
 * @param lob_descriptor
 */
function ocifreedesc ($lob_descriptor) {}

/**
 * &Alias; <xref linkend="function.oci-lob-save" />
 * @link http://php.net/manual/en/function.ocisavelob.php
 * @param lob_descriptor
 * @param data
 * @param offset[optional]
 */
function ocisavelob ($lob_descriptor, $data, $offset) {}

/**
 * &Alias; <xref linkend="function.oci-lob-import" />
 * @link http://php.net/manual/en/function.ocisavelobfile.php
 * @param lob_descriptor
 * @param filename
 */
function ocisavelobfile ($lob_descriptor, $filename) {}

/**
 * &Alias; <xref linkend="function.oci-lob-export" />
 * @link http://php.net/manual/en/function.ociwritelobtofile.php
 * @param lob_descriptor
 * @param filename
 * @param start[optional]
 * @param length[optional]
 */
function ociwritelobtofile ($lob_descriptor, $filename, $start, $length) {}

/**
 * &Alias; <xref linkend="function.oci-lob-load" />
 * @link http://php.net/manual/en/function.ociloadlob.php
 * @param lob_descriptor
 */
function ociloadlob ($lob_descriptor) {}

/**
 * &Alias; <function>oci_commit</function>
 * @link http://php.net/manual/en/function.ocicommit.php
 * @param connection_resource
 */
function ocicommit ($connection_resource) {}

/**
 * &Alias; <function>oci_rollback</function>
 * @link http://php.net/manual/en/function.ocirollback.php
 * @param connection_resource
 */
function ocirollback ($connection_resource) {}

/**
 * &Alias; <function>oci_new_descriptor</function>
 * @link http://php.net/manual/en/function.ocinewdescriptor.php
 * @param connection_resource
 * @param type[optional]
 */
function ocinewdescriptor ($connection_resource, $type) {}

/**
 * &Alias; <function>oci_set_prefetch</function>
 * @link http://php.net/manual/en/function.ocisetprefetch.php
 * @param statement_resource
 * @param number_of_rows
 */
function ocisetprefetch ($statement_resource, $number_of_rows) {}

/**
 * @param connection_resource_or_connection_string
 * @param username
 * @param old_password
 * @param new_password
 */
function ocipasswordchange ($connection_resource_or_connection_string, $username, $old_password, $new_password) {}

/**
 * &Alias; <xref linkend="function.oci-collection-free" />
 * @link http://php.net/manual/en/function.ocifreecollection.php
 * @param collection
 */
function ocifreecollection ($collection) {}

/**
 * &Alias; <function>oci_new_collection</function>
 * @link http://php.net/manual/en/function.ocinewcollection.php
 * @param connection_resource
 * @param type_name
 * @param schema_name[optional]
 */
function ocinewcollection ($connection_resource, $type_name, $schema_name) {}

/**
 * &Alias; <xref linkend="function.oci-collection-append" />
 * @link http://php.net/manual/en/function.ocicollappend.php
 * @param collection
 * @param value
 */
function ocicollappend ($collection, $value) {}

/**
 * &Alias; <xref linkend="function.oci-collection-element-get" />
 * @link http://php.net/manual/en/function.ocicollgetelem.php
 * @param collection
 * @param index
 */
function ocicollgetelem ($collection, $index) {}

/**
 * &Alias; <xref linkend="function.oci-collection-element-assign" />
 * @link http://php.net/manual/en/function.ocicollassignelem.php
 * @param collection
 * @param index
 * @param value
 */
function ocicollassignelem ($collection, $index, $value) {}

/**
 * &Alias; <xref linkend="function.oci-collection-size" />
 * @link http://php.net/manual/en/function.ocicollsize.php
 * @param collection
 */
function ocicollsize ($collection) {}

/**
 * &Alias; <xref linkend="function.oci-collection-max" />
 * @link http://php.net/manual/en/function.ocicollmax.php
 * @param collection
 */
function ocicollmax ($collection) {}

/**
 * &Alias; <xref linkend="function.oci-collection-trim" />
 * @link http://php.net/manual/en/function.ocicolltrim.php
 * @param collection
 * @param number
 */
function ocicolltrim ($collection, $number) {}


/**
 * Statement execution mode. Statement is not committed
 * automatically when using this mode.
 * @link http://php.net/manual/en/oci8.constants.php
 */
define ('OCI_DEFAULT', 0);

/**
 * Used with oci_connect to connect as SYSOPER
 * using external credentials (oci8.privileged_connect
 * should be enabled for this).
 * @link http://php.net/manual/en/oci8.constants.php
 */
define ('OCI_SYSOPER', 4);

/**
 * Used with oci_connect to connect as SYSDBA
 * using external credentials (oci8.privileged_connect
 * should be enabled for this).
 * @link http://php.net/manual/en/oci8.constants.php
 */
define ('OCI_SYSDBA', 2);
define ('OCI_CRED_EXT', -2147483648);

/**
 * Statement execution mode. Use this mode if you don't want 
 * to execute the query, but get the select-list's description.
 * @link http://php.net/manual/en/oci8.constants.php
 */
define ('OCI_DESCRIBE_ONLY', 16);

/**
 * Statement execution mode. Statement is automatically committed after
 * oci_execute call.
 * @link http://php.net/manual/en/oci8.constants.php
 */
define ('OCI_COMMIT_ON_SUCCESS', 32);

/**
 * Statement fetch mode. Used when the application knows 
 * in advance exactly how many rows it will be fetching. 
 * This mode turns prefetching off for Oracle release 8 
 * or later mode. Cursor is cancelled after the desired 
 * rows are fetched and may result in reduced server-side 
 * resource usage.
 * @link http://php.net/manual/en/oci8.constants.php
 */
define ('OCI_EXACT_FETCH', 2);
define ('OCI_SEEK_SET', 0);
define ('OCI_SEEK_CUR', 1);
define ('OCI_SEEK_END', 2);

/**
 * Used with to free buffers used.
 * @link http://php.net/manual/en/oci8.constants.php
 */
define ('OCI_LOB_BUFFER_FREE', 1);

/**
 * The same as OCI_B_BFILE.
 * @link http://php.net/manual/en/oci8.constants.php
 */
define ('SQLT_BFILEE', 114);

/**
 * The same as OCI_B_CFILEE.
 * @link http://php.net/manual/en/oci8.constants.php
 */
define ('SQLT_CFILEE', 115);

/**
 * The same as OCI_B_CLOB.
 * @link http://php.net/manual/en/oci8.constants.php
 */
define ('SQLT_CLOB', 112);

/**
 * The same as OCI_B_BLOB.
 * @link http://php.net/manual/en/oci8.constants.php
 */
define ('SQLT_BLOB', 113);

/**
 * The same as OCI_B_ROWID.
 * @link http://php.net/manual/en/oci8.constants.php
 */
define ('SQLT_RDD', 104);

/**
 * Used with oci_bind_array_by_name to bind arrays of
 * INTEGER.
 * @link http://php.net/manual/en/oci8.constants.php
 */
define ('SQLT_INT', 3);

/**
 * Used with oci_bind_array_by_name to bind arrays of
 * NUMBER.
 * @link http://php.net/manual/en/oci8.constants.php
 */
define ('SQLT_NUM', 2);
define ('SQLT_RSET', 116);

/**
 * Used with oci_bind_array_by_name to bind arrays of
 * CHAR.
 * @link http://php.net/manual/en/oci8.constants.php
 */
define ('SQLT_AFC', 96);

/**
 * Used with oci_bind_array_by_name to bind arrays of
 * VARCHAR2.
 * Also used with oci_bind_by_name.
 * @link http://php.net/manual/en/oci8.constants.php
 */
define ('SQLT_CHR', 1);

/**
 * Used with oci_bind_array_by_name to bind arrays of
 * VARCHAR.
 * @link http://php.net/manual/en/oci8.constants.php
 */
define ('SQLT_VCS', 9);

/**
 * Used with oci_bind_array_by_name to bind arrays of
 * CHARZ.
 * @link http://php.net/manual/en/oci8.constants.php
 */
define ('SQLT_AVC', 97);

/**
 * Used with oci_bind_array_by_name to bind arrays of
 * STRING.
 * @link http://php.net/manual/en/oci8.constants.php
 */
define ('SQLT_STR', 5);

/**
 * Used with oci_bind_array_by_name to bind arrays of
 * LONG VARCHAR.
 * @link http://php.net/manual/en/oci8.constants.php
 */
define ('SQLT_LVC', 94);

/**
 * Used with oci_bind_array_by_name to bind arrays of
 * FLOAT.
 * @link http://php.net/manual/en/oci8.constants.php
 */
define ('SQLT_FLT', 4);
define ('SQLT_UIN', 68);

/**
 * Used with oci_bind_by_name to bind LONG values.
 * @link http://php.net/manual/en/oci8.constants.php
 */
define ('SQLT_LNG', 8);

/**
 * Used with oci_bind_by_name to bind LONG RAW values.
 * @link http://php.net/manual/en/oci8.constants.php
 */
define ('SQLT_LBI', 24);

/**
 * Used with oci_bind_by_name to bind RAW values.
 * @link http://php.net/manual/en/oci8.constants.php
 */
define ('SQLT_BIN', 23);

/**
 * Used with oci_bind_array_by_name to bind arrays of
 * LONG.
 * @link http://php.net/manual/en/oci8.constants.php
 */
define ('SQLT_ODT', 156);
define ('SQLT_BDOUBLE', 22);
define ('SQLT_BFLOAT', 21);

/**
 * Used with oci_bind_by_name when 
 * binding named data types. Note: in PHP &lt; 5.0 it was called
 * OCI_B_SQLT_NTY.
 * @link http://php.net/manual/en/oci8.constants.php
 */
define ('OCI_B_NTY', 108);

/**
 * The same as OCI_B_NTY.
 * @link http://php.net/manual/en/oci8.constants.php
 */
define ('SQLT_NTY', 108);
define ('OCI_SYSDATE', "SYSDATE");

/**
 * Used with oci_bind_by_name when 
 * binding BFILEs.
 * @link http://php.net/manual/en/oci8.constants.php
 */
define ('OCI_B_BFILE', 114);

/**
 * Used with oci_bind_by_name when 
 * binding CFILEs.
 * @link http://php.net/manual/en/oci8.constants.php
 */
define ('OCI_B_CFILEE', 115);

/**
 * Used with oci_bind_by_name when 
 * binding CLOBs.
 * @link http://php.net/manual/en/oci8.constants.php
 */
define ('OCI_B_CLOB', 112);

/**
 * Used with oci_bind_by_name when 
 * binding BLOBs.
 * @link http://php.net/manual/en/oci8.constants.php
 */
define ('OCI_B_BLOB', 113);

/**
 * Used with oci_bind_by_name when 
 * binding ROWIDs.
 * @link http://php.net/manual/en/oci8.constants.php
 */
define ('OCI_B_ROWID', 104);

/**
 * Used with oci_bind_by_name when 
 * binding cursors, previously allocated with oci_new_descriptor.
 * @link http://php.net/manual/en/oci8.constants.php
 */
define ('OCI_B_CURSOR', 116);
define ('OCI_B_BIN', 23);
define ('OCI_B_INT', 3);
define ('OCI_B_NUM', 2);

/**
 * Default mode of oci_fetch_all.
 * @link http://php.net/manual/en/oci8.constants.php
 */
define ('OCI_FETCHSTATEMENT_BY_COLUMN', 16);

/**
 * Alternative mode of oci_fetch_all.
 * @link http://php.net/manual/en/oci8.constants.php
 */
define ('OCI_FETCHSTATEMENT_BY_ROW', 32);

/**
 * Used with oci_fetch_all and
 * oci_fetch_array to get an associative 
 * array as a result.
 * @link http://php.net/manual/en/oci8.constants.php
 */
define ('OCI_ASSOC', 1);

/**
 * Used with oci_fetch_all and 
 * oci_fetch_array to get an enumerated 
 * array as a result.
 * @link http://php.net/manual/en/oci8.constants.php
 */
define ('OCI_NUM', 2);

/**
 * Used with oci_fetch_all and 
 * oci_fetch_array to get an array with
 * both associative and number indices.
 * @link http://php.net/manual/en/oci8.constants.php
 */
define ('OCI_BOTH', 3);

/**
 * Used with oci_fetch_array to get
 * empty array elements if field's value is &null;.
 * @link http://php.net/manual/en/oci8.constants.php
 */
define ('OCI_RETURN_NULLS', 4);

/**
 * Used with oci_fetch_array to get
 * value of LOB instead of the descriptor.
 * @link http://php.net/manual/en/oci8.constants.php
 */
define ('OCI_RETURN_LOBS', 8);

/**
 * This flag tells oci_new_descriptor to
 * initialize new FILE descriptor.
 * @link http://php.net/manual/en/oci8.constants.php
 */
define ('OCI_DTYPE_FILE', 56);

/**
 * This flag tells oci_new_descriptor to
 * initialize new LOB descriptor.
 * @link http://php.net/manual/en/oci8.constants.php
 */
define ('OCI_DTYPE_LOB', 50);

/**
 * This flag tells oci_new_descriptor to
 * initialize new ROWID descriptor.
 * @link http://php.net/manual/en/oci8.constants.php
 */
define ('OCI_DTYPE_ROWID', 54);

/**
 * The same as OCI_DTYPE_FILE.
 * @link http://php.net/manual/en/oci8.constants.php
 */
define ('OCI_D_FILE', 56);

/**
 * The same as OCI_DTYPE_LOB.
 * @link http://php.net/manual/en/oci8.constants.php
 */
define ('OCI_D_LOB', 50);

/**
 * The same as OCI_DTYPE_ROWID.
 * @link http://php.net/manual/en/oci8.constants.php
 */
define ('OCI_D_ROWID', 54);

/**
 * Used with to indicate
 * explicilty that temporary CLOB should be created.
 * @link http://php.net/manual/en/oci8.constants.php
 */
define ('OCI_TEMP_CLOB', 2);

/**
 * Used with to indicate
 * explicilty that temporary BLOB should be created.
 * @link http://php.net/manual/en/oci8.constants.php
 */
define ('OCI_TEMP_BLOB', 1);

// End of oci8 v.1.3.4
?>
