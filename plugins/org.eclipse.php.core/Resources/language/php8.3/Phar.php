<?php

// Start of Phar v.8.2.6

/**
 * The PharException class provides a phar-specific exception class
 * for try/catch blocks.
 * @link http://www.php.net/manual/en/class.pharexception.php
 */
class PharException extends Exception implements Throwable, Stringable {

	/**
	 * Construct the exception
	 * @link http://www.php.net/manual/en/exception.construct.php
	 * @param string $message [optional] 
	 * @param int $code [optional] 
	 * @param Throwable|null $previous [optional] 
	 * @return string 
	 */
	public function __construct (string $message = '""', int $code = null, ?Throwable $previous = null): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * Gets the Exception message
	 * @link http://www.php.net/manual/en/exception.getmessage.php
	 * @return string Returns the Exception message as a string.
	 */
	final public function getMessage (): string {}

	/**
	 * Gets the Exception code
	 * @link http://www.php.net/manual/en/exception.getcode.php
	 * @return int Returns the exception code as int in
	 * Exception but possibly as other type in
	 * Exception descendants (for example as
	 * string in PDOException).
	 */
	final public function getCode (): int {}

	/**
	 * Gets the file in which the exception was created
	 * @link http://www.php.net/manual/en/exception.getfile.php
	 * @return string Returns the filename in which the exception was created.
	 */
	final public function getFile (): string {}

	/**
	 * Gets the line in which the exception was created
	 * @link http://www.php.net/manual/en/exception.getline.php
	 * @return int Returns the line number where the exception was created.
	 */
	final public function getLine (): int {}

	/**
	 * Gets the stack trace
	 * @link http://www.php.net/manual/en/exception.gettrace.php
	 * @return array Returns the Exception stack trace as an array.
	 */
	final public function getTrace (): array {}

	/**
	 * Returns previous Throwable
	 * @link http://www.php.net/manual/en/exception.getprevious.php
	 * @return Throwable|null Returns the previous Throwable if available 
	 * or null otherwise.
	 */
	final public function getPrevious (): ?Throwable {}

	/**
	 * Gets the stack trace as a string
	 * @link http://www.php.net/manual/en/exception.gettraceasstring.php
	 * @return string Returns the Exception stack trace as a string.
	 */
	final public function getTraceAsString (): string {}

	/**
	 * String representation of the exception
	 * @link http://www.php.net/manual/en/exception.tostring.php
	 * @return string Returns the string representation of the exception.
	 */
	public function __toString (): string {}

}

/**
 * The Phar class provides a high-level interface to accessing and creating
 * phar archives.
 * @link http://www.php.net/manual/en/class.phar.php
 */
class Phar extends RecursiveDirectoryIterator implements RecursiveIterator, Iterator, Traversable, SeekableIterator, Stringable, Countable, ArrayAccess {
	const CURRENT_MODE_MASK = 240;
	const CURRENT_AS_PATHNAME = 32;
	const CURRENT_AS_FILEINFO = 0;
	const CURRENT_AS_SELF = 16;
	const KEY_MODE_MASK = 3840;
	const KEY_AS_PATHNAME = 0;
	const FOLLOW_SYMLINKS = 16384;
	const KEY_AS_FILENAME = 256;
	const NEW_CURRENT_AND_KEY = 256;
	const OTHER_MODE_MASK = 28672;
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
	const OPENSSL_SHA256 = 17;
	const OPENSSL_SHA512 = 18;
	const SHA1 = 2;
	const SHA256 = 3;
	const SHA512 = 4;


	/**
	 * Construct a Phar archive object
	 * @link http://www.php.net/manual/en/phar.construct.php
	 * @param string $filename 
	 * @param int $flags [optional] 
	 * @param string|null $alias [optional] 
	 * @return string 
	 */
	public function __construct (string $filename, int $flags = 'FilesystemIterator::SKIP_DOTS | FilesystemIterator::UNIX_PATHS', ?string $alias = null): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __destruct () {}

	/**
	 * Add an empty directory to the phar archive
	 * @link http://www.php.net/manual/en/phar.addemptydir.php
	 * @param string $directory 
	 * @return void no return value, exception is thrown on failure.
	 */
	public function addEmptyDir (string $directory): void {}

	/**
	 * Add a file from the filesystem to the phar archive
	 * @link http://www.php.net/manual/en/phar.addfile.php
	 * @param string $filename 
	 * @param string|null $localName [optional] 
	 * @return void no return value, exception is thrown on failure.
	 */
	public function addFile (string $filename, ?string $localName = null): void {}

	/**
	 * Add a file from a string to the phar archive
	 * @link http://www.php.net/manual/en/phar.addfromstring.php
	 * @param string $localName 
	 * @param string $contents 
	 * @return void no return value, exception is thrown on failure.
	 */
	public function addFromString (string $localName, string $contents): void {}

	/**
	 * Construct a phar archive from the files within a directory
	 * @link http://www.php.net/manual/en/phar.buildfromdirectory.php
	 * @param string $directory 
	 * @param string $pattern [optional] 
	 * @return array Phar::buildFromDirectory returns an associative array
	 * mapping internal path of file to the full path of the file on the
	 * filesystem.
	 */
	public function buildFromDirectory (string $directory, string $pattern = '""'): array {}

	/**
	 * Construct a phar archive from an iterator
	 * @link http://www.php.net/manual/en/phar.buildfromiterator.php
	 * @param Traversable $iterator 
	 * @param string|null $baseDirectory [optional] 
	 * @return array Phar::buildFromIterator returns an associative array
	 * mapping internal path of file to the full path of the file on the
	 * filesystem.
	 */
	public function buildFromIterator (Traversable $iterator, ?string $baseDirectory = null): array {}

	/**
	 * Compresses all files in the current Phar archive
	 * @link http://www.php.net/manual/en/phar.compressfiles.php
	 * @param int $compression 
	 * @return void No value is returned.
	 */
	public function compressFiles (int $compression): void {}

	/**
	 * Decompresses all files in the current Phar archive
	 * @link http://www.php.net/manual/en/phar.decompressfiles.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function decompressFiles (): bool {}

	/**
	 * Compresses the entire Phar archive using Gzip or Bzip2 compression
	 * @link http://www.php.net/manual/en/phar.compress.php
	 * @param int $compression 
	 * @param string|null $extension [optional] 
	 * @return Phar|null Returns a Phar object, or null on failure.
	 */
	public function compress (int $compression, ?string $extension = null): ?Phar {}

	/**
	 * Decompresses the entire Phar archive
	 * @link http://www.php.net/manual/en/phar.decompress.php
	 * @param string|null $extension [optional] 
	 * @return Phar|null A Phar object is returned on success, and null on failure.
	 */
	public function decompress (?string $extension = null): ?Phar {}

	/**
	 * Convert a phar archive to another executable phar archive file format
	 * @link http://www.php.net/manual/en/phar.converttoexecutable.php
	 * @param int|null $format [optional] 
	 * @param int|null $compression [optional] 
	 * @param string|null $extension [optional] 
	 * @return Phar|null The method returns a Phar object on success,
	 * or null on failure.
	 */
	public function convertToExecutable (?int $format = null, ?int $compression = null, ?string $extension = null): ?Phar {}

	/**
	 * Convert a phar archive to a non-executable tar or zip file
	 * @link http://www.php.net/manual/en/phar.converttodata.php
	 * @param int|null $format [optional] 
	 * @param int|null $compression [optional] 
	 * @param string|null $extension [optional] 
	 * @return PharData|null The method returns a PharData object on success,
	 * or null on failure.
	 */
	public function convertToData (?int $format = null, ?int $compression = null, ?string $extension = null): ?PharData {}

	/**
	 * Copy a file internal to the phar archive to another new file within the phar
	 * @link http://www.php.net/manual/en/phar.copy.php
	 * @param string $from 
	 * @param string $to 
	 * @return bool returns true on success, but it is safer to encase method call in a
	 * try/catch block and assume success if no exception is thrown.
	 */
	public function copy (string $from, string $to): bool {}

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
	public function count (int $mode = COUNT_NORMAL): int {}

