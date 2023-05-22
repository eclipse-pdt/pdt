<?php

// Start of zip v.1.21.1

/**
 * A file archive, compressed with Zip.
 * @link http://www.php.net/manual/en/class.ziparchive.php
 */
class ZipArchive implements Countable {
	/**
	 * Create the archive if it does not exist.
	 * @var int
	const CREATE = 1;
	/**
	 * Error if archive already exists.
	 * @var int
	const EXCL = 2;
	/**
	 * Perform additional consistency checks on the archive, and error if they fail.
	 * @var int
	const CHECKCONS = 4;
	/**
	 * If archive exists, ignore its current contents.
	 * In other words, handle it the same way as an empty archive.
	 * @var int
	const OVERWRITE = 8;
	/**
	 * Open archive in read only mode.
	 * Available as of PHP 7.4.3 and PECL zip 1.17.1, respectively,
	 * if built against libzip ≥ 1.0.0.
	 * @var int
	const RDONLY = 16;
	/**
	 * Ignore case on name lookup
	 * @var int
	const FL_NOCASE = 1;
	/**
	 * Ignore directory component
	 * @var int
	const FL_NODIR = 2;
	/**
	 * Read compressed data
	 * @var int
	const FL_COMPRESSED = 4;
	/**
	 * Use original data, ignoring changes.
	 * @var int
	const FL_UNCHANGED = 8;
	/**
	 * Force recompression of data.
	 * Available as of PHP 8.0.0 and PECL zip 1.18.0.
	 * @var int
	const FL_RECOMPRESS = 16;
	/**
	 * Read encrypted data (implies FL_COMPRESSED).
	 * Available as of PHP 8.0.0 and PECL zip 1.18.0.
	 * @var int
	const FL_ENCRYPTED = 32;
	/**
	 * If file with name exists, overwrite (replace) it.
	 * Available as of PHP 8.0.0 and PECL zip 1.18.0.
	 * @var int
	const FL_OVERWRITE = 8192;
	/**
	 * In local header.
	 * Available as of PHP 8.0.0 and PECL zip 1.18.0.
	 * @var int
	const FL_LOCAL = 256;
	const FL_CENTRAL = 512;
	/**
	 * Guess string encoding (is default). Available as of PHP 7.0.8.
	 * @var int
	const FL_ENC_GUESS = 0;
	/**
	 * Get unmodified string. Available as of PHP 7.0.8.
	 * @var int
	const FL_ENC_RAW = 64;
	/**
	 * Follow specification strictly. Available as of PHP 7.0.8.
	 * @var int
	const FL_ENC_STRICT = 128;
	/**
	 * String is UTF-8 encoded. Available as of PHP 7.0.8.
	 * @var int
	const FL_ENC_UTF_8 = 2048;
	/**
	 * String is CP437 encoded. Available as of PHP 7.0.8.
	 * @var int
	const FL_ENC_CP437 = 4096;
	/**
	 * better of deflate or store.
	 * @var int
	const CM_DEFAULT = -1;
	/**
	 * stored (uncompressed).
	 * @var int
	const CM_STORE = 0;
	/**
	 * shrunk
	 * @var int
	const CM_SHRINK = 1;
	/**
	 * reduced with factor 1
	 * @var int
	const CM_REDUCE_1 = 2;
	/**
	 * reduced with factor 2
	 * @var int
	const CM_REDUCE_2 = 3;
	/**
	 * reduced with factor 3
	 * @var int
	const CM_REDUCE_3 = 4;
	/**
	 * reduced with factor 4
	 * @var int
	const CM_REDUCE_4 = 5;
	/**
	 * imploded
	 * @var int
	const CM_IMPLODE = 6;
	/**
	 * deflated
	 * @var int
	const CM_DEFLATE = 8;
	/**
	 * deflate64
	 * @var int
	const CM_DEFLATE64 = 9;
	/**
	 * PKWARE imploding
	 * @var int
	const CM_PKWARE_IMPLODE = 10;
	/**
	 * BZIP2 algorithm
	 * @var int
	const CM_BZIP2 = 12;
	/**
	 * LZMA algorithm
	 * @var int
	const CM_LZMA = 14;
	/**
	 * LZMA2 algorithm.
	 * Available as of PHP 7.4.3 and PECL zip 1.16.0, respectively,
	 * if built against libzip ≥ 1.6.0.
	 * @var int
	const CM_LZMA2 = 33;
	/**
	 * Zstandard algorithm.
	 * Available as of PHP 8.0.0 and PECL zip 1.19.1, respectively,
	 * if built against libzip ≥ 1.8.0.
	 * @var int
	const CM_ZSTD = 93;
	/**
	 * XZ algorithm.
	 * Available as of PHP 7.4.3 and PECL zip 1.16.1, respectively,
	 * if built against libzip ≥ 1.6.0.
	 * @var int
	const CM_XZ = 95;
	const CM_TERSE = 18;
	const CM_LZ77 = 19;
	const CM_WAVPACK = 97;
	const CM_PPMD = 98;
	/**
	 * No error.
	 * @var int
	const ER_OK = 0;
	/**
	 * Multi-disk zip archives not supported.
	 * @var int
	const ER_MULTIDISK = 1;
	/**
	 * Renaming temporary file failed.
	 * @var int
	const ER_RENAME = 2;
	/**
	 * Closing zip archive failed
	 * @var int
	const ER_CLOSE = 3;
	/**
	 * Seek error
	 * @var int
	const ER_SEEK = 4;
	/**
	 * Read error
	 * @var int
	const ER_READ = 5;
	/**
	 * Write error
	 * @var int
	const ER_WRITE = 6;
	/**
	 * CRC error
	 * @var int
	const ER_CRC = 7;
	/**
	 * Containing zip archive was closed
	 * @var int
	const ER_ZIPCLOSED = 8;
	/**
	 * No such file.
	 * @var int
	const ER_NOENT = 9;
	/**
	 * File already exists
	 * @var int
	const ER_EXISTS = 10;
	/**
	 * Can't open file
	 * @var int
	const ER_OPEN = 11;
	/**
	 * Failure to create temporary file.
	 * @var int
	const ER_TMPOPEN = 12;
	/**
	 * Zlib error
	 * @var int
	const ER_ZLIB = 13;
	/**
	 * Memory allocation failure
	 * @var int
	const ER_MEMORY = 14;
	/**
	 * Entry has been changed
	 * @var string
	const ER_CHANGED = 15;
	/**
	 * Compression method not supported.
	 * @var int
	const ER_COMPNOTSUPP = 16;
	/**
	 * Premature EOF
	 * @var int
	const ER_EOF = 17;
	/**
	 * Invalid argument
	 * @var int
	const ER_INVAL = 18;
	/**
	 * Not a zip archive
	 * @var int
	const ER_NOZIP = 19;
	/**
	 * Internal error
	 * @var int
	const ER_INTERNAL = 20;
	/**
	 * Zip archive inconsistent
	 * @var int
	const ER_INCONS = 21;
	/**
	 * Can't remove file
	 * @var int
	const ER_REMOVE = 22;
	/**
	 * Entry has been deleted
	 * @var int
	const ER_DELETED = 23;
	/**
	 * Encryption method not supported.
	 * Available as of PHP 7.4.3 and PECL zip 1.16.1, respectively.
	 * @var int
	const ER_ENCRNOTSUPP = 24;
	/**
	 * Read-only archive.
	 * Available as of PHP 7.4.3 and PECL zip 1.16.1, respectively.
	 * @var int
	const ER_RDONLY = 25;
	/**
	 * No password provided.
	 * Available as of PHP 7.4.3 and PECL zip 1.16.1, respectively.
	 * @var int
	const ER_NOPASSWD = 26;
	/**
	 * Wrong password provided.
	 * Available as of PHP 7.4.3 and PECL zip 1.16.1, respectively.
	 * @var int
	const ER_WRONGPASSWD = 27;
	const ER_OPNOTSUPP = 28;
	const ER_INUSE = 29;
	const ER_TELL = 30;
	const ER_COMPRESSED_DATA = 31;
	/**
	 * Operation cancelled.
	 * Available as of PHP 7.4.3 and PECL zip 1.16.1, respectively,
	 * if built against libzip ≥ 1.6.0.
	 * @var int
	const ER_CANCELLED = 32;
	/**
	 * Since PECL zip 1.12.4
	 * @var int
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
	/**
	 * No encryption. Available as of PHP 7.2.0 and PECL zip 1.14.0, respectively.
	 * @var int
	const EM_NONE = 0;
	/**
	 * Traditional PKWARE encryption. Available as of PHP 8.0.0 and PECL zip 1.19.0, respectively.
	 * @var int
	const EM_TRAD_PKWARE = 1;
	/**
	 * AES 128 encryption. Available as of PHP 7.2.0 and PECL zip 1.14.0, respectively,
	 * if built against libzip ≥ 1.2.0.
	 * @var int
	const EM_AES_128 = 257;
	/**
	 * AES 192 encryption. Available as of PHP 7.2.0 and PECL zip 1.14.0, respectively,
	 * if built against libzip ≥ 1.2.0.
	 * @var int
	const EM_AES_192 = 258;
	/**
	 * AES 256 encryption. Available as of PHP 7.2.0 and PECL zip 1.14.0, respectively,
	 * if built against libzip ≥ 1.2.0.
	 * @var int
	const EM_AES_256 = 259;
	/**
	 * Unknown encryption algorithm. Available as of PHP 8.0.0 and PECL zip 1.19.0, respectively.
	 * @var int
	const EM_UNKNOWN = 65535;
	/**
	 * Zip library version. Available as of PHP 7.4.3 and PECL zip 1.16.0.
	 * @var string
	const LIBZIP_VERSION = "1.9.2";


	/**
	 * Index value of last added entry (file or directory).
	 * Available as of PHP 8.0.0 and PECL zip 1.18.0.
	 * @var int
	 * @link http://www.php.net/manual/en/class.ziparchive.php#ziparchive.props.lastid
	 */
	public readonly int $lastId;

