<?php

// Start of zip v.1.21.1

/**
 * A file archive, compressed with Zip.
 * @link http://www.php.net/manual/en/class.ziparchive.php
 */
class ZipArchive implements Countable {
	const CREATE = 1;
	const EXCL = 2;
	const CHECKCONS = 4;
	const OVERWRITE = 8;
	const RDONLY = 16;
	const FL_NOCASE = 1;
	const FL_NODIR = 2;
	const FL_COMPRESSED = 4;
	const FL_UNCHANGED = 8;
	const FL_RECOMPRESS = 16;
	const FL_ENCRYPTED = 32;
	const FL_OVERWRITE = 8192;
	const FL_LOCAL = 256;
	const FL_CENTRAL = 512;
	const FL_ENC_GUESS = 0;
	const FL_ENC_RAW = 64;
	const FL_ENC_STRICT = 128;
	const FL_ENC_UTF_8 = 2048;
	const FL_ENC_CP437 = 4096;
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
	const CM_BZIP2 = 12;
	const CM_LZMA = 14;
	const CM_LZMA2 = 33;
	const CM_ZSTD = 93;
	const CM_XZ = 95;
	const CM_TERSE = 18;
	const CM_LZ77 = 19;
	const CM_WAVPACK = 97;
	const CM_PPMD = 98;
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
	const ER_ENCRNOTSUPP = 24;
	const ER_RDONLY = 25;
	const ER_NOPASSWD = 26;
	const ER_WRONGPASSWD = 27;
	const ER_OPNOTSUPP = 28;
	const ER_INUSE = 29;
	const ER_TELL = 30;
	const ER_COMPRESSED_DATA = 31;
	const ER_CANCELLED = 32;
	const OPSYS_DOS = 0;
	const OPSYS_AMIGA = 1;
	const OPSYS_OPENVMS = 2;
	const OPSYS_UNIX = 3;
	const OPSYS_VM_CMS = 4;
	const OPSYS_ATARI_ST = 5;
	const OPSYS_OS_2 = 6;
	const OPSYS_MACINTOSH = 7;
	const OPSYS_Z_SYSTEM = 8;
	const OPSYS_CPM = 9;
	const OPSYS_WINDOWS_NTFS = 10;
	const OPSYS_MVS = 11;
	const OPSYS_VSE = 12;
	const OPSYS_ACORN_RISC = 13;
	const OPSYS_VFAT = 14;
	const OPSYS_ALTERNATE_MVS = 15;
	const OPSYS_BEOS = 16;
	const OPSYS_TANDEM = 17;
	const OPSYS_OS_400 = 18;
	const OPSYS_OS_X = 19;
	const OPSYS_DEFAULT = 3;
	const EM_NONE = 0;
	const EM_TRAD_PKWARE = 1;
	const EM_AES_128 = 257;
	const EM_AES_192 = 258;
	const EM_AES_256 = 259;
	const EM_UNKNOWN = 65535;
	const LIBZIP_VERSION = "1.9.2";

	public $lastId;
	public $status;
	public $statusSys;
	public $numFiles;
	public $filename;
	public $comment;


	/**
	 * Open a ZIP file archive
	 * @link http://www.php.net/manual/en/ziparchive.open.php
	 * @param string $filename The file name of the ZIP archive to open.
	 * @param int $flags [optional] <p>
	 * The mode to use to open the archive.
	 * <p>
	 * <br>
	 * <p>
	 * ZipArchive::OVERWRITE
	 * </p>
	 * <br>
	 * <p>
	 * ZipArchive::CREATE
	 * </p>
	 * <br>
	 * <p>
	 * ZipArchive::RDONLY
	 * </p>
	 * <br>
	 * <p>
	 * ZipArchive::EXCL
	 * </p>
	 * <br>
	 * <p>
	 * ZipArchive::CHECKCONS
	 * </p>
	 * </p>
	 * </p>
	 * @return mixed true on success, false or one of the following error codes on error:
	 * <p>
	 * ZipArchive::ER_EXISTS
	 * <br>
	 * File already exists.
	 * ZipArchive::ER_INCONS
	 * <br>
	 * Zip archive inconsistent.
	 * ZipArchive::ER_INVAL
	 * <br>
	 * Invalid argument.
	 * ZipArchive::ER_MEMORY
	 * <br>
	 * Malloc failure.
	 * ZipArchive::ER_NOENT
	 * <br>
	 * No such file.
	 * ZipArchive::ER_NOZIP
	 * <br>
	 * Not a zip archive.
	 * ZipArchive::ER_OPEN
	 * <br>
	 * Can't open file.
	 * ZipArchive::ER_READ
	 * <br>
	 * Read error.
	 * ZipArchive::ER_SEEK
	 * <br>
	 * Seek error.
	 * </p>
	 */
	public function open (string $filename, int $flags = null) {}