	/**
	 * Delete a file within a phar archive
	 * @link http://www.php.net/manual/en/phar.delete.php
	 * @param string $localName 
	 * @return bool returns true on success, but it is better to check for thrown exception,
	 * and assume success if none is thrown.
	 */
	public function delete (string $localName): bool {}

	/**
	 * Deletes the global metadata of the phar
	 * @link http://www.php.net/manual/en/phar.delmetadata.php
	 * @return bool returns true on success, but it is better to check for thrown exception,
	 * and assume success if none is thrown.
	 */
	public function delMetadata (): bool {}

	/**
	 * Extract the contents of a phar archive to a directory
	 * @link http://www.php.net/manual/en/phar.extractto.php
	 * @param string $directory 
	 * @param array|string|null $files [optional] 
	 * @param bool $overwrite [optional] 
	 * @return bool returns true on success, but it is better to check for thrown exception,
	 * and assume success if none is thrown.
	 */
	public function extractTo (string $directory, array|string|null $files = null, bool $overwrite = false): bool {}

	/**
	 * Get the alias for Phar
	 * @link http://www.php.net/manual/en/phar.getalias.php
	 * @return string|null Returns the alias or null if there's no alias.
	 */
	public function getAlias (): ?string {}

	/**
	 * Get the real path to the Phar archive on disk
	 * @link http://www.php.net/manual/en/phar.getpath.php
	 * @return string 
	 */
	public function getPath (): string {}

	/**
	 * Returns phar archive meta-data
	 * @link http://www.php.net/manual/en/phar.getmetadata.php
	 * @param array $unserializeOptions [optional] 
	 * @return mixed Any PHP value that can be serialized and is stored as meta-data for the Phar archive,
	 * or null if no meta-data is stored.
	 */
	public function getMetadata (array $unserializeOptions = '[]'): mixed {}

	/**
	 * Return whether phar was modified
	 * @link http://www.php.net/manual/en/phar.getmodified.php
	 * @return bool true if the phar has been modified since opened, false if not.
	 */
	public function getModified (): bool {}

	/**
	 * Return MD5/SHA1/SHA256/SHA512/OpenSSL signature of a Phar archive
	 * @link http://www.php.net/manual/en/phar.getsignature.php
	 * @return array|false Array with the opened archive's signature in hash key and MD5,
	 * SHA-1,
	 * SHA-256, SHA-512, or OpenSSL
	 * in hash_type. This signature is a hash calculated on the
	 * entire phar's contents, and may be used to verify the integrity of the archive.
	 * A valid signature is absolutely required of all executable phar archives if the
	 * phar.require_hash INI variable
	 * is set to true.
	 * If there is no signature, the function returns false.
	 */
	public function getSignature (): array|false {}

	/**
	 * Return the PHP loader or bootstrap stub of a Phar archive
	 * @link http://www.php.net/manual/en/phar.getstub.php
	 * @return string Returns a string containing the contents of the bootstrap loader (stub) of
	 * the current Phar archive.
	 */
	public function getStub (): string {}

	/**
	 * Return version info of Phar archive
	 * @link http://www.php.net/manual/en/phar.getversion.php
	 * @return string The opened archive's API version. This is not to be confused with
	 * the API version that the loaded phar extension will use to create
	 * new phars. Each Phar archive has the API version hard-coded into
	 * its manifest. See Phar file format
	 * documentation for more information.
	 */
	public function getVersion (): string {}

	/**
	 * Returns whether phar has global meta-data
	 * @link http://www.php.net/manual/en/phar.hasmetadata.php
	 * @return bool Returns true if meta-data has been set, and false if not.
	 */
	public function hasMetadata (): bool {}

	/**
	 * Used to determine whether Phar write operations are being buffered, or are flushing directly to disk
	 * @link http://www.php.net/manual/en/phar.isbuffering.php
	 * @return bool Returns true if the write operations are being buffer, false otherwise.
	 */
	public function isBuffering (): bool {}

	/**
	 * Returns Phar::GZ or PHAR::BZ2 if the entire phar archive is compressed (.tar.gz/tar.bz and so on)
	 * @link http://www.php.net/manual/en/phar.iscompressed.php
	 * @return int|false Phar::GZ, Phar::BZ2 or false.
	 */
	public function isCompressed (): int|false {}

	/**
	 * Returns true if the phar archive is based on the tar/phar/zip file format depending on the parameter
	 * @link http://www.php.net/manual/en/phar.isfileformat.php
	 * @param int $format 
	 * @return bool Returns true if the phar archive matches the file format requested by the parameter
	 */
	public function isFileFormat (int $format): bool {}

	/**
	 * Returns true if the phar archive can be modified
	 * @link http://www.php.net/manual/en/phar.iswritable.php
	 * @return bool Returns true if the phar archive can be modified
	 */
	public function isWritable (): bool {}

	/**
	 * Determines whether a file exists in the phar
	 * @link http://www.php.net/manual/en/phar.offsetexists.php
	 * @param string $localName 
	 * @return bool Returns true if the file exists within the phar, or false if not.
	 */
	public function offsetExists (string $localName): bool {}

	/**
	 * Gets a PharFileInfo object for a specific file
	 * @link http://www.php.net/manual/en/phar.offsetget.php
	 * @param string $localName 
	 * @return SplFileInfo A PharFileInfo object is returned that can be used to
	 * iterate over a file's contents or to retrieve information about the current file.
	 */
	public function offsetGet (string $localName): SplFileInfo {}

	/**
	 * Set the contents of an internal file to those of an external file
	 * @link http://www.php.net/manual/en/phar.offsetset.php
	 * @param string $localName 
	 * @param resource|string $value 
	 * @return void No return values.
	 */
	public function offsetSet (string $localName, $value): void {}

	/**
	 * Remove a file from a phar
	 * @link http://www.php.net/manual/en/phar.offsetunset.php
	 * @param string $localName 
	 * @return void No value is returned.
	 */
	public function offsetUnset (string $localName): void {}

	/**
	 * Set the alias for the Phar archive
	 * @link http://www.php.net/manual/en/phar.setalias.php
	 * @param string $alias 
	 * @return bool 
	 */
	public function setAlias (string $alias): bool {}

	/**
	 * Used to set the PHP loader or bootstrap stub of a Phar archive to the default loader
	 * @link http://www.php.net/manual/en/phar.setdefaultstub.php
	 * @param string|null $index [optional] 
	 * @param string|null $webIndex [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setDefaultStub (?string $index = null, ?string $webIndex = null): bool {}

	/**
	 * Sets phar archive meta-data
	 * @link http://www.php.net/manual/en/phar.setmetadata.php
	 * @param mixed $metadata 
	 * @return void No value is returned.
	 */
	public function setMetadata (mixed $metadata): void {}

	/**
	 * Set the signature algorithm for a phar and apply it
	 * @link http://www.php.net/manual/en/phar.setsignaturealgorithm.php
	 * @param int $algo 
	 * @param string|null $privateKey [optional] 
	 * @return void No value is returned.
	 */
	public function setSignatureAlgorithm (int $algo, ?string $privateKey = null): void {}

	/**
	 * Used to set the PHP loader or bootstrap stub of a Phar archive
	 * @link http://www.php.net/manual/en/phar.setstub.php
	 * @param string $stub 
	 * @param int $len [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setStub (string $stub, int $len = -1): bool {}

	/**
	 * Start buffering Phar write operations, do not modify the Phar object on disk
	 * @link http://www.php.net/manual/en/phar.startbuffering.php
	 * @return void No value is returned.
	 */
	public function startBuffering (): void {}

	/**
	 * Stop buffering write requests to the Phar archive, and save changes to disk
	 * @link http://www.php.net/manual/en/phar.stopbuffering.php
	 * @return void No value is returned.
	 */
	public function stopBuffering (): void {}

