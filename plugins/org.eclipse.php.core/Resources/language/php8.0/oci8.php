<?php

// Start of oci8 v.3.0.1

/**
 * OCI8 LOB functionality for large binary (BLOB) and character (CLOB) objects.
 * <p>The OCI-Lob class was renamed to OCILob in PHP 8 and PECL OCI8 3.0.0 to align with PHP naming standards.</p>
 * @link http://www.php.net/manual/en/class.ocilob.php
 */
class OCILob  {

	/**
	 * Saves data to the large object
	 * @link http://www.php.net/manual/en/ocilob.save.php
	 * @param string $data The data to be saved.
	 * @param int $offset [optional] Can be used to indicate offset from the beginning of the large object.
	 * @return bool true on success or false on failure
	 */
	public function save (string $data, int $offset = null) {}

	/**
	 * Imports file data to the LOB
	 * @link http://www.php.net/manual/en/ocilob.import.php
	 * @param string $filename Path to the file.
	 * @return bool true on success or false on failure
	 */
	public function import (string $filename) {}

	/**
	 * Alias: OCILob::import
	 * @link http://www.php.net/manual/en/ocilob.savefile.php
	 * @param string $filename
	 */
	public function saveFile (string $filename) {}

	/**
	 * Returns large object's contents
	 * @link http://www.php.net/manual/en/ocilob.load.php
	 * @return mixed the contents of the object, or false on errors.
	 */
	public function load () {}

	/**
	 * Reads part of the large object
	 * @link http://www.php.net/manual/en/ocilob.read.php
	 * @param int $length The length of data to read, in bytes (BLOB) or characters (CLOB).
	 * Large values will be rounded down to 1 MB.
	 * @return mixed the contents as a string, or false on failure.
	 */
	public function read (int $length) {}

	/**
	 * Tests for end-of-file on a large object's descriptor
	 * @link http://www.php.net/manual/en/ocilob.eof.php
	 * @return bool true if internal pointer of large object is at the end of LOB.
	 * Otherwise returns false.
	 */
	public function eof () {}

	/**
	 * Returns the current position of internal pointer of large object
	 * @link http://www.php.net/manual/en/ocilob.tell.php
	 * @return mixed current position of a LOB's internal pointer or false if an
	 * error occurred.
	 */
	public function tell () {}

	/**
	 * Moves the internal pointer to the beginning of the large object
	 * @link http://www.php.net/manual/en/ocilob.rewind.php
	 * @return bool true on success or false on failure
	 */
	public function rewind () {}

	/**
	 * Sets the internal pointer of the large object
	 * @link http://www.php.net/manual/en/ocilob.seek.php
	 * @param int $offset Indicates the amount of bytes, on which internal pointer should be
	 * moved from the position, pointed by whence.
	 * @param int $whence [optional] <p>
	 * May be one of:
	 * <p>
	 * OCI_SEEK_SET - sets the position equal to 
	 * offset
	 * OCI_SEEK_CUR - adds offset 
	 * bytes to the current position 
	 * OCI_SEEK_END - adds offset
	 * bytes to the end of large object (use negative value to move to a position
	 * before the end of large object)
	 * </p>
	 * </p>
	 * @return bool true on success or false on failure
	 */
	public function seek (int $offset, int $whence = null) {}

	/**
	 * Returns size of large object
	 * @link http://www.php.net/manual/en/ocilob.size.php
	 * @return mixed length of large object value or false on failure.
	 * Empty objects have zero length.
	 */
	public function size () {}

	/**
	 * Writes data to the large object
	 * @link http://www.php.net/manual/en/ocilob.write.php
	 * @param string $data The data to write in the LOB.
	 * @param mixed $length [optional] If this parameter is an integer, writing will stop after
	 * length bytes have been written or the end of
	 * data is reached, whichever comes first.
	 * @return mixed the number of bytes written or false on failure.
	 */
	public function write (string $data, $length = null) {}

	/**
	 * Appends data from the large object to another large object
	 * @link http://www.php.net/manual/en/ocilob.append.php
	 * @param OCILob $from The copied LOB.
	 * @return bool true on success or false on failure
	 */
	public function append (OCILob $from) {}

	/**
	 * Truncates large object
	 * @link http://www.php.net/manual/en/ocilob.truncate.php
	 * @param int $length [optional] If provided, this method will truncate the LOB to
	 * length bytes. Otherwise, it will completely
	 * purge the LOB.
	 * @return bool true on success or false on failure
	 */
	public function truncate (int $length = null) {}

	/**
	 * Erases a specified portion of the internal LOB data
	 * @link http://www.php.net/manual/en/ocilob.erase.php
	 * @param mixed $offset [optional] 
	 * @param mixed $length [optional] 
	 * @return mixed the actual number of characters/bytes erased or false on failure.
	 */
	public function erase ($offset = null, $length = null) {}

	/**
	 * Flushes/writes buffer of the LOB to the server
	 * @link http://www.php.net/manual/en/ocilob.flush.php
	 * @param int $flag [optional] By default, resources are not freed, but using flag 
	 * OCI_LOB_BUFFER_FREE you can do it explicitly.
	 * Be sure you know what you're doing - next read/write operation to the
	 * same part of LOB will involve a round-trip to the server and initialize
	 * new buffer resources. It is recommended to use 
	 * OCI_LOB_BUFFER_FREE flag only when you are not
	 * going to work with the LOB anymore.
	 * @return bool true on success or false on failure
	 * <p>
	 * Returns false if buffering was not enabled or an error occurred.
	 * </p>
	 */
	public function flush (int $flag = null): bool {}

	/**
	 * Changes current state of buffering for the large object
	 * @link http://www.php.net/manual/en/ocilob.setbuffering.php
	 * @param bool $mode true for on and false for off.
	 * @return bool true on success or false on failure Repeated calls to this method with the same flag will
	 * return true.
	 */
	public function setBuffering (bool $mode) {}

	/**
	 * Returns current state of buffering for the large object
	 * @link http://www.php.net/manual/en/ocilob.getbuffering.php
	 * @return bool false if buffering for the large object is off and true if
	 * buffering is used.
	 */
	public function getBuffering () {}

	/**
	 * Alias: OCILob::export
	 * @link http://www.php.net/manual/en/ocilob.writetofile.php
	 * @param string $filename
	 * @param int|null $offset [optional]
	 * @param int|null $length [optional]
	 */
	public function writeToFile (string $filename, int|null $offset = null, int|null $length = null) {}