	/**
	 * Status of the Zip Archive.
	 * Available for closed archive, as of PHP 8.0.0 and PECL zip 1.18.0.
	 * @var int
	 * @link http://www.php.net/manual/en/class.ziparchive.php#ziparchive.props.status
	 */
	public readonly int $status;

	/**
	 * System status of the Zip Archive.
	 * Available for closed archive, as of PHP 8.0.0 and PECL zip 1.18.0.
	 * @var int
	 * @link http://www.php.net/manual/en/class.ziparchive.php#ziparchive.props.statussys
	 */
	public readonly int $statusSys;

	/**
	 * Number of files in archive
	 * @var int
	 * @link http://www.php.net/manual/en/class.ziparchive.php#ziparchive.props.numfiles
	 */
	public readonly int $numFiles;

	/**
	 * File name in the file system
	 * @var string
	 * @link http://www.php.net/manual/en/class.ziparchive.php#ziparchive.props.filename
	 */
	public readonly string $filename;

	/**
	 * Comment for the archive
	 * @var string
	 * @link http://www.php.net/manual/en/class.ziparchive.php#ziparchive.props.comment
	 */
	public readonly string $comment;

	/**
	 * Open a ZIP file archive
	 * @link http://www.php.net/manual/en/ziparchive.open.php
	 * @param string $filename 
	 * @param int $flags [optional] 
	 * @return bool|int Returns true on success, false or one of the following error codes on error:
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
	public function open (string $filename, int $flags = null): bool|int {}

	/**
	 * Set the password for the active archive
	 * @link http://www.php.net/manual/en/ziparchive.setpassword.php
	 * @param string $password The password to be used for the archive.
	 * @return bool Returns true on success or false on failure.
	 */
	public function setPassword (string $password): bool {}

