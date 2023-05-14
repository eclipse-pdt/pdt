<?php

// Start of Phar v.8.0.28

/**
 * The PharException class provides a phar-specific exception class
 * for try/catch blocks.
 * @link http://www.php.net/manual/en/class.pharexception.php
 */
class PharException extends Exception implements Throwable, Stringable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param ?Throwable|null $previous [optional]
	 */
	public function __construct (string $message = ''int , $code = 0?Throwable|null , $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ??Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

/**
 * The Phar class provides a high-level interface to accessing and creating
 * phar archives.
 * @link http://www.php.net/manual/en/class.phar.php
 */
class Phar extends RecursiveDirectoryIterator implements RecursiveIterator, SeekableIterator, Traversable, Iterator, Stringable, Countable, ArrayAccess {
	const CURRENT_MODE_MASK = 240;
	const CURRENT_AS_PATHNAME = 32;
	const CURRENT_AS_FILEINFO = 0;
	const CURRENT_AS_SELF = 16;
	const KEY_MODE_MASK = 3840;
	const KEY_AS_PATHNAME = 0;
	const FOLLOW_SYMLINKS = 512;
	const KEY_AS_FILENAME = 256;
	const NEW_CURRENT_AND_KEY = 256;
	const OTHER_MODE_MASK = 12288;
	const SKIP_DOTS = 4096;
	const UNIX_PATHS = 8192;
	const BZ2 = 8192;
	const GZ = 4096;
	const NONE = 0;
	const PHAR = 1;
	const TAR = 2;
	const ZIP = 3;
	const COMPRESSED = 61440;
	const PHP = 0;
	const PHPS = 1;
	const MD5 = 1;
	const OPENSSL = 16;
	const SHA1 = 2;
	const SHA256 = 3;
	const SHA512 = 4;


	/**
	 * Construct a Phar archive object
	 * @link http://www.php.net/manual/en/phar.construct.php
	 * @param string $filename
	 * @param int $flags [optional]
	 * @param ?string|null $alias [optional]
	 */
	public function __construct (string $filenameint , $flags = 12288?string|null , $alias = null) {}

	/**
	 * Destructs a Phar archive object
	 * @link http://www.php.net/manual/en/phar.destruct.php
	 */
	public function __destruct () {}

	/**
	 * Add an empty directory to the phar archive
	 * @link http://www.php.net/manual/en/phar.addemptydir.php
	 * @param string $directory The name of the empty directory to create in the phar archive
	 * @return void no return value, exception is thrown on failure.
	 */
	public function addEmptyDir (string $directory) {}

	/**
	 * Add a file from the filesystem to the phar archive
	 * @link http://www.php.net/manual/en/phar.addfile.php
	 * @param string $filename Full or relative path to a file on disk to be added
	 * to the phar archive.
	 * @param mixed $localName [optional] Path that the file will be stored in the archive.
	 * @return void no return value, exception is thrown on failure.
	 */
	public function addFile (string $filename, $localName = null) {}

	/**
	 * Add a file from a string to the phar archive
	 * @link http://www.php.net/manual/en/phar.addfromstring.php
	 * @param string $localName Path that the file will be stored in the archive.
	 * @param string $contents The file contents to store
	 * @return void no return value, exception is thrown on failure.
	 */
	public function addFromString (string $localName, string $contents) {}

	/**
	 * Construct a phar archive from the files within a directory
	 * @link http://www.php.net/manual/en/phar.buildfromdirectory.php
	 * @param string $directory The full or relative path to the directory that contains all files
	 * to add to the archive.
	 * @param string $pattern [optional] An optional pcre regular expression that is used to filter the
	 * list of files. Only file paths matching the regular expression
	 * will be included in the archive.
	 * @return array Phar::buildFromDirectory returns an associative array
	 * mapping internal path of file to the full path of the file on the
	 * filesystem.
	 */
	public function buildFromDirectory (string $directory, string $pattern = null) {}

	/**
	 * Construct a phar archive from an iterator
	 * @link http://www.php.net/manual/en/phar.buildfromiterator.php
	 * @param Traversable $iterator Any iterator that either associatively maps phar file to location or
	 * returns SplFileInfo objects
	 * @param mixed $baseDirectory [optional] For iterators that return SplFileInfo objects, the portion of each
	 * file's full path to remove when adding to the phar archive
	 * @return array Phar::buildFromIterator returns an associative array
	 * mapping internal path of file to the full path of the file on the
	 * filesystem.
	 */
	public function buildFromIterator ($iterator, $baseDirectory = null) {}

	/**
	 * Compresses all files in the current Phar archive
	 * @link http://www.php.net/manual/en/phar.compressfiles.php
	 * @param int $compression Compression must be one of Phar::GZ,
	 * Phar::BZ2 to add compression, or Phar::NONE
	 * to remove compression.
	 * @return void 
	 */
	public function compressFiles (int $compression) {}

	/**
	 * Decompresses all files in the current Phar archive
	 * @link http://www.php.net/manual/en/phar.decompressfiles.php
	 * @return bool true on success or false on failure
	 */
	public function decompressFiles () {}

	/**
	 * Compresses the entire Phar archive using Gzip or Bzip2 compression
	 * @link http://www.php.net/manual/en/phar.compress.php
	 * @param int $compression Compression must be one of Phar::GZ,
	 * Phar::BZ2 to add compression, or Phar::NONE
	 * to remove compression.
	 * @param mixed $extension [optional] By default, the extension is .phar.gz
	 * or .phar.bz2 for compressing phar archives, and
	 * .phar.tar.gz or .phar.tar.bz2 for
	 * compressing tar archives. For decompressing, the default file extensions
	 * are .phar and .phar.tar.
	 * @return mixed a Phar object, or null on failure.
	 */
	public function compress (int $compression, $extension = null) {}

	/**
	 * Decompresses the entire Phar archive
	 * @link http://www.php.net/manual/en/phar.decompress.php
	 * @param mixed $extension [optional] For decompressing, the default file extensions
	 * are .phar and .phar.tar.
	 * Use this parameter to specify another file extension. Be aware
	 * that all executable phar archives must contain .phar
	 * in their filename.
	 * @return mixed A Phar object is returned on success, and null on failure.
	 */
	public function decompress ($extension = null) {}

	/**
	 * Convert a phar archive to another executable phar archive file format
	 * @link http://www.php.net/manual/en/phar.converttoexecutable.php
	 * @param mixed $format [optional] This should be one of Phar::PHAR, Phar::TAR,
	 * or Phar::ZIP. If set to null, the existing file format
	 * will be preserved.
	 * @param mixed $compression [optional] This should be one of Phar::NONE for no whole-archive
	 * compression, Phar::GZ for zlib-based compression, and
	 * Phar::BZ2 for bzip-based compression.
	 * @param mixed $extension [optional] <p>
	 * This parameter is used to override the default file extension for a
	 * converted archive. Note that all zip- and tar-based phar archives must contain
	 * .phar in their file extension in order to be processed as a
	 * phar archive.
	 * </p>
	 * <p>
	 * If converting to a phar-based archive, the default extensions are
	 * .phar, .phar.gz, or .phar.bz2
	 * depending on the specified compression. For tar-based phar archives, the
	 * default extensions are .phar.tar, .phar.tar.gz,
	 * and .phar.tar.bz2. For zip-based phar archives, the
	 * default extension is .phar.zip.
	 * </p>
	 * @return mixed The method returns a Phar object on success,
	 * or null on failure.
	 */
	public function convertToExecutable ($format = null, $compression = null, $extension = null) {}

	/**
	 * Convert a phar archive to a non-executable tar or zip file
	 * @link http://www.php.net/manual/en/phar.converttodata.php
	 * @param mixed $format [optional] This should be one of Phar::TAR
	 * or Phar::ZIP. If set to null, the existing file format
	 * will be preserved.
	 * @param mixed $compression [optional] This should be one of Phar::NONE for no whole-archive
	 * compression, Phar::GZ for zlib-based compression, and
	 * Phar::BZ2 for bzip-based compression.
	 * @param mixed $extension [optional] <p>
	 * This parameter is used to override the default file extension for a
	 * converted archive. Note that .phar cannot be used
	 * anywhere in the filename for a non-executable tar or zip archive.
	 * </p>
	 * <p>
	 * If converting to a tar-based phar archive, the
	 * default extensions are .tar, .tar.gz,
	 * and .tar.bz2 depending on specified compression.
	 * For zip-based archives, the
	 * default extension is .zip.
	 * </p>
	 * @return mixed The method returns a PharData object on success,
	 * or null on failure.
	 */
	public function convertToData ($format = null, $compression = null, $extension = null) {}

	/**
	 * Copy a file internal to the phar archive to another new file within the phar
	 * @link http://www.php.net/manual/en/phar.copy.php
	 * @param string $from 
	 * @param string $to 
	 * @return bool returns true on success, but it is safer to encase method call in a
	 * try/catch block and assume success if no exception is thrown.
	 */
	public function copy (string $from, string $to) {}

	/**
	 * Returns the number of entries (files) in the Phar archive
	 * @link http://www.php.net/manual/en/phar.count.php
	 * @param int $mode [optional] mode is an integer value specifying the counting mode to be used.
	 * By default, it is set to COUNT_NORMAL,
	 * which counts only the number of items in the archive that have not been deleted or hidden.
	 * When set to COUNT_RECURSIVE, it counts all items in the archive,
	 * including those that have been deleted or hidden.
	 * @return int The number of files contained within this phar, or 0 (the number zero)
	 * if none.
	 */
	public function count (int $mode = null) {}

	/**
	 * Delete a file within a phar archive
	 * @link http://www.php.net/manual/en/phar.delete.php
	 * @param string $localName Path within an archive to the file to delete.
	 * @return bool returns true on success, but it is better to check for thrown exception,
	 * and assume success if none is thrown.
	 */
	public function delete (string $localName) {}

	/**
	 * Deletes the global metadata of the phar
	 * @link http://www.php.net/manual/en/phar.delmetadata.php
	 * @return bool returns true on success, but it is better to check for thrown exception,
	 * and assume success if none is thrown.
	 */
	public function delMetadata () {}

	/**
	 * Extract the contents of a phar archive to a directory
	 * @link http://www.php.net/manual/en/phar.extractto.php
	 * @param string $directory Path to extract the given files to
	 * @param mixed $files [optional] The name of a file or directory to extract, or an array of files/directories to extract,
	 * null to skip this param
	 * @param bool $overwrite [optional] Set to true to enable overwriting existing files
	 * @return bool returns true on success, but it is better to check for thrown exception,
	 * and assume success if none is thrown.
	 */
	public function extractTo (string $directory, $files = null, bool $overwrite = null) {}

	/**
	 * Get the alias for Phar
	 * @link http://www.php.net/manual/en/phar.getalias.php
	 * @return mixed the alias or null if there's no alias.
	 */
	public function getAlias () {}

	/**
	 * Get the real path to the Phar archive on disk
	 * @link http://www.php.net/manual/en/phar.getpath.php
	 * @return string 
	 */
	public function getPath () {}

	/**
	 * Returns phar archive meta-data
	 * @link http://www.php.net/manual/en/phar.getmetadata.php
	 * @param array $unserializeOptions [optional] 
	 * @return mixed Any PHP value that can be serialized and is stored as meta-data for the Phar archive,
	 * or null if no meta-data is stored.
	 */
	public function getMetadata (array $unserializeOptions = null) {}

	/**
	 * Return whether phar was modified
	 * @link http://www.php.net/manual/en/phar.getmodified.php
	 * @return bool true if the phar has been modified since opened, false if not.
	 */
	public function getModified () {}

	/**
	 * Return MD5/SHA1/SHA256/SHA512/OpenSSL signature of a Phar archive
	 * @link http://www.php.net/manual/en/phar.getsignature.php
	 * @return mixed Array with the opened archive's signature in hash key and MD5,
	 * SHA-1,
	 * SHA-256, SHA-512, or OpenSSL
	 * in hash_type. This signature is a hash calculated on the
	 * entire phar's contents, and may be used to verify the integrity of the archive.
	 * A valid signature is absolutely required of all executable phar archives if the
	 * phar.require_hash INI variable
	 * is set to true.
	 * If there is no signature, the function returns false.
	 */
	public function getSignature () {}

	/**
	 * Return the PHP loader or bootstrap stub of a Phar archive
	 * @link http://www.php.net/manual/en/phar.getstub.php
	 * @return string a string containing the contents of the bootstrap loader (stub) of
	 * the current Phar archive.
	 */
	public function getStub () {}

	/**
	 * Return version info of Phar archive
	 * @link http://www.php.net/manual/en/phar.getversion.php
	 * @return string The opened archive's API version. This is not to be confused with
	 * the API version that the loaded phar extension will use to create
	 * new phars. Each Phar archive has the API version hard-coded into
	 * its manifest. See Phar file format
	 * documentation for more information.
	 */
	public function getVersion () {}

	/**
	 * Returns whether phar has global meta-data
	 * @link http://www.php.net/manual/en/phar.hasmetadata.php
	 * @return bool true if meta-data has been set, and false if not.
	 */
	public function hasMetadata () {}

	/**
	 * Used to determine whether Phar write operations are being buffered, or are flushing directly to disk
	 * @link http://www.php.net/manual/en/phar.isbuffering.php
	 * @return bool true if the write operations are being buffer, false otherwise.
	 */
	public function isBuffering () {}

	/**
	 * Returns Phar::GZ or PHAR::BZ2 if the entire phar archive is compressed (.tar.gz/tar.bz and so on)
	 * @link http://www.php.net/manual/en/phar.iscompressed.php
	 * @return mixed Phar::GZ, Phar::BZ2 or false.
	 */
	public function isCompressed () {}

	/**
	 * Returns true if the phar archive is based on the tar/phar/zip file format depending on the parameter
	 * @link http://www.php.net/manual/en/phar.isfileformat.php
	 * @param int $format Either Phar::PHAR, Phar::TAR, or
	 * Phar::ZIP to test for the format of the archive.
	 * @return bool true if the phar archive matches the file format requested by the parameter
	 */
	public function isFileFormat (int $format) {}

	/**
	 * Returns true if the phar archive can be modified
	 * @link http://www.php.net/manual/en/phar.iswritable.php
	 * @return bool true if the phar archive can be modified
	 */
	public function isWritable () {}

	/**
	 * Determines whether a file exists in the phar
	 * @link http://www.php.net/manual/en/phar.offsetexists.php
	 * @param string $localName The filename (relative path) to look for in a Phar.
	 * @return bool true if the file exists within the phar, or false if not.
	 */
	public function offsetExists (string $localName) {}

	/**
	 * Gets a PharFileInfo object for a specific file
	 * @link http://www.php.net/manual/en/phar.offsetget.php
	 * @param string $localName The filename (relative path) to look for in a Phar.
	 * @return SplFileInfo A PharFileInfo object is returned that can be used to
	 * iterate over a file's contents or to retrieve information about the current file.
	 */
	public function offsetGet (string $localName) {}

	/**
	 * Set the contents of an internal file to those of an external file
	 * @link http://www.php.net/manual/en/phar.offsetset.php
	 * @param string $localName The filename (relative path) to modify in a Phar.
	 * @param mixed $value Content of the file.
	 * @return void No return values.
	 */
	public function offsetSet (string $localName, $value) {}

	/**
	 * Remove a file from a phar
	 * @link http://www.php.net/manual/en/phar.offsetunset.php
	 * @param string $localName The filename (relative path) to modify in a Phar.
	 * @return void 
	 */
	public function offsetUnset (string $localName) {}

	/**
	 * Set the alias for the Phar archive
	 * @link http://www.php.net/manual/en/phar.setalias.php
	 * @param string $alias A shorthand string that this archive can be referred to in phar
	 * stream wrapper access.
	 * @return bool 
	 */
	public function setAlias (string $alias) {}

	/**
	 * Used to set the PHP loader or bootstrap stub of a Phar archive to the default loader
	 * @link http://www.php.net/manual/en/phar.setdefaultstub.php
	 * @param mixed $index [optional] Relative path within the phar archive to run if accessed on the command-line
	 * @param mixed $webIndex [optional] Relative path within the phar archive to run if accessed through a web browser
	 * @return bool true on success or false on failure
	 */
	public function setDefaultStub ($index = null, $webIndex = null) {}

	/**
	 * Sets phar archive meta-data
	 * @link http://www.php.net/manual/en/phar.setmetadata.php
	 * @param mixed $metadata Any PHP variable containing information to store that describes the phar archive
	 * @return void 
	 */
	public function setMetadata ($metadata) {}

	/**
	 * Set the signature algorithm for a phar and apply it
	 * @link http://www.php.net/manual/en/phar.setsignaturealgorithm.php
	 * @param int $algo One of Phar::MD5,
	 * Phar::SHA1, Phar::SHA256,
	 * Phar::SHA512, or Phar::OPENSSL
	 * @param mixed $privateKey [optional] The contents of an OpenSSL private key, as extracted from a certificate or
	 * OpenSSL key file:
	 * <pre>
	 * <code>&lt;?php
	 * $private = openssl_get_privatekey(file_get_contents('private.pem'));
	 * $pkey = '';
	 * openssl_pkey_export($private, $pkey);
	 * $p-&gt;setSignatureAlgorithm(Phar::OPENSSL, $pkey);
	 * ?&gt;</code>
	 * </pre>
	 * See phar introduction for instructions on
	 * naming and placement of the public key file.
	 * @return void 
	 */
	public function setSignatureAlgorithm (int $algo, $privateKey = null) {}

	/**
	 * Used to set the PHP loader or bootstrap stub of a Phar archive
	 * @link http://www.php.net/manual/en/phar.setstub.php
	 * @param string $stub A string or an open stream handle to use as the executable stub for this
	 * phar archive.
	 * @param int $len [optional] 
	 * @return bool true on success or false on failure
	 */
	public function setStub (string $stub, int $len = null) {}

	/**
	 * Start buffering Phar write operations, do not modify the Phar object on disk
	 * @link http://www.php.net/manual/en/phar.startbuffering.php
	 * @return void 
	 */
	public function startBuffering () {}

	/**
	 * Stop buffering write requests to the Phar archive, and save changes to disk
	 * @link http://www.php.net/manual/en/phar.stopbuffering.php
	 * @return void 
	 */
	public function stopBuffering () {}

	/**
	 * Returns the api version
	 * @link http://www.php.net/manual/en/phar.apiversion.php
	 * @return string The API version string as in &quot;1.0.0&quot;.
	 */
	final public static function apiVersion (): string {}

	/**
	 * Returns whether phar extension supports compression using either zlib or bzip2
	 * @link http://www.php.net/manual/en/phar.cancompress.php
	 * @param int $compression [optional] Either Phar::GZ or Phar::BZ2 can be
	 * used to test whether compression is possible with a specific compression
	 * algorithm (zlib or bzip2).
	 * @return bool true if compression/decompression is available, false if not.
	 */
	final public static function canCompress (int $compression = null): bool {}

	/**
	 * Returns whether phar extension supports writing and creating phars
	 * @link http://www.php.net/manual/en/phar.canwrite.php
	 * @return bool true if write access is enabled, false if it is disabled.
	 */
	final public static function canWrite (): bool {}

	/**
	 * Create a phar-file format specific stub
	 * @link http://www.php.net/manual/en/phar.createdefaultstub.php
	 * @param mixed $index [optional] Relative path within the phar archive to run if accessed on the command-line
	 * @param mixed $webIndex [optional] Relative path within the phar archive to run if accessed through a web browser
	 * @return string a string containing the contents of a customized bootstrap loader (stub)
	 * that allows the created Phar archive to work with or without the Phar extension
	 * enabled.
	 */
	final public static function createDefaultStub ($index = null, $webIndex = null): string {}

	/**
	 * Return array of supported compression algorithms
	 * @link http://www.php.net/manual/en/phar.getsupportedcompression.php
	 * @return array an array containing any of Phar::GZ or
	 * Phar::BZ2, depending on the availability of
	 * the zlib extension or the
	 * bz2 extension.
	 */
	final public static function getSupportedCompression (): array {}

	/**
	 * Return array of supported signature types
	 * @link http://www.php.net/manual/en/phar.getsupportedsignatures.php
	 * @return array an array containing any of MD5, SHA-1,
	 * SHA-256, SHA-512, or OpenSSL.
	 */
	final public static function getSupportedSignatures (): array {}

	/**
	 * Instructs phar to intercept fopen, file_get_contents, opendir, and all of the stat-related functions
	 * @link http://www.php.net/manual/en/phar.interceptfilefuncs.php
	 * @return void 
	 */
	final public static function interceptFileFuncs (): void {}

	/**
	 * Returns whether the given filename is a valid phar filename
	 * @link http://www.php.net/manual/en/phar.isvalidpharfilename.php
	 * @param string $filename The name or full path to a phar archive not yet created
	 * @param bool $executable [optional] This parameter determines whether the filename should be treated as
	 * a phar executable archive, or a data non-executable archive
	 * @return bool true if the filename is valid, false if not.
	 */
	final public static function isValidPharFilename (string $filename, bool $executable = null): bool {}

	/**
	 * Loads any phar archive with an alias
	 * @link http://www.php.net/manual/en/phar.loadphar.php
	 * @param string $filename the full or relative path to the phar archive to open
	 * @param mixed $alias [optional] The alias that may be used to refer to the phar archive. Note
	 * that many phar archives specify an explicit alias inside the
	 * phar archive, and a PharException will be thrown if
	 * a new alias is specified in this case.
	 * @return bool true on success or false on failure
	 */
	final public static function loadPhar (string $filename, $alias = null): bool {}

	/**
	 * Reads the currently executed file (a phar) and registers its manifest
	 * @link http://www.php.net/manual/en/phar.mapphar.php
	 * @param mixed $alias [optional] The alias that can be used in phar:// URLs to
	 * refer to this archive, rather than its full path.
	 * @param int $offset [optional] Unused variable, here for compatibility with PEAR's PHP_Archive.
	 * @return bool true on success or false on failure
	 */
	final public static function mapPhar ($alias = null, int $offset = null): bool {}

	/**
	 * Returns the full path on disk or full phar URL to the currently executing Phar archive
	 * @link http://www.php.net/manual/en/phar.running.php
	 * @param bool $returnPhar [optional] If false, the full path on disk to the phar
	 * archive is returned. If true, a full phar URL is returned.
	 * @return string the filename if valid, empty string otherwise.
	 */
	final public static function running (bool $returnPhar = null): string {}

	/**
	 * Mount an external path or file to a virtual location within the phar archive
	 * @link http://www.php.net/manual/en/phar.mount.php
	 * @param string $pharPath The internal path within the phar archive to use as the mounted path location.
	 * This must be a relative path within the phar archive, and must not already exist.
	 * @param string $externalPath A path or URL to an external file or directory to mount within the phar archive
	 * @return void No return. PharException is thrown on failure.
	 */
	final public static function mount (string $pharPath, string $externalPath): void {}

	/**
	 * Defines a list of up to 4 $_SERVER variables that should be modified for execution
	 * @link http://www.php.net/manual/en/phar.mungserver.php
	 * @param array $variables An array of any of the strings
	 * REQUEST_URI, PHP_SELF,
	 * SCRIPT_NAME and SCRIPT_FILENAME.
	 * Other values trigger an exception, and Phar::mungServer
	 * is case-sensitive.
	 * @return void No return.
	 */
	final public static function mungServer (array $variables): void {}

	/**
	 * Completely remove a phar archive from disk and from memory
	 * @link http://www.php.net/manual/en/phar.unlinkarchive.php
	 * @param string $filename The path on disk to the phar archive.
	 * @return bool true on success or false on failure
	 */
	final public static function unlinkArchive (string $filename): bool {}

	/**
	 * Routes a request from a web browser to an internal file within the phar archive
	 * @link http://www.php.net/manual/en/phar.webphar.php
	 * @param mixed $alias [optional] The alias that can be used in phar:// URLs to
	 * refer to this archive, rather than its full path.
	 * @param mixed $index [optional] The location within the phar of the directory index.
	 * @param mixed $fileNotFoundScript [optional] The location of the script to run when a file is not found. This
	 * script should output the proper HTTP 404 headers.
	 * @param array $mimeTypes [optional] An array mapping additional file extensions to MIME type.
	 * If the default mapping is sufficient, pass an empty array.
	 * By default, these extensions are mapped to these MIME types:
	 * <pre>
	 * <code>&lt;?php
	 * $mimes = array(
	 * 'phps' =&gt; Phar::PHPS, &#47;&#47; pass to highlight_file()
	 * 'c' =&gt; 'text&#47;plain',
	 * 'cc' =&gt; 'text&#47;plain',
	 * 'cpp' =&gt; 'text&#47;plain',
	 * 'c++' =&gt; 'text&#47;plain',
	 * 'dtd' =&gt; 'text&#47;plain',
	 * 'h' =&gt; 'text&#47;plain',
	 * 'log' =&gt; 'text&#47;plain',
	 * 'rng' =&gt; 'text&#47;plain',
	 * 'txt' =&gt; 'text&#47;plain',
	 * 'xsd' =&gt; 'text&#47;plain',
	 * 'php' =&gt; Phar::PHP, &#47;&#47; parse as PHP
	 * 'inc' =&gt; Phar::PHP, &#47;&#47; parse as PHP
	 * 'avi' =&gt; 'video&#47;avi',
	 * 'bmp' =&gt; 'image&#47;bmp',
	 * 'css' =&gt; 'text&#47;css',
	 * 'gif' =&gt; 'image&#47;gif',
	 * 'htm' =&gt; 'text&#47;html',
	 * 'html' =&gt; 'text&#47;html',
	 * 'htmls' =&gt; 'text&#47;html',
	 * 'ico' =&gt; 'image&#47;x-ico',
	 * 'jpe' =&gt; 'image&#47;jpeg',
	 * 'jpg' =&gt; 'image&#47;jpeg',
	 * 'jpeg' =&gt; 'image&#47;jpeg',
	 * 'js' =&gt; 'application&#47;x-javascript',
	 * 'midi' =&gt; 'audio&#47;midi',
	 * 'mid' =&gt; 'audio&#47;midi',
	 * 'mod' =&gt; 'audio&#47;mod',
	 * 'mov' =&gt; 'movie&#47;quicktime',
	 * 'mp3' =&gt; 'audio&#47;mp3',
	 * 'mpg' =&gt; 'video&#47;mpeg',
	 * 'mpeg' =&gt; 'video&#47;mpeg',
	 * 'pdf' =&gt; 'application&#47;pdf',
	 * 'png' =&gt; 'image&#47;png',
	 * 'swf' =&gt; 'application&#47;shockwave-flash',
	 * 'tif' =&gt; 'image&#47;tiff',
	 * 'tiff' =&gt; 'image&#47;tiff',
	 * 'wav' =&gt; 'audio&#47;wav',
	 * 'xbm' =&gt; 'image&#47;xbm',
	 * 'xml' =&gt; 'text&#47;xml',
	 * );
	 * ?&gt;</code>
	 * </pre>
	 * @param mixed $rewrite [optional] <p>
	 * The rewrites function is passed a string as its only parameter and must return a string or false.
	 * </p>
	 * <p>
	 * If you are using fast-cgi or cgi then the parameter passed to the function is the value of the 
	 * $_SERVER['PATH_INFO'] variable. Otherwise, the parameter passed to the function is the value
	 * of the $_SERVER['REQUEST_URI'] variable.
	 * </p>
	 * <p>
	 * If a string is returned it is used as the internal file path. If false is returned then webPhar() will
	 * send a HTTP 403 Denied Code.
	 * </p>
	 * @return void 
	 */
	final public static function webPhar ($alias = null, $index = null, $fileNotFoundScript = null, array $mimeTypes = null, $rewrite = null): void {}

	/**
	 * Returns whether current entry is a directory and not '.' or '..'
	 * @link http://www.php.net/manual/en/recursivedirectoryiterator.haschildren.php
	 * @param bool $allowLinks [optional] 
	 * @return bool whether the current entry is a directory, but not '.' or '..'
	 */
	public function hasChildren (bool $allowLinks = null) {}

	/**
	 * Returns an iterator for the current entry if it is a directory
	 * @link http://www.php.net/manual/en/recursivedirectoryiterator.getchildren.php
	 * @return RecursiveDirectoryIterator The filename, file information, or $this depending on the set flags.
	 * See the FilesystemIterator
	 * constants.
	 */
	public function getChildren () {}

	/**
	 * Get sub path
	 * @link http://www.php.net/manual/en/recursivedirectoryiterator.getsubpath.php
	 * @return string The sub path.
	 */
	public function getSubPath () {}

	/**
	 * Get sub path and name
	 * @link http://www.php.net/manual/en/recursivedirectoryiterator.getsubpathname.php
	 * @return string The sub path (sub directory) and filename.
	 */
	public function getSubPathname () {}

	/**
	 * Rewinds back to the beginning
	 * @link http://www.php.net/manual/en/filesystemiterator.rewind.php
	 * @return void 
	 */
	public function rewind () {}

	/**
	 * Retrieve the key for the current file
	 * @link http://www.php.net/manual/en/filesystemiterator.key.php
	 * @return string the pathname or filename depending on the set flags.
	 * See the FilesystemIterator constants.
	 */
	public function key () {}

	/**
	 * The current file
	 * @link http://www.php.net/manual/en/filesystemiterator.current.php
	 * @return mixed The filename, file information, or $this depending on the set flags.
	 * See the FilesystemIterator constants.
	 */
	public function current () {}

	/**
	 * Get the handling flags
	 * @link http://www.php.net/manual/en/filesystemiterator.getflags.php
	 * @return int The integer value of the set flags.
	 */
	public function getFlags () {}

	/**
	 * Sets handling flags
	 * @link http://www.php.net/manual/en/filesystemiterator.setflags.php
	 * @param int $flags The handling flags to set.
	 * See the FilesystemIterator constants.
	 * @return void 
	 */
	public function setFlags (int $flags) {}

	/**
	 * Return file name of current DirectoryIterator item
	 * @link http://www.php.net/manual/en/directoryiterator.getfilename.php
	 * @return string the file name of the current DirectoryIterator item.
	 */
	public function getFilename () {}

	/**
	 * Gets the file extension
	 * @link http://www.php.net/manual/en/directoryiterator.getextension.php
	 * @return string a string containing the file extension, or an
	 * empty string if the file has no extension.
	 */
	public function getExtension () {}

	/**
	 * Get base name of current DirectoryIterator item
	 * @link http://www.php.net/manual/en/directoryiterator.getbasename.php
	 * @param string $suffix [optional] If the base name ends in suffix, 
	 * this will be cut.
	 * @return string The base name of the current DirectoryIterator item.
	 */
	public function getBasename (string $suffix = null) {}

	/**
	 * Determine if current DirectoryIterator item is '.' or '..'
	 * @link http://www.php.net/manual/en/directoryiterator.isdot.php
	 * @return bool true if the entry is . or ..,
	 * otherwise false
	 */
	public function isDot () {}

	/**
	 * Check whether current DirectoryIterator position is a valid file
	 * @link http://www.php.net/manual/en/directoryiterator.valid.php
	 * @return bool true if the position is valid, otherwise false
	 */
	public function valid () {}

	/**
	 * Move forward to next DirectoryIterator item
	 * @link http://www.php.net/manual/en/directoryiterator.next.php
	 * @return void 
	 */
	public function next () {}

	/**
	 * Seek to a DirectoryIterator item
	 * @link http://www.php.net/manual/en/directoryiterator.seek.php
	 * @param int $offset The zero-based numeric position to seek to.
	 * @return void 
	 */
	public function seek (int $offset) {}

	/**
	 * Get file name as a string
	 * @link http://www.php.net/manual/en/directoryiterator.tostring.php
	 * @return string the file name of the current DirectoryIterator item.
	 */
	public function __toString (): string {}

	/**
	 * Gets the path to the file
	 * @link http://www.php.net/manual/en/splfileinfo.getpathname.php
	 * @return string The path to the file.
	 */
	public function getPathname () {}

	/**
	 * Gets file permissions
	 * @link http://www.php.net/manual/en/splfileinfo.getperms.php
	 * @return mixed the file permissions on success, or false on failure.
	 */
	public function getPerms () {}

	/**
	 * Gets the inode for the file
	 * @link http://www.php.net/manual/en/splfileinfo.getinode.php
	 * @return mixed the inode number for the filesystem object on success, or false on failure.
	 */
	public function getInode () {}

	/**
	 * Gets file size
	 * @link http://www.php.net/manual/en/splfileinfo.getsize.php
	 * @return mixed The filesize in bytes on success, or false on failure.
	 */
	public function getSize () {}

	/**
	 * Gets the owner of the file
	 * @link http://www.php.net/manual/en/splfileinfo.getowner.php
	 * @return mixed The owner id in numerical format on success, or false on failure.
	 */
	public function getOwner () {}

	/**
	 * Gets the file group
	 * @link http://www.php.net/manual/en/splfileinfo.getgroup.php
	 * @return mixed The group id in numerical format on success, or false on failure.
	 */
	public function getGroup () {}

	/**
	 * Gets last access time of the file
	 * @link http://www.php.net/manual/en/splfileinfo.getatime.php
	 * @return mixed the time the file was last accessed on success, or false on failure.
	 */
	public function getATime () {}

	/**
	 * Gets the last modified time
	 * @link http://www.php.net/manual/en/splfileinfo.getmtime.php
	 * @return mixed the last modified time for the file, in a Unix timestamp on success, or false on failure.
	 */
	public function getMTime () {}

	/**
	 * Gets the inode change time
	 * @link http://www.php.net/manual/en/splfileinfo.getctime.php
	 * @return mixed The last change time, in a Unix timestamp on success, or false on failure.
	 */
	public function getCTime () {}

	/**
	 * Gets file type
	 * @link http://www.php.net/manual/en/splfileinfo.gettype.php
	 * @return mixed A string representing the type of the entry.
	 * May be one of file, link,
	 * dir, block, fifo,
	 * char, socket, or unknown, or false on failure.
	 */
	public function getType () {}

	/**
	 * Tells if file is readable
	 * @link http://www.php.net/manual/en/splfileinfo.isreadable.php
	 * @return bool true if readable, false otherwise.
	 */
	public function isReadable () {}

	/**
	 * Tells if the file is executable
	 * @link http://www.php.net/manual/en/splfileinfo.isexecutable.php
	 * @return bool true if executable, false otherwise.
	 */
	public function isExecutable () {}

	/**
	 * Tells if the object references a regular file
	 * @link http://www.php.net/manual/en/splfileinfo.isfile.php
	 * @return bool true if the file exists and is a regular file (not a link), false otherwise.
	 */
	public function isFile () {}

	/**
	 * Tells if the file is a directory
	 * @link http://www.php.net/manual/en/splfileinfo.isdir.php
	 * @return bool true if a directory, false otherwise.
	 */
	public function isDir () {}

	/**
	 * Tells if the file is a link
	 * @link http://www.php.net/manual/en/splfileinfo.islink.php
	 * @return bool true if the file is a link, false otherwise.
	 */
	public function isLink () {}

	/**
	 * Gets the target of a link
	 * @link http://www.php.net/manual/en/splfileinfo.getlinktarget.php
	 * @return mixed the target of the filesystem link on success, or false on failure.
	 */
	public function getLinkTarget () {}

	/**
	 * Gets absolute path to file
	 * @link http://www.php.net/manual/en/splfileinfo.getrealpath.php
	 * @return mixed the path to the file, or false if the file does not exist.
	 */
	public function getRealPath () {}

	/**
	 * Gets an SplFileInfo object for the file
	 * @link http://www.php.net/manual/en/splfileinfo.getfileinfo.php
	 * @param mixed $class [optional] Name of an SplFileInfo derived class to use.
	 * @return SplFileInfo An SplFileInfo object created for the file.
	 */
	public function getFileInfo ($class = null) {}

	/**
	 * Gets an SplFileInfo object for the path
	 * @link http://www.php.net/manual/en/splfileinfo.getpathinfo.php
	 * @param mixed $class [optional] Name of an SplFileInfo derived class to use, or itself if null.
	 * @return mixed an SplFileInfo object for the parent path of the file on success, or null on failure.
	 */
	public function getPathInfo ($class = null) {}

	/**
	 * Gets an SplFileObject object for the file
	 * @link http://www.php.net/manual/en/splfileinfo.openfile.php
	 * @param string $mode [optional] The mode for opening the file. See the fopen
	 * documentation for descriptions of possible modes. The default 
	 * is read only.
	 * @param bool $useIncludePath [optional] parameter.use_include_path
	 * @param mixed $context [optional] parameter.context
	 * @return SplFileObject The opened file as an SplFileObject object.
	 */
	public function openFile (string $mode = null, bool $useIncludePath = null, $context = null) {}

	/**
	 * Sets the class used with SplFileInfo::openFile
	 * @link http://www.php.net/manual/en/splfileinfo.setfileclass.php
	 * @param string $class [optional] The class name to use when SplFileInfo::openFile
	 * is called.
	 * @return void 
	 */
	public function setFileClass (string $class = null) {}

	/**
	 * Sets the class used with SplFileInfo::getFileInfo and SplFileInfo::getPathInfo
	 * @link http://www.php.net/manual/en/splfileinfo.setinfoclass.php
	 * @param string $class [optional] The class name to use when
	 * SplFileInfo::getFileInfo and
	 * SplFileInfo::getPathInfo are called.
	 * @return void 
	 */
	public function setInfoClass (string $class = null) {}

	public function __debugInfo () {}

	final public function _bad_state_ex () {}

}

/**
 * The PharData class provides a high-level interface to accessing and creating
 * non-executable tar and zip archives. Because these archives do not contain
 * a stub and cannot be executed by the phar extension, it is possible to create
 * and manipulate regular zip and tar files using the PharData class even if
 * phar.readonly php.ini setting is 1.
 * @link http://www.php.net/manual/en/class.phardata.php
 */
class PharData extends RecursiveDirectoryIterator implements RecursiveIterator, SeekableIterator, Traversable, Iterator, Stringable, Countable, ArrayAccess {
	const CURRENT_MODE_MASK = 240;
	const CURRENT_AS_PATHNAME = 32;
	const CURRENT_AS_FILEINFO = 0;
	const CURRENT_AS_SELF = 16;
	const KEY_MODE_MASK = 3840;
	const KEY_AS_PATHNAME = 0;
	const FOLLOW_SYMLINKS = 512;
	const KEY_AS_FILENAME = 256;
	const NEW_CURRENT_AND_KEY = 256;
	const OTHER_MODE_MASK = 12288;
	const SKIP_DOTS = 4096;
	const UNIX_PATHS = 8192;


	/**
	 * Construct a non-executable tar or zip archive object
	 * @link http://www.php.net/manual/en/phardata.construct.php
	 * @param string $filename
	 * @param int $flags [optional]
	 * @param ?string|null $alias [optional]
	 * @param int $format [optional]
	 */
	public function __construct (string $filenameint , $flags = 12288?string|null , $alias = nullint , $format = 0) {}

	/**
	 * Destructs a non-executable tar or zip archive object
	 * @link http://www.php.net/manual/en/phardata.destruct.php
	 */
	public function __destruct () {}

	/**
	 * Add an empty directory to the tar/zip archive
	 * @link http://www.php.net/manual/en/phardata.addemptydir.php
	 * @param string $directory The name of the empty directory to create in the phar archive
	 * @return void no return value, exception is thrown on failure.
	 */
	public function addEmptyDir (string $directory) {}

	/**
	 * Add a file from the filesystem to the tar/zip archive
	 * @link http://www.php.net/manual/en/phardata.addfile.php
	 * @param string $filename Full or relative path to a file on disk to be added
	 * to the phar archive.
	 * @param mixed $localName [optional] Path that the file will be stored in the archive.
	 * @return void no return value, exception is thrown on failure.
	 */
	public function addFile (string $filename, $localName = null) {}

	/**
	 * Add a file from a string to the tar/zip archive
	 * @link http://www.php.net/manual/en/phardata.addfromstring.php
	 * @param string $localName Path that the file will be stored in the archive.
	 * @param string $contents The file contents to store
	 * @return void no return value, exception is thrown on failure.
	 */
	public function addFromString (string $localName, string $contents) {}

	/**
	 * Construct a tar/zip archive from the files within a directory
	 * @link http://www.php.net/manual/en/phardata.buildfromdirectory.php
	 * @param string $directory The full or relative path to the directory that contains all files
	 * to add to the archive.
	 * @param string $pattern [optional] An optional pcre regular expression that is used to filter the
	 * list of files. Only file paths matching the regular expression
	 * will be included in the archive.
	 * @return array Phar::buildFromDirectory returns an associative array
	 * mapping internal path of file to the full path of the file on the
	 * filesystem, or false on failure.
	 */
	public function buildFromDirectory (string $directory, string $pattern = null) {}

	/**
	 * Construct a tar or zip archive from an iterator
	 * @link http://www.php.net/manual/en/phardata.buildfromiterator.php
	 * @param Traversable $iterator Any iterator that either associatively maps tar/zip file to location or
	 * returns SplFileInfo objects
	 * @param mixed $baseDirectory [optional] For iterators that return SplFileInfo objects, the portion of each
	 * file's full path to remove when adding to the tar/zip archive
	 * @return array PharData::buildFromIterator returns an associative array
	 * mapping internal path of file to the full path of the file on the
	 * filesystem.
	 */
	public function buildFromIterator ($iterator, $baseDirectory = null) {}

	/**
	 * Compresses all files in the current tar/zip archive
	 * @link http://www.php.net/manual/en/phardata.compressfiles.php
	 * @param int $compression Compression must be one of Phar::GZ,
	 * Phar::BZ2 to add compression, or Phar::NONE
	 * to remove compression.
	 * @return void 
	 */
	public function compressFiles (int $compression) {}

	/**
	 * Decompresses all files in the current zip archive
	 * @link http://www.php.net/manual/en/phardata.decompressfiles.php
	 * @return bool true on success or false on failure
	 */
	public function decompressFiles () {}

	/**
	 * Compresses the entire tar/zip archive using Gzip or Bzip2 compression
	 * @link http://www.php.net/manual/en/phardata.compress.php
	 * @param int $compression Compression must be one of Phar::GZ,
	 * Phar::BZ2 to add compression, or Phar::NONE
	 * to remove compression.
	 * @param mixed $extension [optional] By default, the extension is .tar.gz or .tar.bz2
	 * for compressing a tar, and .tar for decompressing.
	 * @return mixed A PharData object is returned on success,
	 * or null on failure.
	 */
	public function compress (int $compression, $extension = null) {}

	/**
	 * Decompresses the entire Phar archive
	 * @link http://www.php.net/manual/en/phardata.decompress.php
	 * @param mixed $extension [optional] For decompressing, the default file extension
	 * is .tar.
	 * Use this parameter to specify another file extension. Be aware that only
	 * executable archives can contain .phar in their filename.
	 * @return mixed A PharData object is returned on success,
	 * or null on failure.
	 */
	public function decompress ($extension = null) {}

	/**
	 * Convert a non-executable tar/zip archive to an executable phar archive
	 * @link http://www.php.net/manual/en/phardata.converttoexecutable.php
	 * @param mixed $format [optional] This should be one of Phar::PHAR, Phar::TAR,
	 * or Phar::ZIP. If set to null, the existing file format
	 * will be preserved.
	 * @param mixed $compression [optional] This should be one of Phar::NONE for no whole-archive
	 * compression, Phar::GZ for zlib-based compression, and
	 * Phar::BZ2 for bzip-based compression.
	 * @param mixed $extension [optional] <p>
	 * This parameter is used to override the default file extension for a
	 * converted archive. Note that all zip- and tar-based phar archives must contain
	 * .phar in their file extension in order to be processed as a
	 * phar archive.
	 * </p>
	 * <p>
	 * If converting to a phar-based archive, the default extensions are
	 * .phar, .phar.gz, or .phar.bz2
	 * depending on the specified compression. For tar-based phar archives, the
	 * default extensions are .phar.tar, .phar.tar.gz,
	 * and .phar.tar.bz2. For zip-based phar archives, the
	 * default extension is .phar.zip.
	 * </p>
	 * @return mixed The method returns a Phar object on success,
	 * or null on failure.
	 */
	public function convertToExecutable ($format = null, $compression = null, $extension = null) {}

	/**
	 * Convert a phar archive to a non-executable tar or zip file
	 * @link http://www.php.net/manual/en/phardata.converttodata.php
	 * @param mixed $format [optional] This should be one of Phar::TAR
	 * or Phar::ZIP. If set to null, the existing file format
	 * will be preserved.
	 * @param mixed $compression [optional] This should be one of Phar::NONE for no whole-archive
	 * compression, Phar::GZ for zlib-based compression, and
	 * Phar::BZ2 for bzip-based compression.
	 * @param mixed $extension [optional] <p>
	 * This parameter is used to override the default file extension for a
	 * converted archive. Note that .phar cannot be used
	 * anywhere in the filename for a non-executable tar or zip archive.
	 * </p>
	 * <p>
	 * If converting to a tar-based phar archive, the
	 * default extensions are .tar, .tar.gz,
	 * and .tar.bz2 depending on specified compression.
	 * For zip-based archives, the
	 * default extension is .zip.
	 * </p>
	 * @return mixed The method returns a PharData object on success,
	 * or null on failure.
	 */
	public function convertToData ($format = null, $compression = null, $extension = null) {}

	/**
	 * Copy a file internal to the phar archive to another new file within the phar
	 * @link http://www.php.net/manual/en/phardata.copy.php
	 * @param string $from 
	 * @param string $to 
	 * @return bool returns true on success, but it is safer to encase method call in a
	 * try/catch block and assume success if no exception is thrown.
	 */
	public function copy (string $from, string $to) {}

	/**
	 * @param int $mode [optional]
	 */
	public function count (int $mode = 0) {}

	/**
	 * Delete a file within a tar/zip archive
	 * @link http://www.php.net/manual/en/phardata.delete.php
	 * @param string $localName Path within an archive to the file to delete.
	 * @return bool returns true on success, but it is better to check for thrown exception,
	 * and assume success if none is thrown.
	 */
	public function delete (string $localName) {}

	/**
	 * Deletes the global metadata of a zip archive
	 * @link http://www.php.net/manual/en/phardata.delmetadata.php
	 * @return bool returns true on success, but it is better to check for thrown exception,
	 * and assume success if none is thrown.
	 */
	public function delMetadata () {}

	/**
	 * Extract the contents of a tar/zip archive to a directory
	 * @link http://www.php.net/manual/en/phardata.extractto.php
	 * @param string $directory Path to extract the given files to
	 * @param mixed $files [optional] The name of a file or directory to extract, or an array of files/directories to extract
	 * @param bool $overwrite [optional] Set to true to enable overwriting existing files
	 * @return bool returns true on success, but it is better to check for thrown exception,
	 * and assume success if none is thrown.
	 */
	public function extractTo (string $directory, $files = null, bool $overwrite = null) {}

	public function getAlias () {}

	public function getPath () {}

	/**
	 * @param array[] $unserializeOptions [optional]
	 */
	public function getMetadata (array $unserializeOptions = 'Array') {}

	public function getModified () {}

	public function getSignature () {}

	public function getStub () {}

	public function getVersion () {}

	public function hasMetadata () {}

	public function isBuffering () {}

	public function isCompressed () {}

	/**
	 * @param int $format
	 */
	public function isFileFormat (int $format) {}

	/**
	 * Returns true if the tar/zip archive can be modified
	 * @link http://www.php.net/manual/en/phardata.iswritable.php
	 * @return bool true if the tar/zip archive can be modified
	 */
	public function isWritable () {}

	/**
	 * @param mixed $localName
	 */
	public function offsetExists ($localName = null) {}

	/**
	 * @param mixed $localName
	 */
	public function offsetGet ($localName = null) {}

	/**
	 * Set the contents of a file within the tar/zip to those of an external file or string
	 * @link http://www.php.net/manual/en/phardata.offsetset.php
	 * @param string $localName The filename (relative path) to modify in a tar or zip archive.
	 * @param mixed $value Content of the file.
	 * @return void No return values.
	 */
	public function offsetSet (string $localName, $value) {}

	/**
	 * Remove a file from a tar/zip archive
	 * @link http://www.php.net/manual/en/phardata.offsetunset.php
	 * @param string $localName The filename (relative path) to modify in the tar/zip archive.
	 * @return void 
	 */
	public function offsetUnset (string $localName) {}

	/**
	 * Dummy function (Phar::setAlias is not valid for PharData)
	 * @link http://www.php.net/manual/en/phardata.setalias.php
	 * @param string $alias A shorthand string that this archive can be referred to in phar
	 * stream wrapper access. This parameter is ignored.
	 * @return bool 
	 */
	public function setAlias (string $alias) {}

	/**
	 * Dummy function (Phar::setDefaultStub is not valid for PharData)
	 * @link http://www.php.net/manual/en/phardata.setdefaultstub.php
	 * @param mixed $index [optional] Relative path within the phar archive to run if accessed on the command-line
	 * @param mixed $webIndex [optional] Relative path within the phar archive to run if accessed through a web browser
	 * @return bool true on success or false on failure
	 */
	public function setDefaultStub ($index = null, $webIndex = null) {}

	/**
	 * Sets phar archive meta-data
	 * @link http://www.php.net/manual/en/phardata.setmetadata.php
	 * @param mixed $metadata Any PHP variable containing information to store that describes the phar archive
	 * @return void 
	 */
	public function setMetadata ($metadata) {}

	/**
	 * Set the signature algorithm for a phar and apply it
	 * @link http://www.php.net/manual/en/phardata.setsignaturealgorithm.php
	 * @param int $algo One of Phar::MD5,
	 * Phar::SHA1, Phar::SHA256,
	 * Phar::SHA512, or Phar::OPENSSL
	 * @param mixed $privateKey [optional] 
	 * @return void 
	 */
	public function setSignatureAlgorithm (int $algo, $privateKey = null) {}

	/**
	 * Dummy function (Phar::setStub is not valid for PharData)
	 * @link http://www.php.net/manual/en/phardata.setstub.php
	 * @param string $stub A string or an open stream handle to use as the executable stub for this
	 * phar archive. This parameter is ignored.
	 * @param int $len [optional] 
	 * @return bool true on success or false on failure
	 */
	public function setStub (string $stub, int $len = null) {}

	public function startBuffering () {}

	public function stopBuffering () {}

	final public static function apiVersion (): string {}

	/**
	 * @param int $compression [optional]
	 */
	final public static function canCompress (int $compression = 0): bool {}

	final public static function canWrite (): bool {}

	/**
	 * @param ?string|null $index [optional]
	 * @param ?string|null $webIndex [optional]
	 */
	final public static function createDefaultStub (?string|null $index = null?string|null , $webIndex = null): string {}

	final public static function getSupportedCompression (): array {}

	final public static function getSupportedSignatures (): array {}

	final public static function interceptFileFuncs (): void {}

	/**
	 * @param string $filename
	 * @param bool $executable [optional]
	 */
	final public static function isValidPharFilename (string $filenamebool , $executable = 1): bool {}

	/**
	 * @param string $filename
	 * @param ?string|null $alias [optional]
	 */
	final public static function loadPhar (string $filename?string|null , $alias = null): bool {}

	/**
	 * @param ?string|null $alias [optional]
	 * @param int $offset [optional]
	 */
	final public static function mapPhar (?string|null $alias = nullint , $offset = 0): bool {}

	/**
	 * @param bool $returnPhar [optional]
	 */
	final public static function running (bool $returnPhar = 1): string {}

	/**
	 * @param string $pharPath
	 * @param string $externalPath
	 */
	final public static function mount (string $pharPathstring , $externalPath): void {}

	/**
	 * @param array[] $variables
	 */
	final public static function mungServer (array $variables): void {}

	/**
	 * @param string $filename
	 */
	final public static function unlinkArchive (string $filename): bool {}

	/**
	 * @param ?string|null $alias [optional]
	 * @param ?string|null $index [optional]
	 * @param ?string|null $fileNotFoundScript [optional]
	 * @param array[] $mimeTypes [optional]
	 * @param ?callable|null $rewrite [optional]
	 */
	final public static function webPhar (?string|null $alias = null?string|null , $index = null?string|null , $fileNotFoundScript = nullarray , $mimeTypes = 'Array'?callable|null , $rewrite = null): void {}

	/**
	 * Returns whether current entry is a directory and not '.' or '..'
	 * @link http://www.php.net/manual/en/recursivedirectoryiterator.haschildren.php
	 * @param bool $allowLinks [optional] 
	 * @return bool whether the current entry is a directory, but not '.' or '..'
	 */
	public function hasChildren (bool $allowLinks = null) {}

	/**
	 * Returns an iterator for the current entry if it is a directory
	 * @link http://www.php.net/manual/en/recursivedirectoryiterator.getchildren.php
	 * @return RecursiveDirectoryIterator The filename, file information, or $this depending on the set flags.
	 * See the FilesystemIterator
	 * constants.
	 */
	public function getChildren () {}

	/**
	 * Get sub path
	 * @link http://www.php.net/manual/en/recursivedirectoryiterator.getsubpath.php
	 * @return string The sub path.
	 */
	public function getSubPath () {}

	/**
	 * Get sub path and name
	 * @link http://www.php.net/manual/en/recursivedirectoryiterator.getsubpathname.php
	 * @return string The sub path (sub directory) and filename.
	 */
	public function getSubPathname () {}

	/**
	 * Rewinds back to the beginning
	 * @link http://www.php.net/manual/en/filesystemiterator.rewind.php
	 * @return void 
	 */
	public function rewind () {}

	/**
	 * Retrieve the key for the current file
	 * @link http://www.php.net/manual/en/filesystemiterator.key.php
	 * @return string the pathname or filename depending on the set flags.
	 * See the FilesystemIterator constants.
	 */
	public function key () {}

	/**
	 * The current file
	 * @link http://www.php.net/manual/en/filesystemiterator.current.php
	 * @return mixed The filename, file information, or $this depending on the set flags.
	 * See the FilesystemIterator constants.
	 */
	public function current () {}

	/**
	 * Get the handling flags
	 * @link http://www.php.net/manual/en/filesystemiterator.getflags.php
	 * @return int The integer value of the set flags.
	 */
	public function getFlags () {}

	/**
	 * Sets handling flags
	 * @link http://www.php.net/manual/en/filesystemiterator.setflags.php
	 * @param int $flags The handling flags to set.
	 * See the FilesystemIterator constants.
	 * @return void 
	 */
	public function setFlags (int $flags) {}

	/**
	 * Return file name of current DirectoryIterator item
	 * @link http://www.php.net/manual/en/directoryiterator.getfilename.php
	 * @return string the file name of the current DirectoryIterator item.
	 */
	public function getFilename () {}

	/**
	 * Gets the file extension
	 * @link http://www.php.net/manual/en/directoryiterator.getextension.php
	 * @return string a string containing the file extension, or an
	 * empty string if the file has no extension.
	 */
	public function getExtension () {}

	/**
	 * Get base name of current DirectoryIterator item
	 * @link http://www.php.net/manual/en/directoryiterator.getbasename.php
	 * @param string $suffix [optional] If the base name ends in suffix, 
	 * this will be cut.
	 * @return string The base name of the current DirectoryIterator item.
	 */
	public function getBasename (string $suffix = null) {}

	/**
	 * Determine if current DirectoryIterator item is '.' or '..'
	 * @link http://www.php.net/manual/en/directoryiterator.isdot.php
	 * @return bool true if the entry is . or ..,
	 * otherwise false
	 */
	public function isDot () {}

	/**
	 * Check whether current DirectoryIterator position is a valid file
	 * @link http://www.php.net/manual/en/directoryiterator.valid.php
	 * @return bool true if the position is valid, otherwise false
	 */
	public function valid () {}

	/**
	 * Move forward to next DirectoryIterator item
	 * @link http://www.php.net/manual/en/directoryiterator.next.php
	 * @return void 
	 */
	public function next () {}

	/**
	 * Seek to a DirectoryIterator item
	 * @link http://www.php.net/manual/en/directoryiterator.seek.php
	 * @param int $offset The zero-based numeric position to seek to.
	 * @return void 
	 */
	public function seek (int $offset) {}

	/**
	 * Get file name as a string
	 * @link http://www.php.net/manual/en/directoryiterator.tostring.php
	 * @return string the file name of the current DirectoryIterator item.
	 */
	public function __toString (): string {}

	/**
	 * Gets the path to the file
	 * @link http://www.php.net/manual/en/splfileinfo.getpathname.php
	 * @return string The path to the file.
	 */
	public function getPathname () {}

	/**
	 * Gets file permissions
	 * @link http://www.php.net/manual/en/splfileinfo.getperms.php
	 * @return mixed the file permissions on success, or false on failure.
	 */
	public function getPerms () {}

	/**
	 * Gets the inode for the file
	 * @link http://www.php.net/manual/en/splfileinfo.getinode.php
	 * @return mixed the inode number for the filesystem object on success, or false on failure.
	 */
	public function getInode () {}

	/**
	 * Gets file size
	 * @link http://www.php.net/manual/en/splfileinfo.getsize.php
	 * @return mixed The filesize in bytes on success, or false on failure.
	 */
	public function getSize () {}

	/**
	 * Gets the owner of the file
	 * @link http://www.php.net/manual/en/splfileinfo.getowner.php
	 * @return mixed The owner id in numerical format on success, or false on failure.
	 */
	public function getOwner () {}

	/**
	 * Gets the file group
	 * @link http://www.php.net/manual/en/splfileinfo.getgroup.php
	 * @return mixed The group id in numerical format on success, or false on failure.
	 */
	public function getGroup () {}

	/**
	 * Gets last access time of the file
	 * @link http://www.php.net/manual/en/splfileinfo.getatime.php
	 * @return mixed the time the file was last accessed on success, or false on failure.
	 */
	public function getATime () {}

	/**
	 * Gets the last modified time
	 * @link http://www.php.net/manual/en/splfileinfo.getmtime.php
	 * @return mixed the last modified time for the file, in a Unix timestamp on success, or false on failure.
	 */
	public function getMTime () {}

	/**
	 * Gets the inode change time
	 * @link http://www.php.net/manual/en/splfileinfo.getctime.php
	 * @return mixed The last change time, in a Unix timestamp on success, or false on failure.
	 */
	public function getCTime () {}

	/**
	 * Gets file type
	 * @link http://www.php.net/manual/en/splfileinfo.gettype.php
	 * @return mixed A string representing the type of the entry.
	 * May be one of file, link,
	 * dir, block, fifo,
	 * char, socket, or unknown, or false on failure.
	 */
	public function getType () {}

	/**
	 * Tells if file is readable
	 * @link http://www.php.net/manual/en/splfileinfo.isreadable.php
	 * @return bool true if readable, false otherwise.
	 */
	public function isReadable () {}

	/**
	 * Tells if the file is executable
	 * @link http://www.php.net/manual/en/splfileinfo.isexecutable.php
	 * @return bool true if executable, false otherwise.
	 */
	public function isExecutable () {}

	/**
	 * Tells if the object references a regular file
	 * @link http://www.php.net/manual/en/splfileinfo.isfile.php
	 * @return bool true if the file exists and is a regular file (not a link), false otherwise.
	 */
	public function isFile () {}

	/**
	 * Tells if the file is a directory
	 * @link http://www.php.net/manual/en/splfileinfo.isdir.php
	 * @return bool true if a directory, false otherwise.
	 */
	public function isDir () {}

	/**
	 * Tells if the file is a link
	 * @link http://www.php.net/manual/en/splfileinfo.islink.php
	 * @return bool true if the file is a link, false otherwise.
	 */
	public function isLink () {}

	/**
	 * Gets the target of a link
	 * @link http://www.php.net/manual/en/splfileinfo.getlinktarget.php
	 * @return mixed the target of the filesystem link on success, or false on failure.
	 */
	public function getLinkTarget () {}

	/**
	 * Gets absolute path to file
	 * @link http://www.php.net/manual/en/splfileinfo.getrealpath.php
	 * @return mixed the path to the file, or false if the file does not exist.
	 */
	public function getRealPath () {}

	/**
	 * Gets an SplFileInfo object for the file
	 * @link http://www.php.net/manual/en/splfileinfo.getfileinfo.php
	 * @param mixed $class [optional] Name of an SplFileInfo derived class to use.
	 * @return SplFileInfo An SplFileInfo object created for the file.
	 */
	public function getFileInfo ($class = null) {}

	/**
	 * Gets an SplFileInfo object for the path
	 * @link http://www.php.net/manual/en/splfileinfo.getpathinfo.php
	 * @param mixed $class [optional] Name of an SplFileInfo derived class to use, or itself if null.
	 * @return mixed an SplFileInfo object for the parent path of the file on success, or null on failure.
	 */
	public function getPathInfo ($class = null) {}

	/**
	 * Gets an SplFileObject object for the file
	 * @link http://www.php.net/manual/en/splfileinfo.openfile.php
	 * @param string $mode [optional] The mode for opening the file. See the fopen
	 * documentation for descriptions of possible modes. The default 
	 * is read only.
	 * @param bool $useIncludePath [optional] parameter.use_include_path
	 * @param mixed $context [optional] parameter.context
	 * @return SplFileObject The opened file as an SplFileObject object.
	 */
	public function openFile (string $mode = null, bool $useIncludePath = null, $context = null) {}

	/**
	 * Sets the class used with SplFileInfo::openFile
	 * @link http://www.php.net/manual/en/splfileinfo.setfileclass.php
	 * @param string $class [optional] The class name to use when SplFileInfo::openFile
	 * is called.
	 * @return void 
	 */
	public function setFileClass (string $class = null) {}

	/**
	 * Sets the class used with SplFileInfo::getFileInfo and SplFileInfo::getPathInfo
	 * @link http://www.php.net/manual/en/splfileinfo.setinfoclass.php
	 * @param string $class [optional] The class name to use when
	 * SplFileInfo::getFileInfo and
	 * SplFileInfo::getPathInfo are called.
	 * @return void 
	 */
	public function setInfoClass (string $class = null) {}

	public function __debugInfo () {}

	final public function _bad_state_ex () {}

}

/**
 * The PharFileInfo class provides a high-level interface to the contents
 * and attributes of a single file within a phar archive.
 * @link http://www.php.net/manual/en/class.pharfileinfo.php
 */
class PharFileInfo extends SplFileInfo implements Stringable {

	/**
	 * Construct a Phar entry object
	 * @link http://www.php.net/manual/en/pharfileinfo.construct.php
	 * @param string $filename
	 */
	public function __construct (string $filename) {}

	/**
	 * Destructs a Phar entry object
	 * @link http://www.php.net/manual/en/pharfileinfo.destruct.php
	 */
	public function __destruct () {}

	/**
	 * Sets file-specific permission bits
	 * @link http://www.php.net/manual/en/pharfileinfo.chmod.php
	 * @param int $perms permissions (see chmod)
	 * @return void 
	 */
	public function chmod (int $perms) {}

	/**
	 * Compresses the current Phar entry with either zlib or bzip2 compression
	 * @link http://www.php.net/manual/en/pharfileinfo.compress.php
	 * @param int $compression Compression must be Phar::GZ or Phar::BZ2.
	 * @return bool true on success or false on failure
	 */
	public function compress (int $compression) {}

	/**
	 * Decompresses the current Phar entry within the phar
	 * @link http://www.php.net/manual/en/pharfileinfo.decompress.php
	 * @return bool true on success or false on failure
	 */
	public function decompress () {}

	/**
	 * Deletes the metadata of the entry
	 * @link http://www.php.net/manual/en/pharfileinfo.delmetadata.php
	 * @return bool true if successful, false if the entry had no metadata.
	 * As with all functionality that modifies the contents of
	 * a phar, the phar.readonly INI variable
	 * must be off in order to succeed if the file is within a Phar
	 * archive. Files within PharData archives do not have
	 * this restriction.
	 */
	public function delMetadata () {}

	/**
	 * Returns the actual size of the file (with compression) inside the Phar archive
	 * @link http://www.php.net/manual/en/pharfileinfo.getcompressedsize.php
	 * @return int The size in bytes of the file within the Phar archive on disk.
	 */
	public function getCompressedSize () {}

	/**
	 * Returns CRC32 code or throws an exception if CRC has not been verified
	 * @link http://www.php.net/manual/en/pharfileinfo.getcrc32.php
	 * @return int The crc32 checksum of the file within the Phar archive.
	 */
	public function getCRC32 () {}

	/**
	 * Get the complete file contents of the entry
	 * @link http://www.php.net/manual/en/pharfileinfo.getcontent.php
	 * @return string the file contents.
	 */
	public function getContent () {}

	/**
	 * Returns file-specific meta-data saved with a file
	 * @link http://www.php.net/manual/en/pharfileinfo.getmetadata.php
	 * @param array $unserializeOptions [optional] 
	 * @return mixed any PHP variable that can be serialized and is stored as meta-data for the file,
	 * or null if no meta-data is stored.
	 */
	public function getMetadata (array $unserializeOptions = null) {}

	/**
	 * Returns the Phar file entry flags
	 * @link http://www.php.net/manual/en/pharfileinfo.getpharflags.php
	 * @return int The Phar flags (always 0 in the current implementation)
	 */
	public function getPharFlags () {}

	/**
	 * Returns the metadata of the entry
	 * @link http://www.php.net/manual/en/pharfileinfo.hasmetadata.php
	 * @return bool false if no metadata is set or is null, true if metadata is not null
	 */
	public function hasMetadata () {}

	/**
	 * Returns whether the entry is compressed
	 * @link http://www.php.net/manual/en/pharfileinfo.iscompressed.php
	 * @param mixed $compression [optional] One of Phar::GZ or Phar::BZ2,
	 * defaults to any compression.
	 * @return bool true if the file is compressed within the Phar archive, false if not.
	 */
	public function isCompressed ($compression = null) {}

	/**
	 * Returns whether file entry has had its CRC verified
	 * @link http://www.php.net/manual/en/pharfileinfo.iscrcchecked.php
	 * @return bool true if the file has had its CRC verified, false if not.
	 */
	public function isCRCChecked () {}

	/**
	 * Sets file-specific meta-data saved with a file
	 * @link http://www.php.net/manual/en/pharfileinfo.setmetadata.php
	 * @param mixed $metadata Any PHP variable containing information to store alongside a file
	 * @return void 
	 */
	public function setMetadata ($metadata) {}

	/**
	 * Gets the path without filename
	 * @link http://www.php.net/manual/en/splfileinfo.getpath.php
	 * @return string the path to the file.
	 */
	public function getPath () {}

	/**
	 * Gets the filename
	 * @link http://www.php.net/manual/en/splfileinfo.getfilename.php
	 * @return string The filename.
	 */
	public function getFilename () {}

	/**
	 * Gets the file extension
	 * @link http://www.php.net/manual/en/splfileinfo.getextension.php
	 * @return string a string containing the file extension, or an
	 * empty string if the file has no extension.
	 */
	public function getExtension () {}

	/**
	 * Gets the base name of the file
	 * @link http://www.php.net/manual/en/splfileinfo.getbasename.php
	 * @param string $suffix [optional] Optional suffix to omit from the base name returned.
	 * @return string the base name without path information.
	 */
	public function getBasename (string $suffix = null) {}

	/**
	 * Gets the path to the file
	 * @link http://www.php.net/manual/en/splfileinfo.getpathname.php
	 * @return string The path to the file.
	 */
	public function getPathname () {}

	/**
	 * Gets file permissions
	 * @link http://www.php.net/manual/en/splfileinfo.getperms.php
	 * @return mixed the file permissions on success, or false on failure.
	 */
	public function getPerms () {}

	/**
	 * Gets the inode for the file
	 * @link http://www.php.net/manual/en/splfileinfo.getinode.php
	 * @return mixed the inode number for the filesystem object on success, or false on failure.
	 */
	public function getInode () {}

	/**
	 * Gets file size
	 * @link http://www.php.net/manual/en/splfileinfo.getsize.php
	 * @return mixed The filesize in bytes on success, or false on failure.
	 */
	public function getSize () {}

	/**
	 * Gets the owner of the file
	 * @link http://www.php.net/manual/en/splfileinfo.getowner.php
	 * @return mixed The owner id in numerical format on success, or false on failure.
	 */
	public function getOwner () {}

	/**
	 * Gets the file group
	 * @link http://www.php.net/manual/en/splfileinfo.getgroup.php
	 * @return mixed The group id in numerical format on success, or false on failure.
	 */
	public function getGroup () {}

	/**
	 * Gets last access time of the file
	 * @link http://www.php.net/manual/en/splfileinfo.getatime.php
	 * @return mixed the time the file was last accessed on success, or false on failure.
	 */
	public function getATime () {}

	/**
	 * Gets the last modified time
	 * @link http://www.php.net/manual/en/splfileinfo.getmtime.php
	 * @return mixed the last modified time for the file, in a Unix timestamp on success, or false on failure.
	 */
	public function getMTime () {}

	/**
	 * Gets the inode change time
	 * @link http://www.php.net/manual/en/splfileinfo.getctime.php
	 * @return mixed The last change time, in a Unix timestamp on success, or false on failure.
	 */
	public function getCTime () {}

	/**
	 * Gets file type
	 * @link http://www.php.net/manual/en/splfileinfo.gettype.php
	 * @return mixed A string representing the type of the entry.
	 * May be one of file, link,
	 * dir, block, fifo,
	 * char, socket, or unknown, or false on failure.
	 */
	public function getType () {}

	/**
	 * Tells if the entry is writable
	 * @link http://www.php.net/manual/en/splfileinfo.iswritable.php
	 * @return bool true if writable, false otherwise;
	 */
	public function isWritable () {}

	/**
	 * Tells if file is readable
	 * @link http://www.php.net/manual/en/splfileinfo.isreadable.php
	 * @return bool true if readable, false otherwise.
	 */
	public function isReadable () {}

	/**
	 * Tells if the file is executable
	 * @link http://www.php.net/manual/en/splfileinfo.isexecutable.php
	 * @return bool true if executable, false otherwise.
	 */
	public function isExecutable () {}

	/**
	 * Tells if the object references a regular file
	 * @link http://www.php.net/manual/en/splfileinfo.isfile.php
	 * @return bool true if the file exists and is a regular file (not a link), false otherwise.
	 */
	public function isFile () {}

	/**
	 * Tells if the file is a directory
	 * @link http://www.php.net/manual/en/splfileinfo.isdir.php
	 * @return bool true if a directory, false otherwise.
	 */
	public function isDir () {}

	/**
	 * Tells if the file is a link
	 * @link http://www.php.net/manual/en/splfileinfo.islink.php
	 * @return bool true if the file is a link, false otherwise.
	 */
	public function isLink () {}

	/**
	 * Gets the target of a link
	 * @link http://www.php.net/manual/en/splfileinfo.getlinktarget.php
	 * @return mixed the target of the filesystem link on success, or false on failure.
	 */
	public function getLinkTarget () {}

	/**
	 * Gets absolute path to file
	 * @link http://www.php.net/manual/en/splfileinfo.getrealpath.php
	 * @return mixed the path to the file, or false if the file does not exist.
	 */
	public function getRealPath () {}

	/**
	 * Gets an SplFileInfo object for the file
	 * @link http://www.php.net/manual/en/splfileinfo.getfileinfo.php
	 * @param mixed $class [optional] Name of an SplFileInfo derived class to use.
	 * @return SplFileInfo An SplFileInfo object created for the file.
	 */
	public function getFileInfo ($class = null) {}

	/**
	 * Gets an SplFileInfo object for the path
	 * @link http://www.php.net/manual/en/splfileinfo.getpathinfo.php
	 * @param mixed $class [optional] Name of an SplFileInfo derived class to use, or itself if null.
	 * @return mixed an SplFileInfo object for the parent path of the file on success, or null on failure.
	 */
	public function getPathInfo ($class = null) {}

	/**
	 * Gets an SplFileObject object for the file
	 * @link http://www.php.net/manual/en/splfileinfo.openfile.php
	 * @param string $mode [optional] The mode for opening the file. See the fopen
	 * documentation for descriptions of possible modes. The default 
	 * is read only.
	 * @param bool $useIncludePath [optional] parameter.use_include_path
	 * @param mixed $context [optional] parameter.context
	 * @return SplFileObject The opened file as an SplFileObject object.
	 */
	public function openFile (string $mode = null, bool $useIncludePath = null, $context = null) {}

	/**
	 * Sets the class used with SplFileInfo::openFile
	 * @link http://www.php.net/manual/en/splfileinfo.setfileclass.php
	 * @param string $class [optional] The class name to use when SplFileInfo::openFile
	 * is called.
	 * @return void 
	 */
	public function setFileClass (string $class = null) {}

	/**
	 * Sets the class used with SplFileInfo::getFileInfo and SplFileInfo::getPathInfo
	 * @link http://www.php.net/manual/en/splfileinfo.setinfoclass.php
	 * @param string $class [optional] The class name to use when
	 * SplFileInfo::getFileInfo and
	 * SplFileInfo::getPathInfo are called.
	 * @return void 
	 */
	public function setInfoClass (string $class = null) {}

	/**
	 * Returns the path to the file as a string
	 * @link http://www.php.net/manual/en/splfileinfo.tostring.php
	 * @return string the path to the file.
	 */
	public function __toString (): string {}

	public function __debugInfo () {}

	final public function _bad_state_ex () {}

}
// End of Phar v.8.0.28