	/**
	 * Exports LOB's contents to a file
	 * @link http://www.php.net/manual/en/ocilob.export.php
	 * @param string $filename Path to the file.
	 * @param mixed $offset [optional] Indicates from where to start exporting.
	 * @param mixed $length [optional] Indicates the length of data to be exported.
	 * @return bool true on success or false on failure
	 */
	public function export (string $filename, $offset = null, $length = null) {}

	/**
	 * Writes a temporary large object
	 * @link http://www.php.net/manual/en/ocilob.writetemporary.php
	 * @param string $data The data to write.
	 * @param int $type [optional] <p>
	 * Can be one of the following:
	 * <p>
	 * OCI_TEMP_BLOB is used to create temporary BLOBs 
	 * OCI_TEMP_CLOB is used to create
	 * temporary CLOBs
	 * </p>
	 * </p>
	 * @return bool true on success or false on failure
	 */
	public function writeTemporary (string $data, int $type = null) {}

	/**
	 * Closes LOB descriptor
	 * @link http://www.php.net/manual/en/ocilob.close.php
	 * @return bool true on success or false on failure
	 */
	public function close () {}

	/**
	 * Frees resources associated with the LOB descriptor
	 * @link http://www.php.net/manual/en/ocilob.free.php
	 * @return bool true on success or false on failure
	 */
	public function free () {}

}

/**
 * OCI8 Collection functionality.
 * <p>The OCI-Collection class was renamed to OCICollection in PHP 8 OCI8 3.0.0 to align with PHP naming standards.</p>
 * @link http://www.php.net/manual/en/class.ocicollection.php
 */
class OCICollection  {

	/**
	 * Frees the resources associated with the collection object
	 * @link http://www.php.net/manual/en/ocicollection.free.php
	 * @return bool true on success or false on failure
	 */
	public function free () {}

	/**
	 * Appends element to the collection
	 * @link http://www.php.net/manual/en/ocicollection.append.php
	 * @param string $value The value to be added to the collection.
	 * @return bool true on success or false on failure
	 */
	public function append (string $value) {}

	/**
	 * Returns value of the element
	 * @link http://www.php.net/manual/en/ocicollection.getelem.php
	 * @param int $index The element index. First index is 0.
	 * @return mixed false if such element doesn't exist; null if element is null;
	 * string if element is column of a string datatype or number if element is
	 * numeric field.
	 */
	public function getElem (int $index) {}

	/**
	 * Assigns a value to the collection from another existing collection
	 * @link http://www.php.net/manual/en/ocicollection.assign.php
	 * @param OCICollection $from An instance of OCICollection.
	 * @return bool true on success or false on failure
	 */
	public function assign (OCICollection $from) {}

	/**
	 * Assigns a value to the element of the collection
	 * @link http://www.php.net/manual/en/ocicollection.assignelem.php
	 * @param int $index The element index. First index is 0.
	 * @param string $value Can be a string or a number.
	 * @return bool true on success or false on failure
	 */
	public function assignElem (int $index, string $value) {}

	/**
	 * Returns size of the collection
	 * @link http://www.php.net/manual/en/ocicollection.size.php
	 * @return mixed the number of elements in the collection or false on error.
	 */
	public function size () {}

	/**
	 * Returns the maximum number of elements in the collection
	 * @link http://www.php.net/manual/en/ocicollection.max.php
	 * @return mixed the maximum number as an integer, or false on errors.
	 * <p>
	 * If the returned value is 0, then the number of elements is not limited.
	 * </p>
	 */
	public function max () {}

	/**
	 * Trims elements from the end of the collection
	 * @link http://www.php.net/manual/en/ocicollection.trim.php
	 * @param int $num The number of elements to be trimmed.
	 * @return bool true on success or false on failure
	 */
	public function trim (int $num) {}

}

/**
 * Associates a PHP variable with a column for query fetches
 * @link http://www.php.net/manual/en/function.oci-define-by-name.php
 * @param resource $statement oci.arg.statement.id
 * @param string $column <p>
 * The column name used in the query.
 * </p>
 * <p>
 * Use uppercase for Oracle's default, non-case sensitive column
 * names. Use the exact column name case for case-sensitive
 * column names.
 * </p>
 * @param mixed $var The PHP variable that will contain the returned column value.
 * @param int $type [optional] <p>
 * The data type to be returned. Generally not needed. Note that
 * Oracle-style data conversions are not performed. For example,
 * SQLT_INT will be ignored and the returned
 * data type will still be SQLT_CHR.
 * </p>
 * <p>
 * You can optionally use oci_new_descriptor
 * to allocate LOB/ROWID/BFILE descriptors.
 * </p>
 * @return bool true on success or false on failure
 */
function oci_define_by_name ($statement, string $column, &$var, int $type = null): bool {}

/**
 * Alias: oci_define_by_name
 * @link http://www.php.net/manual/en/function.ocidefinebyname.php
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
 * @param resource $statement A valid OCI8 statement identifier.
 * @param string $param The colon-prefixed bind variable placeholder used in the
 * statement. The colon is optional
 * in param. Oracle does not use question
 * marks for placeholders.
 * @param mixed $var The PHP variable to be associated with param
 * @param int $max_length [optional] Sets the maximum length for the data. If you set it to -1, this
 * function will use the current length
 * of var to set the maximum
 * length. In this case the var must
 * exist and contain data
 * when oci_bind_by_name is called.
 * @param int $type [optional] <p>
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
 * <br>
 * <p>
 * SQLT_BFILEE or OCI_B_BFILE
 * - for BFILEs;
 * </p>
 * <br>
 * <p>
 * SQLT_CFILEE or OCI_B_CFILEE
 * - for CFILEs;
 * </p>
 * <br>
 * <p>
 * SQLT_CLOB or OCI_B_CLOB
 * - for CLOBs;
 * </p>
 * <br>
 * <p>
 * SQLT_BLOB or OCI_B_BLOB
 * - for BLOBs;
 * </p>
 * <br>
 * <p>
 * SQLT_RDD or OCI_B_ROWID
 * - for ROWIDs;
 * </p>
 * <br>
 * <p>
 * SQLT_NTY or OCI_B_NTY
 * - for named datatypes;
 * </p>
 * <br>
 * <p>
 * SQLT_INT or OCI_B_INT - for integers;
 * </p>
 * <br>
 * <p>
 * SQLT_CHR - for VARCHARs;
 * </p>
 * <br>
 * <p>
 * SQLT_BIN or OCI_B_BIN
 * - for RAW columns;
 * </p>
 * <br>
 * <p>
 * SQLT_LNG - for LONG columns;
 * </p>
 * <br>
 * <p>
 * SQLT_LBI - for LONG RAW columns;
 * </p>
 * <br>
 * <p>
 * SQLT_RSET - for cursors created
 * with oci_new_cursor;
 * </p>
 * <br>
 * <p>
 * SQLT_BOL or OCI_B_BOL
 * - for PL/SQL BOOLEANs (Requires OCI8 2.0.7 and Oracle Database 12c)
 * </p>
 * </p>
 * </p>
 * @return bool true on success or false on failure
 */