	/**
	 * Close the active archive (opened or newly created)
	 * @link http://www.php.net/manual/en/ziparchive.close.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function close (): bool {}

	/**
	 * Counts the number of files in the archive
	 * @link http://www.php.net/manual/en/ziparchive.count.php
	 * @return int Returns the number of files in the archive.
	 */
	public function count (): int {}

	/**
	 * Returns the status error message, system and/or zip messages
	 * @link http://www.php.net/manual/en/ziparchive.getstatusstring.php
	 * @return string Returns a string with the status message.
	 */
	public function getStatusString (): string {}

	/**
	 * Clear the status error message, system and/or zip messages
	 * @link http://www.php.net/manual/en/ziparchive.clearerror.php
	 * @return void No value is returned.
	 */
	public function clearError (): void {}

	/**
	 * Add a new directory
	 * @link http://www.php.net/manual/en/ziparchive.addemptydir.php
	 * @param string $dirname 
	 * @param int $flags [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function addEmptyDir (string $dirname, int $flags = null): bool {}

	/**
	 * Add a file to a ZIP archive using its contents
	 * @link http://www.php.net/manual/en/ziparchive.addfromstring.php
	 * @param string $name 
	 * @param string $content 
	 * @param int $flags [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function addFromString (string $name, string $content, int $flags = \ZipArchive::FL_OVERWRITE): bool {}

	/**
	 * Adds a file to a ZIP archive from the given path
	 * @link http://www.php.net/manual/en/ziparchive.addfile.php
	 * @param string $filepath 
	 * @param string $entryname [optional] 
	 * @param int $start [optional] 
	 * @param int $length [optional] 
	 * @param int $flags [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function addFile (string $filepath, string $entryname = '""', int $start = null, int $length = null, int $flags = \ZipArchive::FL_OVERWRITE): bool {}

	/**
	 * Replace file in ZIP archive with a given path
	 * @link http://www.php.net/manual/en/ziparchive.replacefile.php
	 * @param string $filepath 
	 * @param int $index 
	 * @param int $start [optional] 
	 * @param int $length [optional] 
	 * @param int $flags [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function replaceFile (string $filepath, int $index, int $start = null, int $length = null, int $flags = null): bool {}

	/**
	 * Add files from a directory by glob pattern
	 * @link http://www.php.net/manual/en/ziparchive.addglob.php
	 * @param string $pattern A glob pattern against which files will be matched.
	 * @param int $flags [optional] A bit mask of glob() flags.
	 * @param array $options [optional] An associative array of options. Available options are:
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
	 * <p>"add_path"</p>
	 * <p>Prefix to prepend when translating to the local path of the file within
	 * the archive. This is applied after any remove operations defined by the
	 * "remove_path" or "remove_all_path"
	 * options.</p>
	 * <p>"remove_path"</p>
	 * <p>Prefix to remove from matching file paths before adding to the archive.</p>
	 * <p>"remove_all_path"</p>
	 * <p>true to use the file name only and add to the root of the archive.</p>
	 * <p>"flags"</p>
	 * <p>Bitmask consisting of
	 * ZipArchive::FL_OVERWRITE,
	 * ZipArchive::FL_ENC_GUESS,
	 * ZipArchive::FL_ENC_UTF_8,
	 * ZipArchive::FL_ENC_CP437.
	 * The behaviour of these constants is described on the
	 * ZIP constants page.</p>
	 * <p>"comp_method"</p>
	 * <p>Compression method, one of the ZipArchive::CM_&#42;
	 * constants, see ZIP constants page.</p>
	 * <p>"comp_flags"</p>
	 * <p>Compression level.</p>
	 * <p>"enc_method"</p>
	 * <p>Encryption method, one of the ZipArchive::EM_&#42;
	 * constants, see ZIP constants page.</p>
	 * <p>"enc_password"</p>
	 * <p>Password used for encryption.</p>
	 * @return array|false An array of added files on success or false on failure
	 */
	public function addGlob (string $pattern, int $flags = null, array $options = '[]'): array|false {}

