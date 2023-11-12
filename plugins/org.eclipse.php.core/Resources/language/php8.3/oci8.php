<?php

// Start of oci8 v.3.3.0

/**
 * OCI8 LOB functionality for large binary (BLOB) and character (CLOB) objects.
 * <p>The OCI-Lob class was renamed to OCILob in PHP 8 and PECL OCI8 3.0.0 to align with PHP naming standards.</p>
 * @link http://www.php.net/manual/en/class.ocilob.php
 */
#[AllowDynamicProperties]
class OCILob  {

	/**
	 * Saves data to the large object
	 * @link http://www.php.net/manual/en/ocilob.save.php
	 * @param string $data 
	 * @param int $offset [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function save (string $data, int $offset = null): bool {}

	/**
	 * Imports file data to the LOB
	 * @link http://www.php.net/manual/en/ocilob.import.php
	 * @param string $filename 
	 * @return bool Returns true on success or false on failure.
	 */
	public function import (string $filename): bool {}

	/**
	 * Alias of OCILob::import
	 * @link http://www.php.net/manual/en/ocilob.savefile.php
	 * @param string $filename 
	 * @return bool Returns true on success or false on failure.
	 */
	public function saveFile (string $filename): bool {}

	/**
	 * Returns large object's contents
	 * @link http://www.php.net/manual/en/ocilob.load.php
	 * @return string|false Returns the contents of the object, or false on errors.
	 */
	public function load (): string|false {}

	/**
	 * Reads part of the large object
	 * @link http://www.php.net/manual/en/ocilob.read.php
	 * @param int $length 
	 * @return string|false Returns the contents as a string, or false on failure.
	 */
	public function read (int $length): string|false {}

	/**
	 * Tests for end-of-file on a large object's descriptor
	 * @link http://www.php.net/manual/en/ocilob.eof.php
	 * @return bool Returns true if internal pointer of large object is at the end of LOB.
	 * Otherwise returns false.
	 */
	public function eof (): bool {}

	/**
	 * Returns the current position of internal pointer of large object
	 * @link http://www.php.net/manual/en/ocilob.tell.php
	 * @return int|false Returns current position of a LOB's internal pointer or false if an
	 * error occurred.
	 */
	public function tell (): int|false {}

	/**
	 * Moves the internal pointer to the beginning of the large object
	 * @link http://www.php.net/manual/en/ocilob.rewind.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function rewind (): bool {}

	/**
	 * Sets the internal pointer of the large object
	 * @link http://www.php.net/manual/en/ocilob.seek.php
	 * @param int $offset 
	 * @param int $whence [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function seek (int $offset, int $whence = OCI_SEEK_SET): bool {}

	/**
	 * Returns size of large object
	 * @link http://www.php.net/manual/en/ocilob.size.php
	 * @return int|false Returns length of large object value or false on failure.
	 * Empty objects have zero length.
	 */
	public function size (): int|false {}

	/**
	 * Writes data to the large object
	 * @link http://www.php.net/manual/en/ocilob.write.php
	 * @param string $data 
	 * @param int|null $length [optional] 
	 * @return int|false Returns the number of bytes written or false on failure.
	 */
	public function write (string $data, ?int $length = null): int|false {}

	/**
	 * Appends data from the large object to another large object
	 * @link http://www.php.net/manual/en/ocilob.append.php
	 * @param OCILob $from 
	 * @return bool Returns true on success or false on failure.
	 */
	public function append (OCILob $from): bool {}

	/**
	 * Truncates large object
	 * @link http://www.php.net/manual/en/ocilob.truncate.php
	 * @param int $length [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function truncate (int $length = null): bool {}

	/**
	 * Erases a specified portion of the internal LOB data
	 * @link http://www.php.net/manual/en/ocilob.erase.php
	 * @param int|null $offset [optional] 
	 * @param int|null $length [optional] 
	 * @return int|false Returns the actual number of characters/bytes erased or false on failure.
	 */
	public function erase (?int $offset = null, ?int $length = null): int|false {}

	/**
	 * Flushes/writes buffer of the LOB to the server
	 * @link http://www.php.net/manual/en/ocilob.flush.php
	 * @param int $flag [optional] 
	 * @return bool Returns true on success or false on failure.
	 * <p>Returns false if buffering was not enabled or an error occurred.</p>
	 */
	public function flush (int $flag = null): bool {}

	/**
	 * Changes current state of buffering for the large object
	 * @link http://www.php.net/manual/en/ocilob.setbuffering.php
	 * @param bool $mode 
	 * @return bool Returns true on success or false on failure. Repeated calls to this method with the same flag will
	 * return true.
	 */
	public function setBuffering (bool $mode): bool {}

	/**
	 * Returns current state of buffering for the large object
	 * @link http://www.php.net/manual/en/ocilob.getbuffering.php
	 * @return bool Returns false if buffering for the large object is off and true if
	 * buffering is used.
	 */
	public function getBuffering (): bool {}

	/**
	 * Alias of OCILob::export
	 * @link http://www.php.net/manual/en/ocilob.writetofile.php
	 * @param string $filename 
	 * @param int|null $offset [optional] 
	 * @param int|null $length [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function writeToFile (string $filename, ?int $offset = null, ?int $length = null): bool {}

	/**
	 * Exports LOB's contents to a file
	 * @link http://www.php.net/manual/en/ocilob.export.php
	 * @param string $filename 
	 * @param int|null $offset [optional] 
	 * @param int|null $length [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function export (string $filename, ?int $offset = null, ?int $length = null): bool {}

	/**
	 * Writes a temporary large object
	 * @link http://www.php.net/manual/en/ocilob.writetemporary.php
	 * @param string $data 
	 * @param int $type [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function writeTemporary (string $data, int $type = OCI_TEMP_CLOB): bool {}

	/**
	 * Closes LOB descriptor
	 * @link http://www.php.net/manual/en/ocilob.close.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function close (): bool {}

	/**
	 * Frees resources associated with the LOB descriptor
	 * @link http://www.php.net/manual/en/ocilob.free.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function free (): bool {}

}

/**
 * OCI8 Collection functionality.
 * <p>The OCI-Collection class was renamed to OCICollection in PHP 8 OCI8 3.0.0 to align with PHP naming standards.</p>
 * @link http://www.php.net/manual/en/class.ocicollection.php
 */
#[AllowDynamicProperties]
class OCICollection  {