function oci_bind_by_name ($statement, string $param, &$var, int $max_length = null, int $type = null): bool {}

/**
 * Alias: oci_bind_by_name
 * @link http://www.php.net/manual/en/function.ocibindbyname.php
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
 * @param resource $statement A valid OCI statement identifier.
 * @param string $param The Oracle placeholder.
 * @param array $var An array.
 * @param int $max_array_length Sets the maximum length both for incoming and result arrays.
 * @param int $max_item_length [optional] Sets maximum length for array items. If not specified or equals to -1,
 * oci_bind_array_by_name will find the longest
 * element in the incoming array and will use it as the maximum length.
 * @param int $type [optional] <p>
 * Should be used to set the type of PL/SQL array items. See list of
 * available types below:
 * </p>
 * <p>
 * <p>
 * <br>
 * <p>
 * SQLT_NUM - for arrays of NUMBER.
 * </p>
 * <br>
 * <p>
 * SQLT_INT - for arrays of INTEGER (Note: INTEGER
 * it is actually a synonym for NUMBER(38), but
 * SQLT_NUM type won't work in this case even
 * though they are synonyms).
 * </p>
 * <br>
 * <p>
 * SQLT_FLT - for arrays of FLOAT.
 * </p>
 * <br>
 * <p>
 * SQLT_AFC - for arrays of CHAR.
 * </p>
 * <br>
 * <p>
 * SQLT_CHR - for arrays of VARCHAR2.
 * </p>
 * <br>
 * <p>
 * SQLT_VCS - for arrays of VARCHAR.
 * </p>
 * <br>
 * <p>
 * SQLT_AVC - for arrays of CHARZ.
 * </p>
 * <br>
 * <p>
 * SQLT_STR - for arrays of STRING.
 * </p>
 * <br>
 * <p>
 * SQLT_LVC - for arrays of LONG VARCHAR.
 * </p>
 * <br>
 * <p>
 * SQLT_ODT - for arrays of DATE.
 * </p>
 * </p>
 * </p>
 * @return bool true on success or false on failure
 */
function oci_bind_array_by_name ($statement, string $param, array &$var, int $max_array_length, int $max_item_length = null, int $type = null): bool {}

/**
 * Frees a descriptor
 * @link http://www.php.net/manual/en/function.oci-free-descriptor.php
 * @param OCILob $lob 
 * @return bool true on success or false on failure
 */
function oci_free_descriptor (OCILob $lob): bool {}

/**
 * Alias: OCILob::free
 * @link http://www.php.net/manual/en/function.ocifreedesc.php
 * @param OCILob $lob
 * @deprecated 
 */
function ocifreedesc (OCILob $lob): bool {}

/**
 * @param OCILob $lob
 * @param string $data
 * @param int $offset [optional]
 */
function oci_lob_save (OCILob $lob, string $data, int $offset = 0): bool {}

/**
 * Alias: OCILob::save
 * @link http://www.php.net/manual/en/function.ocisavelob.php
 * @param OCILob $lob
 * @param string $data
 * @param int $offset [optional]
 * @deprecated 
 */
function ocisavelob (OCILob $lob, string $data, int $offset = 0): bool {}

/**
 * @param OCILob $lob
 * @param string $filename
 */
function oci_lob_import (OCILob $lob, string $filename): bool {}

/**
 * Alias: OCILob::import
 * @link http://www.php.net/manual/en/function.ocisavelobfile.php
 * @param OCILob $lob
 * @param string $filename
 * @deprecated 
 */
function ocisavelobfile (OCILob $lob, string $filename): bool {}

/**
 * @param OCILob $lob
 */
function oci_lob_load (OCILob $lob): string|false {}

/**
 * Alias: OCILob::load
 * @link http://www.php.net/manual/en/function.ociloadlob.php
 * @param OCILob $lob
 * @deprecated 
 */
function ociloadlob (OCILob $lob): string|false {}

/**
 * @param OCILob $lob
 * @param int $length
 */
function oci_lob_read (OCILob $lob, int $length): string|false {}

/**
 * @param OCILob $lob
 */
function oci_lob_eof (OCILob $lob): bool {}

/**
 * @param OCILob $lob
 */
function oci_lob_tell (OCILob $lob): int|false {}

/**
 * @param OCILob $lob
 */
function oci_lob_rewind (OCILob $lob): bool {}

/**
 * @param OCILob $lob
 * @param int $offset
 * @param int $whence [optional]
 */
function oci_lob_seek (OCILob $lob, int $offset, int $whence = 0): bool {}

/**
 * @param OCILob $lob
 */
function oci_lob_size (OCILob $lob): int|false {}

/**
 * @param OCILob $lob
 * @param string $data
 * @param int|null $length [optional]
 */
function oci_lob_write (OCILob $lob, string $data, int|null $length = null): int|false {}

/**
 * @param OCILob $to
 * @param OCILob $from
 */
function oci_lob_append (OCILob $to, OCILob $from): bool {}

/**
 * @param OCILob $lob
 * @param int $length [optional]
 */
function oci_lob_truncate (OCILob $lob, int $length = 0): bool {}

/**
 * @param OCILob $lob
 * @param int|null $offset [optional]
 * @param int|null $length [optional]
 */
function oci_lob_erase (OCILob $lob, int|null $offset = null, int|null $length = null): int|false {}

/**
 * @param OCILob $lob
 * @param int $flag [optional]
 */
function oci_lob_flush (OCILob $lob, int $flag = 0): bool {}

/**
 * @param OCILob $lob
 * @param bool $mode
 */
function ocisetbufferinglob (OCILob $lob, bool $mode): bool {}

/**
 * @param OCILob $lob
 */
function ocigetbufferinglob (OCILob $lob): bool {}

/**
 * Copies large object
 * @link http://www.php.net/manual/en/function.oci-lob-copy.php
 * @param OCILob $to The destination LOB.
 * @param OCILob $from The copied LOB.
 * @param mixed $length [optional] Indicates the length of data to be copied.
 * @return bool true on success or false on failure
 */