	/**
	 * Add files from a directory by PCRE pattern
	 * @link http://www.php.net/manual/en/ziparchive.addpattern.php
	 * @param string $pattern A PCRE pattern against which files will be matched.
	 * @param string $path [optional] The directory that will be scanned. Defaults to the current working directory.
	 * @param array $options [optional] An associative array of options accepted by ZipArchive::addGlob.
	 * @return array|false An array of added files on success or false on failure
	 */
	public function addPattern (string $pattern, string $path = '"."', array $options = '[]'): array|false {}

	/**
	 * Renames an entry defined by its index
	 * @link http://www.php.net/manual/en/ziparchive.renameindex.php
	 * @param int $index 
	 * @param string $new_name 
	 * @return bool Returns true on success or false on failure.
	 */
	public function renameIndex (int $index, string $new_name): bool {}

	/**
	 * Renames an entry defined by its name
	 * @link http://www.php.net/manual/en/ziparchive.renamename.php
	 * @param string $name 
	 * @param string $new_name 
	 * @return bool Returns true on success or false on failure.
	 */
	public function renameName (string $name, string $new_name): bool {}

	/**
	 * Set the comment of a ZIP archive
	 * @link http://www.php.net/manual/en/ziparchive.setarchivecomment.php
	 * @param string $comment 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setArchiveComment (string $comment): bool {}

	/**
	 * Returns the Zip archive comment
	 * @link http://www.php.net/manual/en/ziparchive.getarchivecomment.php
	 * @param int $flags [optional] 
	 * @return string|false Returns the Zip archive comment or false on failure.
	 */
	public function getArchiveComment (int $flags = null): string|false {}