	/**
	 * Set the password for the active archive
	 * @link http://www.php.net/manual/en/ziparchive.setpassword.php
	 * @param string $password The password to be used for the archive.
	 * @return bool true on success or false on failure
	 */
	public function setPassword (string $password) {}

	/**
	 * Close the active archive (opened or newly created)
	 * @link http://www.php.net/manual/en/ziparchive.close.php
	 * @return bool true on success or false on failure
	 */
	public function close () {}

	/**
	 * Counts the number of files in the archive
	 * @link http://www.php.net/manual/en/ziparchive.count.php
	 * @return int the number of files in the archive.
	 */
	public function count () {}

	/**
	 * Returns the status error message, system and/or zip messages
	 * @link http://www.php.net/manual/en/ziparchive.getstatusstring.php
	 * @return string a string with the status message.
	 */
	public function getStatusString () {}

	/**
	 * Clear the status error message, system and/or zip messages
	 * @link http://www.php.net/manual/en/ziparchive.clearerror.php
	 * @return void 
	 */
	public function clearError (): void {}

	/**
	 * Add a new directory
	 * @link http://www.php.net/manual/en/ziparchive.addemptydir.php
	 * @param string $dirname The directory to add.
	 * @param int $flags [optional] Bitmask consisting of
	 * ZipArchive::FL_ENC_GUESS,
	 * ZipArchive::FL_ENC_UTF_8,
	 * ZipArchive::FL_ENC_CP437.
	 * The behaviour of these constants is described on the
	 * ZIP constants page.
	 * @return bool true on success or false on failure
	 */
	public function addEmptyDir (string $dirname, int $flags = null) {}

	/**
	 * Add a file to a ZIP archive using its contents
	 * @link http://www.php.net/manual/en/ziparchive.addfromstring.php
	 * @param string $name The name of the entry to create.
	 * @param string $content The contents to use to create the entry. It is used in a binary
	 * safe mode.
	 * @param int $flags [optional] Bitmask consisting of
	 * ZipArchive::FL_OVERWRITE,
	 * ZipArchive::FL_ENC_GUESS,
	 * ZipArchive::FL_ENC_UTF_8,
	 * ZipArchive::FL_ENC_CP437.
	 * The behaviour of these constants is described on the
	 * ZIP constants page.
	 * @return bool true on success or false on failure
	 */
	public function addFromString (string $name, string $content, int $flags = null) {}

	/**
	 * Adds a file to a ZIP archive from the given path
	 * @link http://www.php.net/manual/en/ziparchive.addfile.php
	 * @param string $filepath The path to the file to add.
	 * @param string $entryname [optional] If supplied and not empty, this is the local name inside the ZIP archive that will override the filepath.
	 * @param int $start [optional] For partial copy, start position.
	 * @param int $length [optional] For partial copy, length to be copied, if 0 or -1 the whole file (starting from start) is used.
	 * @param int $flags [optional] Bitmask consisting of
	 * ZipArchive::FL_OVERWRITE,
	 * ZipArchive::FL_ENC_GUESS,
	 * ZipArchive::FL_ENC_UTF_8,
	 * ZipArchive::FL_ENC_CP437.
	 * The behaviour of these constants is described on the
	 * ZIP constants page.
	 * @return bool true on success or false on failure
	 */
	public function addFile (string $filepath, string $entryname = null, int $start = null, int $length = null, int $flags = null) {}