function oci_lob_copy (OCILob $to, OCILob $from, $length = null): bool {}

/**
 * Compares two LOB/FILE locators for equality
 * @link http://www.php.net/manual/en/function.oci-lob-is-equal.php
 * @param OCILob $lob1 A LOB identifier.
 * @param OCILob $lob2 A LOB identifier.
 * @return bool true if these objects are equal, false otherwise.
 */
function oci_lob_is_equal (OCILob $lob1, OCILob $lob2): bool {}

/**
 * @param OCILob $lob
 * @param string $filename
 * @param int|null $offset [optional]
 * @param int|null $length [optional]
 */
function oci_lob_export (OCILob $lob, string $filename, int|null $offset = null, int|null $length = null): bool {}

/**
 * Alias: OCILob::export
 * @link http://www.php.net/manual/en/function.ociwritelobtofile.php
 * @param OCILob $lob
 * @param string $filename
 * @param int|null $offset [optional]
 * @param int|null $length [optional]
 * @deprecated 
 */
function ociwritelobtofile (OCILob $lob, string $filename, int|null $offset = null, int|null $length = null): bool {}

/**
 * Initializes a new empty LOB or FILE descriptor
 * @link http://www.php.net/manual/en/function.oci-new-descriptor.php
 * @param resource $connection An Oracle connection identifier, returned by 
 * oci_connect or oci_pconnect.
 * @param int $type [optional] Valid values for type are: 
 * OCI_DTYPE_FILE, OCI_DTYPE_LOB and
 * OCI_DTYPE_ROWID.
 * @return mixed a new LOB or FILE descriptor on success, or null on failure.
 */
function oci_new_descriptor ($connection, int $type = null): ?OCILob {}

/**
 * Alias: oci_new_descriptor
 * @link http://www.php.net/manual/en/function.ocinewdescriptor.php
 * @param mixed $connection
 * @param int $type [optional]
 * @deprecated 
 */
function ocinewdescriptor ($connection = null, int $type = 50): ?OCILob {}

/**
 * Rolls back the outstanding database transaction
 * @link http://www.php.net/manual/en/function.oci-rollback.php
 * @param resource $connection An Oracle connection identifier, returned by
 * oci_connect, oci_pconnect
 * or oci_new_connect.
 * @return bool true on success or false on failure
 */
function oci_rollback ($connection): bool {}

/**
 * Alias: oci_rollback
 * @link http://www.php.net/manual/en/function.ocirollback.php
 * @param mixed $connection
 * @deprecated 
 */
function ocirollback ($connection = null): bool {}

/**
 * Commits the outstanding database transaction
 * @link http://www.php.net/manual/en/function.oci-commit.php
 * @param resource $connection An Oracle connection identifier, returned by
 * oci_connect, oci_pconnect, or oci_new_connect.
 * @return bool true on success or false on failure
 */
function oci_commit ($connection): bool {}

/**
 * Alias: oci_commit
 * @link http://www.php.net/manual/en/function.ocicommit.php
 * @param mixed $connection
 * @deprecated 
 */
function ocicommit ($connection = null): bool {}

/**
 * Returns the name of a field from the statement
 * @link http://www.php.net/manual/en/function.oci-field-name.php
 * @param resource $statement A valid OCI statement identifier.
 * @param mixed $column Can be the field's index (1-based) or name.
 * @return mixed the name as a string, or false on failure
 */
function oci_field_name ($statement, $column): string|false {}

/**
 * Alias: oci_field_name
 * @link http://www.php.net/manual/en/function.ocicolumnname.php
 * @param mixed $statement
 * @param string|int $column
 * @deprecated 
 */
function ocicolumnname ($statement = null, string|int $column): string|false {}

/**
 * Returns field's size
 * @link http://www.php.net/manual/en/function.oci-field-size.php
 * @param resource $statement A valid OCI statement identifier.
 * @param mixed $column Can be the field's index (1-based) or name.
 * @return mixed the size of a column in bytes, or false on failure
 */
function oci_field_size ($statement, $column): int|false {}

/**
 * Alias: oci_field_size
 * @link http://www.php.net/manual/en/function.ocicolumnsize.php
 * @param mixed $statement
 * @param string|int $column
 * @deprecated 
 */
function ocicolumnsize ($statement = null, string|int $column): int|false {}

/**
 * Tell the scale of the field
 * @link http://www.php.net/manual/en/function.oci-field-scale.php
 * @param resource $statement A valid OCI statement identifier.
 * @param mixed $column Can be the field's index (1-based) or name.
 * @return mixed the scale as an integer, or false on failure
 */
function oci_field_scale ($statement, $column): int|false {}

/**
 * Alias: oci_field_scale
 * @link http://www.php.net/manual/en/function.ocicolumnscale.php
 * @param mixed $statement
 * @param string|int $column
 * @deprecated 
 */
function ocicolumnscale ($statement = null, string|int $column): int|false {}

/**
 * Tell the precision of a field
 * @link http://www.php.net/manual/en/function.oci-field-precision.php
 * @param resource $statement A valid OCI statement identifier.
 * @param mixed $column Can be the field's index (1-based) or name.
 * @return mixed the precision as an integer, or false on failure
 */
function oci_field_precision ($statement, $column): int|false {}

/**
 * Alias: oci_field_precision
 * @link http://www.php.net/manual/en/function.ocicolumnprecision.php
 * @param mixed $statement
 * @param string|int $column
 * @deprecated 
 */
function ocicolumnprecision ($statement = null, string|int $column): int|false {}

/**
 * Returns a field's data type name
 * @link http://www.php.net/manual/en/function.oci-field-type.php
 * @param resource $statement A valid OCI statement identifier.
 * @param mixed $column Can be the field's index (1-based) or name.
 * @return mixed the field data type as a string or an integer, or false on failure
 */
function oci_field_type ($statement, $column): string|int|false {}

/**
 * Alias: oci_field_type
 * @link http://www.php.net/manual/en/function.ocicolumntype.php
 * @param mixed $statement
 * @param string|int $column
 * @deprecated 
 */
function ocicolumntype ($statement = null, string|int $column): string|int|false {}

/**
 * Tell the raw Oracle data type of the field
 * @link http://www.php.net/manual/en/function.oci-field-type-raw.php
 * @param resource $statement A valid OCI statement identifier.
 * @param mixed $column Can be the field's index (1-based) or name.
 * @return mixed Oracle's raw data type as a number, or false on failure
 */
function oci_field_type_raw ($statement, $column): int|false {}