	/**
	 * Returns the api version
	 * @link http://www.php.net/manual/en/phar.apiversion.php
	 * @return string The API version string as in "1.0.0".
	 */
	final public static function apiVersion (): string {}

	/**
	 * Returns whether phar extension supports compression using either zlib or bzip2
	 * @link http://www.php.net/manual/en/phar.cancompress.php
	 * @param int $compression [optional] 
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
	 * @param string|null $index [optional] 
	 * @param string|null $webIndex [optional] 
	 * @return string Returns a string containing the contents of a customized bootstrap loader (stub)
	 * that allows the created Phar archive to work with or without the Phar extension
	 * enabled.
	 */
	final public static function createDefaultStub (?string $index = null, ?string $webIndex = null): string {}

	/**
	 * Return array of supported compression algorithms
	 * @link http://www.php.net/manual/en/phar.getsupportedcompression.php
	 * @return array Returns an array containing any of Phar::GZ or
	 * Phar::BZ2, depending on the availability of
	 * the zlib extension or the
	 * bz2 extension.
	 */
	final public static function getSupportedCompression (): array {}

	/**
	 * Return array of supported signature types
	 * @link http://www.php.net/manual/en/phar.getsupportedsignatures.php
	 * @return array Returns an array containing any of MD5, SHA-1,
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
	 * @param string $filename 
	 * @param bool $executable [optional] 
	 * @return bool Returns true if the filename is valid, false if not.
	 */
	final public static function isValidPharFilename (string $filename, bool $executable = true): bool {}

	/**
	 * Loads any phar archive with an alias
	 * @link http://www.php.net/manual/en/phar.loadphar.php
	 * @param string $filename 
	 * @param string|null $alias [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	final public static function loadPhar (string $filename, ?string $alias = null): bool {}

	/**
	 * Reads the currently executed file (a phar) and registers its manifest
	 * @link http://www.php.net/manual/en/phar.mapphar.php
	 * @param string|null $alias [optional] 
	 * @param int $offset [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	final public static function mapPhar (?string $alias = null, int $offset = null): bool {}

	/**
	 * Returns the full path on disk or full phar URL to the currently executing Phar archive
	 * @link http://www.php.net/manual/en/phar.running.php
	 * @param bool $returnPhar [optional] 
	 * @return string Returns the filename if valid, empty string otherwise.
	 */
	final public static function running (bool $returnPhar = true): string {}

	/**
	 * Mount an external path or file to a virtual location within the phar archive
	 * @link http://www.php.net/manual/en/phar.mount.php
	 * @param string $pharPath 
	 * @param string $externalPath 
	 * @return void No return. PharException is thrown on failure.
	 */
	final public static function mount (string $pharPath, string $externalPath): void {}

	/**
	 * Defines a list of up to 4 $_SERVER variables that should be modified for execution
	 * @link http://www.php.net/manual/en/phar.mungserver.php
	 * @param array $variables 
	 * @return void No return.
	 */
	final public static function mungServer (array $variables): void {}

	/**
	 * Completely remove a phar archive from disk and from memory
	 * @link http://www.php.net/manual/en/phar.unlinkarchive.php
	 * @param string $filename 
	 * @return bool Returns true on success or false on failure.
	 */
	final public static function unlinkArchive (string $filename): bool {}

	/**
	 * Routes a request from a web browser to an internal file within the phar archive
	 * @link http://www.php.net/manual/en/phar.webphar.php
	 * @param string|null $alias [optional] 
	 * @param string|null $index [optional] 
	 * @param string|null $fileNotFoundScript [optional] 
	 * @param array $mimeTypes [optional] 
	 * @param callable|null $rewrite [optional] 
	 * @return void No value is returned.
	 */
	final public static function webPhar (?string $alias = null, ?string $index = null, ?string $fileNotFoundScript = null, array $mimeTypes = '[]', ?callable $rewrite = null): void {}

	/**
	 * Returns whether current entry is a directory and not '.' or '..'
	 * @link http://www.php.net/manual/en/recursivedirectoryiterator.haschildren.php
	 * @param bool $allowLinks [optional] 
	 * @return bool Returns whether the current entry is a directory, but not '.' or '..'
	 */
	public function hasChildren (bool $allowLinks = false): bool {}

	/**
	 * Returns an iterator for the current entry if it is a directory
	 * @link http://www.php.net/manual/en/recursivedirectoryiterator.getchildren.php
	 * @return RecursiveDirectoryIterator The filename, file information, or $this depending on the set flags.
	 * See the FilesystemIterator
	 * constants.
	 */
	public function getChildren (): RecursiveDirectoryIterator {}

	/**
	 * Get sub path
	 * @link http://www.php.net/manual/en/recursivedirectoryiterator.getsubpath.php
	 * @return string The sub path.
	 */
	public function getSubPath (): string {}

	/**
	 * Get sub path and name
	 * @link http://www.php.net/manual/en/recursivedirectoryiterator.getsubpathname.php
	 * @return string The sub path (sub directory) and filename.
	 */
	public function getSubPathname (): string {}

	/**
	 * Rewinds back to the beginning
	 * @link http://www.php.net/manual/en/filesystemiterator.rewind.php
	 * @return void No value is returned.
	 */
	public function rewind (): void {}

	/**
	 * Retrieve the key for the current file
	 * @link http://www.php.net/manual/en/filesystemiterator.key.php
	 * @return string Returns the pathname or filename depending on the set flags.
	 * See the FilesystemIterator constants.
	 */
	public function key (): string {}

	/**
	 * The current file
	 * @link http://www.php.net/manual/en/filesystemiterator.current.php
	 * @return string|SplFileInfo|FilesystemIterator The filename, file information, or $this depending on the set flags.
	 * See the FilesystemIterator constants.
	 */
	public function current (): string|SplFileInfo|FilesystemIterator {}

	/**
	 * Get the handling flags
	 * @link http://www.php.net/manual/en/filesystemiterator.getflags.php
	 * @return int The integer value of the set flags.
	 */
	public function getFlags (): int {}

	/**
	 * Sets handling flags
	 * @link http://www.php.net/manual/en/filesystemiterator.setflags.php
	 * @param int $flags 
	 * @return void No value is returned.
	 */
	public function setFlags (int $flags): void {}

	/**
	 * Return file name of current DirectoryIterator item
	 * @link http://www.php.net/manual/en/directoryiterator.getfilename.php
	 * @return string Returns the file name of the current DirectoryIterator item.
	 */
	public function getFilename (): string {}

	/**
	 * Gets the file extension
	 * @link http://www.php.net/manual/en/directoryiterator.getextension.php
	 * @return string Returns a string containing the file extension, or an
	 * empty string if the file has no extension.
	 */
	public function getExtension (): string {}

	/**
	 * Get base name of current DirectoryIterator item
	 * @link http://www.php.net/manual/en/directoryiterator.getbasename.php
	 * @param string $suffix [optional] 
	 * @return string The base name of the current DirectoryIterator item.
	 */
	public function getBasename (string $suffix = '""'): string {}

	/**
	 * Determine if current DirectoryIterator item is '.' or '..'
	 * @link http://www.php.net/manual/en/directoryiterator.isdot.php
	 * @return bool true if the entry is . or ..,
	 * otherwise false
	 */
	public function isDot (): bool {}

	/**
	 * Check whether current DirectoryIterator position is a valid file
	 * @link http://www.php.net/manual/en/directoryiterator.valid.php
	 * @return bool Returns true if the position is valid, otherwise false
	 */
	public function valid (): bool {}

	/**
	 * Move forward to next DirectoryIterator item
	 * @link http://www.php.net/manual/en/directoryiterator.next.php
	 * @return void No value is returned.
	 */
	public function next (): void {}

	/**
	 * Seek to a DirectoryIterator item
	 * @link http://www.php.net/manual/en/directoryiterator.seek.php
	 * @param int $offset 
	 * @return void No value is returned.
	 */
	public function seek (int $offset): void {}