	/**
	 * Set the comment of an entry defined by its index
	 * @link http://www.php.net/manual/en/ziparchive.setcommentindex.php
	 * @param int $index 
	 * @param string $comment 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setCommentIndex (int $index, string $comment): bool {}

	/**
	 * Set the comment of an entry defined by its name
	 * @link http://www.php.net/manual/en/ziparchive.setcommentname.php
	 * @param string $name 
	 * @param string $comment 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setCommentName (string $name, string $comment): bool {}

	/**
	 * Set the modification time of an entry defined by its index
	 * @link http://www.php.net/manual/en/ziparchive.setmtimeindex.php
	 * @param int $index 
	 * @param int $timestamp 
	 * @param int $flags [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setMtimeIndex (int $index, int $timestamp, int $flags = null): bool {}

	/**
	 * Set the modification time of an entry defined by its name
	 * @link http://www.php.net/manual/en/ziparchive.setmtimename.php
	 * @param string $name 
	 * @param int $timestamp 
	 * @param int $flags [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setMtimeName (string $name, int $timestamp, int $flags = null): bool {}

	/**
	 * Returns the comment of an entry using the entry index
	 * @link http://www.php.net/manual/en/ziparchive.getcommentindex.php
	 * @param int $index 
	 * @param int $flags [optional] 
	 * @return string|false Returns the comment on success or false on failure.
	 */
	public function getCommentIndex (int $index, int $flags = null): string|false {}

	/**
	 * Returns the comment of an entry using the entry name
	 * @link http://www.php.net/manual/en/ziparchive.getcommentname.php
	 * @param string $name 
	 * @param int $flags [optional] 
	 * @return string|false Returns the comment on success or false on failure.
	 */
	public function getCommentName (string $name, int $flags = null): string|false {}

	/**
	 * Delete an entry in the archive using its index
	 * @link http://www.php.net/manual/en/ziparchive.deleteindex.php
	 * @param int $index 
	 * @return bool Returns true on success or false on failure.
	 */
	public function deleteIndex (int $index): bool {}