/**
 * Alias: oci_field_type_raw
 * @link http://www.php.net/manual/en/function.ocicolumntyperaw.php
 * @param mixed $statement
 * @param string|int $column
 * @deprecated 
 */
function ocicolumntyperaw ($statement = null, string|int $column): int|false {}

/**
 * Checks if a field in the currently fetched row is null
 * @link http://www.php.net/manual/en/function.oci-field-is-null.php
 * @param resource $statement A valid OCI statement identifier.
 * @param mixed $column Can be the field's index (1-based) or name.
 * @return bool true if column is null, false otherwise.
 */
function oci_field_is_null ($statement, $column): bool {}

/**
 * Alias: oci_field_is_null
 * @link http://www.php.net/manual/en/function.ocicolumnisnull.php
 * @param mixed $statement
 * @param string|int $column
 * @deprecated 
 */
function ocicolumnisnull ($statement = null, string|int $column): bool {}

/**
 * Executes a statement
 * @link http://www.php.net/manual/en/function.oci-execute.php
 * @param resource $statement A valid OCI statement identifier.
 * @param int $mode [optional] <p>
 * An optional second parameter can be one of the following constants:
 * <table>
 * Execution Modes
 * <table>
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
 * use OCI_DEFAULT which is equivalent
 * to OCI_NO_AUTO_COMMIT.</td>
 * </tr>
 * </table>
 * </table>
 * </p>
 * <p>
 * Using OCI_NO_AUTO_COMMIT mode starts or continues a
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
 * @return bool true on success or false on failure
 */
function oci_execute ($statement, int $mode = null): bool {}

/**
 * Alias: oci_execute
 * @link http://www.php.net/manual/en/function.ociexecute.php
 * @param mixed $statement
 * @param int $mode [optional]
 * @deprecated 
 */
function ociexecute ($statement = null, int $mode = 32): bool {}

/**
 * Cancels reading from cursor
 * @link http://www.php.net/manual/en/function.oci-cancel.php
 * @param resource $statement An OCI statement.
 * @return bool true on success or false on failure
 */
function oci_cancel ($statement): bool {}

/**
 * Alias: oci_cancel
 * @link http://www.php.net/manual/en/function.ocicancel.php
 * @param mixed $statement
 * @deprecated 
 */
function ocicancel ($statement = null): bool {}

/**
 * Fetches the next row from a query into internal buffers
 * @link http://www.php.net/manual/en/function.oci-fetch.php
 * @param resource $statement oci.arg.statement.id
 * @return bool true on success or false if there are no more rows in the
 * statement.
 */
function oci_fetch ($statement): bool {}

/**
 * Alias: oci_fetch
 * @link http://www.php.net/manual/en/function.ocifetch.php
 * @param mixed $statement
 * @deprecated 
 */
function ocifetch ($statement = null): bool {}

/**
 * Obsolete variant of oci_fetch_array, oci_fetch_object,
 * oci_fetch_assoc and
 * oci_fetch_row
 * @link http://www.php.net/manual/en/function.ocifetchinto.php
 * @param mixed $statement
 * @param mixed $result
 * @param int $mode [optional]
 * @deprecated 
 */
function ocifetchinto ($statement = null, &$result = null, int $mode = 2): int|false {}

/**
 * Fetches multiple rows from a query into a two-dimensional array
 * @link http://www.php.net/manual/en/function.oci-fetch-all.php
 * @param resource $statement oci.arg.statement.id
 * @param array $output <p>
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
 * @param int $offset [optional] The number of initial rows to discard when fetching the
 * result. The default value is 0, so the first row onwards is
 * returned.
 * @param int $limit [optional] The number of rows to return. The default is -1 meaning return
 * all the rows from offset + 1 onwards.
 * @param int $flags [optional] <p>
 * Parameter flags indicates the array
 * structure and whether associative arrays should be used.
 * <table>
 * oci_fetch_all Array Structure Modes
 * <table>
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
 * </table>
 * </p>
 * <p>
 * Arrays can be indexed either by column heading or numerically.
 * Only one index mode will be returned.
 * <table>
 * oci_fetch_all Array Index Modes
 * <table>
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
 * may be 0 or more.
 */
function oci_fetch_all ($statement, array &$output, int $offset = null, int $limit = null, int $flags = null): int {}

/**
 * Alias: oci_fetch_all
 * @link http://www.php.net/manual/en/function.ocifetchstatement.php
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
 * @param resource $statement oci.arg.statement.id
 * @param int $mode [optional] 
 * @return mixed an object. Each attribute of the object corresponds to a
 * column of the row. If there are no more rows in
 * the statement then false is returned.
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
 * the appropriate case for attribute access.
 * </p>
 * <p>
 * Attribute values will be null for any NULL
 * data fields.
 * </p>
 */
function oci_fetch_object ($statement, int $mode = null): stdClass|false {}

/**
 * Returns the next row from a query as a numeric array
 * @link http://www.php.net/manual/en/function.oci-fetch-row.php
 * @param resource $statement oci.arg.statement.id
 * @return mixed a numerically indexed array. If there are no more rows in
 * the statement then false is returned.
 */
function oci_fetch_row ($statement): array|false {}

/**
 * Returns the next row from a query as an associative array
 * @link http://www.php.net/manual/en/function.oci-fetch-assoc.php
 * @param resource $statement oci.arg.statement.id
 * @return mixed an associative array. If there are no more rows in
 * the statement then false is returned.
 */
function oci_fetch_assoc ($statement): array|false {}

/**
 * Returns the next row from a query as an associative or numeric array
 * @link http://www.php.net/manual/en/function.oci-fetch-array.php
 * @param resource $statement oci.arg.statement.id
 * <p>
 * Can also be a statement identifier returned by oci_get_implicit_resultset.
 * </p>
 * @param int $mode [optional] <p>
 * An optional second parameter can be any combination of the following
 * constants:
 * <table>
 * oci_fetch_array Modes
 * <table>
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
 * <td>Creates elements for null fields. The element
 * values will be a PHP null.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>OCI_RETURN_LOBS</td>
 * <td>Returns the contents of LOBs instead of the LOB
 * descriptors.</td>
 * </tr>
 * </table>
 * </table>
 * </p>
 * <p>
 * The default mode is OCI_BOTH.
 * </p>
 * <p>
 * Use the addition operator &quot;+&quot; to specify more than
 * one mode at a time.
 * </p>
 * @return mixed an array with associative and/or numeric indices. If there
 * are no more rows in the statement then
 * false is returned.
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
 * </p>
 * <p>
 * The table name is not included in the array index. If your query
 * contains two different columns with the same name,
 * use OCI_NUM or add a column alias to the query
 * to ensure name uniqueness, see example #7. Otherwise only one
 * column will be returned via PHP.
 * </p>
 */