	/**
	 * Get file name as a string
	 * @link http://www.php.net/manual/en/directoryiterator.tostring.php
	 * @return string Returns the file name of the current DirectoryIterator item.
	 */
	public function __toString (): string {}

	/**
	 * Gets the path to the file
	 * @link http://www.php.net/manual/en/splfileinfo.getpathname.php
	 * @return string The path to the file.
	 */
	public function getPathname (): string {}

	/**
	 * Gets file permissions
	 * @link http://www.php.net/manual/en/splfileinfo.getperms.php
	 * @return int|false Returns the file permissions on success, or false on failure.
	 */
	public function getPerms (): int|false {}

	/**
	 * Gets the inode for the file
	 * @link http://www.php.net/manual/en/splfileinfo.getinode.php
	 * @return int|false Returns the inode number for the filesystem object on success, or false on failure.
	 */
	public function getInode (): int|false {}

	/**
	 * Gets file size
	 * @link http://www.php.net/manual/en/splfileinfo.getsize.php
	 * @return int|false The filesize in bytes on success, or false on failure.
	 */
	public function getSize (): int|false {}

	/**
	 * Gets the owner of the file
	 * @link http://www.php.net/manual/en/splfileinfo.getowner.php
	 * @return int|false The owner id in numerical format on success, or false on failure.
	 */
	public function getOwner (): int|false {}

	/**
	 * Gets the file group
	 * @link http://www.php.net/manual/en/splfileinfo.getgroup.php
	 * @return int|false The group id in numerical format on success, or false on failure.
	 */
	public function getGroup (): int|false {}

	/**
	 * Gets last access time of the file
	 * @link http://www.php.net/manual/en/splfileinfo.getatime.php
	 * @return int|false Returns the time the file was last accessed on success, or false on failure.
	 */
	public function getATime (): int|false {}

	/**
	 * Gets the last modified time
	 * @link http://www.php.net/manual/en/splfileinfo.getmtime.php
	 * @return int|false Returns the last modified time for the file, in a Unix timestamp on success, or false on failure.
	 */
	public function getMTime (): int|false {}

	/**
	 * Gets the inode change time
	 * @link http://www.php.net/manual/en/splfileinfo.getctime.php
	 * @return int|false The last change time, in a Unix timestamp on success, or false on failure.
	 */
	public function getCTime (): int|false {}

	/**
	 * Gets file type
	 * @link http://www.php.net/manual/en/splfileinfo.gettype.php
	 * @return string|false A string representing the type of the entry.
	 * May be one of file, link,
	 * dir, block, fifo,
	 * char, socket, or unknown, or false on failure.
	 */
	public function getType (): string|false {}

	/**
	 * Tells if file is readable
	 * @link http://www.php.net/manual/en/splfileinfo.isreadable.php
	 * @return bool Returns true if readable, false otherwise.
	 */
	public function isReadable (): bool {}

	/**
	 * Tells if the file is executable
	 * @link http://www.php.net/manual/en/splfileinfo.isexecutable.php
	 * @return bool Returns true if executable, false otherwise.
	 */
	public function isExecutable (): bool {}

	/**
	 * Tells if the object references a regular file
	 * @link http://www.php.net/manual/en/splfileinfo.isfile.php
	 * @return bool Returns true if the file exists and is a regular file (not a link), false otherwise.
	 */
	public function isFile (): bool {}

	/**
	 * Tells if the file is a directory
	 * @link http://www.php.net/manual/en/splfileinfo.isdir.php
	 * @return bool Returns true if a directory, false otherwise.
	 */
	public function isDir (): bool {}

	/**
	 * Tells if the file is a link
	 * @link http://www.php.net/manual/en/splfileinfo.islink.php
	 * @return bool Returns true if the file is a link, false otherwise.
	 */
	public function isLink (): bool {}

	/**
	 * Gets the target of a link
	 * @link http://www.php.net/manual/en/splfileinfo.getlinktarget.php
	 * @return string|false Returns the target of the filesystem link on success, or false on failure.
	 */
	public function getLinkTarget (): string|false {}

	/**
	 * Gets absolute path to file
	 * @link http://www.php.net/manual/en/splfileinfo.getrealpath.php
	 * @return string|false Returns the path to the file, or false if the file does not exist.
	 */
	public function getRealPath (): string|false {}

	/**
	 * Gets an SplFileInfo object for the file
	 * @link http://www.php.net/manual/en/splfileinfo.getfileinfo.php
	 * @param string|null $class [optional] 
	 * @return SplFileInfo An SplFileInfo object created for the file.
	 */
	public function getFileInfo (?string $class = null): SplFileInfo {}

	/**
	 * Gets an SplFileInfo object for the path
	 * @link http://www.php.net/manual/en/splfileinfo.getpathinfo.php
	 * @param string|null $class [optional] 
	 * @return SplFileInfo|null Returns an SplFileInfo object for the parent path of the file on success, or null on failure.
	 */
	public function getPathInfo (?string $class = null): ?SplFileInfo {}

	/**
	 * Gets an SplFileObject object for the file
	 * @link http://www.php.net/manual/en/splfileinfo.openfile.php
	 * @param string $mode [optional] 
	 * @param bool $useIncludePath [optional] 
	 * @param resource|null $context [optional] 
	 * @return SplFileObject The opened file as an SplFileObject object.
	 */
	public function openFile (string $mode = '"r"', bool $useIncludePath = false, $context = null): SplFileObject {}

	/**
	 * Sets the class used with SplFileInfo::openFile
	 * @link http://www.php.net/manual/en/splfileinfo.setfileclass.php
	 * @param string $class [optional] 
	 * @return void No value is returned.
	 */
	public function setFileClass (string $class = 'SplFileObject::class'): void {}

	/**
	 * Sets the class used with SplFileInfo::getFileInfo and SplFileInfo::getPathInfo
	 * @link http://www.php.net/manual/en/splfileinfo.setinfoclass.php
	 * @param string $class [optional] 
	 * @return void No value is returned.
	 */
	public function setInfoClass (string $class = 'SplFileInfo::class'): void {}

	/**
	 * {@inheritdoc}
	 */
	public function __debugInfo () {}

	/**
	 * {@inheritdoc}
	 * @deprecated 
	 */
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
class PharData extends RecursiveDirectoryIterator implements RecursiveIterator, Iterator, Traversable, SeekableIterator, Stringable, Countable, ArrayAccess {
	const CURRENT_MODE_MASK = 240;
	const CURRENT_AS_PATHNAME = 32;
	const CURRENT_AS_FILEINFO = 0;
	const CURRENT_AS_SELF = 16;
	const KEY_MODE_MASK = 3840;
	const KEY_AS_PATHNAME = 0;
	const FOLLOW_SYMLINKS = 16384;
	const KEY_AS_FILENAME = 256;
	const NEW_CURRENT_AND_KEY = 256;
	const OTHER_MODE_MASK = 28672;
	const SKIP_DOTS = 4096;
	const UNIX_PATHS = 8192;


	/**
	 * Construct a non-executable tar or zip archive object
	 * @link http://www.php.net/manual/en/phardata.construct.php
	 * @param string $filename 
	 * @param int $flags [optional] 
	 * @param string|null $alias [optional] 
	 * @param int $format [optional] 
	 * @return string 
	 */
	public function __construct (string $filename, int $flags = 'FilesystemIterator::SKIP_DOTS | FilesystemIterator::UNIX_PATHS', ?string $alias = null, int $format = null): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __destruct () {}

	/**
	 * Add an empty directory to the tar/zip archive
	 * @link http://www.php.net/manual/en/phardata.addemptydir.php
	 * @param string $directory 
	 * @return void no return value, exception is thrown on failure.
	 */
	public function addEmptyDir (string $directory): void {}

	/**
	 * Add a file from the filesystem to the tar/zip archive
	 * @link http://www.php.net/manual/en/phardata.addfile.php
	 * @param string $filename 
	 * @param string|null $localName [optional] 
	 * @return void no return value, exception is thrown on failure.
	 */
	public function addFile (string $filename, ?string $localName = null): void {}