	/**
	 * Replace file in ZIP archive with a given path
	 * @link http://www.php.net/manual/en/ziparchive.replacefile.php
	 * @param string $filepath The path to the file to add.
	 * @param int $index The index of the file to be replaced, its name is unchanged.
	 * @param int $start [optional] For partial copy, start position.
	 * @param int $length [optional] For partial copy, length to be copied, if 0 or -1 the whole file (starting from start) is used.
	 * @param int $flags [optional] Bitmask consisting of
	 * ZipArchive::FL_ENC_GUESS,
	 * ZipArchive::FL_ENC_UTF_8,
	 * ZipArchive::FL_ENC_CP437.
	 * The behaviour of these constants is described on the
	 * ZIP constants page.
	 * @return bool true on success or false on failure
	 */
	public function replaceFile (string $filepath, int $index, int $start = null, int $length = null, int $flags = null) {}

	/**
	 * Add files from a directory by glob pattern
	 * @link http://www.php.net/manual/en/ziparchive.addglob.php
	 * @param string $pattern A glob pattern against which files will be matched.
	 * @param int $flags [optional] A bit mask of glob() flags.
	 * @param array $options [optional] <p>
	 * An associative array of options. Available options are:
	 * <p>
	 * <br>
	 * <p>
	 * "add_path"
	 * </p>
	 * <p>
	 * Prefix to prepend when translating to the local path of the file within
	 * the archive. This is applied after any remove operations defined by the
	 * "remove_path" or "remove_all_path"
	 * options.
	 * </p>
	 * <br>
	 * <p>
	 * "remove_path"
	 * </p>
	 * <p>
	 * Prefix to remove from matching file paths before adding to the archive.
	 * </p>
	 * <br>
	 * <p>
	 * "remove_all_path"
	 * </p>
	 * <p>
	 * true to use the file name only and add to the root of the archive.
	 * </p>
	 * <br>
	 * <p>
	 * "flags"
	 * </p>
	 * <p>
	 * Bitmask consisting of
	 * ZipArchive::FL_OVERWRITE,
	 * ZipArchive::FL_ENC_GUESS,
	 * ZipArchive::FL_ENC_UTF_8,
	 * ZipArchive::FL_ENC_CP437.
	 * The behaviour of these constants is described on the
	 * ZIP constants page.
	 * </p>
	 * <br>
	 * <p>
	 * "comp_method"
	 * </p>
	 * <p>
	 * Compression method, one of the ZipArchive::CM_&#42;
	 * constants, see ZIP constants page.
	 * </p>
	 * <br>
	 * <p>
	 * "comp_flags"
	 * </p>
	 * <p>
	 * Compression level.
	 * </p>
	 * <br>
	 * <p>
	 * "enc_method"
	 * </p>
	 * <p>
	 * Encryption method, one of the ZipArchive::EM_&#42;
	 * constants, see ZIP constants page.
	 * </p>
	 * <br>
	 * <p>
	 * "enc_password"
	 * </p>
	 * <p>
	 * Password used for encryption.
	 * </p>
	 * </p>
	 * </p>
	 * @return mixed An array of added files on success or false on failure
	 */
	public function addGlob (string $pattern, int $flags = null, array $options = null) {}

	/**
	 * Add files from a directory by PCRE pattern
	 * @link http://www.php.net/manual/en/ziparchive.addpattern.php
	 * @param string $pattern A PCRE pattern against which files will be matched.
	 * @param string $path [optional] The directory that will be scanned. Defaults to the current working directory.
	 * @param array $options [optional] An associative array of options accepted by ZipArchive::addGlob.
	 * @return mixed An array of added files on success or false on failure
	 */
	public function addPattern (string $pattern, string $path = null, array $options = null) {}

	/**
	 * Renames an entry defined by its index
	 * @link http://www.php.net/manual/en/ziparchive.renameindex.php
	 * @param int $index Index of the entry to rename.
	 * @param string $new_name New name.
	 * @return bool true on success or false on failure
	 */
	public function renameIndex (int $index, string $new_name) {}

