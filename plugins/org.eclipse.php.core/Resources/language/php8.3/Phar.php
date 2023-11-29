<?php

// Start of Phar v.8.3.0

class PharException extends Exception implements Throwable, Stringable {

	/**
	 * {@inheritdoc}
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, ?Throwable $previous = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getMessage (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getCode () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getFile (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getLine (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTrace (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function getPrevious (): ?Throwable {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTraceAsString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

}

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
	 * {@inheritdoc}
	 * @param string $filename
	 * @param int $flags [optional]
	 * @param string|null $alias [optional]
	 */
	public function __construct (string $filename, int $flags = 12288, ?string $alias = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function __destruct () {}

	/**
	 * {@inheritdoc}
	 * @param string $directory
	 */
	public function addEmptyDir (string $directory) {}

	/**
	 * {@inheritdoc}
	 * @param string $filename
	 * @param string|null $localName [optional]
	 */
	public function addFile (string $filename, ?string $localName = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $localName
	 * @param string $contents
	 */
	public function addFromString (string $localName, string $contents) {}

	/**
	 * {@inheritdoc}
	 * @param string $directory
	 * @param string $pattern [optional]
	 */
	public function buildFromDirectory (string $directory, string $pattern = '') {}

	/**
	 * {@inheritdoc}
	 * @param Traversable $iterator
	 * @param string|null $baseDirectory [optional]
	 */
	public function buildFromIterator (Traversable $iterator, ?string $baseDirectory = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param int $compression
	 */
	public function compressFiles (int $compression) {}

	/**
	 * {@inheritdoc}
	 */
	public function decompressFiles () {}

	/**
	 * {@inheritdoc}
	 * @param int $compression
	 * @param string|null $extension [optional]
	 */
	public function compress (int $compression, ?string $extension = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $extension [optional]
	 */
	public function decompress (?string $extension = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param int|null $format [optional]
	 * @param int|null $compression [optional]
	 * @param string|null $extension [optional]
	 */
	public function convertToExecutable (?int $format = NULL, ?int $compression = NULL, ?string $extension = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param int|null $format [optional]
	 * @param int|null $compression [optional]
	 * @param string|null $extension [optional]
	 */
	public function convertToData (?int $format = NULL, ?int $compression = NULL, ?string $extension = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $from
	 * @param string $to
	 */
	public function copy (string $from, string $to) {}

	/**
	 * {@inheritdoc}
	 * @param int $mode [optional]
	 */
	public function count (int $mode = 0) {}

	/**
	 * {@inheritdoc}
	 * @param string $localName
	 */
	public function delete (string $localName) {}

	/**
	 * {@inheritdoc}
	 */
	public function delMetadata () {}

	/**
	 * {@inheritdoc}
	 * @param string $directory
	 * @param array|string|null $files [optional]
	 * @param bool $overwrite [optional]
	 */
	public function extractTo (string $directory, array|string|null $files = NULL, bool $overwrite = false) {}

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
	 * {@inheritdoc}
	 */
	public function isWritable () {}

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
	 * {@inheritdoc}
	 * @param mixed $localName
	 * @param mixed $value
	 */
	public function offsetSet ($localName = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $localName
	 */
	public function offsetUnset ($localName = null) {}

	/**
	 * {@inheritdoc}
	 * @param string $alias
	 */
	public function setAlias (string $alias) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $index [optional]
	 * @param string|null $webIndex [optional]
	 */
	public function setDefaultStub (?string $index = NULL, ?string $webIndex = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $metadata
	 */
	public function setMetadata (mixed $metadata = null) {}

	/**
	 * {@inheritdoc}
	 * @param int $algo
	 * @param string|null $privateKey [optional]
	 */
	public function setSignatureAlgorithm (int $algo, ?string $privateKey = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $stub
	 * @param int $length [optional]
	 */
	public function setStub ($stub = null, int $length = NULL) {}

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
	 * {@inheritdoc}
	 * @param bool $allowLinks [optional]
	 */
	public function hasChildren (bool $allowLinks = false) {}

	/**
	 * {@inheritdoc}
	 */
	public function getChildren () {}

	/**
	 * {@inheritdoc}
	 */
	public function getSubPath () {}

	/**
	 * {@inheritdoc}
	 */
	public function getSubPathname () {}

	/**
	 * {@inheritdoc}
	 */
	public function rewind () {}

	/**
	 * {@inheritdoc}
	 */
	public function key () {}

	/**
	 * {@inheritdoc}
	 */
	public function current () {}

	/**
	 * {@inheritdoc}
	 */
	public function getFlags () {}

	/**
	 * {@inheritdoc}
	 * @param int $flags
	 */
	public function setFlags (int $flags) {}

	/**
	 * {@inheritdoc}
	 */
	public function getFilename () {}

	/**
	 * {@inheritdoc}
	 */
	public function getExtension () {}

	/**
	 * {@inheritdoc}
	 * @param string $suffix [optional]
	 */
	public function getBasename (string $suffix = '') {}

	/**
	 * {@inheritdoc}
	 */
	public function isDot () {}

	/**
	 * {@inheritdoc}
	 */
	public function valid () {}

	/**
	 * {@inheritdoc}
	 */
	public function next () {}

	/**
	 * {@inheritdoc}
	 * @param int $offset
	 */
	public function seek (int $offset) {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function getPathname () {}

	/**
	 * {@inheritdoc}
	 */
	public function getPerms () {}

	/**
	 * {@inheritdoc}
	 */
	public function getInode () {}

	/**
	 * {@inheritdoc}
	 */
	public function getSize () {}

	/**
	 * {@inheritdoc}
	 */
	public function getOwner () {}

	/**
	 * {@inheritdoc}
	 */
	public function getGroup () {}

	/**
	 * {@inheritdoc}
	 */
	public function getATime () {}

	/**
	 * {@inheritdoc}
	 */
	public function getMTime () {}

	/**
	 * {@inheritdoc}
	 */
	public function getCTime () {}

	/**
	 * {@inheritdoc}
	 */
	public function getType () {}

	/**
	 * {@inheritdoc}
	 */
	public function isReadable () {}

	/**
	 * {@inheritdoc}
	 */
	public function isExecutable () {}

	/**
	 * {@inheritdoc}
	 */
	public function isFile () {}

	/**
	 * {@inheritdoc}
	 */
	public function isDir () {}

	/**
	 * {@inheritdoc}
	 */
	public function isLink () {}

	/**
	 * {@inheritdoc}
	 */
	public function getLinkTarget () {}

	/**
	 * {@inheritdoc}
	 */
	public function getRealPath () {}

	/**
	 * {@inheritdoc}
	 * @param string|null $class [optional]
	 */
	public function getFileInfo (?string $class = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $class [optional]
	 */
	public function getPathInfo (?string $class = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $mode [optional]
	 * @param bool $useIncludePath [optional]
	 * @param mixed $context [optional]
	 */
	public function openFile (string $mode = 'r', bool $useIncludePath = false, $context = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $class [optional]
	 */
	public function setFileClass (string $class = 'SplFileObject') {}

	/**
	 * {@inheritdoc}
	 * @param string $class [optional]
	 */
	public function setInfoClass (string $class = 'SplFileInfo') {}

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
	 * {@inheritdoc}
	 * @param string $filename
	 * @param int $flags [optional]
	 * @param string|null $alias [optional]
	 * @param int $format [optional]
	 */
	public function __construct (string $filename, int $flags = 12288, ?string $alias = NULL, int $format = 0) {}

	/**
	 * {@inheritdoc}
	 */
	public function __destruct () {}

	/**
	 * {@inheritdoc}
	 * @param string $directory
	 */
	public function addEmptyDir (string $directory) {}

	/**
	 * {@inheritdoc}
	 * @param string $filename
	 * @param string|null $localName [optional]
	 */
	public function addFile (string $filename, ?string $localName = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $localName
	 * @param string $contents
	 */
	public function addFromString (string $localName, string $contents) {}

	/**
	 * {@inheritdoc}
	 * @param string $directory
	 * @param string $pattern [optional]
	 */
	public function buildFromDirectory (string $directory, string $pattern = '') {}

	/**
	 * {@inheritdoc}
	 * @param Traversable $iterator
	 * @param string|null $baseDirectory [optional]
	 */
	public function buildFromIterator (Traversable $iterator, ?string $baseDirectory = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param int $compression
	 */
	public function compressFiles (int $compression) {}

	/**
	 * {@inheritdoc}
	 */
	public function decompressFiles () {}

	/**
	 * {@inheritdoc}
	 * @param int $compression
	 * @param string|null $extension [optional]
	 */
	public function compress (int $compression, ?string $extension = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $extension [optional]
	 */
	public function decompress (?string $extension = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param int|null $format [optional]
	 * @param int|null $compression [optional]
	 * @param string|null $extension [optional]
	 */
	public function convertToExecutable (?int $format = NULL, ?int $compression = NULL, ?string $extension = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param int|null $format [optional]
	 * @param int|null $compression [optional]
	 * @param string|null $extension [optional]
	 */
	public function convertToData (?int $format = NULL, ?int $compression = NULL, ?string $extension = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $from
	 * @param string $to
	 */
	public function copy (string $from, string $to) {}

	/**
	 * {@inheritdoc}
	 * @param int $mode [optional]
	 */
	public function count (int $mode = 0) {}

	/**
	 * {@inheritdoc}
	 * @param string $localName
	 */
	public function delete (string $localName) {}

	/**
	 * {@inheritdoc}
	 */
	public function delMetadata () {}

	/**
	 * {@inheritdoc}
	 * @param string $directory
	 * @param array|string|null $files [optional]
	 * @param bool $overwrite [optional]
	 */
	public function extractTo (string $directory, array|string|null $files = NULL, bool $overwrite = false) {}

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
	 * {@inheritdoc}
	 */
	public function isWritable () {}

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
	 * {@inheritdoc}
	 * @param mixed $localName
	 * @param mixed $value
	 */
	public function offsetSet ($localName = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $localName
	 */
	public function offsetUnset ($localName = null) {}

	/**
	 * {@inheritdoc}
	 * @param string $alias
	 */
	public function setAlias (string $alias) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $index [optional]
	 * @param string|null $webIndex [optional]
	 */
	public function setDefaultStub (?string $index = NULL, ?string $webIndex = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $metadata
	 */
	public function setMetadata (mixed $metadata = null) {}

	/**
	 * {@inheritdoc}
	 * @param int $algo
	 * @param string|null $privateKey [optional]
	 */
	public function setSignatureAlgorithm (int $algo, ?string $privateKey = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $stub
	 * @param int $length [optional]
	 */
	public function setStub ($stub = null, int $length = NULL) {}

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
	 * {@inheritdoc}
	 * @param bool $allowLinks [optional]
	 */
	public function hasChildren (bool $allowLinks = false) {}

	/**
	 * {@inheritdoc}
	 */
	public function getChildren () {}

	/**
	 * {@inheritdoc}
	 */
	public function getSubPath () {}

	/**
	 * {@inheritdoc}
	 */
	public function getSubPathname () {}

	/**
	 * {@inheritdoc}
	 */
	public function rewind () {}

	/**
	 * {@inheritdoc}
	 */
	public function key () {}

	/**
	 * {@inheritdoc}
	 */
	public function current () {}

	/**
	 * {@inheritdoc}
	 */
	public function getFlags () {}

	/**
	 * {@inheritdoc}
	 * @param int $flags
	 */
	public function setFlags (int $flags) {}

	/**
	 * {@inheritdoc}
	 */
	public function getFilename () {}

	/**
	 * {@inheritdoc}
	 */
	public function getExtension () {}

	/**
	 * {@inheritdoc}
	 * @param string $suffix [optional]
	 */
	public function getBasename (string $suffix = '') {}

	/**
	 * {@inheritdoc}
	 */
	public function isDot () {}

	/**
	 * {@inheritdoc}
	 */
	public function valid () {}

	/**
	 * {@inheritdoc}
	 */
	public function next () {}

	/**
	 * {@inheritdoc}
	 * @param int $offset
	 */
	public function seek (int $offset) {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function getPathname () {}

	/**
	 * {@inheritdoc}
	 */
	public function getPerms () {}

	/**
	 * {@inheritdoc}
	 */
	public function getInode () {}

	/**
	 * {@inheritdoc}
	 */
	public function getSize () {}

	/**
	 * {@inheritdoc}
	 */
	public function getOwner () {}

	/**
	 * {@inheritdoc}
	 */
	public function getGroup () {}

	/**
	 * {@inheritdoc}
	 */
	public function getATime () {}

	/**
	 * {@inheritdoc}
	 */
	public function getMTime () {}

	/**
	 * {@inheritdoc}
	 */
	public function getCTime () {}

	/**
	 * {@inheritdoc}
	 */
	public function getType () {}

	/**
	 * {@inheritdoc}
	 */
	public function isReadable () {}

	/**
	 * {@inheritdoc}
	 */
	public function isExecutable () {}

	/**
	 * {@inheritdoc}
	 */
	public function isFile () {}

	/**
	 * {@inheritdoc}
	 */
	public function isDir () {}

	/**
	 * {@inheritdoc}
	 */
	public function isLink () {}

	/**
	 * {@inheritdoc}
	 */
	public function getLinkTarget () {}

	/**
	 * {@inheritdoc}
	 */
	public function getRealPath () {}

	/**
	 * {@inheritdoc}
	 * @param string|null $class [optional]
	 */
	public function getFileInfo (?string $class = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $class [optional]
	 */
	public function getPathInfo (?string $class = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $mode [optional]
	 * @param bool $useIncludePath [optional]
	 * @param mixed $context [optional]
	 */
	public function openFile (string $mode = 'r', bool $useIncludePath = false, $context = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $class [optional]
	 */
	public function setFileClass (string $class = 'SplFileObject') {}

	/**
	 * {@inheritdoc}
	 * @param string $class [optional]
	 */
	public function setInfoClass (string $class = 'SplFileInfo') {}

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

class PharFileInfo extends SplFileInfo implements Stringable {

	/**
	 * {@inheritdoc}
	 * @param string $filename
	 */
	public function __construct (string $filename) {}

	/**
	 * {@inheritdoc}
	 */
	public function __destruct () {}

	/**
	 * {@inheritdoc}
	 * @param int $perms
	 */
	public function chmod (int $perms) {}

	/**
	 * {@inheritdoc}
	 * @param int $compression
	 */
	public function compress (int $compression) {}

	/**
	 * {@inheritdoc}
	 */
	public function decompress () {}

	/**
	 * {@inheritdoc}
	 */
	public function delMetadata () {}

	/**
	 * {@inheritdoc}
	 */
	public function getCompressedSize () {}

	/**
	 * {@inheritdoc}
	 */
	public function getCRC32 () {}

	/**
	 * {@inheritdoc}
	 */
	public function getContent () {}

	/**
	 * {@inheritdoc}
	 * @param array $unserializeOptions [optional]
	 */
	public function getMetadata (array $unserializeOptions = array (
)) {}

	/**
	 * {@inheritdoc}
	 */
	public function getPharFlags () {}

	/**
	 * {@inheritdoc}
	 */
	public function hasMetadata () {}

	/**
	 * {@inheritdoc}
	 * @param int|null $compression [optional]
	 */
	public function isCompressed (?int $compression = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function isCRCChecked () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $metadata
	 */
	public function setMetadata (mixed $metadata = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getPath () {}

	/**
	 * {@inheritdoc}
	 */
	public function getFilename () {}

	/**
	 * {@inheritdoc}
	 */
	public function getExtension () {}

	/**
	 * {@inheritdoc}
	 * @param string $suffix [optional]
	 */
	public function getBasename (string $suffix = '') {}

	/**
	 * {@inheritdoc}
	 */
	public function getPathname () {}

	/**
	 * {@inheritdoc}
	 */
	public function getPerms () {}

	/**
	 * {@inheritdoc}
	 */
	public function getInode () {}

	/**
	 * {@inheritdoc}
	 */
	public function getSize () {}

	/**
	 * {@inheritdoc}
	 */
	public function getOwner () {}

	/**
	 * {@inheritdoc}
	 */
	public function getGroup () {}

	/**
	 * {@inheritdoc}
	 */
	public function getATime () {}

	/**
	 * {@inheritdoc}
	 */
	public function getMTime () {}

	/**
	 * {@inheritdoc}
	 */
	public function getCTime () {}

	/**
	 * {@inheritdoc}
	 */
	public function getType () {}

	/**
	 * {@inheritdoc}
	 */
	public function isWritable () {}

	/**
	 * {@inheritdoc}
	 */
	public function isReadable () {}

	/**
	 * {@inheritdoc}
	 */
	public function isExecutable () {}

	/**
	 * {@inheritdoc}
	 */
	public function isFile () {}

	/**
	 * {@inheritdoc}
	 */
	public function isDir () {}

	/**
	 * {@inheritdoc}
	 */
	public function isLink () {}

	/**
	 * {@inheritdoc}
	 */
	public function getLinkTarget () {}

	/**
	 * {@inheritdoc}
	 */
	public function getRealPath () {}

	/**
	 * {@inheritdoc}
	 * @param string|null $class [optional]
	 */
	public function getFileInfo (?string $class = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string|null $class [optional]
	 */
	public function getPathInfo (?string $class = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $mode [optional]
	 * @param bool $useIncludePath [optional]
	 * @param mixed $context [optional]
	 */
	public function openFile (string $mode = 'r', bool $useIncludePath = false, $context = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $class [optional]
	 */
	public function setFileClass (string $class = 'SplFileObject') {}

	/**
	 * {@inheritdoc}
	 * @param string $class [optional]
	 */
	public function setInfoClass (string $class = 'SplFileInfo') {}

	/**
	 * {@inheritdoc}
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
// End of Phar v.8.3.0