function oci_fetch_array ($statement, int $mode = null): array|false {}

/**
 * Frees all resources associated with statement or cursor
 * @link http://www.php.net/manual/en/function.oci-free-statement.php
 * @param resource $statement A valid OCI statement identifier.
 * @return bool true on success or false on failure
 */
function oci_free_statement ($statement): bool {}

/**
 * Alias: oci_free_statement
 * @link http://www.php.net/manual/en/function.ocifreestatement.php
 * @param mixed $statement
 * @deprecated 
 */
function ocifreestatement ($statement = null): bool {}

/**
 * @param mixed $statement
 */
function oci_free_cursor ($statement = null): bool {}

/**
 * Alias: oci_free_statement
 * @link http://www.php.net/manual/en/function.ocifreecursor.php
 * @param mixed $statement
 * @deprecated 
 */
function ocifreecursor ($statement = null): bool {}

/**
 * Closes an Oracle connection
 * @link http://www.php.net/manual/en/function.oci-close.php
 * @param resource $connection An Oracle connection identifier returned by 
 * oci_connect, oci_pconnect,
 * or oci_new_connect.
 * @return mixed null when oci8.old_oci_close_semantics is enabled,
 * or true otherwise.
 */
function oci_close ($connection): ?bool {}

/**
 * Alias: oci_close
 * @link http://www.php.net/manual/en/function.ocilogoff.php
 * @param mixed $connection
 * @deprecated 
 */
function ocilogoff ($connection = null): ?bool {}

/**
 * Connect to the Oracle server using a unique connection
 * @link http://www.php.net/manual/en/function.oci-new-connect.php
 * @param string $username The Oracle user name.
 * @param string $password The password for username.
 * @param mixed $connection_string [optional] oci.db
 * @param string $encoding [optional] oci.charset
 * @param int $session_mode [optional] oci.sessionmode
 * @return mixed a connection identifier or false on error.
 */
function oci_new_connect (string $username, string $password, $connection_string = null, string $encoding = null, int $session_mode = null) {}

/**
 * Alias: oci_new_connect
 * @link http://www.php.net/manual/en/function.ocinlogon.php
 * @param string $username
 * @param string $password
 * @param string|null $connection_string [optional]
 * @param string $encoding [optional]
 * @param int $session_mode [optional]
 * @deprecated 
 */
function ocinlogon (string $username, string $password, string|null $connection_string = null, string $encoding = '', int $session_mode = 0) {}

/**
 * Connect to an Oracle database
 * @link http://www.php.net/manual/en/function.oci-connect.php
 * @param string $username The Oracle user name.
 * @param string $password The password for username.
 * @param mixed $connection_string [optional] oci.db
 * @param string $encoding [optional] oci.charset
 * @param int $session_mode [optional] oci.sessionmode
 * @return mixed a connection identifier or false on error.
 */
function oci_connect (string $username, string $password, $connection_string = null, string $encoding = null, int $session_mode = null) {}

/**
 * Alias: oci_connect
 * @link http://www.php.net/manual/en/function.ocilogon.php
 * @param string $username
 * @param string $password
 * @param string|null $connection_string [optional]
 * @param string $encoding [optional]
 * @param int $session_mode [optional]
 * @deprecated 
 */
function ocilogon (string $username, string $password, string|null $connection_string = null, string $encoding = '', int $session_mode = 0) {}

/**
 * Connect to an Oracle database using a persistent connection
 * @link http://www.php.net/manual/en/function.oci-pconnect.php
 * @param string $username The Oracle user name.
 * @param string $password The password for username.
 * @param mixed $connection_string [optional] oci.db
 * @param string $encoding [optional] oci.charset
 * @param int $session_mode [optional] oci.sessionmode
 * @return mixed a connection identifier or false on error.
 */
function oci_pconnect (string $username, string $password, $connection_string = null, string $encoding = null, int $session_mode = null) {}

/**
 * Alias: oci_pconnect
 * @link http://www.php.net/manual/en/function.ociplogon.php
 * @param string $username
 * @param string $password
 * @param string|null $connection_string [optional]
 * @param string $encoding [optional]
 * @param int $session_mode [optional]
 * @deprecated 
 */
function ociplogon (string $username, string $password, string|null $connection_string = null, string $encoding = '', int $session_mode = 0) {}

/**
 * Returns the last error found
 * @link http://www.php.net/manual/en/function.oci-error.php
 * @param mixed $connection_or_statement [optional] For most errors, connection_or_statement is the
 * resource handle that was passed to the failing function call.
 * For connection errors with oci_connect,
 * oci_new_connect or 
 * oci_pconnect null should be passed.
 * @return mixed If no error is found, oci_error returns
 * false. Otherwise, oci_error returns the
 * error information as an associative array.
 * <p>
 * <table>
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
 * </table>
 * </p>
 */
function oci_error ($connection_or_statement = null): array|false {}

/**
 * Alias: oci_error
 * @link http://www.php.net/manual/en/function.ocierror.php
 * @param mixed $connection_or_statement [optional]
 * @deprecated 
 */
function ocierror ($connection_or_statement = null): array|false {}

/**
 * Returns the number of result columns in a statement
 * @link http://www.php.net/manual/en/function.oci-num-fields.php
 * @param resource $statement A valid OCI statement identifier.
 * @return int the number of columns as an int.
 */
function oci_num_fields ($statement): int {}

/**
 * Alias: oci_num_fields
 * @link http://www.php.net/manual/en/function.ocinumcols.php
 * @param mixed $statement
 * @deprecated 
 */
function ocinumcols ($statement = null): int {}

/**
 * Prepares an Oracle statement for execution
 * @link http://www.php.net/manual/en/function.oci-parse.php
 * @param resource $connection An Oracle connection identifier, returned by 
 * oci_connect, oci_pconnect, or oci_new_connect.
 * @param string $sql <p>
 * The SQL or PL/SQL statement.
 * </p>
 * <p>
 * SQL statements should not end with a
 * semi-colon (&quot;;&quot;). PL/SQL
 * statements should end with a semi-colon
 * (&quot;;&quot;).
 * </p>
 * @return mixed a statement handle on success, or false on error.
 */
function oci_parse ($connection, string $sql) {}