	/**
	 * Renames an entry defined by its name
	 * @link http://www.php.net/manual/en/ziparchive.renamename.php
	 * @param string $name Name of the entry to rename.
	 * @param string $new_name New name.
	 * @return bool true on success or false on failure
	 */
	public function renameName (string $name, string $new_name) {}

	/**
	 * Set the comment of a ZIP archive
	 * @link http://www.php.net/manual/en/ziparchive.setarchivecomment.php
	 * @param string $comment The contents of the comment.
	 * @return bool true on success or false on failure
	 */
	public function setArchiveComment (string $comment) {}

	/**
	 * Returns the Zip archive comment
	 * @link http://www.php.net/manual/en/ziparchive.getarchivecomment.php
	 * @param int $flags [optional] If flags is set to ZipArchive::FL_UNCHANGED, the original unchanged
	 * comment is returned.
	 * @return mixed the Zip archive comment or false on failure.
	 */
	public function getArchiveComment (int $flags = null) {}

	/**
	 * Set the comment of an entry defined by its index
	 * @link http://www.php.net/manual/en/ziparchive.setcommentindex.php
	 * @param int $index Index of the entry.
	 * @param string $comment The contents of the comment.
	 * @return bool true on success or false on failure
	 */
	public function setCommentIndex (int $index, string $comment) {}

	/**
	 * Set the comment of an entry defined by its name
	 * @link http://www.php.net/manual/en/ziparchive.setcommentname.php
	 * @param string $name Name of the entry.
	 * @param string $comment The contents of the comment.
	 * @return bool true on success or false on failure
	 */
	public function setCommentName (string $name, string $comment) {}

	/**
	 * Set the modification time of an entry defined by its index
	 * @link http://www.php.net/manual/en/ziparchive.setmtimeindex.php
	 * @param int $index Index of the entry.
	 * @param int $timestamp The modification time (unix timestamp) of the file.
	 * @param int $flags [optional] Optional flags, unused for now.
	 * @return bool true on success or false on failure
	 */
	public function setMtimeIndex (int $index, int $timestamp, int $flags = null) {}

	/**
	 * Set the modification time of an entry defined by its name
	 * @link http://www.php.net/manual/en/ziparchive.setmtimename.php
	 * @param string $name Name of the entry.
	 * @param int $timestamp The modification time (unix timestamp) of the file.
	 * @param int $flags [optional] Optional flags, unused for now.
	 * @return bool true on success or false on failure
	 */
	public function setMtimeName (string $name, int $timestamp, int $flags = null) {}

	/**
	 * Returns the comment of an entry using the entry index
	 * @link http://www.php.net/manual/en/ziparchive.getcommentindex.php
	 * @param int $index Index of the entry
	 * @param int $flags [optional] If flags is set to ZipArchive::FL_UNCHANGED, the original unchanged
	 * comment is returned.
	 * @return mixed the comment on success or false on failure.
	 */
	public function getCommentIndex (int $index, int $flags = null) {}

	/**
	 * Returns the comment of an entry using the entry name
	 * @link http://www.php.net/manual/en/ziparchive.getcommentname.php
	 * @param string $name Name of the entry
	 * @param int $flags [optional] If flags is set to ZipArchive::FL_UNCHANGED, the original unchanged
	 * comment is returned.
	 * @return mixed the comment on success or false on failure.
	 */
	public function getCommentName (string $name, int $flags = null) {}

	/**
	 * Delete an entry in the archive using its index
	 * @link http://www.php.net/manual/en/ziparchive.deleteindex.php
	 * @param int $index Index of the entry to delete.
	 * @return bool true on success or false on failure
	 */
	public function deleteIndex (int $index) {}

	/**
	 * Delete an entry in the archive using its name
	 * @link http://www.php.net/manual/en/ziparchive.deletename.php
	 * @param string $name Name of the entry to delete.
	 * @return bool true on success or false on failure
	 */
	public function deleteName (string $name) {}