	/**
	 * Frees the resources associated with the collection object
	 * @link http://www.php.net/manual/en/ocicollection.free.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function free (): bool {}

	/**
	 * Appends element to the collection
	 * @link http://www.php.net/manual/en/ocicollection.append.php
	 * @param string $value 
	 * @return bool Returns true on success or false on failure.
	 */
	public function append (string $value): bool {}

	/**
	 * Returns value of the element
	 * @link http://www.php.net/manual/en/ocicollection.getelem.php
	 * @param int $index 
	 * @return string|float|null|false Returns false if such element doesn't exist; null if element is null;
	 * string if element is column of a string datatype or number if element is
	 * numeric field.
	 */
	public function getElem (int $index): string|float|null|false {}

	/**
	 * Assigns a value to the collection from another existing collection
	 * @link http://www.php.net/manual/en/ocicollection.assign.php
	 * @param OCICollection $from 
	 * @return bool Returns true on success or false on failure.
	 */
	public function assign (OCICollection $from): bool {}

	/**
	 * Assigns a value to the element of the collection
	 * @link http://www.php.net/manual/en/ocicollection.assignelem.php
	 * @param int $index 
	 * @param string $value 
	 * @return bool Returns true on success or false on failure.
	 */
	public function assignElem (int $index, string $value): bool {}

	/**
	 * Returns size of the collection
	 * @link http://www.php.net/manual/en/ocicollection.size.php
	 * @return int|false Returns the number of elements in the collection or false on error.
	 */
	public function size (): int|false {}

	/**
	 * Returns the maximum number of elements in the collection
	 * @link http://www.php.net/manual/en/ocicollection.max.php
	 * @return int|false Returns the maximum number as an integer, or false on errors.
	 * <p>If the returned value is 0, then the number of elements is not limited.</p>
	 */
	public function max (): int|false {}