	/**
	 * Add a file from a string to the tar/zip archive
	 * @link http://www.php.net/manual/en/phardata.addfromstring.php
	 * @param string $localName 
	 * @param string $contents 
	 * @return void no return value, exception is thrown on failure.
	 */
	public function addFromString (string $localName, string $contents): void {}

	/**
	 * Construct a tar/zip archive from the files within a directory
	 * @link http://www.php.net/manual/en/phardata.buildfromdirectory.php
	 * @param string $directory 
	 * @param string $pattern [optional] 
	 * @return array Phar::buildFromDirectory returns an associative array
	 * mapping internal path of file to the full path of the file on the
	 * filesystem, or false on failure.
	 */
	public function buildFromDirectory (string $directory, string $pattern = '""'): array {}

	/**
	 * Construct a tar or zip archive from an iterator
	 * @link http://www.php.net/manual/en/phardata.buildfromiterator.php
	 * @param Traversable $iterator 
	 * @param string|null $baseDirectory [optional] 
	 * @return array PharData::buildFromIterator returns an associative array
	 * mapping internal path of file to the full path of the file on the
	 * filesystem.
	 */
	public function buildFromIterator (Traversable $iterator, ?string $baseDirectory = null): array {}

	/**
	 * Compresses all files in the current tar/zip archive
	 * @link http://www.php.net/manual/en/phardata.compressfiles.php
	 * @param int $compression 
	 * @return void No value is returned.
	 */
	public function compressFiles (int $compression): void {}

	/**
	 * Decompresses all files in the current zip archive
	 * @link http://www.php.net/manual/en/phardata.decompressfiles.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function decompressFiles (): bool {}

	/**
	 * Compresses the entire tar/zip archive using Gzip or Bzip2 compression
	 * @link http://www.php.net/manual/en/phardata.compress.php
	 * @param int $compression 
	 * @param string|null $extension [optional] 
	 * @return PharData|null A PharData object is returned on success,
	 * or null on failure.
	 */
	public function compress (int $compression, ?string $extension = null): ?PharData {}

	/**
	 * Decompresses the entire Phar archive
	 * @link http://www.php.net/manual/en/phardata.decompress.php
	 * @param string|null $extension [optional] 
	 * @return PharData|null A PharData object is returned on success,
	 * or null on failure.
	 */
	public function decompress (?string $extension = null): ?PharData {}

	/**
	 * Convert a non-executable tar/zip archive to an executable phar archive
	 * @link http://www.php.net/manual/en/phardata.converttoexecutable.php
	 * @param int|null $format [optional] 
	 * @param int|null $compression [optional] 
	 * @param string|null $extension [optional] 
	 * @return Phar|null The method returns a Phar object on success,
	 * or null on failure.
	 */
	public function convertToExecutable (?int $format = null, ?int $compression = null, ?string $extension = null): ?Phar {}

	/**
	 * Convert a phar archive to a non-executable tar or zip file
	 * @link http://www.php.net/manual/en/phardata.converttodata.php
	 * @param int|null $format [optional] 
	 * @param int|null $compression [optional] 
	 * @param string|null $extension [optional] 
	 * @return PharData|null The method returns a PharData object on success,
	 * or null on failure.
	 */
	public function convertToData (?int $format = null, ?int $compression = null, ?string $extension = null): ?PharData {}

	/**
	 * Copy a file internal to the phar archive to another new file within the phar
	 * @link http://www.php.net/manual/en/phardata.copy.php
	 * @param string $from 
	 * @param string $to 
	 * @return bool returns true on success, but it is safer to encase method call in a
	 * try/catch block and assume success if no exception is thrown.
	 */
	public function copy (string $from, string $to): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $mode [optional]
	 */
	public function count (int $mode = 0) {}

	/**
	 * Delete a file within a tar/zip archive
	 * @link http://www.php.net/manual/en/phardata.delete.php
	 * @param string $localName 
	 * @return bool returns true on success, but it is better to check for thrown exception,
	 * and assume success if none is thrown.
	 */
	public function delete (string $localName): bool {}

	/**
	 * Deletes the global metadata of a zip archive
	 * @link http://www.php.net/manual/en/phardata.delmetadata.php
	 * @return bool returns true on success, but it is better to check for thrown exception,
	 * and assume success if none is thrown.
	 */
	public function delMetadata (): bool {}

	/**
	 * Extract the contents of a tar/zip archive to a directory
	 * @link http://www.php.net/manual/en/phardata.extractto.php
	 * @param string $directory 
	 * @param array|string|null $files [optional] 
	 * @param bool $overwrite [optional] 
	 * @return bool returns true on success, but it is better to check for thrown exception,
	 * and assume success if none is thrown.
	 */
	public function extractTo (string $directory, array|string|null $files = null, bool $overwrite = false): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getAlias () {}

	/**
	 * {@inheritdoc}
	 */
	public function getPath () {}

	/**
	 * {@inheritdoc}
	 * @param array $unserializeOptions [optional]
	 */
	public function getMetadata (array $unserializeOptions = array (
)) {}

	/**
	 * {@inheritdoc}
	 */
	public function getModified () {}

	/**
	 * {@inheritdoc}
	 */
	public function getSignature () {}

	/**
	 * {@inheritdoc}
	 */
	public function getStub () {}

	/**
	 * {@inheritdoc}
	 */
	public function getVersion () {}

	/**
	 * {@inheritdoc}
	 */
	public function hasMetadata () {}

	/**
	 * {@inheritdoc}
	 */
	public function isBuffering () {}

	/**
	 * {@inheritdoc}
	 */
	public function isCompressed () {}

	/**
	 * {@inheritdoc}
	 * @param int $format
	 */
	public function isFileFormat (int $format) {}

	/**
	 * Returns true if the tar/zip archive can be modified
	 * @link http://www.php.net/manual/en/phardata.iswritable.php
	 * @return bool Returns true if the tar/zip archive can be modified
	 */
	public function isWritable (): bool {}