	/**
	 * Get the details of an entry defined by its name
	 * @link http://www.php.net/manual/en/ziparchive.statname.php
	 * @param string $name Name of the entry
	 * @param int $flags [optional] <p>
	 * The flags argument specifies how the name lookup should be done.
	 * Also, ZipArchive::FL_UNCHANGED may be ORed to it to request
	 * information about the original file in the archive,
	 * ignoring any changes made.
	 * <p>
	 * <br>
	 * <p>
	 * ZipArchive::FL_NOCASE
	 * </p>
	 * <br>
	 * <p>
	 * ZipArchive::FL_NODIR
	 * </p>
	 * <br>
	 * <p>
	 * ZipArchive::FL_UNCHANGED
	 * </p>
	 * </p>
	 * </p>
	 * @return mixed an array containing the entry details or false on failure.
	 */
	public function statName (string $name, int $flags = null) {}

	/**
	 * Get the details of an entry defined by its index
	 * @link http://www.php.net/manual/en/ziparchive.statindex.php
	 * @param int $index Index of the entry
	 * @param int $flags [optional] ZipArchive::FL_UNCHANGED may be ORed to it to request
	 * information about the original file in the archive,
	 * ignoring any changes made.
	 * @return mixed an array containing the entry details or false on failure.
	 */
	public function statIndex (int $index, int $flags = null) {}

	/**
	 * Returns the index of the entry in the archive
	 * @link http://www.php.net/manual/en/ziparchive.locatename.php
	 * @param string $name The name of the entry to look up
	 * @param int $flags [optional] <p>
	 * The flags are specified by ORing the following values,
	 * or 0 for none of them.
	 * <p>
	 * <br>
	 * <p>
	 * ZipArchive::FL_NOCASE
	 * </p>
	 * <br>
	 * <p>
	 * ZipArchive::FL_NODIR
	 * </p>
	 * </p>
	 * </p>
	 * @return mixed the index of the entry on success or false on failure.
	 */
	public function locateName (string $name, int $flags = null) {}

	/**
	 * Returns the name of an entry using its index
	 * @link http://www.php.net/manual/en/ziparchive.getnameindex.php
	 * @param int $index Index of the entry.
	 * @param int $flags [optional] If flags is set to ZipArchive::FL_UNCHANGED, the original unchanged
	 * name is returned.
	 * @return mixed the name on success or false on failure.
	 */
	public function getNameIndex (int $index, int $flags = null) {}

	/**
	 * Revert all global changes done in the archive
	 * @link http://www.php.net/manual/en/ziparchive.unchangearchive.php
	 * @return bool true on success or false on failure
	 */
	public function unchangeArchive () {}

	/**
	 * Undo all changes done in the archive
	 * @link http://www.php.net/manual/en/ziparchive.unchangeall.php
	 * @return bool true on success or false on failure
	 */
	public function unchangeAll () {}

	/**
	 * Revert all changes done to an entry at the given index
	 * @link http://www.php.net/manual/en/ziparchive.unchangeindex.php
	 * @param int $index Index of the entry.
	 * @return bool true on success or false on failure
	 */
	public function unchangeIndex (int $index) {}

	/**
	 * Revert all changes done to an entry with the given name
	 * @link http://www.php.net/manual/en/ziparchive.unchangename.php
	 * @param string $name Name of the entry.
	 * @return bool true on success or false on failure
	 */
	public function unchangeName (string $name) {}

	/**
	 * Extract the archive contents
	 * @link http://www.php.net/manual/en/ziparchive.extractto.php
	 * @param string $pathto Location where to extract the files.
	 * @param mixed $files [optional] The entries to extract. It accepts either a single entry name or
	 * an array of names.
	 * @return bool true on success or false on failure
	 */
	public function extractTo (string $pathto, $files = null) {}

	/**
	 * Returns the entry contents using its name
	 * @link http://www.php.net/manual/en/ziparchive.getfromname.php
	 * @param string $name Name of the entry
	 * @param int $len [optional] The length to be read from the entry. If 0, then the
	 * entire entry is read.
	 * @param int $flags [optional] <p>
	 * The flags to use to find the entry. The following values may
	 * be ORed.
	 * <p>
	 * <br>
	 * <p>
	 * ZipArchive::FL_UNCHANGED
	 * </p>
	 * <br>
	 * <p>
	 * ZipArchive::FL_COMPRESSED
	 * </p>
	 * <br>
	 * <p>
	 * ZipArchive::FL_NOCASE
	 * </p>
	 * </p>
	 * </p>
	 * @return mixed the contents of the entry on success or false on failure.
	 */
	public function getFromName (string $name, int $len = null, int $flags = null) {}

