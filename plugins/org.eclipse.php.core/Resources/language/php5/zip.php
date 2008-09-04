<?php

// Start of zip v.2.0.0

class ZipArchive  {
	const CREATE = 1;
	const EXCL = 2;
	const CHECKCONS = 4;
	const OVERWRITE = 8;
	const FL_NOCASE = 1;
	const FL_NODIR = 2;
	const FL_COMPRESSED = 4;
	const FL_UNCHANGED = 8;
	const CM_DEFAULT = -1;
	const CM_STORE = 0;
	const CM_SHRINK = 1;
	const CM_REDUCE_1 = 2;
	const CM_REDUCE_2 = 3;
	const CM_REDUCE_3 = 4;
	const CM_REDUCE_4 = 5;
	const CM_IMPLODE = 6;
	const CM_DEFLATE = 8;
	const CM_DEFLATE64 = 9;
	const CM_PKWARE_IMPLODE = 10;
	const ER_OK = 0;
	const ER_MULTIDISK = 1;
	const ER_RENAME = 2;
	const ER_CLOSE = 3;
	const ER_SEEK = 4;
	const ER_READ = 5;
	const ER_WRITE = 6;
	const ER_CRC = 7;
	const ER_ZIPCLOSED = 8;
	const ER_NOENT = 9;
	const ER_EXISTS = 10;
	const ER_OPEN = 11;
	const ER_TMPOPEN = 12;
	const ER_ZLIB = 13;
	const ER_MEMORY = 14;
	const ER_CHANGED = 15;
	const ER_COMPNOTSUPP = 16;
	const ER_EOF = 17;
	const ER_INVAL = 18;
	const ER_NOZIP = 19;
	const ER_INTERNAL = 20;
	const ER_INCONS = 21;
	const ER_REMOVE = 22;
	const ER_DELETED = 23;


	/**
	 * Open a ZIP file archive
	 * @link http://php.net/manual/en/function.ziparchive-open.php
	 * @param filename string
	 * @param flags int[optional]
	 * @return mixed true on success or the error code.
	 */
	public function open ($filename, $flags = null) {}

	/**
	 * Close the active archive (opened or newly created)
	 * @link http://php.net/manual/en/function.ziparchive-close.php
	 * @return bool 
	 */
	public function close () {}

	/**
	 * Add a new directory
	 * @link http://php.net/manual/en/function.ziparchive-addemptydir.php
	 * @param dirname string
	 * @return bool 
	 */
	public function addEmptyDir ($dirname) {}

	/**
	 * Add a file to a ZIP archive using its contents
	 * @link http://php.net/manual/en/function.ziparchive-addfromstring.php
	 * @param localname string
	 * @param contents string
	 * @return bool 
	 */
	public function addFromString ($localname, $contents) {}

	/**
	 * Adds a file to a ZIP archive from the given path
	 * @link http://php.net/manual/en/function.ziparchive-addfile.php
	 * @param filename string
	 * @param localname string[optional]
	 * @return bool 
	 */
	public function addFile ($filename, $localname = null) {}

	/**
	 * Renames an entry defined by its index
	 * @link http://php.net/manual/en/function.ziparchive-renameindex.php
	 * @param index int
	 * @param newname string
	 * @return bool 
	 */
	public function renameIndex ($index, $newname) {}

	/**
	 * Renames an entry defined by its name
	 * @link http://php.net/manual/en/function.ziparchive-renamename.php
	 * @param name string
	 * @param newname string
	 * @return bool 
	 */
	public function renameName ($name, $newname) {}

	/**
	 * Set the comment of a ZIP archive
	 * @link http://php.net/manual/en/function.ziparchive-setarchivecomment.php
	 * @param comment string
	 * @return mixed 
	 */
	public function setArchiveComment ($comment) {}

	/**
	 * Returns the Zip archive comment
	 * @link http://php.net/manual/en/function.ziparchive-getarchivecomment.php
	 * @return string the Zip archive comment or false on failure.
	 */
	public function getArchiveComment () {}

	/**
	 * Set the comment of an entry defined by its index
	 * @link http://php.net/manual/en/function.ziparchive-setcommentindex.php
	 * @param index int
	 * @param comment string
	 * @return mixed 
	 */
	public function setCommentIndex ($index, $comment) {}

	/**
	 * Set the comment of an entry defined by its name
	 * @link http://php.net/manual/en/function.ziparchive-setCommentName.php
	 * @param name string
	 * @param comment string
	 * @return mixed 
	 */
	public function setCommentName ($name, $comment) {}

	/**
	 * Returns the comment of an entry using the entry index
	 * @link http://php.net/manual/en/function.ziparchive-getcommentindex.php
	 * @param index int
	 * @param flags int[optional]
	 * @return string the comment on success or false on failure.
	 */
	public function getCommentIndex ($index, $flags = null) {}

	/**
	 * Returns the comment of an entry using the entry name
	 * @link http://php.net/manual/en/function.ziparchive-getcommentname.php
	 * @param name string
	 * @param flags int[optional]
	 * @return string the comment on success or false on failure.
	 */
	public function getCommentName ($name, $flags = null) {}

	/**
	 * delete an entry in the archive using its index
	 * @link http://php.net/manual/en/function.ziparchive-deleteindex.php
	 * @param index int
	 * @return bool 
	 */
	public function deleteIndex ($index) {}

	/**
	 * delete an entry in the archive using its name
	 * @link http://php.net/manual/en/function.ziparchive-deletename.php
	 * @param name string
	 * @return bool 
	 */
	public function deleteName ($name) {}