	/**
	 * Delete an entry in the archive using its name
	 * @link http://www.php.net/manual/en/ziparchive.deletename.php
	 * @param string $name 
	 * @return bool Returns true on success or false on failure.
	 */
	public function deleteName (string $name): bool {}

	/**
	 * Get the details of an entry defined by its name
	 * @link http://www.php.net/manual/en/ziparchive.statname.php
	 * @param string $name 
	 * @param int $flags [optional] 
	 * @return array|false Returns an array containing the entry details or false on failure.
	 */
	public function statName (string $name, int $flags = null): array|false {}

	/**
	 * Get the details of an entry defined by its index
	 * @link http://www.php.net/manual/en/ziparchive.statindex.php
	 * @param int $index 
	 * @param int $flags [optional] 
	 * @return array|false Returns an array containing the entry details or false on failure.
	 */
	public function statIndex (int $index, int $flags = null): array|false {}

	/**
	 * Returns the index of the entry in the archive
	 * @link http://www.php.net/manual/en/ziparchive.locatename.php
	 * @param string $name 
	 * @param int $flags [optional] 
	 * @return int|false Returns the index of the entry on success or false on failure.
	 */
	public function locateName (string $name, int $flags = null): int|false {}

	/**
	 * Returns the name of an entry using its index
	 * @link http://www.php.net/manual/en/ziparchive.getnameindex.php
	 * @param int $index 
	 * @param int $flags [optional] 
	 * @return string|false Returns the name on success or false on failure.
	 */
	public function getNameIndex (int $index, int $flags = null): string|false {}

	/**
	 * Revert all global changes done in the archive
	 * @link http://www.php.net/manual/en/ziparchive.unchangearchive.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function unchangeArchive (): bool {}

	/**
	 * Undo all changes done in the archive
	 * @link http://www.php.net/manual/en/ziparchive.unchangeall.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function unchangeAll (): bool {}

	/**
	 * Revert all changes done to an entry at the given index
	 * @link http://www.php.net/manual/en/ziparchive.unchangeindex.php
	 * @param int $index 
	 * @return bool Returns true on success or false on failure.
	 */
	public function unchangeIndex (int $index): bool {}

	/**
	 * Revert all changes done to an entry with the given name
	 * @link http://www.php.net/manual/en/ziparchive.unchangename.php
	 * @param string $name 
	 * @return bool Returns true on success or false on failure.
	 */
	public function unchangeName (string $name): bool {}

	/**
	 * Extract the archive contents
	 * @link http://www.php.net/manual/en/ziparchive.extractto.php
	 * @param string $pathto 
	 * @param array|string|null $files [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function extractTo (string $pathto, array|string|null $files = null): bool {}

	/**
	 * Returns the entry contents using its name
	 * @link http://www.php.net/manual/en/ziparchive.getfromname.php
	 * @param string $name 
	 * @param int $len [optional] 
	 * @param int $flags [optional] 
	 * @return string|false Returns the contents of the entry on success or false on failure.
	 */
	public function getFromName (string $name, int $len = null, int $flags = null): string|false {}

	/**
	 * Returns the entry contents using its index
	 * @link http://www.php.net/manual/en/ziparchive.getfromindex.php
	 * @param int $index 
	 * @param int $len [optional] 
	 * @param int $flags [optional] 
	 * @return string|false Returns the contents of the entry on success or false on failure.
	 */
	public function getFromIndex (int $index, int $len = null, int $flags = null): string|false {}

	/**
	 * Get a file handler to the entry defined by its index (read only)
	 * @link http://www.php.net/manual/en/ziparchive.getstreamindex.php
	 * @param int $index 
	 * @param int $flags [optional] 
	 * @return resource|false Returns a file pointer (resource) on success or false on failure.
	 */
	public function getStreamIndex (int $index, int $flags = null) {}