	/**
	 * Returns the entry contents using its index
	 * @link http://www.php.net/manual/en/ziparchive.getfromindex.php
	 * @param int $index Index of the entry
	 * @param int $len [optional] The length to be read from the entry. If 0, then the
	 * entire entry is read.
	 * @param int $flags [optional] <p>
	 * The flags to use to open the archive. the following values may
	 * be ORed to it.
	 * <p>
	 * <br>
	 * <p>
	 * ZipArchive::FL_UNCHANGED
	 * </p>
	 * <br>
	 * <p>
	 * ZipArchive::FL_COMPRESSED
	 * </p>
	 * </p>
	 * </p>
	 * @return mixed the contents of the entry on success or false on failure.
	 */
	public function getFromIndex (int $index, int $len = null, int $flags = null) {}

	/**
	 * Get a file handler to the entry defined by its index (read only)
	 * @link http://www.php.net/manual/en/ziparchive.getstreamindex.php
	 * @param int $index Index of the entry
	 * @param int $flags [optional] If flags is set to ZipArchive::FL_UNCHANGED, the original unchanged
	 * stream is returned.
	 * @return mixed a file pointer (resource) on success or false on failure.
	 */
	public function getStreamIndex (int $index, int $flags = null) {}

	/**
	 * Get a file handler to the entry defined by its name (read only)
	 * @link http://www.php.net/manual/en/ziparchive.getstreamname.php
	 * @param string $name The name of the entry to use.
	 * @param int $flags [optional] If flags is set to ZipArchive::FL_UNCHANGED, the original unchanged
	 * stream is returned.
	 * @return mixed a file pointer (resource) on success or false on failure.
	 */
	public function getStreamName (string $name, int $flags = null) {}

	/**
	 * Get a file handler to the entry defined by its name (read only)
	 * @link http://www.php.net/manual/en/ziparchive.getstream.php
	 * @param string $name The name of the entry to use.
	 * @return mixed a file pointer (resource) on success or false on failure.
	 */
	public function getStream (string $name) {}

	/**
	 * Set the external attributes of an entry defined by its name
	 * @link http://www.php.net/manual/en/ziparchive.setexternalattributesname.php
	 * @param string $name Name of the entry.
	 * @param int $opsys The operating system code defined by one of the ZipArchive::OPSYS_ constants.
	 * @param int $attr The external attributes. Value depends on operating system.
	 * @param int $flags [optional] Optional flags. Currently unused.
	 * @return bool true on success or false on failure
	 */
	public function setExternalAttributesName (string $name, int $opsys, int $attr, int $flags = null) {}

	/**
	 * Set the external attributes of an entry defined by its index
	 * @link http://www.php.net/manual/en/ziparchive.setexternalattributesindex.php
	 * @param int $index Index of the entry.
	 * @param int $opsys The operating system code defined by one of the ZipArchive::OPSYS_ constants.
	 * @param int $attr The external attributes. Value depends on operating system.
	 * @param int $flags [optional] Optional flags. Currently unused.
	 * @return bool true on success or false on failure
	 */
	public function setExternalAttributesIndex (int $index, int $opsys, int $attr, int $flags = null) {}

	/**
	 * Retrieve the external attributes of an entry defined by its name
	 * @link http://www.php.net/manual/en/ziparchive.getexternalattributesname.php
	 * @param string $name Name of the entry.
	 * @param int $opsys On success, receive the operating system code defined by one of the ZipArchive::OPSYS_ constants.
	 * @param int $attr On success, receive the external attributes. Value depends on operating system.
	 * @param int $flags [optional] If flags is set to ZipArchive::FL_UNCHANGED, the original unchanged
	 * attributes are returned.
	 * @return bool true on success or false on failure
	 */
	public function getExternalAttributesName (string $name, int &$opsys, int &$attr, int $flags = null) {}