	/**
	 * {@inheritdoc}
	 * @param mixed $localName
	 */
	public function offsetExists ($localName = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $localName
	 */
	public function offsetGet ($localName = null) {}

	/**
	 * Set the contents of a file within the tar/zip to those of an external file or string
	 * @link http://www.php.net/manual/en/phardata.offsetset.php
	 * @param string $localName 
	 * @param resource|string $value 
	 * @return void No return values.
	 */
	public function offsetSet (string $localName, $value): void {}

	/**
	 * Remove a file from a tar/zip archive
	 * @link http://www.php.net/manual/en/phardata.offsetunset.php
	 * @param string $localName 
	 * @return void No value is returned.
	 */
	public function offsetUnset (string $localName): void {}

	/**
	 * Dummy function (Phar::setAlias is not valid for PharData)
	 * @link http://www.php.net/manual/en/phardata.setalias.php
	 * @param string $alias 
	 * @return bool 
	 */
	public function setAlias (string $alias): bool {}

	/**
	 * Dummy function (Phar::setDefaultStub is not valid for PharData)
	 * @link http://www.php.net/manual/en/phardata.setdefaultstub.php
	 * @param string|null $index [optional] 
	 * @param string|null $webIndex [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setDefaultStub (?string $index = null, ?string $webIndex = null): bool {}

	/**
	 * Sets phar archive meta-data
	 * @link http://www.php.net/manual/en/phardata.setmetadata.php
	 * @param mixed $metadata 
	 * @return void No value is returned.
	 */
	public function setMetadata (mixed $metadata): void {}

	/**
	 * Set the signature algorithm for a phar and apply it
	 * @link http://www.php.net/manual/en/phardata.setsignaturealgorithm.php
	 * @param int $algo 
	 * @param string|null $privateKey [optional] 
	 * @return void No value is returned.
	 */
	public function setSignatureAlgorithm (int $algo, ?string $privateKey = null): void {}

	/**
	 * Dummy function (Phar::setStub is not valid for PharData)
	 * @link http://www.php.net/manual/en/phardata.setstub.php
	 * @param string $stub 
	 * @param int $len [optional] 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setStub (string $stub, int $len = -1): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function startBuffering () {}

	/**
	 * {@inheritdoc}
	 */
	public function stopBuffering () {}

	/**
	 * {@inheritdoc}
	 */
	final public static function apiVersion (): string {}

	/**
	 * {@inheritdoc}
	 * @param int $compression [optional]
	 */
	final public static function canCompress (int $compression = 0): bool {}

	/**
	 * {@inheritdoc}
	 */
	final public static function canWrite (): bool {}

	/**
	 * {@inheritdoc}
	 * @param string|null $index [optional]
	 * @param string|null $webIndex [optional]
	 */
	final public static function createDefaultStub (?string $index = NULL, ?string $webIndex = NULL): string {}

	/**
	 * {@inheritdoc}
	 */
	final public static function getSupportedCompression (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public static function getSupportedSignatures (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public static function interceptFileFuncs (): void {}

	/**
	 * {@inheritdoc}
	 * @param string $filename
	 * @param bool $executable [optional]
	 */
	final public static function isValidPharFilename (string $filename, bool $executable = true): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $filename
	 * @param string|null $alias [optional]
	 */
	final public static function loadPhar (string $filename, ?string $alias = NULL): bool {}

	/**
	 * {@inheritdoc}
	 * @param string|null $alias [optional]
	 * @param int $offset [optional]
	 */
	final public static function mapPhar (?string $alias = NULL, int $offset = 0): bool {}

	/**
	 * {@inheritdoc}
	 * @param bool $returnPhar [optional]
	 */
	final public static function running (bool $returnPhar = true): string {}

	/**
	 * {@inheritdoc}
	 * @param string $pharPath
	 * @param string $externalPath
	 */
	final public static function mount (string $pharPath, string $externalPath): void {}

	/**
	 * {@inheritdoc}
	 * @param array $variables
	 */
	final public static function mungServer (array $variables): void {}

	/**
	 * {@inheritdoc}
	 * @param string $filename
	 */
	final public static function unlinkArchive (string $filename): bool {}

	/**
	 * {@inheritdoc}
	 * @param string|null $alias [optional]
	 * @param string|null $index [optional]
	 * @param string|null $fileNotFoundScript [optional]
	 * @param array $mimeTypes [optional]
	 * @param callable|null $rewrite [optional]
	 */
	final public static function webPhar (?string $alias = NULL, ?string $index = NULL, ?string $fileNotFoundScript = NULL, array $mimeTypes = array (
), ?callable $rewrite = NULL): void {}

	/**
	 * Returns whether current entry is a directory and not '.' or '..'
	 * @link http://www.php.net/manual/en/recursivedirectoryiterator.haschildren.php
	 * @param bool $allowLinks [optional] 
	 * @return bool Returns whether the current entry is a directory, but not '.' or '..'
	 */
	public function hasChildren (bool $allowLinks = false): bool {}

	/**
	 * Returns an iterator for the current entry if it is a directory
	 * @link http://www.php.net/manual/en/recursivedirectoryiterator.getchildren.php
	 * @return RecursiveDirectoryIterator The filename, file information, or $this depending on the set flags.
	 * See the FilesystemIterator
	 * constants.
	 */
	public function getChildren (): RecursiveDirectoryIterator {}

	/**
	 * Get sub path
	 * @link http://www.php.net/manual/en/recursivedirectoryiterator.getsubpath.php
	 * @return string The sub path.
	 */
	public function getSubPath (): string {}

	/**
	 * Get sub path and name
	 * @link http://www.php.net/manual/en/recursivedirectoryiterator.getsubpathname.php
	 * @return string The sub path (sub directory) and filename.
	 */
	public function getSubPathname (): string {}

	/**
	 * Rewinds back to the beginning
	 * @link http://www.php.net/manual/en/filesystemiterator.rewind.php
	 * @return void No value is returned.
	 */
	public function rewind (): void {}

	/**
	 * Retrieve the key for the current file
	 * @link http://www.php.net/manual/en/filesystemiterator.key.php
	 * @return string Returns the pathname or filename depending on the set flags.
	 * See the FilesystemIterator constants.
	 */
	public function key (): string {}

	/**
	 * The current file
	 * @link http://www.php.net/manual/en/filesystemiterator.current.php
	 * @return string|SplFileInfo|FilesystemIterator The filename, file information, or $this depending on the set flags.
	 * See the FilesystemIterator constants.
	 */
	public function current (): string|SplFileInfo|FilesystemIterator {}

	/**
	 * Get the handling flags
	 * @link http://www.php.net/manual/en/filesystemiterator.getflags.php
	 * @return int The integer value of the set flags.
	 */
	public function getFlags (): int {}

	/**
	 * Sets handling flags
	 * @link http://www.php.net/manual/en/filesystemiterator.setflags.php
	 * @param int $flags 
	 * @return void No value is returned.
	 */
	public function setFlags (int $flags): void {}

	/**
	 * Return file name of current DirectoryIterator item
	 * @link http://www.php.net/manual/en/directoryiterator.getfilename.php
	 * @return string Returns the file name of the current DirectoryIterator item.
	 */
	public function getFilename (): string {}

	/**
	 * Gets the file extension
	 * @link http://www.php.net/manual/en/directoryiterator.getextension.php
	 * @return string Returns a string containing the file extension, or an
	 * empty string if the file has no extension.
	 */
	public function getExtension (): string {}

	/**
	 * Get base name of current DirectoryIterator item
	 * @link http://www.php.net/manual/en/directoryiterator.getbasename.php
	 * @param string $suffix [optional] 
	 * @return string The base name of the current DirectoryIterator item.
	 */
	public function getBasename (string $suffix = '""'): string {}

	/**
	 * Determine if current DirectoryIterator item is '.' or '..'
	 * @link http://www.php.net/manual/en/directoryiterator.isdot.php
	 * @return bool true if the entry is . or ..,
	 * otherwise false
	 */
	public function isDot (): bool {}

	/**
	 * Check whether current DirectoryIterator position is a valid file
	 * @link http://www.php.net/manual/en/directoryiterator.valid.php
	 * @return bool Returns true if the position is valid, otherwise false
	 */
	public function valid (): bool {}

	/**
	 * Move forward to next DirectoryIterator item
	 * @link http://www.php.net/manual/en/directoryiterator.next.php
	 * @return void No value is returned.
	 */
	public function next (): void {}

	/**
	 * Seek to a DirectoryIterator item
	 * @link http://www.php.net/manual/en/directoryiterator.seek.php
	 * @param int $offset 
	 * @return void No value is returned.
	 */
	public function seek (int $offset): void {}

	/**
	 * Get file name as a string
	 * @link http://www.php.net/manual/en/directoryiterator.tostring.php
	 * @return string Returns the file name of the current DirectoryIterator item.
	 */
	public function __toString (): string {}

	/**
	 * Gets the path to the file
	 * @link http://www.php.net/manual/en/splfileinfo.getpathname.php
	 * @return string The path to the file.
	 */
	public function getPathname (): string {}

	/**
	 * Gets file permissions
	 * @link http://www.php.net/manual/en/splfileinfo.getperms.php
	 * @return int|false Returns the file permissions on success, or false on failure.
	 */
	public function getPerms (): int|false {}

	/**
	 * Gets the inode for the file
	 * @link http://www.php.net/manual/en/splfileinfo.getinode.php
	 * @return int|false Returns the inode number for the filesystem object on success, or false on failure.
	 */
	public function getInode (): int|false {}

	/**
	 * Gets file size
	 * @link http://www.php.net/manual/en/splfileinfo.getsize.php
	 * @return int|false The filesize in bytes on success, or false on failure.
	 */
	public function getSize (): int|false {}

	/**
	 * Gets the owner of the file
	 * @link http://www.php.net/manual/en/splfileinfo.getowner.php
	 * @return int|false The owner id in numerical format on success, or false on failure.
	 */
	public function getOwner (): int|false {}

	/**
	 * Gets the file group
	 * @link http://www.php.net/manual/en/splfileinfo.getgroup.php
	 * @return int|false The group id in numerical format on success, or false on failure.
	 */
	public function getGroup (): int|false {}

	/**
	 * Gets last access time of the file
	 * @link http://www.php.net/manual/en/splfileinfo.getatime.php
	 * @return int|false Returns the time the file was last accessed on success, or false on failure.
	 */
	public function getATime (): int|false {}

	/**
	 * Gets the last modified time
	 * @link http://www.php.net/manual/en/splfileinfo.getmtime.php
	 * @return int|false Returns the last modified time for the file, in a Unix timestamp on success, or false on failure.
	 */
	public function getMTime (): int|false {}

	/**
	 * Gets the inode change time
	 * @link http://www.php.net/manual/en/splfileinfo.getctime.php
	 * @return int|false The last change time, in a Unix timestamp on success, or false on failure.
	 */
	public function getCTime (): int|false {}

	/**
	 * Gets file type
	 * @link http://www.php.net/manual/en/splfileinfo.gettype.php
	 * @return string|false A string representing the type of the entry.
	 * May be one of file, link,
	 * dir, block, fifo,
	 * char, socket, or unknown, or false on failure.
	 */
	public function getType (): string|false {}

	/**
	 * Tells if file is readable
	 * @link http://www.php.net/manual/en/splfileinfo.isreadable.php
	 * @return bool Returns true if readable, false otherwise.
	 */
	public function isReadable (): bool {}

	/**
	 * Tells if the file is executable
	 * @link http://www.php.net/manual/en/splfileinfo.isexecutable.php
	 * @return bool Returns true if executable, false otherwise.
	 */
	public function isExecutable (): bool {}

	/**
	 * Tells if the object references a regular file
	 * @link http://www.php.net/manual/en/splfileinfo.isfile.php
	 * @return bool Returns true if the file exists and is a regular file (not a link), false otherwise.
	 */
	public function isFile (): bool {}

	/**
	 * Tells if the file is a directory
	 * @link http://www.php.net/manual/en/splfileinfo.isdir.php
	 * @return bool Returns true if a directory, false otherwise.
	 */
	public function isDir (): bool {}

	/**
	 * Tells if the file is a link
	 * @link http://www.php.net/manual/en/splfileinfo.islink.php
	 * @return bool Returns true if the file is a link, false otherwise.
	 */
	public function isLink (): bool {}

	/**
	 * Gets the target of a link
	 * @link http://www.php.net/manual/en/splfileinfo.getlinktarget.php
	 * @return string|false Returns the target of the filesystem link on success, or false on failure.
	 */
	public function getLinkTarget (): string|false {}

	/**
	 * Gets absolute path to file
	 * @link http://www.php.net/manual/en/splfileinfo.getrealpath.php
	 * @return string|false Returns the path to the file, or false if the file does not exist.
	 */
	public function getRealPath (): string|false {}

	/**
	 * Gets an SplFileInfo object for the file
	 * @link http://www.php.net/manual/en/splfileinfo.getfileinfo.php
	 * @param string|null $class [optional] 
	 * @return SplFileInfo An SplFileInfo object created for the file.
	 */
	public function getFileInfo (?string $class = null): SplFileInfo {}

	/**
	 * Gets an SplFileInfo object for the path
	 * @link http://www.php.net/manual/en/splfileinfo.getpathinfo.php
	 * @param string|null $class [optional] 
	 * @return SplFileInfo|null Returns an SplFileInfo object for the parent path of the file on success, or null on failure.
	 */
	public function getPathInfo (?string $class = null): ?SplFileInfo {}

	/**
	 * Gets an SplFileObject object for the file
	 * @link http://www.php.net/manual/en/splfileinfo.openfile.php
	 * @param string $mode [optional] 
	 * @param bool $useIncludePath [optional] 
	 * @param resource|null $context [optional] 
	 * @return SplFileObject The opened file as an SplFileObject object.
	 */
	public function openFile (string $mode = '"r"', bool $useIncludePath = false, $context = null): SplFileObject {}

	/**
	 * Sets the class used with SplFileInfo::openFile
	 * @link http://www.php.net/manual/en/splfileinfo.setfileclass.php
	 * @param string $class [optional] 
	 * @return void No value is returned.
	 */
	public function setFileClass (string $class = 'SplFileObject::class'): void {}

	/**
	 * Sets the class used with SplFileInfo::getFileInfo and SplFileInfo::getPathInfo
	 * @link http://www.php.net/manual/en/splfileinfo.setinfoclass.php
	 * @param string $class [optional] 
	 * @return void No value is returned.
	 */
	public function setInfoClass (string $class = 'SplFileInfo::class'): void {}

	/**
	 * {@inheritdoc}
	 */
	public function __debugInfo () {}

	/**
	 * {@inheritdoc}
	 * @deprecated 
	 */
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
	 * @return string 
	 */
	public function __construct (string $filename): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __destruct () {}

	/**
	 * Sets file-specific permission bits
	 * @link http://www.php.net/manual/en/pharfileinfo.chmod.php
	 * @param int $perms 
	 * @return void No value is returned.
	 */
	public function chmod (int $perms): void {}

	/**
	 * Compresses the current Phar entry with either zlib or bzip2 compression
	 * @link http://www.php.net/manual/en/pharfileinfo.compress.php
	 * @param int $compression 
	 * @return bool Returns true on success or false on failure.
	 */
	public function compress (int $compression): bool {}

	/**
	 * Decompresses the current Phar entry within the phar
	 * @link http://www.php.net/manual/en/pharfileinfo.decompress.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function decompress (): bool {}

	/**
	 * Deletes the metadata of the entry
	 * @link http://www.php.net/manual/en/pharfileinfo.delmetadata.php
	 * @return bool Returns true if successful, false if the entry had no metadata.
	 * As with all functionality that modifies the contents of
	 * a phar, the phar.readonly INI variable
	 * must be off in order to succeed if the file is within a Phar
	 * archive. Files within PharData archives do not have
	 * this restriction.
	 */
	public function delMetadata (): bool {}

	/**
	 * Returns the actual size of the file (with compression) inside the Phar archive
	 * @link http://www.php.net/manual/en/pharfileinfo.getcompressedsize.php
	 * @return int The size in bytes of the file within the Phar archive on disk.
	 */
	public function getCompressedSize (): int {}

	/**
	 * Returns CRC32 code or throws an exception if CRC has not been verified
	 * @link http://www.php.net/manual/en/pharfileinfo.getcrc32.php
	 * @return int The crc32 checksum of the file within the Phar archive.
	 */
	public function getCRC32 (): int {}

	/**
	 * Get the complete file contents of the entry
	 * @link http://www.php.net/manual/en/pharfileinfo.getcontent.php
	 * @return string Returns the file contents.
	 */
	public function getContent (): string {}

	/**
	 * Returns file-specific meta-data saved with a file
	 * @link http://www.php.net/manual/en/pharfileinfo.getmetadata.php
	 * @param array $unserializeOptions [optional] 
	 * @return mixed any PHP variable that can be serialized and is stored as meta-data for the file,
	 * or null if no meta-data is stored.
	 */
	public function getMetadata (array $unserializeOptions = '[]'): mixed {}

	/**
	 * Returns the Phar file entry flags
	 * @link http://www.php.net/manual/en/pharfileinfo.getpharflags.php
	 * @return int The Phar flags (always 0 in the current implementation)
	 */
	public function getPharFlags (): int {}

	/**
	 * Returns the metadata of the entry
	 * @link http://www.php.net/manual/en/pharfileinfo.hasmetadata.php
	 * @return bool Returns false if no metadata is set or is null, true if metadata is not null
	 */
	public function hasMetadata (): bool {}

	/**
	 * Returns whether the entry is compressed
	 * @link http://www.php.net/manual/en/pharfileinfo.iscompressed.php
	 * @param int|null $compression [optional] 
	 * @return bool true if the file is compressed within the Phar archive, false if not.
	 */
	public function isCompressed (?int $compression = null): bool {}

	/**
	 * Returns whether file entry has had its CRC verified
	 * @link http://www.php.net/manual/en/pharfileinfo.iscrcchecked.php
	 * @return bool true if the file has had its CRC verified, false if not.
	 */
	public function isCRCChecked (): bool {}

	/**
	 * Sets file-specific meta-data saved with a file
	 * @link http://www.php.net/manual/en/pharfileinfo.setmetadata.php
	 * @param mixed $metadata 
	 * @return void No value is returned.
	 */
	public function setMetadata (mixed $metadata): void {}

	/**
	 * Gets the path without filename
	 * @link http://www.php.net/manual/en/splfileinfo.getpath.php
	 * @return string Returns the path to the file.
	 */
	public function getPath (): string {}

	/**
	 * Gets the filename
	 * @link http://www.php.net/manual/en/splfileinfo.getfilename.php
	 * @return string The filename.
	 */
	public function getFilename (): string {}

	/**
	 * Gets the file extension
	 * @link http://www.php.net/manual/en/splfileinfo.getextension.php
	 * @return string Returns a string containing the file extension, or an
	 * empty string if the file has no extension.
	 */
	public function getExtension (): string {}

	/**
	 * Gets the base name of the file
	 * @link http://www.php.net/manual/en/splfileinfo.getbasename.php
	 * @param string $suffix [optional] 
	 * @return string Returns the base name without path information.
	 */
	public function getBasename (string $suffix = '""'): string {}

	/**
	 * Gets the path to the file
	 * @link http://www.php.net/manual/en/splfileinfo.getpathname.php
	 * @return string The path to the file.
	 */
	public function getPathname (): string {}

	/**
	 * Gets file permissions
	 * @link http://www.php.net/manual/en/splfileinfo.getperms.php
	 * @return int|false Returns the file permissions on success, or false on failure.
	 */
	public function getPerms (): int|false {}

	/**
	 * Gets the inode for the file
	 * @link http://www.php.net/manual/en/splfileinfo.getinode.php
	 * @return int|false Returns the inode number for the filesystem object on success, or false on failure.
	 */
	public function getInode (): int|false {}

	/**
	 * Gets file size
	 * @link http://www.php.net/manual/en/splfileinfo.getsize.php
	 * @return int|false The filesize in bytes on success, or false on failure.
	 */
	public function getSize (): int|false {}

	/**
	 * Gets the owner of the file
	 * @link http://www.php.net/manual/en/splfileinfo.getowner.php
	 * @return int|false The owner id in numerical format on success, or false on failure.
	 */
	public function getOwner (): int|false {}

	/**
	 * Gets the file group
	 * @link http://www.php.net/manual/en/splfileinfo.getgroup.php
	 * @return int|false The group id in numerical format on success, or false on failure.
	 */
	public function getGroup (): int|false {}

	/**
	 * Gets last access time of the file
	 * @link http://www.php.net/manual/en/splfileinfo.getatime.php
	 * @return int|false Returns the time the file was last accessed on success, or false on failure.
	 */
	public function getATime (): int|false {}

	/**
	 * Gets the last modified time
	 * @link http://www.php.net/manual/en/splfileinfo.getmtime.php
	 * @return int|false Returns the last modified time for the file, in a Unix timestamp on success, or false on failure.
	 */
	public function getMTime (): int|false {}

	/**
	 * Gets the inode change time
	 * @link http://www.php.net/manual/en/splfileinfo.getctime.php
	 * @return int|false The last change time, in a Unix timestamp on success, or false on failure.
	 */
	public function getCTime (): int|false {}

	/**
	 * Gets file type
	 * @link http://www.php.net/manual/en/splfileinfo.gettype.php
	 * @return string|false A string representing the type of the entry.
	 * May be one of file, link,
	 * dir, block, fifo,
	 * char, socket, or unknown, or false on failure.
	 */
	public function getType (): string|false {}

	/**
	 * Tells if the entry is writable
	 * @link http://www.php.net/manual/en/splfileinfo.iswritable.php
	 * @return bool Returns true if writable, false otherwise;
	 */
	public function isWritable (): bool {}

	/**
	 * Tells if file is readable
	 * @link http://www.php.net/manual/en/splfileinfo.isreadable.php
	 * @return bool Returns true if readable, false otherwise.
	 */
	public function isReadable (): bool {}

	/**
	 * Tells if the file is executable
	 * @link http://www.php.net/manual/en/splfileinfo.isexecutable.php
	 * @return bool Returns true if executable, false otherwise.
	 */
	public function isExecutable (): bool {}

	/**
	 * Tells if the object references a regular file
	 * @link http://www.php.net/manual/en/splfileinfo.isfile.php
	 * @return bool Returns true if the file exists and is a regular file (not a link), false otherwise.
	 */
	public function isFile (): bool {}

	/**
	 * Tells if the file is a directory
	 * @link http://www.php.net/manual/en/splfileinfo.isdir.php
	 * @return bool Returns true if a directory, false otherwise.
	 */
	public function isDir (): bool {}

	/**
	 * Tells if the file is a link
	 * @link http://www.php.net/manual/en/splfileinfo.islink.php
	 * @return bool Returns true if the file is a link, false otherwise.
	 */
	public function isLink (): bool {}

	/**
	 * Gets the target of a link
	 * @link http://www.php.net/manual/en/splfileinfo.getlinktarget.php
	 * @return string|false Returns the target of the filesystem link on success, or false on failure.
	 */
	public function getLinkTarget (): string|false {}

	/**
	 * Gets absolute path to file
	 * @link http://www.php.net/manual/en/splfileinfo.getrealpath.php
	 * @return string|false Returns the path to the file, or false if the file does not exist.
	 */
	public function getRealPath (): string|false {}

	/**
	 * Gets an SplFileInfo object for the file
	 * @link http://www.php.net/manual/en/splfileinfo.getfileinfo.php
	 * @param string|null $class [optional] 
	 * @return SplFileInfo An SplFileInfo object created for the file.
	 */
	public function getFileInfo (?string $class = null): SplFileInfo {}

	/**
	 * Gets an SplFileInfo object for the path
	 * @link http://www.php.net/manual/en/splfileinfo.getpathinfo.php
	 * @param string|null $class [optional] 
	 * @return SplFileInfo|null Returns an SplFileInfo object for the parent path of the file on success, or null on failure.
	 */
	public function getPathInfo (?string $class = null): ?SplFileInfo {}

	/**
	 * Gets an SplFileObject object for the file
	 * @link http://www.php.net/manual/en/splfileinfo.openfile.php
	 * @param string $mode [optional] 
	 * @param bool $useIncludePath [optional] 
	 * @param resource|null $context [optional] 
	 * @return SplFileObject The opened file as an SplFileObject object.
	 */
	public function openFile (string $mode = '"r"', bool $useIncludePath = false, $context = null): SplFileObject {}

	/**
	 * Sets the class used with SplFileInfo::openFile
	 * @link http://www.php.net/manual/en/splfileinfo.setfileclass.php
	 * @param string $class [optional] 
	 * @return void No value is returned.
	 */
	public function setFileClass (string $class = 'SplFileObject::class'): void {}

	/**
	 * Sets the class used with SplFileInfo::getFileInfo and SplFileInfo::getPathInfo
	 * @link http://www.php.net/manual/en/splfileinfo.setinfoclass.php
	 * @param string $class [optional] 
	 * @return void No value is returned.
	 */
	public function setInfoClass (string $class = 'SplFileInfo::class'): void {}

	/**
	 * Returns the path to the file as a string
	 * @link http://www.php.net/manual/en/splfileinfo.tostring.php
	 * @return string Returns the path to the file.
	 */
	public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __debugInfo () {}

	/**
	 * {@inheritdoc}
	 * @deprecated 
	 */
	final public function _bad_state_ex () {}

}
// End of Phar v.8.2.6