	/**
	 * Get a file handler to the entry defined by its name (read only)
	 * @link http://www.php.net/manual/en/ziparchive.getstreamname.php
	 * @param string $name 
	 * @param int $flags [optional] 
	 * @return resource|false Returns a file pointer (resource) on success or false on failure.
	 */
	public function getStreamName (string $name, int $flags = null) {}

	/**
	 * Get a file handler to the entry defined by its name (read only)
	 * @link http://www.php.net/manual/en/ziparchive.getstream.php
	 * @param string $name 
	 * @return resource|false Returns a file pointer (resource) on success or false on failure.
	 */
	public function getStream (string $name) {}

	/**
	 * Set the external attributes of an entry defined by its name
	 * @link http://www.php.net/manual/en/ziparchive.setexternalattributesname.php
	 * @param string $name 
	 * @param int $opsys 
	 * @param int $attr 
	 * @param int $flags [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setExternalAttributesName (string $name, int $opsys, int $attr, int $flags = null): bool {}

	/**
	 * Set the external attributes of an entry defined by its index
	 * @link http://www.php.net/manual/en/ziparchive.setexternalattributesindex.php
	 * @param int $index 
	 * @param int $opsys 
	 * @param int $attr 
	 * @param int $flags [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setExternalAttributesIndex (int $index, int $opsys, int $attr, int $flags = null): bool {}

	/**
	 * Retrieve the external attributes of an entry defined by its name
	 * @link http://www.php.net/manual/en/ziparchive.getexternalattributesname.php
	 * @param string $name 
	 * @param int $opsys 
	 * @param int $attr 
	 * @param int $flags [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function getExternalAttributesName (string $name, int &$opsys, int &$attr, int $flags = null): bool {}

	/**
	 * Retrieve the external attributes of an entry defined by its index
	 * @link http://www.php.net/manual/en/ziparchive.getexternalattributesindex.php
	 * @param int $index 
	 * @param int $opsys 
	 * @param int $attr 
	 * @param int $flags [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function getExternalAttributesIndex (int $index, int &$opsys, int &$attr, int $flags = null): bool {}

	/**
	 * Set the compression method of an entry defined by its name
	 * @link http://www.php.net/manual/en/ziparchive.setcompressionname.php
	 * @param string $name 
	 * @param int $method 
	 * @param int $compflags [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setCompressionName (string $name, int $method, int $compflags = null): bool {}

	/**
	 * Set the compression method of an entry defined by its index
	 * @link http://www.php.net/manual/en/ziparchive.setcompressionindex.php
	 * @param int $index 
	 * @param int $method 
	 * @param int $compflags [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setCompressionIndex (int $index, int $method, int $compflags = null): bool {}

	/**
	 * Set the encryption method of an entry defined by its name
	 * @link http://www.php.net/manual/en/ziparchive.setencryptionname.php
	 * @param string $name 
	 * @param int $method 
	 * @param string|null $password [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setEncryptionName (string $name, int $method, ?string $password = null): bool {}

	/**
	 * Set the encryption method of an entry defined by its index
	 * @link http://www.php.net/manual/en/ziparchive.setencryptionindex.php
	 * @param int $index 
	 * @param int $method 
	 * @param string|null $password [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setEncryptionIndex (int $index, int $method, ?string $password = null): bool {}

	/**
	 * Register a callback to provide updates during archive close.
	 * @link http://www.php.net/manual/en/ziparchive.registerprogresscallback.php
	 * @param float $rate 
	 * @param callable $callback 
	 * @return bool Returns true on success or false on failure.
	 */
	public function registerProgressCallback (float $rate, callable $callback): bool {}

	/**
	 * Register a callback to allow cancellation during archive close.
	 * @link http://www.php.net/manual/en/ziparchive.registercancelcallback.php
	 * @param callable $callback 
	 * @return bool Returns true on success or false on failure.
	 */
	public function registerCancelCallback (callable $callback): bool {}