	/**
	 * Retrieve the external attributes of an entry defined by its index
	 * @link http://www.php.net/manual/en/ziparchive.getexternalattributesindex.php
	 * @param int $index Index of the entry.
	 * @param int $opsys On success, receive the operating system code defined by one of the ZipArchive::OPSYS_ constants.
	 * @param int $attr On success, receive the external attributes. Value depends on operating system.
	 * @param int $flags [optional] If flags is set to ZipArchive::FL_UNCHANGED, the original unchanged
	 * attributes are returned.
	 * @return bool true on success or false on failure
	 */
	public function getExternalAttributesIndex (int $index, int &$opsys, int &$attr, int $flags = null) {}

	/**
	 * Set the compression method of an entry defined by its name
	 * @link http://www.php.net/manual/en/ziparchive.setcompressionname.php
	 * @param string $name Name of the entry.
	 * @param int $method The compression method, one of the
	 * ZipArchive::CM_&#42; constants.
	 * @param int $compflags [optional] Compression level.
	 * @return bool true on success or false on failure
	 */
	public function setCompressionName (string $name, int $method, int $compflags = null) {}

	/**
	 * Set the compression method of an entry defined by its index
	 * @link http://www.php.net/manual/en/ziparchive.setcompressionindex.php
	 * @param int $index Index of the entry.
	 * @param int $method The compression method, one of the
	 * ZipArchive::CM_&#42; constants.
	 * @param int $compflags [optional] Compression level.
	 * @return bool true on success or false on failure
	 */
	public function setCompressionIndex (int $index, int $method, int $compflags = null) {}

	/**
	 * Set the encryption method of an entry defined by its name
	 * @link http://www.php.net/manual/en/ziparchive.setencryptionname.php
	 * @param string $name Name of the entry.
	 * @param int $method The encryption method defined by one of the ZipArchive::EM_ constants.
	 * @param mixed $password [optional] Optional password, default used when missing.
	 * @return bool true on success or false on failure
	 */
	public function setEncryptionName (string $name, int $method, $password = null) {}

	/**
	 * Set the encryption method of an entry defined by its index
	 * @link http://www.php.net/manual/en/ziparchive.setencryptionindex.php
	 * @param int $index Index of the entry.
	 * @param int $method The encryption method defined by one of the ZipArchive::EM_ constants.
	 * @param mixed $password [optional] Optional password, default used when missing.
	 * @return bool true on success or false on failure
	 */
	public function setEncryptionIndex (int $index, int $method, $password = null) {}

	/**
	 * Register a callback to provide updates during archive close.
	 * @link http://www.php.net/manual/en/ziparchive.registerprogresscallback.php
	 * @param float $rate Change between each call of the callback (from 0.0 to 1.0).
	 * @param callable $callback This function will receive the current state as a float (from 0.0 to 1.0).
	 * @return bool true on success or false on failure
	 */
	public function registerProgressCallback (float $rate, callable $callback) {}

	/**
	 * Register a callback to allow cancellation during archive close.
	 * @link http://www.php.net/manual/en/ziparchive.registercancelcallback.php
	 * @param callable $callback If this function return 0 operation will continue, other value it will be cancelled.
	 * @return bool true on success or false on failure
	 */
	public function registerCancelCallback (callable $callback) {}

	/**
	 * Check if a compression method is supported by libzip
	 * @link http://www.php.net/manual/en/ziparchive.iscompressionmethoddupported.php
	 * @param int $method The compression method, one of the
	 * ZipArchive::CM_&#42; constants.
	 * @param bool $enc [optional] If true check for compression, else check for decompression.
	 * @return bool true on success or false on failure
	 */
	public static function isCompressionMethodSupported (int $method, bool $enc = null): bool {}

	/**
	 * Check if a encryption method is supported by libzip
	 * @link http://www.php.net/manual/en/ziparchive.isencryptionmethoddupported.php
	 * @param int $method The encryption method, one of the
	 * ZipArchive::EM_&#42; constants.
	 * @param bool $enc [optional] If true check for encryption, else check for decryption.
	 * @return bool true on success or false on failure
	 */
	public static function isEncryptionMethodSupported (int $method, bool $enc = null): bool {}

}