/**
 * Alias: oci_parse
 * @link http://www.php.net/manual/en/function.ociparse.php
 * @param mixed $connection
 * @param string $sql
 * @deprecated 
 */
function ociparse ($connection = null, string $sql) {}

/**
 * Returns the next child statement resource from a parent statement resource that has Oracle Database Implicit Result Sets
 * @link http://www.php.net/manual/en/function.oci-get-implicit-resultset.php
 * @param resource $statement A valid OCI8 statement identifier created
 * by oci_parse and executed
 * by oci_execute. The statement
 * identifier may or may not be associated with a SQL statement
 * that returns Implicit Result Sets.
 * @return mixed a statement handle for the next child statement available
 * on statement. Returns false when child
 * statements do not exist, or all child statements have been returned
 * by previous calls
 * to oci_get_implicit_resultset.
 */
function oci_get_implicit_resultset ($statement) {}

/**
 * Sets number of rows to be prefetched by queries
 * @link http://www.php.net/manual/en/function.oci-set-prefetch.php
 * @param resource $statement oci.arg.statement.id
 * @param int $rows The number of rows to be prefetched, &gt;= 0
 * @return bool true on success or false on failure
 */
function oci_set_prefetch ($statement, int $rows): bool {}

/**
 * Alias: oci_set_prefetch
 * @link http://www.php.net/manual/en/function.ocisetprefetch.php
 * @param mixed $statement
 * @param int $rows
 * @deprecated 
 */
function ocisetprefetch ($statement = null, int $rows): bool {}

/**
 * Sets the client identifier
 * @link http://www.php.net/manual/en/function.oci-set-client-identifier.php
 * @param resource $connection oci.parameter.connection
 * @param string $client_id User chosen string up to 64 bytes long.
 * @return bool true on success or false on failure
 */
function oci_set_client_identifier ($connection, string $client_id): bool {}

/**
 * Sets the database edition
 * @link http://www.php.net/manual/en/function.oci-set-edition.php
 * @param string $edition Oracle Database edition name previously created with the SQL
 * "CREATE EDITION" command.
 * @return bool true on success or false on failure
 */
function oci_set_edition (string $edition): bool {}

/**
 * Sets the module name
 * @link http://www.php.net/manual/en/function.oci-set-module-name.php
 * @param resource $connection oci.parameter.connection
 * @param string $name User chosen string up to 48 bytes long.
 * @return bool true on success or false on failure
 */
function oci_set_module_name ($connection, string $name): bool {}

/**
 * Sets the action name
 * @link http://www.php.net/manual/en/function.oci-set-action.php
 * @param resource $connection oci.parameter.connection
 * @param string $action User chosen string up to 32 bytes long.
 * @return bool true on success or false on failure
 */
function oci_set_action ($connection, string $action): bool {}

/**
 * Sets the client information
 * @link http://www.php.net/manual/en/function.oci-set-client-info.php
 * @param resource $connection oci.parameter.connection
 * @param string $client_info User chosen string up to 64 bytes long.
 * @return bool true on success or false on failure
 */
function oci_set_client_info ($connection, string $client_info): bool {}

/**
 * Sets the database operation
 * @link http://www.php.net/manual/en/function.oci-set-db-operation.php
 * @param resource $connection oci.parameter.connection
 * @param string $action User chosen string.
 * @return bool true on success or false on failure
 */
function oci_set_db_operation ($connection, string $action): bool {}

/**
 * Sets a millisecond timeout for database calls
 * @link http://www.php.net/manual/en/function.oci-set-call-timout.php
 * @param resource $connection oci.parameter.connection
 * @param int $timeout The maximum time in milliseconds that any single round-trip between PHP and Oracle Database may take.
 * @return bool true on success or false on failure
 */
function oci_set_call_timeout ($connection, int $timeout): bool {}

/**
 * Changes password of Oracle's user
 * @link http://www.php.net/manual/en/function.oci-password-change.php
 * @param resource $connection An Oracle connection identifier, returned by 
 * oci_connect or oci_pconnect.
 * @param string $username The Oracle user name.
 * @param string $old_password The old password.
 * @param string $new_password The new password to be set.
 * @return bool When database_name is provided, oci_password_change returns true on success,
 * or false on failure. When connection is provided, oci_password_change returns
 * the connection resource on success, or false on failure.
 */
function oci_password_change ($connection, string $username, string $old_password, string $new_password) {}

/**
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
 * @param resource $connection An Oracle connection identifier, returned by 
 * oci_connect or oci_pconnect.
 * @return mixed a new statement handle, or false on error.
 */
function oci_new_cursor ($connection) {}

/**
 * Alias: oci_new_cursor
 * @link http://www.php.net/manual/en/function.ocinewcursor.php
 * @param mixed $connection
 * @deprecated 
 */
function ocinewcursor ($connection = null) {}

/**
 * Returns field's value from the fetched row
 * @link http://www.php.net/manual/en/function.oci-result.php
 * @param resource $statement 
 * @param mixed $column Can be either use the column number (1-based) or the column name.
 * The case of the column name must be the case that Oracle meta data
 * describes the column as, which is uppercase for columns created
 * case insensitively.
 * @return mixed everything as strings except for abstract types (ROWIDs, LOBs and
 * FILEs). Returns false on error.
 */
function oci_result ($statement, $column): mixed {}

/**
 * Alias: oci_result
 * @link http://www.php.net/manual/en/function.ociresult.php
 * @param mixed $statement
 * @param string|int $column
 * @deprecated 
 */
function ociresult ($statement = null, string|int $column): mixed {}

/**
 * Returns the Oracle client library version
 * @link http://www.php.net/manual/en/function.oci-client-version.php
 * @return string the version number as a string.
 */
function oci_client_version (): string {}

/**
 * Returns the Oracle Database version
 * @link http://www.php.net/manual/en/function.oci-server-version.php
 * @param resource $connection 
 * @return mixed the version information as a string or false on error.
 */
function oci_server_version ($connection): string|false {}

/**
 * Alias: oci_server_version
 * @link http://www.php.net/manual/en/function.ociserverversion.php
 * @param mixed $connection
 * @deprecated 
 */
function ociserverversion ($connection = null): string|false {}

/**
 * Returns the type of a statement
 * @link http://www.php.net/manual/en/function.oci-statement-type.php
 * @param resource $statement A valid OCI8 statement identifier from oci_parse.
 * @return mixed the type of statement as one of the
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
 * <p>
 * Returns false on error.
 * </p>
 */
function oci_statement_type ($statement): string|false {}