	/**
	 * Check if a compression method is supported by libzip
	 * @link http://www.php.net/manual/en/ziparchive.iscompressionmethoddupported.php
	 * @param int $method 
	 * @param bool $enc [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public static function isCompressionMethodSupported (int $method, bool $enc = true): bool {}

	/**
	 * Check if a encryption method is supported by libzip
	 * @link http://www.php.net/manual/en/ziparchive.isencryptionmethoddupported.php
	 * @param int $method 
	 * @param bool $enc [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public static function isEncryptionMethodSupported (int $method, bool $enc = true): bool {}

}

/**
 * Open a ZIP file archive
 * @link http://www.php.net/manual/en/function.zip-open.php
 * @param string $filename 
 * @return resource|int|false Returns a resource handle for later use with
 * zip_read and zip_close
 * or returns either false or the number of error if filename
 * does not exist or in case of other error.
 * @deprecated 1
 */
function zip_open (string $filename) {}

/**
 * Close a ZIP file archive
 * @link http://www.php.net/manual/en/function.zip-close.php
 * @param resource $zip 
 * @return void No value is returned.
 * @deprecated 1
 */
function zip_close ($zip): void {}

/**
 * Read next entry in a ZIP file archive
 * @link http://www.php.net/manual/en/function.zip-read.php
 * @param resource $zip 
 * @return resource|false Returns a directory entry resource for later use with the
 * zip_entry_... functions, or false if
 * there are no more entries to read, or an error code if an error
 * occurred.
 * @deprecated 1
 */
function zip_read ($zip) {}

/**
 * Open a directory entry for reading
 * @link http://www.php.net/manual/en/function.zip-entry-open.php
 * @param resource $zip_dp 
 * @param resource $zip_entry 
 * @param string $mode [optional] 
 * @return bool Returns true on success or false on failure.
 * <p>Unlike fopen and other similar functions,
 * the return value of zip_entry_open only
 * indicates the result of the operation and is not needed for
 * reading or closing the directory entry.</p>
 * @deprecated 1
 */
function zip_entry_open ($zip_dp, $zip_entry, string $mode = '"rb"'): bool {}

/**
 * Close a directory entry
 * @link http://www.php.net/manual/en/function.zip-entry-close.php
 * @param resource $zip_entry 
 * @return bool Returns true on success or false on failure.
 * @deprecated 1
 */
function zip_entry_close ($zip_entry): bool {}

/**
 * Read from an open directory entry
 * @link http://www.php.net/manual/en/function.zip-entry-read.php
 * @param resource $zip_entry 
 * @param int $len [optional] 
 * @return string|false Returns the data read, empty string on end of a file, or false on error.
 * @deprecated 1
 */
function zip_entry_read ($zip_entry, int $len = 1024): string|false {}

/**
 * Retrieve the name of a directory entry
 * @link http://www.php.net/manual/en/function.zip-entry-name.php
 * @param resource $zip_entry 
 * @return string|false The name of the directory entry, or false on failure.
 * @deprecated 1
 */
function zip_entry_name ($zip_entry): string|false {}

/**
 * Retrieve the compressed size of a directory entry
 * @link http://www.php.net/manual/en/function.zip-entry-compressedsize.php
 * @param resource $zip_entry 
 * @return int|false The compressed size, or false on failure.
 * @deprecated 1
 */
function zip_entry_compressedsize ($zip_entry): int|false {}

/**
 * Retrieve the actual file size of a directory entry
 * @link http://www.php.net/manual/en/function.zip-entry-filesize.php
 * @param resource $zip_entry 
 * @return int|false The size of the directory entry, or false on failure.
 * @deprecated 1
 */
function zip_entry_filesize ($zip_entry): int|false {}

/**
 * Retrieve the compression method of a directory entry
 * @link http://www.php.net/manual/en/function.zip-entry-compressionmethod.php
 * @param resource $zip_entry 
 * @return string|false The compression method, or false on failure.
 * @deprecated 1
 */
function zip_entry_compressionmethod ($zip_entry): string|false {}

// End of zip v.1.21.1
