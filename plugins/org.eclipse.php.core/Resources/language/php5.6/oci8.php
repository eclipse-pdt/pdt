<?php

// Start of oci8 v.1.4.7

class OCI_Lob  {

	/**
	 * Returns large object's contents
	 * @link http://www.php.net/manual/en/oci-lob.load.php
	 * @return string the contents of the object, or false on errors.
	 */
	public function load () {}

	/**
	 * Returns the current position of internal pointer of large object
	 * @link http://www.php.net/manual/en/oci-lob.tell.php
	 * @return int current position of a LOB's internal pointer or false if an
	 * error occurred.
	 */
	public function tell () {}

	/**
	 * Truncates large object
	 * @link http://www.php.net/manual/en/oci-lob.truncate.php
	 * @param length int[optional] <p>
	 * If provided, this method will truncate the LOB to
	 * length bytes. Otherwise, it will completely
	 * purge the LOB.
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 */
	public function truncate ($length = null) {}

	/**
	 * Erases a specified portion of the internal LOB data
	 * @link http://www.php.net/manual/en/oci-lob.erase.php
	 * @param offset int[optional] <p>
	 * </p>
	 * @param length int[optional] <p>
	 * </p>
	 * @return int the actual number of characters/bytes erased  or false on failure.
	 */
	public function erase ($offset = null, $length = null) {}

	/**
	 * Flushes/writes buffer of the LOB to the server
	 * @link http://www.php.net/manual/en/oci-lob.flush.php
	 * @param flag int[optional] <p>
	 * By default, resources are not freed, but using flag 
	 * OCI_LOB_BUFFER_FREE you can do it explicitly.
	 * Be sure you know what you're doing - next read/write operation to the
	 * same part of LOB will involve a round-trip to the server and initialize
	 * new buffer resources. It is recommended to use 
	 * OCI_LOB_BUFFER_FREE flag only when you are not
	 * going to work with the LOB anymore.
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 * </p>
	 * <p>
	 * Returns false if buffering was not enabled or an error occurred.
	 */
	public function flush ($flag = null) {}

	/**
	 * Changes current state of buffering for the large object
	 * @link http://www.php.net/manual/en/oci-lob.setbuffering.php
	 * @param on_off bool <p>
	 * true for on and false for off.
	 * </p>
	 * @return bool Returns true on success or false on failure. Repeated calls to this method with the same flag will
	 * return true.
	 */
	public function setbuffering ($on_off) {}

	/**
	 * Returns current state of buffering for the large object
	 * @link http://www.php.net/manual/en/oci-lob.getbuffering.php
	 * @return bool false if buffering for the large object is off and true if
	 * buffering is used.
	 */
	public function getbuffering () {}

	/**
	 * Moves the internal pointer to the beginning of the large object
	 * @link http://www.php.net/manual/en/oci-lob.rewind.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function rewind () {}

	/**
	 * Reads part of the large object
	 * @link http://www.php.net/manual/en/oci-lob.read.php
	 * @param length int <p>
	 * The length of data to read, in bytes.
	 * </p>
	 * @return string the contents as a string,  or false on failure.
	 */
	public function read ($length) {}

	/**
	 * Tests for end-of-file on a large object's descriptor
	 * @link http://www.php.net/manual/en/oci-lob.eof.php
	 * @return bool true if internal pointer of large object is at the end of LOB.
	 * Otherwise returns false.
	 */
	public function eof () {}

	/**
	 * Sets the internal pointer of the large object
	 * @link http://www.php.net/manual/en/oci-lob.seek.php
	 * @param offset int <p>
	 * Indicates the amount of bytes, on which internal pointer should be
	 * moved from the position, pointed by whence.
	 * </p>
	 * @param whence int[optional] <p>
	 * May be one of:
	 * OCI_SEEK_SET - sets the position equal to 
	 * offset
	 * OCI_SEEK_CUR - adds offset 
	 * bytes to the current position 
	 * OCI_SEEK_END - adds offset
	 * bytes to the end of large object (use negative value to move to a position
	 * before the end of large object)
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 */
	public function seek ($offset, $whence = null) {}

	/**
	 * Writes data to the large object
	 * @link http://www.php.net/manual/en/oci-lob.write.php
	 * @param data string <p>
	 * The data to write in the LOB.
	 * </p>
	 * @param length int[optional] <p>
	 * If this parameter is given, writing will stop after 
	 * length bytes have been written or the end of
	 * data is reached, whichever comes first.
	 * </p>
	 * @return int the number of bytes written  or false on failure.
	 */
	public function write ($data, $length = null) {}

	/**
	 * Appends data from the large object to another large object
	 * @link http://www.php.net/manual/en/oci-lob.append.php
	 * @param lob_from OCI-Lob <p>
	 * The copied LOB.
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 */
	public function append (OCI-Lob $lob_from) {}

	/**
	 * Returns size of large object
	 * @link http://www.php.net/manual/en/oci-lob.size.php
	 * @return int length of large object value  or false on failure.
	 * Empty objects have zero length.
	 */
	public function size () {}

	/**
	 * &Alias; <function>OCI-Lob::export</function>
	 * @link http://www.php.net/manual/en/oci-lob.writetofile.php
	 * @param filename
	 * @param start[optional]
	 * @param length[optional]
	 */
	public function writetofile ($filename, $start, $length) {}

	/**
	 * Exports LOB's contents to a file
	 * @link http://www.php.net/manual/en/oci-lob.export.php
	 * @param filename string <p>
	 * Path to the file.
	 * </p>
	 * @param start int[optional] <p>
	 * Indicates from where to start exporting.
	 * </p>
	 * @param length int[optional] <p>
	 * Indicates the length of data to be exported. 
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 */
	public function export ($filename, $start = null, $length = null) {}

	/**
	 * Imports file data to the LOB
	 * @link http://www.php.net/manual/en/oci-lob.import.php
	 * @param filename string <p>
	 * Path to the file.
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 */
	public function import ($filename) {}

	/**
	 * Writes a temporary large object
	 * @link http://www.php.net/manual/en/oci-lob.writetemporary.php
	 * @param data string <p>
	 * The data to write.
	 * </p>
	 * @param lob_type int[optional] <p>
	 * Can be one of the following:
	 * OCI_TEMP_BLOB is used to create temporary BLOBs 
	 * OCI_TEMP_CLOB is used to create
	 * temporary CLOBs
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 */
	public function writetemporary ($data, $lob_type = null) {}

	/**
	 * Closes LOB descriptor
	 * @link http://www.php.net/manual/en/oci-lob.close.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function close () {}

	/**
	 * Saves data to the large object
	 * @link http://www.php.net/manual/en/oci-lob.save.php
	 * @param data string <p>
	 * The data to be saved.
	 * </p>
	 * @param offset int[optional] <p>
	 * Can be used to indicate offset from the beginning of the large object.
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 */
	public function save ($data, $offset = null) {}

	/**
	 * &Alias; <function>OCI-Lob::import</function>
	 * @link http://www.php.net/manual/en/oci-lob.savefile.php
	 * @param filename
	 */
	public function savefile ($filename) {}