/**
 * Open a ZIP file archive
 * @link http://www.php.net/manual/en/function.zip-open.php
 * @param string $filename The file name of the ZIP archive to open.
 * @return mixed a resource handle for later use with
 * zip_read and zip_close
 * or returns either false or the number of error if filename
 * does not exist or in case of other error.
 * @deprecated 
 */
function zip_open (string $filename) {}

/**
 * Close a ZIP file archive
 * @link http://www.php.net/manual/en/function.zip-close.php
 * @param resource $zip A ZIP file previously opened with zip_open.
 * @return void 
 * @deprecated 
 */
function zip_close ($zip): void {}

/**
 * Read next entry in a ZIP file archive
 * @link http://www.php.net/manual/en/function.zip-read.php
 * @param resource $zip A ZIP file previously opened with zip_open.
 * @return mixed a directory entry resource for later use with the
 * zip_entry_... functions, or false if
 * there are no more entries to read, or an error code if an error
 * occurred.
 * @deprecated 
 */
function zip_read ($zip) {}

/**
 * Open a directory entry for reading
 * @link http://www.php.net/manual/en/function.zip-entry-open.php
 * @param resource $zip_dp A valid resource handle returned by zip_open.
 * @param resource $zip_entry A directory entry returned by zip_read.
 * @param string $mode [optional] <p>
 * Any of the modes specified in the documentation of
 * fopen.
 * </p>
 * <p>
 * Currently, mode is ignored and is always
 * "rb". This is due to the fact that zip support
 * in PHP is read only access.
 * </p>
 * @return bool true on success or false on failure
 * <p>
 * Unlike fopen and other similar functions,
 * the return value of zip_entry_open only
 * indicates the result of the operation and is not needed for
 * reading or closing the directory entry.
 * </p>
 * @deprecated 
 */
function zip_entry_open ($zip_dp, $zip_entry, string $mode = null): bool {}

/**
 * Close a directory entry
 * @link http://www.php.net/manual/en/function.zip-entry-close.php
 * @param resource $zip_entry A directory entry previously opened zip_entry_open.
 * @return bool true on success or false on failure
 * @deprecated 
 */
function zip_entry_close ($zip_entry): bool {}

/**
 * Read from an open directory entry
 * @link http://www.php.net/manual/en/function.zip-entry-read.php
 * @param resource $zip_entry A directory entry returned by zip_read.
 * @param int $len [optional] <p>
 * The number of bytes to return.
 * </p>
 * <p>
 * This should be the uncompressed length you wish to read.
 * </p>
 * @return mixed the data read, empty string on end of a file, or false on error.
 * @deprecated 
 */
function zip_entry_read ($zip_entry, int $len = null): string|false {}

/**
 * Retrieve the name of a directory entry
 * @link http://www.php.net/manual/en/function.zip-entry-name.php
 * @param resource $zip_entry A directory entry returned by zip_read.
 * @return mixed The name of the directory entry, or false on failure.
 * @deprecated 
 */
function zip_entry_name ($zip_entry): string|false {}

/**
 * Retrieve the compressed size of a directory entry
 * @link http://www.php.net/manual/en/function.zip-entry-compressedsize.php
 * @param resource $zip_entry A directory entry returned by zip_read.
 * @return mixed The compressed size, or false on failure.
 * @deprecated 
 */
function zip_entry_compressedsize ($zip_entry): int|false {}

/**
 * Retrieve the actual file size of a directory entry
 * @link http://www.php.net/manual/en/function.zip-entry-filesize.php
 * @param resource $zip_entry A directory entry returned by zip_read.
 * @return mixed The size of the directory entry, or false on failure.
 * @deprecated 
 */
function zip_entry_filesize ($zip_entry): int|false {}

/**
 * Retrieve the compression method of a directory entry
 * @link http://www.php.net/manual/en/function.zip-entry-compressionmethod.php
 * @param resource $zip_entry A directory entry returned by zip_read.
 * @return mixed The compression method, or false on failure.
 * @deprecated 
 */
function zip_entry_compressionmethod ($zip_entry): string|false {}

// End of zip v.1.21.1