	/**
	 * Get the details of an entry defined by its name.
	 * @link http://php.net/manual/en/function.ziparchive-statname.php
	 * @param name name
	 * @param flags int[optional]
	 * @return mixed an array containing the entry details or false on failure.
	 */
	public function statName ($name, $flags = null) {}

	/**
	 * Get the details of an entry defined by its index.
	 * @link http://php.net/manual/en/function.ziparchive-statindex.php
	 * @param index int
	 * @param flags int[optional]
	 * @return mixed an array containing the entry details or false on failure.
	 */
	public function statIndex ($index, $flags = null) {}

	/**
	 * Returns the index of the entry in the archive
	 * @link http://php.net/manual/en/function.ziparchive-locatename.php
	 * @param name string
	 * @param flags int[optional]
	 * @return mixed the index of the entry on success or false on failure.
	 */
	public function locateName ($name, $flags = null) {}

	/**
	 * Returns the name of an entry using its index
	 * @link http://php.net/manual/en/function.ziparchive-getnameindex.php
	 * @param index int
	 * @return string the name on success or false on failure.
	 */
	public function getNameIndex ($index) {}

	/**
	 * Revert all global changes done in the archive.
	 * @link http://php.net/manual/en/function.ziparchive-unchangearchive.php
	 * @return mixed 
	 */
	public function unchangeArchive () {}

	/**
	 * Undo all changes done in the archive.
	 * @link http://php.net/manual/en/function.ziparchive-unchangeall.php
	 * @return mixed 
	 */
	public function unchangeAll () {}

	/**
	 * Revert all changes done to an entry at the given index.
	 * @link http://php.net/manual/en/function.ziparchive-unchangeindex.php
	 * @param index int
	 * @return mixed 
	 */
	public function unchangeIndex ($index) {}

	/**
	 * Revert all changes done to an entry with the given name.
	 * @link http://php.net/manual/en/function.ziparchive-unchangename.php
	 * @param name string
	 * @return mixed 
	 */
	public function unchangeName ($name) {}

	/**
	 * Extract the archive contents
	 * @link http://php.net/manual/en/function.ziparchive-extractto.php
	 * @param destination string
	 * @param entries mixed[optional]
	 * @return mixed 
	 */
	public function extractTo ($destination, $entries = null) {}

	/**
	 * Returns the entry contents using its name.
	 * @link http://php.net/manual/en/function.ziparchive-getfromname.php
	 * @param name string
	 * @param flags int[optional]
	 * @return mixed the contents of the entry on success or false on failure.
	 */
	public function getFromName ($name, $flags = null) {}

	/**
	 * Returns the entry contents using its index.
	 * @link http://php.net/manual/en/function.ziparchive-getfromindex.php
	 * @param index int
	 * @param flags int[optional]
	 * @return mixed the contents of the entry on success or false on failure.
	 */
	public function getFromIndex ($index, $flags = null) {}

	/**
	 * Get a file handler to the entry defined by its name (read only).
	 * @link http://php.net/manual/en/function.ziparchive-getstream.php
	 * @param name string
	 * @return resource a file pointer (resource) on success or false on failure.
	 */
	public function getStream ($name) {}

}

/**
 * Open a ZIP file archive
 * @link http://php.net/manual/en/function.zip-open.php
 * @param filename string
 * @return mixed a resource handle for later use with
 */
function zip_open ($filename) {}

/**
 * Close a ZIP file archive
 * @link http://php.net/manual/en/function.zip-close.php
 * @param zip resource
 * @return void 
 */
function zip_close ($zip) {}

/**
 * Read next entry in a ZIP file archive
 * @link http://php.net/manual/en/function.zip-read.php
 * @param zip resource
 * @return mixed a directory entry resource for later use with the
 */
function zip_read ($zip) {}

/**
 * Open a directory entry for reading
 * @link http://php.net/manual/en/function.zip-entry-open.php
 * @param zip resource
 * @param zip_entry resource
 * @param mode string[optional]
 * @return bool 
 */
function zip_entry_open ($zip, $zip_entry, $mode = null) {}

/**
 * Close a directory entry
 * @link http://php.net/manual/en/function.zip-entry-close.php
 * @param zip_entry resource
 * @return bool 
 */
function zip_entry_close ($zip_entry) {}

/**
 * Read from an open directory entry
 * @link http://php.net/manual/en/function.zip-entry-read.php
 * @param zip_entry resource
 * @param length int[optional]
 * @return string the data read, or false if the end of the file is
 */
function zip_entry_read ($zip_entry, $length = null) {}

/**
 * Retrieve the actual file size of a directory entry
 * @link http://php.net/manual/en/function.zip-entry-filesize.php
 * @param zip_entry resource
 * @return int 
 */
function zip_entry_filesize ($zip_entry) {}

/**
 * Retrieve the name of a directory entry
 * @link http://php.net/manual/en/function.zip-entry-name.php
 * @param zip_entry resource
 * @return string 
 */
function zip_entry_name ($zip_entry) {}

/**
 * Retrieve the compressed size of a directory entry
 * @link http://php.net/manual/en/function.zip-entry-compressedsize.php
 * @param zip_entry resource
 * @return int 
 */
function zip_entry_compressedsize ($zip_entry) {}

/**
 * Retrieve the compression method of a directory entry
 * @link http://php.net/manual/en/function.zip-entry-compressionmethod.php
 * @param zip_entry resource
 * @return string 
 */
function zip_entry_compressionmethod ($zip_entry) {}

// End of zip v.2.0.0
?>