	/**
	 * Frees resources associated with the LOB descriptor
	 * @link http://www.php.net/manual/en/oci-lob.free.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function free () {}

}

class OCI_Collection  {

	/**
	 * Appends element to the collection
	 * @link http://www.php.net/manual/en/oci-collection.append.php
	 * @param value mixed <p>
	 * The value to be added to the collection. Can be a string or a number.
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 */
	public function append ($value) {}

	/**
	 * Returns value of the element
	 * @link http://www.php.net/manual/en/oci-collection.getelem.php
	 * @param index int <p>
	 * The element index. First index is 1.
	 * </p>
	 * @return mixed false if such element doesn't exist; &null; if element is &null;;
	 * string if element is column of a string datatype or number if element is
	 * numeric field.
	 */
	public function getelem ($index) {}

	/**
	 * Assigns a value to the element of the collection
	 * @link http://www.php.net/manual/en/oci-collection.assignelem.php
	 * @param index int <p>
	 * The element index. First index is 1.
	 * </p>
	 * @param value mixed <p>
	 * Can be a string or a number.
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 */
	public function assignelem ($index, $value) {}

	/**
	 * Assigns a value to the collection from another existing collection
	 * @link http://www.php.net/manual/en/oci-collection.assign.php
	 * @param from OCI-Collection <p>
	 * An instance of OCI-Collection.
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 */
	public function assign (OCI-Collection $from) {}

	/**
	 * Returns size of the collection
	 * @link http://www.php.net/manual/en/oci-collection.size.php
	 * @return int the number of elements in the collection or false on error.
	 */
	public function size () {}

	/**
	 * Returns the maximum number of elements in the collection
	 * @link http://www.php.net/manual/en/oci-collection.max.php
	 * @return int the maximum number as an integer, or false on errors.
	 * </p>
	 * <p>
	 * If the returned value is 0, then the number of elements is not limited.
	 */
	public function max () {}

	/**
	 * Trims elements from the end of the collection
	 * @link http://www.php.net/manual/en/oci-collection.trim.php
	 * @param num int <p>
	 * The number of elements to be trimmed.
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 */
	public function trim ($num) {}