/**
 * Alias: oci_statement_type
 * @link http://www.php.net/manual/en/function.ocistatementtype.php
 * @param mixed $statement
 * @deprecated 
 */
function ocistatementtype ($statement = null): string|false {}

/**
 * Returns number of rows affected during statement execution
 * @link http://www.php.net/manual/en/function.oci-num-rows.php
 * @param resource $statement A valid OCI statement identifier.
 * @return mixed the number of rows affected as an integer, or false on failure
 */
function oci_num_rows ($statement): int|false {}

/**
 * Alias: oci_num_rows
 * @link http://www.php.net/manual/en/function.ocirowcount.php
 * @param mixed $statement
 * @deprecated 
 */
function ocirowcount ($statement = null): int|false {}

/**
 * @param OCICollection $collection
 */
function oci_free_collection (OCICollection $collection): bool {}

/**
 * Alias: OCICollection::free
 * @link http://www.php.net/manual/en/function.ocifreecollection.php
 * @param OCICollection $collection
 * @deprecated 
 */
function ocifreecollection (OCICollection $collection): bool {}

/**
 * @param OCICollection $collection
 * @param string $value
 */
function oci_collection_append (OCICollection $collection, string $value): bool {}

/**
 * Alias: OCICollection::append
 * @link http://www.php.net/manual/en/function.ocicollappend.php
 * @param OCICollection $collection
 * @param string $value
 * @deprecated 
 */
function ocicollappend (OCICollection $collection, string $value): bool {}

/**
 * @param OCICollection $collection
 * @param int $index
 */
function oci_collection_element_get (OCICollection $collection, int $index): string|float|false|null {}

/**
 * Alias: OCICollection::getElem
 * @link http://www.php.net/manual/en/function.ocicollgetelem.php
 * @param OCICollection $collection
 * @param int $index
 * @deprecated 
 */
function ocicollgetelem (OCICollection $collection, int $index): string|float|false|null {}

/**
 * @param OCICollection $to
 * @param OCICollection $from
 */
function oci_collection_assign (OCICollection $to, OCICollection $from): bool {}

/**
 * @param OCICollection $collection
 * @param int $index
 * @param string $value
 */
function oci_collection_element_assign (OCICollection $collection, int $index, string $value): bool {}

/**
 * Alias: OCICollection::assignElem
 * @link http://www.php.net/manual/en/function.ocicollassignelem.php
 * @param OCICollection $collection
 * @param int $index
 * @param string $value
 * @deprecated 
 */
function ocicollassignelem (OCICollection $collection, int $index, string $value): bool {}

/**
 * @param OCICollection $collection
 */
function oci_collection_size (OCICollection $collection): int|false {}

/**
 * Alias: OCICollection::size
 * @link http://www.php.net/manual/en/function.ocicollsize.php
 * @param OCICollection $collection
 * @deprecated 
 */
function ocicollsize (OCICollection $collection): int|false {}

/**
 * @param OCICollection $collection
 */
function oci_collection_max (OCICollection $collection): int|false {}

/**
 * Alias: OCICollection::max
 * @link http://www.php.net/manual/en/function.ocicollmax.php
 * @param OCICollection $collection
 * @deprecated 
 */
function ocicollmax (OCICollection $collection): int|false {}

/**
 * @param OCICollection $collection
 * @param int $num
 */
function oci_collection_trim (OCICollection $collection, int $num): bool {}

/**
 * Alias: OCICollection::trim
 * @link http://www.php.net/manual/en/function.ocicolltrim.php
 * @param OCICollection $collection
 * @param int $num
 * @deprecated 
 */
function ocicolltrim (OCICollection $collection, int $num): bool {}

/**
 * Allocates new collection object
 * @link http://www.php.net/manual/en/function.oci-new-collection.php
 * @param resource $connection An Oracle connection identifier, returned by 
 * oci_connect or oci_pconnect.
 * @param string $type_name Should be a valid named type (uppercase).
 * @param mixed $schema [optional] Should point to the scheme, where the named type was created. The name
 * of the current user is used when null is passed.
 * @return mixed a new OCICollection object or false on
 * error.
 */
function oci_new_collection ($connection, string $type_name, $schema = null): OCICollection|false {}

/**
 * Alias: oci_new_collection
 * @link http://www.php.net/manual/en/function.ocinewcollection.php
 * @param mixed $connection
 * @param string $type_name
 * @param string|null $schema [optional]
 * @deprecated 
 */
function ocinewcollection ($connection = null, string $type_name, string|null $schema = null): OCICollection|false {}

/**
 * Register a user-defined callback function for Oracle Database TAF
 * @link http://www.php.net/manual/en/function.oci-register-taf-callback.php
 * @param resource $connection An Oracle connection identifier.
 * @param mixed $callback <p>
 * A user-defined callback to register for Oracle TAF. It can be a
 * string of the function name or a Closure (anonymous function).
 * </p>
 * <p>
 * The interface of a TAF user-defined callback function is as follows:
 * </p>
 * intuserCallbackFn
 * resourceconnection
 * intevent
 * inttype
 * <p>
 * See the parameter description and an example on 
 * OCI8 Transparent Application Failover (TAF) Support page.
 * </p>
 * @return bool true on success or false on failure
 */
function oci_register_taf_callback ($connection, $callback): bool {}

/**
 * Unregister a user-defined callback function for Oracle Database TAF
 * @link http://www.php.net/manual/en/function.oci-unregister-taf-callback.php
 * @param resource $connection An Oracle connection identifier.
 * @return bool true on success or false on failure
 */
function oci_unregister_taf_callback ($connection): bool {}


/**
 * See OCI_NO_AUTO_COMMIT.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('OCI_DEFAULT', 0);

/**
 * Used with oci_connect to connect with
 * the SYSOPER privilege. The php.ini setting
 * oci8.privileged_connect
 * should be enabled to use this.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('OCI_SYSOPER', 4);

/**
 * Used with oci_connect to connect with
 * the SYSDBA privilege. The php.ini setting
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
define ('OCI_CRED_EXT', 2147483648);

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
 * for oci_execute. The transaction is not
 * automatically committed when using this mode. For
 * readability in new code, use this value instead of the
 * older, equivalent OCI_DEFAULT constant.
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
 * The same as OCI_B_BOL.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('SQLT_BOL', 252);

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
 * Used with oci_bind_by_name to bind a PL/SQL BOOLEAN
 * variable.
 * @link http://www.php.net/manual/en/oci8.constants.php
 */
define ('OCI_B_BOL', 252);

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
 * array elements if the row items value is null.
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

// End of oci8 v.3.0.1