	/**
	 * Trims elements from the end of the collection
	 * @link http://www.php.net/manual/en/ocicollection.trim.php
	 * @param int $num 
	 * @return bool Returns true on success or false on failure.
	 */
	public function trim (int $num): bool {}

}

/**
 * Associates a PHP variable with a column for query fetches
 * @link http://www.php.net/manual/en/function.oci-define-by-name.php
 * @param resource $statement 
 * @param string $column 
 * @param mixed $var 
 * @param int $type [optional] 
 * @return bool Returns true on success or false on failure.
 */
function oci_define_by_name ($statement, string $column, mixed &$var, int $type = null): bool {}

/**
 * {@inheritdoc}
 * @param mixed $statement
 * @param string $column
 * @param mixed $var
 * @param int $type [optional]
 * @deprecated 
 */
function ocidefinebyname ($statement = null, string $column, mixed &$var = null, int $type = 0): bool {}

/**
 * Binds a PHP variable to an Oracle placeholder
 * @link http://www.php.net/manual/en/function.oci-bind-by-name.php
 * @param resource $statement 
 * @param string $param 
 * @param mixed $var 
 * @param int $max_length [optional] 
 * @param int $type [optional] 
 * @return bool Returns true on success or false on failure.
 */
function oci_bind_by_name ($statement, string $param, mixed &$var, int $max_length = -1, int $type = null): bool {}

/**
 * {@inheritdoc}
 * @param mixed $statement
 * @param string $param
 * @param mixed $var
 * @param int $max_length [optional]
 * @param int $type [optional]
 * @deprecated 
 */
function ocibindbyname ($statement = null, string $param, mixed &$var = null, int $max_length = -1, int $type = 0): bool {}

/**
 * Binds a PHP array to an Oracle PL/SQL array parameter
 * @link http://www.php.net/manual/en/function.oci-bind-array-by-name.php
 * @param resource $statement 
 * @param string $param 
 * @param array $var 
 * @param int $max_array_length 
 * @param int $max_item_length [optional] 
 * @param int $type [optional] 
 * @return bool Returns true on success or false on failure.
 */
function oci_bind_array_by_name ($statement, string $param, array &$var, int $max_array_length, int $max_item_length = -1, int $type = SQLT_AFC): bool {}

/**
 * Frees a descriptor
 * @link http://www.php.net/manual/en/function.oci-free-descriptor.php
 * @param OCILob $lob 
 * @return bool Returns true on success or false on failure.
 */
function oci_free_descriptor (OCILob $lob): bool {}

/**
 * {@inheritdoc}
 * @param OCILob $lob
 * @deprecated 
 */
function ocifreedesc (OCILob $lob): bool {}

/**
 * {@inheritdoc}
 * @param OCILob $lob
 * @param string $data
 * @param int $offset [optional]
 */
function oci_lob_save (OCILob $lob, string $data, int $offset = 0): bool {}

/**
 * {@inheritdoc}
 * @param OCILob $lob
 * @param string $data
 * @param int $offset [optional]
 * @deprecated 
 */
function ocisavelob (OCILob $lob, string $data, int $offset = 0): bool {}

/**
 * {@inheritdoc}
 * @param OCILob $lob
 * @param string $filename
 */
function oci_lob_import (OCILob $lob, string $filename): bool {}

/**
 * {@inheritdoc}
 * @param OCILob $lob
 * @param string $filename
 * @deprecated 
 */
function ocisavelobfile (OCILob $lob, string $filename): bool {}

/**
 * {@inheritdoc}
 * @param OCILob $lob
 */
function oci_lob_load (OCILob $lob): string|false {}

/**
 * {@inheritdoc}
 * @param OCILob $lob
 * @deprecated 
 */
function ociloadlob (OCILob $lob): string|false {}

/**
 * {@inheritdoc}
 * @param OCILob $lob
 * @param int $length
 */
function oci_lob_read (OCILob $lob, int $length): string|false {}

/**
 * {@inheritdoc}
 * @param OCILob $lob
 */
function oci_lob_eof (OCILob $lob): bool {}

/**
 * {@inheritdoc}
 * @param OCILob $lob
 */
function oci_lob_tell (OCILob $lob): int|false {}

/**
 * {@inheritdoc}
 * @param OCILob $lob
 */
function oci_lob_rewind (OCILob $lob): bool {}

/**
 * {@inheritdoc}
 * @param OCILob $lob
 * @param int $offset
 * @param int $whence [optional]
 */
function oci_lob_seek (OCILob $lob, int $offset, int $whence = 0): bool {}

/**
 * {@inheritdoc}
 * @param OCILob $lob
 */
function oci_lob_size (OCILob $lob): int|false {}

/**
 * {@inheritdoc}
 * @param OCILob $lob
 * @param string $data
 * @param int|null $length [optional]
 */
function oci_lob_write (OCILob $lob, string $data, ?int $length = NULL): int|false {}

/**
 * {@inheritdoc}
 * @param OCILob $to
 * @param OCILob $from
 */
function oci_lob_append (OCILob $to, OCILob $from): bool {}

/**
 * {@inheritdoc}
 * @param OCILob $lob
 * @param int $length [optional]
 */
function oci_lob_truncate (OCILob $lob, int $length = 0): bool {}

/**
 * {@inheritdoc}
 * @param OCILob $lob
 * @param int|null $offset [optional]
 * @param int|null $length [optional]
 */
function oci_lob_erase (OCILob $lob, ?int $offset = NULL, ?int $length = NULL): int|false {}

/**
 * {@inheritdoc}
 * @param OCILob $lob
 * @param int $flag [optional]
 */
function oci_lob_flush (OCILob $lob, int $flag = 0): bool {}

/**
 * {@inheritdoc}
 * @param OCILob $lob
 * @param bool $mode
 */
function ocisetbufferinglob (OCILob $lob, bool $mode): bool {}

/**
 * {@inheritdoc}
 * @param OCILob $lob
 */
function ocigetbufferinglob (OCILob $lob): bool {}

/**
 * Copies large object
 * @link http://www.php.net/manual/en/function.oci-lob-copy.php
 * @param OCILob $to 
 * @param OCILob $from 
 * @param int|null $length [optional] 
 * @return bool Returns true on success or false on failure.
 */
function oci_lob_copy (OCILob $to, OCILob $from, ?int $length = null): bool {}

/**
 * Compares two LOB/FILE locators for equality
 * @link http://www.php.net/manual/en/function.oci-lob-is-equal.php
 * @param OCILob $lob1 
 * @param OCILob $lob2 
 * @return bool Returns true if these objects are equal, false otherwise.
 */
function oci_lob_is_equal (OCILob $lob1, OCILob $lob2): bool {}

/**
 * {@inheritdoc}
 * @param OCILob $lob
 * @param string $filename
 * @param int|null $offset [optional]
 * @param int|null $length [optional]
 */
function oci_lob_export (OCILob $lob, string $filename, ?int $offset = NULL, ?int $length = NULL): bool {}

/**
 * {@inheritdoc}
 * @param OCILob $lob
 * @param string $filename
 * @param int|null $offset [optional]
 * @param int|null $length [optional]
 * @deprecated 
 */
function ociwritelobtofile (OCILob $lob, string $filename, ?int $offset = NULL, ?int $length = NULL): bool {}

/**
 * Initializes a new empty LOB or FILE descriptor
 * @link http://www.php.net/manual/en/function.oci-new-descriptor.php
 * @param resource $connection 
 * @param int $type [optional] 
 * @return OCILob|null Returns a new LOB or FILE descriptor on success, or null on failure.
 */
function oci_new_descriptor ($connection, int $type = OCI_DTYPE_LOB): ?OCILob {}

/**
 * {@inheritdoc}
 * @param mixed $connection
 * @param int $type [optional]
 * @deprecated 
 */
function ocinewdescriptor ($connection = null, int $type = 50): ?OCILob {}

/**
 * Rolls back the outstanding database transaction
 * @link http://www.php.net/manual/en/function.oci-rollback.php
 * @param resource $connection 
 * @return bool Returns true on success or false on failure.
 */
function oci_rollback ($connection): bool {}

/**
 * {@inheritdoc}
 * @param mixed $connection
 * @deprecated 
 */
function ocirollback ($connection = null): bool {}

/**
 * Commits the outstanding database transaction
 * @link http://www.php.net/manual/en/function.oci-commit.php
 * @param resource $connection 
 * @return bool Returns true on success or false on failure.
 */
function oci_commit ($connection): bool {}

/**
 * {@inheritdoc}
 * @param mixed $connection
 * @deprecated 
 */
function ocicommit ($connection = null): bool {}

/**
 * Returns the name of a field from the statement
 * @link http://www.php.net/manual/en/function.oci-field-name.php
 * @param resource $statement 
 * @param string|int $column 
 * @return string|false Returns the name as a string, or false on failure
 */
function oci_field_name ($statement, string|int $column): string|false {}

/**
 * {@inheritdoc}
 * @param mixed $statement
 * @param string|int $column
 * @deprecated 
 */
function ocicolumnname ($statement = null, string|int $column): string|false {}

/**
 * Returns field's size
 * @link http://www.php.net/manual/en/function.oci-field-size.php
 * @param resource $statement 
 * @param string|int $column 
 * @return int|false Returns the size of a column in bytes, or false on failure
 */
function oci_field_size ($statement, string|int $column): int|false {}

/**
 * {@inheritdoc}
 * @param mixed $statement
 * @param string|int $column
 * @deprecated 
 */
function ocicolumnsize ($statement = null, string|int $column): int|false {}

/**
 * Tell the scale of the field
 * @link http://www.php.net/manual/en/function.oci-field-scale.php
 * @param resource $statement 
 * @param string|int $column 
 * @return int|false Returns the scale as an integer, or false on failure
 */
function oci_field_scale ($statement, string|int $column): int|false {}

/**
 * {@inheritdoc}
 * @param mixed $statement
 * @param string|int $column
 * @deprecated 
 */
function ocicolumnscale ($statement = null, string|int $column): int|false {}

/**
 * Tell the precision of a field
 * @link http://www.php.net/manual/en/function.oci-field-precision.php
 * @param resource $statement 
 * @param string|int $column 
 * @return int|false Returns the precision as an integer, or false on failure
 */
function oci_field_precision ($statement, string|int $column): int|false {}

/**
 * {@inheritdoc}
 * @param mixed $statement
 * @param string|int $column
 * @deprecated 
 */
function ocicolumnprecision ($statement = null, string|int $column): int|false {}

/**
 * Returns a field's data type name
 * @link http://www.php.net/manual/en/function.oci-field-type.php
 * @param resource $statement 
 * @param string|int $column 
 * @return string|int|false Returns the field data type as a string or an int, or false on failure
 */
function oci_field_type ($statement, string|int $column): string|int|false {}

/**
 * {@inheritdoc}
 * @param mixed $statement
 * @param string|int $column
 * @deprecated 
 */
function ocicolumntype ($statement = null, string|int $column): string|int|false {}

/**
 * Tell the raw Oracle data type of the field
 * @link http://www.php.net/manual/en/function.oci-field-type-raw.php
 * @param resource $statement 
 * @param string|int $column 
 * @return int|false Returns Oracle's raw data type as a number, or false on failure
 */
function oci_field_type_raw ($statement, string|int $column): int|false {}

/**
 * {@inheritdoc}
 * @param mixed $statement
 * @param string|int $column
 * @deprecated 
 */
function ocicolumntyperaw ($statement = null, string|int $column): int|false {}

/**
 * Checks if a field in the currently fetched row is null
 * @link http://www.php.net/manual/en/function.oci-field-is-null.php
 * @param resource $statement 
 * @param string|int $column 
 * @return bool Returns true if column is null, false otherwise.
 */
function oci_field_is_null ($statement, string|int $column): bool {}

/**
 * {@inheritdoc}
 * @param mixed $statement
 * @param string|int $column
 * @deprecated 
 */
function ocicolumnisnull ($statement = null, string|int $column): bool {}

/**
 * Executes a statement
 * @link http://www.php.net/manual/en/function.oci-execute.php
 * @param resource $statement 
 * @param int $mode [optional] 
 * @return bool Returns true on success or false on failure.
 */
function oci_execute ($statement, int $mode = OCI_COMMIT_ON_SUCCESS): bool {}

/**
 * {@inheritdoc}
 * @param mixed $statement
 * @param int $mode [optional]
 * @deprecated 
 */
function ociexecute ($statement = null, int $mode = 32): bool {}

/**
 * Cancels reading from cursor
 * @link http://www.php.net/manual/en/function.oci-cancel.php
 * @param resource $statement 
 * @return bool Returns true on success or false on failure.
 */
function oci_cancel ($statement): bool {}

/**
 * {@inheritdoc}
 * @param mixed $statement
 * @deprecated 
 */
function ocicancel ($statement = null): bool {}

/**
 * Fetches the next row from a query into internal buffers
 * @link http://www.php.net/manual/en/function.oci-fetch.php
 * @param resource $statement 
 * @return bool Returns true on success or false if there are no more rows in the
 * statement.
 */
function oci_fetch ($statement): bool {}

/**
 * {@inheritdoc}
 * @param mixed $statement
 * @deprecated 
 */
function ocifetch ($statement = null): bool {}

/**
 * {@inheritdoc}
 * @param mixed $statement
 * @param mixed $result
 * @param int $mode [optional]
 * @deprecated 
 */
function ocifetchinto ($statement = null, &$result = null, int $mode = 2): int|false {}

/**
 * Fetches multiple rows from a query into a two-dimensional array
 * @link http://www.php.net/manual/en/function.oci-fetch-all.php
 * @param resource $statement 
 * @param array $output 
 * @param int $offset [optional] 
 * @param int $limit [optional] 
 * @param int $flags [optional] 
 * @return int Returns the number of rows in output, which
 * may be 0 or more.
 */
function oci_fetch_all ($statement, array &$output, int $offset = null, int $limit = -1, int $flags = 'OCI_FETCHSTATEMENT_BY_COLUMN | OCI_ASSOC'): int {}

/**
 * {@inheritdoc}
 * @param mixed $statement
 * @param mixed $output
 * @param int $offset [optional]
 * @param int $limit [optional]
 * @param int $flags [optional]
 * @deprecated 
 */
function ocifetchstatement ($statement = null, &$output = null, int $offset = 0, int $limit = -1, int $flags = 17): int {}

/**
 * Returns the next row from a query as an object
 * @link http://www.php.net/manual/en/function.oci-fetch-object.php
 * @param resource $statement 
 * @param int $mode [optional] 
 * @return stdClass|false Returns an object. Each attribute of the object corresponds to a
 * column of the row. If there are no more rows in
 * the statement then false is returned.
 * <p>Any LOB columns are returned as LOB descriptors.</p>
 * <p>DATE columns are returned as strings formatted
 * to the current date format. The default format can be changed with
 * Oracle environment variables such as NLS_LANG or
 * by a previously executed ALTER SESSION SET
 * NLS_DATE_FORMAT command.</p>
 * <p>Oracle's default, non-case sensitive column names will have
 * uppercase attribute names. Case-sensitive column names will have
 * attribute names using the exact column case.
 * Use var_dump on the result object to verify
 * the appropriate case for attribute access.</p>
 * <p>Attribute values will be null for any NULL
 * data fields.</p>
 */
function oci_fetch_object ($statement, int $mode = 'OCI_ASSOC | OCI_RETURN_NULLS'): stdClass|false {}

/**
 * Returns the next row from a query as a numeric array
 * @link http://www.php.net/manual/en/function.oci-fetch-row.php
 * @param resource $statement 
 * @return array|false Returns a numerically indexed array. If there are no more rows in
 * the statement then false is returned.
 */
function oci_fetch_row ($statement): array|false {}

/**
 * Returns the next row from a query as an associative array
 * @link http://www.php.net/manual/en/function.oci-fetch-assoc.php
 * @param resource $statement 
 * @return array|false Returns an associative array. If there are no more rows in
 * the statement then false is returned.
 */
function oci_fetch_assoc ($statement): array|false {}

/**
 * Returns the next row from a query as an associative or numeric array
 * @link http://www.php.net/manual/en/function.oci-fetch-array.php
 * @param resource $statement 
 * @param int $mode [optional] 
 * @return array|false Returns an array with associative and/or numeric indices. If there
 * are no more rows in the statement then
 * false is returned.
 * <p>By default, LOB columns are returned as LOB descriptors.</p>
 * <p>DATE columns are returned as strings formatted
 * to the current date format. The default format can be changed with
 * Oracle environment variables such as NLS_LANG or
 * by a previously executed ALTER SESSION SET
 * NLS_DATE_FORMAT command.</p>
 * <p>Oracle's default, non-case sensitive column names will have
 * uppercase associative indices in the result array. Case-sensitive
 * column names will have array indices using the exact column case.
 * Use var_dump on the result array to verify the
 * appropriate case to use for each query.</p>
 * <p>The table name is not included in the array index. If your query
 * contains two different columns with the same name,
 * use OCI_NUM or add a column alias to the query
 * to ensure name uniqueness, see example #7. Otherwise only one
 * column will be returned via PHP.</p>
 */
function oci_fetch_array ($statement, int $mode = 'OCI_BOTH | OCI_RETURN_NULLS'): array|false {}

/**
 * Frees all resources associated with statement or cursor
 * @link http://www.php.net/manual/en/function.oci-free-statement.php
 * @param resource $statement 
 * @return bool Returns true on success or false on failure.
 */
function oci_free_statement ($statement): bool {}

/**
 * {@inheritdoc}
 * @param mixed $statement
 * @deprecated 
 */
function ocifreestatement ($statement = null): bool {}

/**
 * {@inheritdoc}
 * @param mixed $statement
 */
function oci_free_cursor ($statement = null): bool {}

/**
 * {@inheritdoc}
 * @param mixed $statement
 * @deprecated 
 */
function ocifreecursor ($statement = null): bool {}

/**
 * Closes an Oracle connection
 * @link http://www.php.net/manual/en/function.oci-close.php
 * @param resource $connection 
 * @return bool|null Returns null when oci8.old_oci_close_semantics is enabled,
 * or true otherwise.
 */
function oci_close ($connection): ?bool {}

/**
 * {@inheritdoc}
 * @param mixed $connection
 * @deprecated 
 */
function ocilogoff ($connection = null): ?bool {}

/**
 * Connect to the Oracle server using a unique connection
 * @link http://www.php.net/manual/en/function.oci-new-connect.php
 * @param string $username 
 * @param string $password 
 * @param string|null $connection_string [optional] 
 * @param string $encoding [optional] 
 * @param int $session_mode [optional] 
 * @return resource|false Returns a connection identifier or false on error.
 */
function oci_new_connect (string $username, string $password, ?string $connection_string = null, string $encoding = '""', int $session_mode = OCI_DEFAULT) {}

/**
 * {@inheritdoc}
 * @param string $username
 * @param string $password
 * @param string|null $connection_string [optional]
 * @param string $encoding [optional]
 * @param int $session_mode [optional]
 * @deprecated 
 */
function ocinlogon (string $username, string $password, ?string $connection_string = NULL, string $encoding = '', int $session_mode = 0) {}

/**
 * Connect to an Oracle database
 * @link http://www.php.net/manual/en/function.oci-connect.php
 * @param string $username 
 * @param string $password 
 * @param string|null $connection_string [optional] 
 * @param string $encoding [optional] 
 * @param int $session_mode [optional] 
 * @return resource|false Returns a connection identifier or false on error.
 */
function oci_connect (string $username, string $password, ?string $connection_string = null, string $encoding = '""', int $session_mode = OCI_DEFAULT) {}

/**
 * {@inheritdoc}
 * @param string $username
 * @param string $password
 * @param string|null $connection_string [optional]
 * @param string $encoding [optional]
 * @param int $session_mode [optional]
 * @deprecated 
 */
function ocilogon (string $username, string $password, ?string $connection_string = NULL, string $encoding = '', int $session_mode = 0) {}

/**
 * Connect to an Oracle database using a persistent connection
 * @link http://www.php.net/manual/en/function.oci-pconnect.php
 * @param string $username 
 * @param string $password 
 * @param string|null $connection_string [optional] 
 * @param string $encoding [optional] 
 * @param int $session_mode [optional] 
 * @return resource|false Returns a connection identifier or false on error.
 */
function oci_pconnect (string $username, string $password, ?string $connection_string = null, string $encoding = '""', int $session_mode = OCI_DEFAULT) {}

/**
 * {@inheritdoc}
 * @param string $username
 * @param string $password
 * @param string|null $connection_string [optional]
 * @param string $encoding [optional]
 * @param int $session_mode [optional]
 * @deprecated 
 */
function ociplogon (string $username, string $password, ?string $connection_string = NULL, string $encoding = '', int $session_mode = 0) {}

/**
 * Returns the last error found
 * @link http://www.php.net/manual/en/function.oci-error.php
 * @param resource|null $connection_or_statement [optional] 
 * @return array|false If no error is found, oci_error returns
 * false. Otherwise, oci_error returns the
 * error information as an associative array.
 * <p><table>
 * oci_error Array Description
 * <table>
 * <tr valign="top">
 * <td>Array key</td>
 * <td>Type</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>code</td>
 * <td>int</td>
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
 * <td>int</td>
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
 * </table></p>
 */
function oci_error ($connection_or_statement = null): array|false {}

/**
 * {@inheritdoc}
 * @param mixed $connection_or_statement [optional]
 * @deprecated 
 */
function ocierror ($connection_or_statement = NULL): array|false {}

/**
 * Returns the number of result columns in a statement
 * @link http://www.php.net/manual/en/function.oci-num-fields.php
 * @param resource $statement 
 * @return int Returns the number of columns as an int.
 */
function oci_num_fields ($statement): int {}

/**
 * {@inheritdoc}
 * @param mixed $statement
 * @deprecated 
 */
function ocinumcols ($statement = null): int {}

/**
 * Prepares an Oracle statement for execution
 * @link http://www.php.net/manual/en/function.oci-parse.php
 * @param resource $connection 
 * @param string $sql 
 * @return resource|false Returns a statement handle on success, or false on error.
 */
function oci_parse ($connection, string $sql) {}

/**
 * {@inheritdoc}
 * @param mixed $connection
 * @param string $sql
 * @deprecated 
 */
function ociparse ($connection = null, string $sql) {}

/**
 * Returns the next child statement resource from a parent statement resource that has Oracle Database Implicit Result Sets
 * @link http://www.php.net/manual/en/function.oci-get-implicit-resultset.php
 * @param resource $statement 
 * @return resource|false Returns a statement handle for the next child statement available
 * on statement. Returns false when child
 * statements do not exist, or all child statements have been returned
 * by previous calls
 * to oci_get_implicit_resultset.
 */
function oci_get_implicit_resultset ($statement) {}

/**
 * Sets number of rows to be prefetched by queries
 * @link http://www.php.net/manual/en/function.oci-set-prefetch.php
 * @param resource $statement 
 * @param int $rows 
 * @return bool Returns true on success or false on failure.
 */
function oci_set_prefetch ($statement, int $rows): bool {}

/**
 * {@inheritdoc}
 * @param mixed $statement
 * @param int $rows
 * @deprecated 
 */
function ocisetprefetch ($statement = null, int $rows): bool {}

/**
 * Sets the amount of data prefetched for each CLOB or BLOB.
 * @link http://www.php.net/manual/en/function.oci-set-prefetch-lob.php
 * @param resource $statement 
 * @param int $prefetch_lob_size 
 * @return bool Returns true on success or false on failure.
 */
function oci_set_prefetch_lob ($statement, int $prefetch_lob_size): bool {}

/**
 * Sets the client identifier
 * @link http://www.php.net/manual/en/function.oci-set-client-identifier.php
 * @param resource $connection 
 * @param string $client_id 
 * @return bool Returns true on success or false on failure.
 */
function oci_set_client_identifier ($connection, string $client_id): bool {}

/**
 * Sets the database edition
 * @link http://www.php.net/manual/en/function.oci-set-edition.php
 * @param string $edition 
 * @return bool Returns true on success or false on failure.
 */
function oci_set_edition (string $edition): bool {}

/**
 * Sets the module name
 * @link http://www.php.net/manual/en/function.oci-set-module-name.php
 * @param resource $connection 
 * @param string $name 
 * @return bool Returns true on success or false on failure.
 */
function oci_set_module_name ($connection, string $name): bool {}

/**
 * Sets the action name
 * @link http://www.php.net/manual/en/function.oci-set-action.php
 * @param resource $connection 
 * @param string $action 
 * @return bool Returns true on success or false on failure.
 */
function oci_set_action ($connection, string $action): bool {}

/**
 * Sets the client information
 * @link http://www.php.net/manual/en/function.oci-set-client-info.php
 * @param resource $connection 
 * @param string $client_info 
 * @return bool Returns true on success or false on failure.
 */
function oci_set_client_info ($connection, string $client_info): bool {}

/**
 * Sets the database operation
 * @link http://www.php.net/manual/en/function.oci-set-db-operation.php
 * @param resource $connection 
 * @param string $action 
 * @return bool Returns true on success or false on failure.
 */
function oci_set_db_operation ($connection, string $action): bool {}

/**
 * Sets a millisecond timeout for database calls
 * @link http://www.php.net/manual/en/function.oci-set-call-timout.php
 * @param resource $connection 
 * @param int $timeout 
 * @return bool Returns true on success or false on failure.
 */
function oci_set_call_timeout ($connection, int $timeout): bool {}

/**
 * Changes password of Oracle's user
 * @link http://www.php.net/manual/en/function.oci-password-change.php
 * @param resource $connection 
 * @param string $username 
 * @param string $old_password 
 * @param string $new_password 
 * @return bool When database_name is provided, oci_password_change returns true on success,
 * or false on failure. When connection is provided, oci_password_change returns
 * the connection resource on success, or false on failure.
 */
function oci_password_change ($connection, string $username, string $old_password, string $new_password): bool {}

/**
 * {@inheritdoc}
 * @param mixed $connection
 * @param string $username
 * @param string $old_password
 * @param string $new_password
 * @deprecated 
 */
function ocipasswordchange ($connection = null, string $username, string $old_password, string $new_password) {}

/**
 * Allocates and returns a new cursor (statement handle)
 * @link http://www.php.net/manual/en/function.oci-new-cursor.php
 * @param resource $connection 
 * @return resource|false Returns a new statement handle, or false on error.
 */
function oci_new_cursor ($connection) {}

/**
 * {@inheritdoc}
 * @param mixed $connection
 * @deprecated 
 */
function ocinewcursor ($connection = null) {}

/**
 * Returns field's value from the fetched row
 * @link http://www.php.net/manual/en/function.oci-result.php
 * @param resource $statement 
 * @param string|int $column 
 * @return mixed Returns everything as strings except for abstract types (ROWIDs, LOBs and
 * FILEs). Returns false on error.
 */
function oci_result ($statement, string|int $column): mixed {}

/**
 * {@inheritdoc}
 * @param mixed $statement
 * @param string|int $column
 * @deprecated 
 */
function ociresult ($statement = null, string|int $column): mixed {}

/**
 * Returns the Oracle client library version
 * @link http://www.php.net/manual/en/function.oci-client-version.php
 * @return string Returns the version number as a string.
 */
function oci_client_version (): string {}

/**
 * Returns the Oracle Database version
 * @link http://www.php.net/manual/en/function.oci-server-version.php
 * @param resource $connection 
 * @return string|false Returns the version information as a string or false on error.
 */
function oci_server_version ($connection): string|false {}

/**
 * {@inheritdoc}
 * @param mixed $connection
 * @deprecated 
 */
function ociserverversion ($connection = null): string|false {}

/**
 * Returns the type of a statement
 * @link http://www.php.net/manual/en/function.oci-statement-type.php
 * @param resource $statement 
 * @return string|false Returns the type of statement as one of the
 * following strings.
 * <table>
 * Statement type
 * <table>
 * <tr valign="top">
 * <td>Return String</td>
 * <td>Notes</td>
 * </tr>
 * <tr valign="top">
 * <td>ALTER</td>
 * </tr>
 * <tr valign="top">
 * <td>BEGIN</td>
 * </tr>
 * <tr valign="top">
 * <td>CALL</td>
 * <td>Introduced in PHP 5.2.1 (PECL OCI8 1.2.3)</td>
 * </tr>
 * <tr valign="top">
 * <td>CREATE</td>
 * </tr>
 * <tr valign="top">
 * <td>DECLARE</td>
 * </tr>
 * <tr valign="top">
 * <td>DELETE</td>
 * </tr>
 * <tr valign="top">
 * <td>DROP</td>
 * </tr>
 * <tr valign="top">
 * <td>INSERT</td>
 * </tr>
 * <tr valign="top">
 * <td>SELECT</td>
 * </tr>
 * <tr valign="top">
 * <td>UPDATE</td>
 * </tr>
 * <tr valign="top">
 * <td>UNKNOWN</td>
 * </tr>
 * </table>
 * </table>
 * <p>Returns false on error.</p>
 */
function oci_statement_type ($statement): string|false {}

/**
 * {@inheritdoc}
 * @param mixed $statement
 * @deprecated 
 */
function ocistatementtype ($statement = null): string|false {}

/**
 * Returns number of rows affected during statement execution
 * @link http://www.php.net/manual/en/function.oci-num-rows.php
 * @param resource $statement 
 * @return int|false Returns the number of rows affected as an integer, or false on failure
 */
function oci_num_rows ($statement): int|false {}

/**
 * {@inheritdoc}
 * @param mixed $statement
 * @deprecated 
 */
function ocirowcount ($statement = null): int|false {}

/**
 * {@inheritdoc}
 * @param OCICollection $collection
 */
function oci_free_collection (OCICollection $collection): bool {}

/**
 * {@inheritdoc}
 * @param OCICollection $collection
 * @deprecated 
 */
function ocifreecollection (OCICollection $collection): bool {}

/**
 * {@inheritdoc}
 * @param OCICollection $collection
 * @param string $value
 */
function oci_collection_append (OCICollection $collection, string $value): bool {}

/**
 * {@inheritdoc}
 * @param OCICollection $collection
 * @param string $value
 * @deprecated 
 */
function ocicollappend (OCICollection $collection, string $value): bool {}

/**
 * {@inheritdoc}
 * @param OCICollection $collection
 * @param int $index
 */
function oci_collection_element_get (OCICollection $collection, int $index): string|float|false|null {}

/**
 * {@inheritdoc}
 * @param OCICollection $collection
 * @param int $index
 * @deprecated 
 */
function ocicollgetelem (OCICollection $collection, int $index): string|float|false|null {}

/**
 * {@inheritdoc}
 * @param OCICollection $to
 * @param OCICollection $from
 */
function oci_collection_assign (OCICollection $to, OCICollection $from): bool {}

/**
 * {@inheritdoc}
 * @param OCICollection $collection
 * @param int $index
 * @param string $value
 */
function oci_collection_element_assign (OCICollection $collection, int $index, string $value): bool {}

/**
 * {@inheritdoc}
 * @param OCICollection $collection
 * @param int $index
 * @param string $value
 * @deprecated 
 */
function ocicollassignelem (OCICollection $collection, int $index, string $value): bool {}

/**
 * {@inheritdoc}
 * @param OCICollection $collection
 */
function oci_collection_size (OCICollection $collection): int|false {}

/**
 * {@inheritdoc}
 * @param OCICollection $collection
 * @deprecated 
 */
function ocicollsize (OCICollection $collection): int|false {}

/**
 * {@inheritdoc}
 * @param OCICollection $collection
 */
function oci_collection_max (OCICollection $collection): int|false {}

/**
 * {@inheritdoc}
 * @param OCICollection $collection
 * @deprecated 
 */
function ocicollmax (OCICollection $collection): int|false {}

/**
 * {@inheritdoc}
 * @param OCICollection $collection
 * @param int $num
 */
function oci_collection_trim (OCICollection $collection, int $num): bool {}

/**
 * {@inheritdoc}
 * @param OCICollection $collection
 * @param int $num
 * @deprecated 
 */
function ocicolltrim (OCICollection $collection, int $num): bool {}

/**
 * Allocates new collection object
 * @link http://www.php.net/manual/en/function.oci-new-collection.php
 * @param resource $connection 
 * @param string $type_name 
 * @param string|null $schema [optional] 
 * @return OCICollection|false Returns a new OCICollection object or false on
 * error.
 */
function oci_new_collection ($connection, string $type_name, ?string $schema = null): OCICollection|false {}

/**
 * {@inheritdoc}
 * @param mixed $connection
 * @param string $type_name
 * @param string|null $schema [optional]
 * @deprecated 
 */
function ocinewcollection ($connection = null, string $type_name, ?string $schema = NULL): OCICollection|false {}

/**
 * Register a user-defined callback function for Oracle Database TAF
 * @link http://www.php.net/manual/en/function.oci-register-taf-callback.php
 * @param resource $connection 
 * @param callable|null $callback 
 * @return bool Returns true on success or false on failure.
 */
function oci_register_taf_callback ($connection, ?callable $callback): bool {}

/**
 * Unregister a user-defined callback function for Oracle Database TAF
 * @link http://www.php.net/manual/en/function.oci-unregister-taf-callback.php
 * @param resource $connection 
 * @return bool Returns true on success or false on failure.
 */
function oci_unregister_taf_callback ($connection): bool {}

define ('OCI_DEFAULT', 0);
define ('OCI_SYSOPER', 4);
define ('OCI_SYSDBA', 2);
define ('OCI_CRED_EXT', 2147483648);
define ('OCI_DESCRIBE_ONLY', 16);
define ('OCI_COMMIT_ON_SUCCESS', 32);
define ('OCI_NO_AUTO_COMMIT', 0);
define ('OCI_EXACT_FETCH', 2);
define ('OCI_SEEK_SET', 0);
define ('OCI_SEEK_CUR', 1);
define ('OCI_SEEK_END', 2);
define ('OCI_LOB_BUFFER_FREE', 1);
define ('SQLT_BFILEE', 114);
define ('SQLT_CFILEE', 115);
define ('SQLT_CLOB', 112);
define ('SQLT_BLOB', 113);
define ('SQLT_RDD', 104);
define ('SQLT_INT', 3);
define ('SQLT_NUM', 2);
define ('SQLT_RSET', 116);
define ('SQLT_AFC', 96);
define ('SQLT_CHR', 1);
define ('SQLT_VCS', 9);
define ('SQLT_AVC', 97);
define ('SQLT_STR', 5);
define ('SQLT_LVC', 94);
define ('SQLT_FLT', 4);
define ('SQLT_UIN', 68);
define ('SQLT_LNG', 8);
define ('SQLT_LBI', 24);
define ('SQLT_BIN', 23);
define ('SQLT_ODT', 156);
define ('SQLT_BDOUBLE', 22);
define ('SQLT_BFLOAT', 21);
define ('SQLT_BOL', 252);
define ('OCI_B_NTY', 108);
define ('SQLT_NTY', 108);
define ('OCI_SYSDATE', "SYSDATE");
define ('OCI_B_BFILE', 114);
define ('OCI_B_CFILEE', 115);
define ('OCI_B_CLOB', 112);
define ('OCI_B_BLOB', 113);
define ('OCI_B_ROWID', 104);
define ('OCI_B_CURSOR', 116);
define ('OCI_B_BIN', 23);
define ('OCI_B_INT', 3);
define ('OCI_B_NUM', 2);
define ('OCI_B_BOL', 252);
define ('OCI_FETCHSTATEMENT_BY_COLUMN', 16);
define ('OCI_FETCHSTATEMENT_BY_ROW', 32);
define ('OCI_ASSOC', 1);
define ('OCI_NUM', 2);
define ('OCI_BOTH', 3);
define ('OCI_RETURN_NULLS', 4);
define ('OCI_RETURN_LOBS', 8);
define ('OCI_DTYPE_FILE', 56);
define ('OCI_DTYPE_LOB', 50);
define ('OCI_DTYPE_ROWID', 54);
define ('OCI_D_FILE', 56);
define ('OCI_D_LOB', 50);
define ('OCI_D_ROWID', 54);
define ('OCI_TEMP_CLOB', 2);
define ('OCI_TEMP_BLOB', 1);
define ('OCI_FO_END', 1);
define ('OCI_FO_ABORT', 2);
define ('OCI_FO_REAUTH', 4);
define ('OCI_FO_BEGIN', 8);
define ('OCI_FO_ERROR', 16);
define ('OCI_FO_NONE', 1);
define ('OCI_FO_SESSION', 2);
define ('OCI_FO_SELECT', 4);
define ('OCI_FO_TXNAL', 8);
define ('OCI_FO_RETRY', 25410);

// End of oci8 v.3.3.0