	/**
	 * Frees the resources associated with the collection object
	 * @link http://www.php.net/manual/en/oci-collection.free.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function free () {}

}

/**
 * Associates a PHP variable with a column for query fetches
 * @link http://www.php.net/manual/en/function.oci-define-by-name.php
 * @param statement resource &oci.arg.statement.id;
 * @param column_name string <p>
 * The column name used in the query.
 * </p>
 * <p>
 * Use uppercase for Oracle's default, non-case sensitive column
 * names. Use the exact column name case for case-sensitive
 * column names.
 * </p>
 * @param variable mixed <p>
 * The PHP variable that will contain the returned column value.
 * </p>
 * @param type int[optional] <p>
 * The data type to be returned. Generally not needed. Note that
 * Oracle-style data conversions are not performed. For example,
 * SQLT_INT will be ignored and the returned
 * data type will still be SQLT_CHR.
 * </p>
 * <p>
 * You can optionally use oci_new_descriptor
 * to allocate LOB/ROWID/BFILE descriptors.
 * </p>
 * @return bool Returns true on success or false on failure.
 */
function oci_define_by_name ($statement, $column_name, &$variable, $type = null) {}

/**
 * Binds a PHP variable to an Oracle placeholder
 * @link http://www.php.net/manual/en/function.oci-bind-by-name.php
 * @param statement resource <p>
 * A valid OCI8 statement identifer.
 * </p>
 * @param bv_name string <p>
 * The colon-prefixed bind variable placeholder used in the
 * statement. The colon is optional
 * in bv_name. Oracle does not use question
 * marks for placeholders.
 * </p>
 * @param variable mixed <p>
 * The PHP variable to be associated with bv_name
 * </p>
 * @param maxlength int[optional] <p>
 * Sets the maximum length for the data. If you set it to -1, this
 * function will use the current length
 * of variable to set the maximum
 * length. In this case the variable must
 * exist and contain data
 * when oci_bind_by_name is called.
 * </p>
 * @param type int[optional] <p>
 * The datatype that Oracle will treat the data as. The
 * default type used
 * is SQLT_CHR. Oracle will convert the data
 * between this type and the database column (or PL/SQL variable
 * type), when possible.
 * </p>
 * <p>
 * If you need to bind an abstract datatype (LOB/ROWID/BFILE) you
 * need to allocate it first using the
 * oci_new_descriptor function. The
 * length is not used for abstract datatypes
 * and should be set to -1.
 * </p>
 * <p>
 * Possible values for type are:
 * <p>
 * SQLT_BFILEE or OCI_B_BFILE
 * - for BFILEs;
 * </p>
 * @return bool Returns true on success or false on failure.
 */
function oci_bind_by_name ($statement, $bv_name, &$variable, $maxlength = null, $type = null) {}

/**
 * Binds a PHP array to an Oracle PL/SQL array parameter
 * @link http://www.php.net/manual/en/function.oci-bind-array-by-name.php
 * @param statement resource <p>
 * A valid OCI statement identifier.
 * </p>
 * @param name string <p>
 * The Oracle placeholder.
 * </p>
 * @param var_array array <p>
 * An array.
 * </p>
 * @param max_table_length int <p>
 * Sets the maximum length both for incoming and result arrays.
 * </p>
 * @param max_item_length int[optional] <p>
 * Sets maximum length for array items. If not specified or equals to -1,
 * oci_bind_array_by_name will find the longest
 * element in the incoming array and will use it as the maximum length.
 * </p>
 * @param type int[optional] <p>
 * Should be used to set the type of PL/SQL array items. See list of
 * available types below:
 * </p>
 * <p>
 * <p>
 * SQLT_NUM - for arrays of NUMBER.
 * </p>
 * @return bool Returns true on success or false on failure.
 */
function oci_bind_array_by_name ($statement, $name, array &$var_array, $max_table_length, $max_item_length = null, $type = null) {}

/**
 * Checks if the field is &null;
 * @link http://www.php.net/manual/en/function.oci-field-is-null.php
 * @param statement resource <p>
 * A valid OCI statement identifier.
 * </p>
 * @param field mixed <p>
 * Can be a field's index or a field's name (uppercased).
 * </p>
 * @return bool true if field is &null;, false otherwise.
 */
function oci_field_is_null ($statement, $field) {}

/**
 * Returns the name of a field from the statement
 * @link http://www.php.net/manual/en/function.oci-field-name.php
 * @param statement resource <p>
 * A valid OCI statement identifier.
 * </p>
 * @param field int <p>
 * Can be the field's index (1-based) or name.
 * </p>
 * @return string the name as a string, or false on errors.
 */
function oci_field_name ($statement, $field) {}

/**
 * Returns field's size
 * @link http://www.php.net/manual/en/function.oci-field-size.php
 * @param statement resource <p>
 * A valid OCI statement identifier.
 * </p>
 * @param field mixed <p>
 * Can be the field's index (1-based) or name.
 * </p>
 * @return int the size of a field in bytes, or false on
 * errors.
 */
function oci_field_size ($statement, $field) {}

/**
 * Tell the scale of the field
 * @link http://www.php.net/manual/en/function.oci-field-scale.php
 * @param statement resource <p>
 * A valid OCI statement identifier.
 * </p>
 * @param field int <p>
 * Can be the field's index (1-based) or name.
 * </p>
 * @return int the scale as an integer, or false on errors.
 */
function oci_field_scale ($statement, $field) {}

/**
 * Tell the precision of a field
 * @link http://www.php.net/manual/en/function.oci-field-precision.php
 * @param statement resource <p>
 * A valid OCI statement identifier.
 * </p>
 * @param field int <p>
 * Can be the field's index (1-based) or name.
 * </p>
 * @return int the precision as an integer, or false on errors.
 */
function oci_field_precision ($statement, $field) {}

/**
 * Returns field's data type
 * @link http://www.php.net/manual/en/function.oci-field-type.php
 * @param statement resource <p>
 * A valid OCI statement identifier.
 * </p>
 * @param field int <p>
 * Can be the field's index (1-based) or name.
 * </p>
 * @return mixed the field data type as a string, or false on errors.
 */
function oci_field_type ($statement, $field) {}

/**
 * Tell the raw Oracle data type of the field
 * @link http://www.php.net/manual/en/function.oci-field-type-raw.php
 * @param statement resource <p>
 * A valid OCI statement identifier.
 * </p>
 * @param field int <p>
 * Can be the field's index (1-based) or name.
 * </p>
 * @return int Oracle's raw data type as a string, or false on errors.
 */
function oci_field_type_raw ($statement, $field) {}

/**
 * Executes a statement
 * @link http://www.php.net/manual/en/function.oci-execute.php
 * @param statement resource <p>
 * A valid OCI statement identifier.
 * </p>
 * @param mode int[optional] <p>
 * An optional second parameter can be one of the following constants:
 * <table>
 * Execution Modes
 * <tr valign="top">
 * <td>Constant</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>OCI_COMMIT_ON_SUCCESS</td>
 * <td>Automatically commit all outstanding changes for
 * this connection when the statement has succeeded. This
 * is the default.</td>
 * </tr>
 * <tr valign="top">
 * <td>OCI_DEFAULT</td>
 * <td>Obsolete as of PHP 5.3.2 (PECL OCI8 1.4) but still
 * available for backward compatibility. Use the
 * equivalent OCI_NO_AUTO_COMMIT in new
 * code.</td>
 * </tr>
 * <tr valign="top">
 * <td>OCI_DESCRIBE_ONLY</td>
 * <td>Make query meta data available to functions
 * like oci_field_name but do not
 * create a result set. Any subsequent fetch call such
 * as oci_fetch_array will
 * fail.</td>
 * </tr>
 * <tr valign="top">
 * <td>OCI_NO_AUTO_COMMIT</td>
 * <td>Do not automatically commit changes. Prior to PHP
 * 5.3.2 (PECL OCI8 1.4)
 * use OCI_DEFAULT which is an alias
 * for OCI_NO_AUTO_COMMIT.</td>
 * </tr>
 * </table>
 * </p>
 * <p>
 * Using OCI_NO_AUTO_COMMIT mode starts a
 * transaction. Transactions are automatically rolled back when
 * the connection is closed, or when the script ends. Explicitly
 * call oci_commit to commit a transaction,
 * or oci_rollback to abort it.
 * </p>
 * <p>
 * When inserting or updating data, using transactions is
 * recommended for relational data consistency and for performance
 * reasons.
 * </p>
 * <p>
 * If OCI_NO_AUTO_COMMIT mode is used for any
 * statement including queries, and 
 * oci_commit
 * or oci_rollback is not subsequently
 * called, then OCI8 will perform a rollback at the end of the
 * script even if no data was changed. To avoid an unnecessary
 * rollback, many scripts do not
 * use OCI_NO_AUTO_COMMIT mode for queries or
 * PL/SQL. Be careful to ensure the appropriate transactional
 * consistency for the application when
 * using oci_execute with different modes in
 * the same script.
 * </p>
 * @return bool Returns true on success or false on failure.
 */
function oci_execute ($statement, $mode = null) {}

/**
 * Cancels reading from cursor
 * @link http://www.php.net/manual/en/function.oci-cancel.php
 * @param statement resource <p>
 * An OCI statement.
 * </p>
 * @return bool Returns true on success or false on failure.
 */
function oci_cancel ($statement) {}

/**
 * Fetches the next row from a query into internal buffers
 * @link http://www.php.net/manual/en/function.oci-fetch.php
 * @param statement resource &oci.arg.statement.id;
 * @return bool true on success or false if there are no more rows in the
 * statement.
 */
function oci_fetch ($statement) {}

/**
 * Returns the next row from a query as an object
 * @link http://www.php.net/manual/en/function.oci-fetch-object.php
 * @param statement resource &oci.arg.statement.id;
 * @return object an object. Each attribute of the object corresponds to a
 * column of the row. If there are no more rows in
 * the statement then false is returned.
 * </p>
 * <p>
 * Any LOB columns are returned as LOB descriptors.
 * </p>
 * <p>
 * DATE columns are returned as strings formatted
 * to the current date format. The default format can be changed with
 * Oracle environment variables such as NLS_LANG or
 * by a previously executed ALTER SESSION SET
 * NLS_DATE_FORMAT command.
 * </p>
 * <p>
 * Oracle's default, non-case sensitive column names will have
 * uppercase attribute names. Case-sensitive column names will have
 * attribute names using the exact column case.
 * Use var_dump on the result object to verify
 * the appropriate case to use for each query.
 * </p>
 * <p>
 * Attribute values will be &null; for any NULL
 * data fields.
 */
function oci_fetch_object ($statement) {}

/**
 * Returns the next row from a query as a numeric array
 * @link http://www.php.net/manual/en/function.oci-fetch-row.php
 * @param statement resource &oci.arg.statement.id;
 * @return array a numerically indexed array. If there are no more rows in
 * the statement then false is returned.
 */
function oci_fetch_row ($statement) {}

/**
 * Returns the next row from a query as an associative array
 * @link http://www.php.net/manual/en/function.oci-fetch-assoc.php
 * @param statement resource &oci.arg.statement.id;
 * @return array an associative array. If there are no more rows in
 * the statement then false is returned.
 */
function oci_fetch_assoc ($statement) {}

/**
 * Returns the next row from a query as an associative or numeric array
 * @link http://www.php.net/manual/en/function.oci-fetch-array.php
 * @param statement resource &oci.arg.statement.id;
 * @param mode int[optional] <p>
 * An optional second parameter can be any combination of the following
 * constants:
 * <table>
 * oci_fetch_array Modes
 * <tr valign="top">
 * <td>Constant</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>OCI_BOTH</td>
 * <td>Returns an array with both associative and numeric
 * indices. This is the same
 * as OCI_ASSOC
 * + OCI_NUM and is the default
 * behavior.</td>
 * </tr>
 * <tr valign="top">
 * <td>OCI_ASSOC</td>
 * <td>Returns an associative array.</td>
 * </tr>
 * <tr valign="top">
 * <td>OCI_NUM</td>
 * <td>Returns a numeric array.</td>
 * </tr>
 * <tr valign="top">
 * <td>OCI_RETURN_NULLS</td>
 * <td>Creates elements for &null; fields. The element
 * values will be a PHP &null;.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>OCI_RETURN_LOBS</td>
 * <td>Returns the contents of LOBs instead of the LOB
 * descriptors.</td>
 * </tr>
 * </table>
 * </p>
 * <p>
 * The default mode is OCI_BOTH.
 * </p>
 * <p>
 * Use the addition operator &quot;+&quot; to specify more than
 * one mode at a time.
 * </p>
 * @return array an array with associative and/or numeric indices. If there
 * are no more rows in the statement then
 * false is returned.
 * </p>
 * <p>
 * By default, LOB columns are returned as LOB descriptors.
 * </p>
 * <p>
 * DATE columns are returned as strings formatted
 * to the current date format. The default format can be changed with
 * Oracle environment variables such as NLS_LANG or
 * by a previously executed ALTER SESSION SET
 * NLS_DATE_FORMAT command.
 * </p>
 * <p>
 * Oracle's default, non-case sensitive column names will have
 * uppercase associative indices in the result array. Case-sensitive
 * column names will have array indices using the exact column case.
 * Use var_dump on the result array to verify the
 * appropriate case to use for each query.
 */
function oci_fetch_array ($statement, $mode = null) {}

/**
 * Fetches the next row into an array (deprecated)
 * @link http://www.php.net/manual/en/function.ocifetchinto.php
 * @param statement resource 
 * @param result array 
 * @param mode int[optional] 
 * @return int 
 */
function ocifetchinto ($statement, array &$result, $mode = null) {}

/**
 * Fetches multiple rows from a query into a two-dimensional array
 * @link http://www.php.net/manual/en/function.oci-fetch-all.php
 * @param statement resource &oci.arg.statement.id;
 * @param output array <p>
 * The variable to contain the returned rows.
 * </p>
 * <p>
 * LOB columns are returned as strings, where Oracle supports
 * conversion.
 * </p>
 * <p>
 * See oci_fetch_array for more information
 * on how data and types are fetched.
 * </p>
 * @param skip int[optional] <p>
 * The number of initial rows to discard when fetching the
 * result. The default value is 0, so the first row onwards is
 * returned.
 * </p>
 * @param maxrows int[optional] <p>
 * The number of rows to return. The default is -1 meaning return
 * all the rows from skip + 1 onwards.
 * </p>
 * @param flags int[optional] <p>
 * Parameter flags indicates the array
 * structure and whether associative arrays should be used.
 * <table>
 * oci_fetch_all Array Structure Modes
 * <tr valign="top">
 * <td>Constant</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>OCI_FETCHSTATEMENT_BY_ROW</td>
 * <td>The outer array will contain one sub-array per query
 * row.</td>
 * </tr>
 * <tr valign="top">
 * <td>OCI_FETCHSTATEMENT_BY_COLUMN</td>
 * <td>The outer array will contain one sub-array per query
 * column. This is the default.</td>
 * </tr>
 * </table>
 * </p>
 * <p>
 * Arrays can be indexed by column heading or numerically.
 * <table>
 * oci_fetch_all Array Index Modes
 * <tr valign="top">
 * <td>Constant</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>OCI_NUM</td>
 * <td>Numeric indexes are used for each column's array.</td>
 * </tr>
 * <tr valign="top">
 * <td>OCI_ASSOC</td>
 * <td>Associative indexes are used for each column's
 * array. This is the default.</td>
 * </tr>
 * </table>
 * </p>
 * <p>
 * Use the addition operator &quot;+&quot; to choose a combination
 * of array structure and index modes.
 * </p>
 * <p>
 * Oracle's default, non-case sensitive column names will have
 * uppercase array keys. Case-sensitive column names will have
 * array keys using the exact column case.
 * Use var_dump
 * on output to verify the appropriate case
 * to use for each query.
 * </p>
 * <p>
 * Queries that have more than one column with the same name
 * should use column aliases. Otherwise only one of the columns
 * will appear in an associative array.
 * </p>
 * @return int the number of rows in output, which
 * may be 0 or more,  or false on failure.
 */
function oci_fetch_all ($statement, array &$output, $skip = null, $maxrows = null, $flags = null) {}

/**
 * Frees all resources associated with statement or cursor
 * @link http://www.php.net/manual/en/function.oci-free-statement.php
 * @param statement resource <p>
 * A valid OCI statement identifier.
 * </p>
 * @return bool Returns true on success or false on failure.
 */
function oci_free_statement ($statement) {}

/**
 * Enables or disables internal debug output
 * @link http://www.php.net/manual/en/function.oci-internal-debug.php
 * @param onoff bool <p>
 * Set this to false to turn debug output off or true to turn it on.
 * </p>
 * @return void 
 */
function oci_internal_debug ($onoff) {}

/**
 * Returns the number of result columns in a statement
 * @link http://www.php.net/manual/en/function.oci-num-fields.php
 * @param statement resource <p>
 * A valid OCI statement identifier.
 * </p>
 * @return int the number of columns as an integer, or false on errors.
 */
function oci_num_fields ($statement) {}

/**
 * Prepares an Oracle statement for execution
 * @link http://www.php.net/manual/en/function.oci-parse.php
 * @param connection resource <p>
 * An Oracle connection identifier, returned by 
 * oci_connect, oci_pconnect, or oci_new_connect.
 * </p>
 * @param sql_text string <p>
 * The SQL or PL/SQL statement.
 * </p>
 * <p>
 * SQL statements should not end with a
 * semi-colon (&quot;;&quot;). PL/SQL
 * statements should end with a semi-colon
 * (&quot;;&quot;).
 * </p>
 * @return resource a statement handle on success, or false on error.
 */
function oci_parse ($connection, $sql_text) {}

/**
 * Allocates and returns a new cursor (statement handle)
 * @link http://www.php.net/manual/en/function.oci-new-cursor.php
 * @param connection resource <p>
 * An Oracle connection identifier, returned by 
 * oci_connect or oci_pconnect.
 * </p>
 * @return resource a new statement handle, or false on error.
 */
function oci_new_cursor ($connection) {}

/**
 * Returns field's value from the fetched row
 * @link http://www.php.net/manual/en/function.oci-result.php
 * @param statement resource <p>
 * </p>
 * @param field mixed <p>
 * Can be either use the column number (1-based) or the column name (in
 * uppercase).
 * </p>
 * @return mixed everything as strings except for abstract types (ROWIDs, LOBs and
 * FILEs). Returns false on error.
 */
function oci_result ($statement, $field) {}

/**
 * Returns the Oracle client library version
 * @link http://www.php.net/manual/en/function.oci-client-version.php
 * @return string the version number as a string.
 */
function oci_client_version () {}

/**
 * Returns the Oracle Database version
 * @link http://www.php.net/manual/en/function.oci-server-version.php
 * @param connection resource <p>
 * </p>
 * @return string the version information as a string or false on error.
 */
function oci_server_version ($connection) {}

/**
 * Returns the type of a statement
 * @link http://www.php.net/manual/en/function.oci-statement-type.php
 * @param statement resource <p>
 * A valid OCI8 statement identifier from oci_parse.
 * </p>
 * @return string the type of statement as one of the
 * following strings.
 * <table>
 * Statement type
 * <tr valign="top">
 * <td>Return String</td>
 * <td>Notes</td>
 * </tr>
 * <tr valign="top">
 * <td>ALTER</td>
 * <td></td>
 * </tr>
 * <tr valign="top">
 * <td>BEGIN</td>
 * <td></td>
 * </tr>
 * <tr valign="top">
 * <td>CALL</td>
 * <td>Introduced in PHP 5.2.1 (PECL OCI8 1.2.3)</td>
 * </tr>
 * <tr valign="top">
 * <td>CREATE</td>
 * <td></td>
 * </tr>
 * <tr valign="top">
 * <td>DECLARE</td>
 * <td></td>
 * </tr>
 * <tr valign="top">
 * <td>DELETE</td>
 * <td></td>
 * </tr>
 * <tr valign="top">
 * <td>DROP</td>
 * <td></td>
 * </tr>
 * <tr valign="top">
 * <td>INSERT</td>
 * <td></td>
 * </tr>
 * <tr valign="top">
 * <td>SELECT</td>
 * <td></td>
 * </tr>
 * <tr valign="top">
 * <td>UPDATE</td>
 * <td></td>
 * </tr>
 * <tr valign="top">
 * <td>UNKNOWN</td>
 * <td></td>
 * </tr>
 * </table>
 * </p>
 * <p>
 * Returns false on error.
 */
function oci_statement_type ($statement) {}

/**
 * Returns number of rows affected during statement execution
 * @link http://www.php.net/manual/en/function.oci-num-rows.php
 * @param statement resource <p>
 * A valid OCI statement identifier.
 * </p>
 * @return int the number of rows affected as an integer, or false on errors.
 */
function oci_num_rows ($statement) {}

/**
 * Closes an Oracle connection
 * @link http://www.php.net/manual/en/function.oci-close.php
 * @param connection resource <p>
 * An Oracle connection identifier returned by 
 * oci_connect, oci_pconnect,
 * or oci_new_connect.
 * </p>
 * @return bool Returns true on success or false on failure.
 */
function oci_close ($connection) {}

/**
 * Connect to an Oracle database
 * @link http://www.php.net/manual/en/function.oci-connect.php
 * @param username string <p>
 * The Oracle user name.
 * </p>
 * @param password string <p>
 * The password for username.
 * </p>
 * @param connection_string string[optional] &oci.db;
 * @param character_set string[optional] &oci.charset;
 * @param session_mode int[optional] &oci.sessionmode;
 * @return resource a connection identifier or false on error.
 */
function oci_connect ($username, $password, $connection_string = null, $character_set = null, $session_mode = null) {}

/**
 * Connect to the Oracle server using a unique connection
 * @link http://www.php.net/manual/en/function.oci-new-connect.php
 * @param username string <p>
 * The Oracle user name.
 * </p>
 * @param password string <p>
 * The password for username.
 * </p>
 * @param connection_string string[optional] &oci.db;
 * @param character_set string[optional] &oci.charset;
 * @param session_mode int[optional] &oci.sessionmode;
 * @return resource a connection identifier or false on error.
 */
function oci_new_connect ($username, $password, $connection_string = null, $character_set = null, $session_mode = null) {}

/**
 * Connect to an Oracle database using a persistent connection
 * @link http://www.php.net/manual/en/function.oci-pconnect.php
 * @param username string <p>
 * The Oracle user name.
 * </p>
 * @param password string <p>
 * The password for username.
 * </p>
 * @param connection_string string[optional] &oci.db;
 * @param character_set string[optional] &oci.charset;
 * @param session_mode int[optional] &oci.sessionmode;
 * @return resource a connection identifier or false on error.
 */
function oci_pconnect ($username, $password, $connection_string = null, $character_set = null, $session_mode = null) {}

/**
 * Returns the last error found
 * @link http://www.php.net/manual/en/function.oci-error.php
 * @param resource resource[optional] <p>
 * For most errors, resource is the
 * resource handle that was passed to the failing function call.
 * For connection errors with oci_connect,
 * oci_new_connect or 
 * oci_pconnect do not pass resource.
 * </p>
 * @return array If no error is found, oci_error returns
 * false. Otherwise, oci_error returns the
 * error information as an associative array.
 * </p>
 * <p>
 * <table>
 * oci_error Array Description
 * <tr valign="top">
 * <td>Array key</td>
 * <td>Type</td>
 * <td>&Description;</td>
 * </tr>
 * <tr valign="top">
 * <td>code</td>
 * <td>integer</td>
 * <td>
 * The Oracle error number.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>message</td>
 * <td>string</td>
 * <td>
 * The Oracle error text.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>offset</td>
 * <td>integer</td>
 * <td>
 * The byte position of an error in the SQL statement. If there
 * was no statement, this is 0
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>sqltext</td>
 * <td>string</td>
 * <td>
 * The SQL statement text. If there was no statement, this is
 * an empty string.
 * </td>
 * </tr>
 * </table>
 */
function oci_error ($resource = null) {}

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
 * @link http://www.php.net/manual/en/function.oci-lob-is-equal.php
 * @param lob1 OCI-Lob <p>
 * A LOB identifier.
 * </p>
 * @param lob2 OCI-Lob <p>
 * A LOB identifier.
 * </p>
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
 * @link http://www.php.net/manual/en/function.oci-lob-copy.php
 * @param lob_to OCI-Lob <p>
 * The destination LOB.
 * </p>
 * @param lob_from OCI-Lob <p>
 * The copied LOB.
 * </p>
 * @param length int[optional] <p>
 * Indicates the length of data to be copied.
 * </p>
 * @return bool Returns true on success or false on failure.
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
 * Commits the outstanding database transaction
 * @link http://www.php.net/manual/en/function.oci-commit.php
 * @param connection resource <p>
 * An Oracle connection identifier, returned by
 * oci_connect, oci_pconnect, or oci_new_connect.
 * </p>
 * @return bool Returns true on success or false on failure.
 */
function oci_commit ($connection) {}

/**
 * Rolls back the outstanding database transaction
 * @link http://www.php.net/manual/en/function.oci-rollback.php
 * @param connection resource <p>
 * An Oracle connection identifier, returned by
 * oci_connect, oci_pconnect
 * or oci_new_connect.
 * </p>
 * @return bool Returns true on success or false on failure.
 */
function oci_rollback ($connection) {}

/**
 * Initializes a new empty LOB or FILE descriptor
 * @link http://www.php.net/manual/en/function.oci-new-descriptor.php
 * @param connection resource <p>
 * An Oracle connection identifier, returned by 
 * oci_connect or oci_pconnect.
 * </p>
 * @param type int[optional] <p>
 * Valid values for type are: 
 * OCI_DTYPE_FILE, OCI_DTYPE_LOB and
 * OCI_DTYPE_ROWID.
 * </p>
 * @return OCI-Lob a new LOB or FILE descriptor on success, false on error.
 */
function oci_new_descriptor ($connection, $type = null) {}

/**
 * Sets number of rows to be prefetched by queries
 * @link http://www.php.net/manual/en/function.oci-set-prefetch.php
 * @param statement resource &oci.arg.statement.id;
 * @param rows int <p>
 * The number of rows to be prefetched, &gt;= 0
 * </p>
 * @return bool Returns true on success or false on failure.
 */
function oci_set_prefetch ($statement, $rows) {}

/**
 * Sets the client identifier
 * @link http://www.php.net/manual/en/function.oci-set-client-identifier.php
 * @param connection resource &oci.parameter.connection;
 * @param client_identifier string <p>
 * User chosen string up to 64 bytes long.
 * </p>
 * @return bool Returns true on success or false on failure.
 */
function oci_set_client_identifier ($connection, $client_identifier) {}

/**
 * Sets the database edition
 * @link http://www.php.net/manual/en/function.oci-set-edition.php
 * @param edition string <p>
 * Oracle Database edition name previously created with the SQL
 * "CREATE EDITION" command.
 * </p>
 * @return bool Returns true on success or false on failure.
 */
function oci_set_edition ($edition) {}

/**
 * Sets the module name
 * @link http://www.php.net/manual/en/function.oci-set-module-name.php
 * @param connection resource &oci.parameter.connection;
 * @param module_name string <p>
 * User chosen string up to 48 bytes long.
 * </p>
 * @return bool Returns true on success or false on failure.
 */
function oci_set_module_name ($connection, $module_name) {}

/**
 * Sets the action name
 * @link http://www.php.net/manual/en/function.oci-set-action.php
 * @param connection resource &oci.parameter.connection;
 * @param action_name string <p>
 * User chosen string up to 32 bytes long.
 * </p>
 * @return bool Returns true on success or false on failure.
 */
function oci_set_action ($connection, $action_name) {}

/**
 * Sets the client information
 * @link http://www.php.net/manual/en/function.oci-set-client-info.php
 * @param connection resource 
 * @param client_info string 
 * @return bool Returns true on success or false on failure.
 */
function oci_set_client_info ($connection, $client_info) {}

/**
 * Changes password of Oracle's user
 * @link http://www.php.net/manual/en/function.oci-password-change.php
 * @param connection resource <p>
 * An Oracle connection identifier, returned by 
 * oci_connect or oci_pconnect.
 * </p>
 * @param username string <p>
 * The Oracle user name.
 * </p>
 * @param old_password string <p>
 * The old password.
 * </p>
 * @param new_password string <p>
 * The new password to be set.
 * </p>
 * @return bool Returns true on success or false on failure.
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
 * @link http://www.php.net/manual/en/function.oci-new-collection.php
 * @param connection resource <p>
 * An Oracle connection identifier, returned by 
 * oci_connect or oci_pconnect.
 * </p>
 * @param tdo string <p>
 * Should be a valid named type (uppercase).
 * </p>
 * @param schema string[optional] <p>
 * Should point to the scheme, where the named type was created. The name
 * of the current user is the default value.
 * </p>
 * @return OCI-Collection a new OCICollection object or false on
 * error.
 */
function oci_new_collection ($connection, $tdo, $schema = null) {}

/**
 * @param statement_resource
 */
function oci_free_cursor ($statement_resource) {}

/**
 * &Alias; <function>oci_free_statement</function>
 * @link http://www.php.net/manual/en/function.ocifreecursor.php
 * @param statement_resource
 */
function ocifreecursor ($statement_resource) {}

/**
 * &Alias; <function>oci_bind_by_name</function>
 * @link http://www.php.net/manual/en/function.ocibindbyname.php
 * @param statement_resource
 * @param column_name
 * @param variable
 * @param maximum_length[optional]
 * @param type[optional]
 */
function ocibindbyname ($statement_resource, $column_name, &$variable, $maximum_length, $type) {}

/**
 * &Alias; <function>oci_define_by_name</function>
 * @link http://www.php.net/manual/en/function.ocidefinebyname.php
 * @param statement_resource
 * @param column_name
 * @param variable
 * @param type[optional]
 */
function ocidefinebyname ($statement_resource, $column_name, &$variable, $type) {}

/**
 * &Alias; <function>oci_field_is_null</function>
 * @link http://www.php.net/manual/en/function.ocicolumnisnull.php
 * @param statement_resource
 * @param column_number_or_name
 */
function ocicolumnisnull ($statement_resource, $column_number_or_name) {}

/**
 * &Alias; <function>oci_field_name</function>
 * @link http://www.php.net/manual/en/function.ocicolumnname.php
 * @param statement_resource
 * @param column_number
 */
function ocicolumnname ($statement_resource, $column_number) {}

/**
 * &Alias; <function>oci_field_size</function>
 * @link http://www.php.net/manual/en/function.ocicolumnsize.php
 * @param statement_resource
 * @param column_number_or_name
 */
function ocicolumnsize ($statement_resource, $column_number_or_name) {}

/**
 * &Alias; <function>oci_field_scale</function>
 * @link http://www.php.net/manual/en/function.ocicolumnscale.php
 * @param statement_resource
 * @param column_number
 */
function ocicolumnscale ($statement_resource, $column_number) {}

/**
 * &Alias; <function>oci_field_precision</function>
 * @link http://www.php.net/manual/en/function.ocicolumnprecision.php
 * @param statement_resource
 * @param column_number
 */
function ocicolumnprecision ($statement_resource, $column_number) {}

/**
 * &Alias; <function>oci_field_type</function>
 * @link http://www.php.net/manual/en/function.ocicolumntype.php
 * @param statement_resource
 * @param column_number
 */
function ocicolumntype ($statement_resource, $column_number) {}

/**
 * &Alias; <function>oci_field_type_raw</function>
 * @link http://www.php.net/manual/en/function.ocicolumntyperaw.php
 * @param statement_resource
 * @param column_number
 */
function ocicolumntyperaw ($statement_resource, $column_number) {}

/**
 * &Alias; <function>oci_execute</function>
 * @link http://www.php.net/manual/en/function.ociexecute.php
 * @param statement_resource
 * @param mode[optional]
 */
function ociexecute ($statement_resource, $mode) {}

/**
 * &Alias; <function>oci_cancel</function>
 * @link http://www.php.net/manual/en/function.ocicancel.php
 * @param statement_resource
 */
function ocicancel ($statement_resource) {}

/**
 * &Alias; <function>oci_fetch</function>
 * @link http://www.php.net/manual/en/function.ocifetch.php
 * @param statement_resource
 */
function ocifetch ($statement_resource) {}

/**
 * &Alias; <function>oci_fetch_all</function>
 * @link http://www.php.net/manual/en/function.ocifetchstatement.php
 * @param statement_resource
 * @param output
 * @param skip[optional]
 * @param maximum_rows[optional]
 * @param flags[optional]
 */
function ocifetchstatement ($statement_resource, &$output, $skip, $maximum_rows, $flags) {}

/**
 * &Alias; <function>oci_free_statement</function>
 * @link http://www.php.net/manual/en/function.ocifreestatement.php
 * @param statement_resource
 */
function ocifreestatement ($statement_resource) {}

/**
 * &Alias; <function>oci_internal_debug</function>
 * @link http://www.php.net/manual/en/function.ociinternaldebug.php
 * @param mode
 */
function ociinternaldebug ($mode) {}

/**
 * &Alias; <function>oci_num_fields</function>
 * @link http://www.php.net/manual/en/function.ocinumcols.php
 * @param statement_resource
 */
function ocinumcols ($statement_resource) {}

/**
 * &Alias; <function>oci_parse</function>
 * @link http://www.php.net/manual/en/function.ociparse.php
 * @param connection_resource
 * @param sql_text
 */
function ociparse ($connection_resource, $sql_text) {}

/**
 * &Alias; <function>oci_new_cursor</function>
 * @link http://www.php.net/manual/en/function.ocinewcursor.php
 * @param connection_resource
 */
function ocinewcursor ($connection_resource) {}

/**
 * &Alias; <function>oci_result</function>
 * @link http://www.php.net/manual/en/function.ociresult.php
 * @param statement_resource
 * @param column_number_or_name
 */
function ociresult ($statement_resource, $column_number_or_name) {}

/**
 * &Alias; <function>oci_server_version</function>
 * @link http://www.php.net/manual/en/function.ociserverversion.php
 * @param connection_resource
 */
function ociserverversion ($connection_resource) {}

/**
 * &Alias; <function>oci_statement_type</function>
 * @link http://www.php.net/manual/en/function.ocistatementtype.php
 * @param statement_resource
 */
function ocistatementtype ($statement_resource) {}

/**
 * &Alias; <function>oci_num_rows</function>
 * @link http://www.php.net/manual/en/function.ocirowcount.php
 * @param statement_resource
 */
function ocirowcount ($statement_resource) {}

/**
 * &Alias; <function>oci_close</function>
 * @link http://www.php.net/manual/en/function.ocilogoff.php
 * @param connection_resource
 */
function ocilogoff ($connection_resource) {}

/**
 * &Alias; <function>oci_connect</function>
 * @link http://www.php.net/manual/en/function.ocilogon.php
 * @param username
 * @param password
 * @param connection_string[optional]
 * @param character_set[optional]
 * @param session_mode[optional]
 */
function ocilogon ($username, $password, $connection_string, $character_set, $session_mode) {}

/**
 * &Alias; <function>oci_new_connect</function>
 * @link http://www.php.net/manual/en/function.ocinlogon.php
 * @param username
 * @param password
 * @param connection_string[optional]
 * @param character_set[optional]
 * @param session_mode[optional]
 */
function ocinlogon ($username, $password, $connection_string, $character_set, $session_mode) {}

/**
 * &Alias; <function>oci_pconnect</function>
 * @link http://www.php.net/manual/en/function.ociplogon.php
 * @param username
 * @param password
 * @param connection_string[optional]
 * @param character_set[optional]
 * @param session_mode[optional]
 */
function ociplogon ($username, $password, $connection_string, $character_set, $session_mode) {}

/**
 * &Alias; <function>oci_error</function>
 * @link http://www.php.net/manual/en/function.ocierror.php
 * @param connection_or_statement_resource[optional]
 */
function ocierror ($connection_or_statement_resource) {}

/**
 * &Alias; <function>OCI-Lob::free</function>
 * @link http://www.php.net/manual/en/function.ocifreedesc.php
 * @param lob_descriptor
 */
function ocifreedesc ($lob_descriptor) {}

/**
 * &Alias; <function>OCI-Lob::save</function>
 * @link http://www.php.net/manual/en/function.ocisavelob.php
 * @param lob_descriptor
 * @param data
 * @param offset[optional]
 */
function ocisavelob ($lob_descriptor, $data, $offset) {}

/**
 * &Alias; <function>OCI-Lob::import</function>
 * @link http://www.php.net/manual/en/function.ocisavelobfile.php
 * @param lob_descriptor
 * @param filename
 */
function ocisavelobfile ($lob_descriptor, $filename) {}

/**
 * &Alias; <function>OCI-Lob::export</function>
 * @link http://www.php.net/manual/en/function.ociwritelobtofile.php
 * @param lob_descriptor
 * @param filename
 * @param start[optional]
 * @param length[optional]
 */
function ociwritelobtofile ($lob_descriptor, $filename, $start, $length) {}

/**
 * &Alias; <function>OCI-Lob::load</function>
 * @link http://www.php.net/manual/en/function.ociloadlob.php
 * @param lob_descriptor
 */
function ociloadlob ($lob_descriptor) {}

/**
 * &Alias; <function>oci_commit</function>
 * @link http://www.php.net/manual/en/function.ocicommit.php
 * @param connection_resource
 */
function ocicommit ($connection_resource) {}

/**
 * &Alias; <function>oci_rollback</function>
 * @link http://www.php.net/manual/en/function.ocirollback.php
 * @param connection_resource
 */
function ocirollback ($connection_resource) {}

/**
 * &Alias; <function>oci_new_descriptor</function>
 * @link http://www.php.net/manual/en/function.ocinewdescriptor.php
 * @param connection_resource
 * @param type[optional]
 */
function ocinewdescriptor ($connection_resource, $type) {}

/**
 * &Alias; <function>oci_set_prefetch</function>
 * @link http://www.php.net/manual/en/function.ocisetprefetch.php
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
 * &Alias; <function>OCI-Collection::free</function>
 * @link http://www.php.net/manual/en/function.ocifreecollection.php
 * @param collection
 */
function ocifreecollection ($collection) {}

/**
 * &Alias; <function>oci_new_collection</function>
 * @link http://www.php.net/manual/en/function.ocinewcollection.php
 * @param connection_resource
 * @param type_name
 * @param schema_name[optional]
 */
function ocinewcollection ($connection_resource, $type_name, $schema_name) {}

/**
 * &Alias; <function>OCI-Collection::append</function>
 * @link http://www.php.net/manual/en/function.ocicollappend.php
 * @param collection
 * @param value
 */
function ocicollappend ($collection, $value) {}

/**
 * &Alias; <function>OCI-Collection::getElem</function>
 * @link http://www.php.net/manual/en/function.ocicollgetelem.php
 * @param collection
 * @param index
 */
function ocicollgetelem ($collection, $index) {}

/**
 * &Alias; <function>OCI-Collection::assignElem</function>
 * @link http://www.php.net/manual/en/function.ocicollassignelem.php
 * @param collection
 * @param index
 * @param value
 */
function ocicollassignelem ($collection, $index, $value) {}

/**
 * &Alias; <function>OCI-Collection::size</function>
 * @link http://www.php.net/manual/en/function.ocicollsize.php
 * @param collection
 */
function ocicollsize ($collection) {}

/**
 * &Alias; <function>OCI-Collection::max</function>
 * @link http://www.php.net/manual/en/function.ocicollmax.php
 * @param collection
 */
function ocicollmax ($collection) {}

/**
 * &Alias; <function>OCI-Collection::trim</function>
 * @link http://www.php.net/manual/en/function.ocicolltrim.php
 * @param collection
 * @param number
 */
function ocicolltrim ($collection, $number) {}


/**
 * Statement execution mode
 * for oci_execute. The transaction is
 * not automatically committed when using this mode. From PHP
 * 5.3.2 (PECL OCI8 1.4) onwards,
 * OCI_NO_AUTO_COMMIT is preferred instead
 * of OCI_DEFAULT.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('OCI_DEFAULT', 0);

/**
 * Used with oci_connect to connect with
 * the SYSOPER privilege. The &php.ini; setting
 * oci8.privileged_connect
 * should be enabled to use this.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('OCI_SYSOPER', 4);

/**
 * Used with oci_connect to connect with
 * the SYSDBA privilege. The &php.ini; setting
 * oci8.privileged_connect
 * should be enabled to use this.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('OCI_SYSDBA', 2);

/**
 * Used with oci_connect for using
 * Oracles' External or OS authentication. Introduced in PHP
 * 5.3 and PECL OCI8 1.3.4.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('OCI_CRED_EXT', -2147483648);

/**
 * Statement execution mode
 * for oci_execute. Use this mode if you
 * want meta data such as the column names but don't want to
 * fetch rows from the query.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('OCI_DESCRIBE_ONLY', 16);

/**
 * Statement execution mode for oci_execute
 * call. Automatically commit changes when the statement has
 * succeeded.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('OCI_COMMIT_ON_SUCCESS', 32);

/**
 * Statement execution mode
 * for oci_execute. The statement is not
 * committed automatically when using this mode. For
 * readability in new code, use this value instead of the
 * obsolete OCI_DEFAULT constant.
 * Introduced in PHP 5.3.2 (PECL OCI8 1.4).
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('OCI_NO_AUTO_COMMIT', 0);

/**
 * Obsolete. Statement fetch mode. Used when the application
 * knows in advance exactly how many rows it will be fetching.
 * This mode turns prefetching off for Oracle release 8 or
 * later mode. The cursor is canceled after the desired rows
 * are fetched which may result in reduced server-side
 * resource usage.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('OCI_EXACT_FETCH', 2);

/**
 * Used with to set the seek position.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('OCI_SEEK_SET', 0);

/**
 * Used with to set the seek position.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('OCI_SEEK_CUR', 1);

/**
 * Used with to set the seek position.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('OCI_SEEK_END', 2);

/**
 * Used with to free
 * buffers used.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('OCI_LOB_BUFFER_FREE', 1);

/**
 * The same as OCI_B_BFILE.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('SQLT_BFILEE', 114);

/**
 * The same as OCI_B_CFILEE.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('SQLT_CFILEE', 115);

/**
 * The same as OCI_B_CLOB.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('SQLT_CLOB', 112);

/**
 * The same as OCI_B_BLOB.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('SQLT_BLOB', 113);

/**
 * The same as OCI_B_ROWID.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('SQLT_RDD', 104);

/**
 * The same as OCI_B_INT.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('SQLT_INT', 3);

/**
 * The same as OCI_B_NUM.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('SQLT_NUM', 2);

/**
 * The same as OCI_B_CURSOR.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('SQLT_RSET', 116);

/**
 * Used with oci_bind_array_by_name to bind arrays of
 * CHAR.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('SQLT_AFC', 96);

/**
 * Used with oci_bind_array_by_name to bind arrays of
 * VARCHAR2.
 * Also used with oci_bind_by_name.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('SQLT_CHR', 1);

/**
 * Used with oci_bind_array_by_name to bind arrays of
 * VARCHAR.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('SQLT_VCS', 9);

/**
 * Used with oci_bind_array_by_name to bind arrays of
 * VARCHAR2.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('SQLT_AVC', 97);

/**
 * Used with oci_bind_array_by_name to bind arrays of
 * STRING.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('SQLT_STR', 5);

/**
 * Used with oci_bind_array_by_name to bind arrays of
 * LONG VARCHAR.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('SQLT_LVC', 94);

/**
 * Used with oci_bind_array_by_name to bind arrays of
 * FLOAT.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('SQLT_FLT', 4);

/**
 * Not supported.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('SQLT_UIN', 68);

/**
 * Used with oci_bind_by_name to bind LONG values.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('SQLT_LNG', 8);

/**
 * Used with oci_bind_by_name to bind LONG RAW values.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('SQLT_LBI', 24);

/**
 * The same as OCI_B_BIN.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('SQLT_BIN', 23);

/**
 * Used with oci_bind_array_by_name to bind arrays of
 * LONG.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('SQLT_ODT', 156);

/**
 * Not supported.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('SQLT_BDOUBLE', 22);

/**
 * Not supported.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('SQLT_BFLOAT', 21);

/**
 * Used with oci_bind_by_name when binding
 * named data types. Note: in PHP &lt; 5.0 it was called
 * OCI_B_SQLT_NTY.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('OCI_B_NTY', 108);

/**
 * The same as OCI_B_NTY.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('SQLT_NTY', 108);

/**
 * Obsolete.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('OCI_SYSDATE', "SYSDATE");

/**
 * Used with oci_bind_by_name when binding
 * BFILEs.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('OCI_B_BFILE', 114);

/**
 * Used with oci_bind_by_name when binding
 * CFILEs.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('OCI_B_CFILEE', 115);

/**
 * Used with oci_bind_by_name when binding
 * CLOBs.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('OCI_B_CLOB', 112);

/**
 * Used with oci_bind_by_name when
 * binding BLOBs.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('OCI_B_BLOB', 113);

/**
 * Used with oci_bind_by_name when binding
 * ROWIDs.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('OCI_B_ROWID', 104);

/**
 * Used with oci_bind_by_name when binding
 * cursors, previously allocated
 * with oci_new_descriptor.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('OCI_B_CURSOR', 116);

/**
 * Used with oci_bind_by_name to bind RAW values.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('OCI_B_BIN', 23);

/**
 * Used with oci_bind_array_by_name to bind arrays of
 * INTEGER.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('OCI_B_INT', 3);

/**
 * Used with oci_bind_array_by_name to bind arrays of
 * NUMBER.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('OCI_B_NUM', 2);

/**
 * Default mode of oci_fetch_all.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('OCI_FETCHSTATEMENT_BY_COLUMN', 16);

/**
 * Alternative mode of oci_fetch_all.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('OCI_FETCHSTATEMENT_BY_ROW', 32);

/**
 * Used with oci_fetch_all and
 * oci_fetch_array to get results as an associative
 * array.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('OCI_ASSOC', 1);

/**
 * Used with oci_fetch_all and
 * oci_fetch_array to get results as an
 * enumerated array.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('OCI_NUM', 2);

/**
 * Used with oci_fetch_all and
 * oci_fetch_array to get results as an
 * array with both associative and number indices.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('OCI_BOTH', 3);

/**
 * Used with oci_fetch_array to get empty
 * array elements if the row items value is &null;.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('OCI_RETURN_NULLS', 4);

/**
 * Used with oci_fetch_array to get the
 * data value of the LOB instead of the descriptor.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('OCI_RETURN_LOBS', 8);

/**
 * This flag tells oci_new_descriptor to
 * initialize a new FILE descriptor.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('OCI_DTYPE_FILE', 56);

/**
 * This flag tells oci_new_descriptor to
 * initialize a new LOB descriptor.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('OCI_DTYPE_LOB', 50);

/**
 * This flag tells oci_new_descriptor to
 * initialize a new ROWID descriptor.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('OCI_DTYPE_ROWID', 54);

/**
 * The same as OCI_DTYPE_FILE.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('OCI_D_FILE', 56);

/**
 * The same as OCI_DTYPE_LOB.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('OCI_D_LOB', 50);

/**
 * The same as OCI_DTYPE_ROWID.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('OCI_D_ROWID', 54);

/**
 * Used with 
 * to indicate that a temporary CLOB should be created.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('OCI_TEMP_CLOB', 2);

/**
 * Used with 
 * to indicate that a temporary BLOB should be created.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('OCI_TEMP_BLOB', 1);

// End of oci8 v.1.4.7
?>
