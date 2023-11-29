<?php

// Start of zip v.1.22.3

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
	const FL_OPEN_FILE_NOW = 1073741824;
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
	const ER_DATA_LENGTH = 33;
	const ER_NOT_ALLOWED = 34;
	const AFL_RDONLY = 2;
	const AFL_IS_TORRENTZIP = 4;
	const AFL_WANT_TORRENTZIP = 8;
	const AFL_CREATE_OR_KEEP_FILE_FOR_EMPTY_ARCHIVE = 16;
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
	const LIBZIP_VERSION = "1.10.1";
	const LENGTH_TO_END = 0;
	const LENGTH_UNCHECKED = -2;


	public int $lastId;

	public int $status;

	public int $statusSys;

	public int $numFiles;

	public string $filename;

	public string $comment;

	/**
	 * {@inheritdoc}
	 * @param string $filename
	 * @param int $flags [optional]
	 */
	public function open (string $filename, int $flags = 0) {}

	/**
	 * {@inheritdoc}
	 * @param string $password
	 */
	public function setPassword (string $password) {}

	/**
	 * {@inheritdoc}
	 */
	public function close () {}

	/**
	 * {@inheritdoc}
	 */
	public function count () {}

	/**
	 * {@inheritdoc}
	 */
	public function getStatusString () {}

	/**
	 * {@inheritdoc}
	 */
	public function clearError (): void {}

	/**
	 * {@inheritdoc}
	 * @param string $dirname
	 * @param int $flags [optional]
	 */
	public function addEmptyDir (string $dirname, int $flags = 0) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 * @param string $content
	 * @param int $flags [optional]
	 */
	public function addFromString (string $name, string $content, int $flags = 8192) {}

	/**
	 * {@inheritdoc}
	 * @param string $filepath
	 * @param string $entryname [optional]
	 * @param int $start [optional]
	 * @param int $length [optional]
	 * @param int $flags [optional]
	 */
	public function addFile (string $filepath, string $entryname = '', int $start = 0, int $length = 0, int $flags = 8192) {}

	/**
	 * {@inheritdoc}
	 * @param string $filepath
	 * @param int $index
	 * @param int $start [optional]
	 * @param int $length [optional]
	 * @param int $flags [optional]
	 */
	public function replaceFile (string $filepath, int $index, int $start = 0, int $length = 0, int $flags = 0) {}

	/**
	 * {@inheritdoc}
	 * @param string $pattern
	 * @param int $flags [optional]
	 * @param array $options [optional]
	 */
	public function addGlob (string $pattern, int $flags = 0, array $options = array (
)) {}

	/**
	 * {@inheritdoc}
	 * @param string $pattern
	 * @param string $path [optional]
	 * @param array $options [optional]
	 */
	public function addPattern (string $pattern, string $path = '.', array $options = array (
)) {}

	/**
	 * {@inheritdoc}
	 * @param int $index
	 * @param string $new_name
	 */
	public function renameIndex (int $index, string $new_name) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 * @param string $new_name
	 */
	public function renameName (string $name, string $new_name) {}

	/**
	 * {@inheritdoc}
	 * @param string $comment
	 */
	public function setArchiveComment (string $comment) {}

	/**
	 * {@inheritdoc}
	 * @param int $flags [optional]
	 */
	public function getArchiveComment (int $flags = 0) {}

	/**
	 * {@inheritdoc}
	 * @param int $flag
	 * @param int $value
	 */
	public function setArchiveFlag (int $flag, int $value): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $flag
	 * @param int $flags [optional]
	 */
	public function getArchiveFlag (int $flag, int $flags = 0): int {}

	/**
	 * {@inheritdoc}
	 * @param int $index
	 * @param string $comment
	 */
	public function setCommentIndex (int $index, string $comment) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 * @param string $comment
	 */
	public function setCommentName (string $name, string $comment) {}

	/**
	 * {@inheritdoc}
	 * @param int $index
	 * @param int $timestamp
	 * @param int $flags [optional]
	 */
	public function setMtimeIndex (int $index, int $timestamp, int $flags = 0) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 * @param int $timestamp
	 * @param int $flags [optional]
	 */
	public function setMtimeName (string $name, int $timestamp, int $flags = 0) {}

	/**
	 * {@inheritdoc}
	 * @param int $index
	 * @param int $flags [optional]
	 */
	public function getCommentIndex (int $index, int $flags = 0) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 * @param int $flags [optional]
	 */
	public function getCommentName (string $name, int $flags = 0) {}

	/**
	 * {@inheritdoc}
	 * @param int $index
	 */
	public function deleteIndex (int $index) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 */
	public function deleteName (string $name) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 * @param int $flags [optional]
	 */
	public function statName (string $name, int $flags = 0) {}

	/**
	 * {@inheritdoc}
	 * @param int $index
	 * @param int $flags [optional]
	 */
	public function statIndex (int $index, int $flags = 0) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 * @param int $flags [optional]
	 */
	public function locateName (string $name, int $flags = 0) {}

	/**
	 * {@inheritdoc}
	 * @param int $index
	 * @param int $flags [optional]
	 */
	public function getNameIndex (int $index, int $flags = 0) {}

	/**
	 * {@inheritdoc}
	 */
	public function unchangeArchive () {}

	/**
	 * {@inheritdoc}
	 */
	public function unchangeAll () {}

	/**
	 * {@inheritdoc}
	 * @param int $index
	 */
	public function unchangeIndex (int $index) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 */
	public function unchangeName (string $name) {}

	/**
	 * {@inheritdoc}
	 * @param string $pathto
	 * @param array|string|null $files [optional]
	 */
	public function extractTo (string $pathto, array|string|null $files = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 * @param int $len [optional]
	 * @param int $flags [optional]
	 */
	public function getFromName (string $name, int $len = 0, int $flags = 0) {}

	/**
	 * {@inheritdoc}
	 * @param int $index
	 * @param int $len [optional]
	 * @param int $flags [optional]
	 */
	public function getFromIndex (int $index, int $len = 0, int $flags = 0) {}

	/**
	 * {@inheritdoc}
	 * @param int $index
	 * @param int $flags [optional]
	 */
	public function getStreamIndex (int $index, int $flags = 0) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 * @param int $flags [optional]
	 */
	public function getStreamName (string $name, int $flags = 0) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 */
	public function getStream (string $name) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 * @param int $opsys
	 * @param int $attr
	 * @param int $flags [optional]
	 */
	public function setExternalAttributesName (string $name, int $opsys, int $attr, int $flags = 0) {}

	/**
	 * {@inheritdoc}
	 * @param int $index
	 * @param int $opsys
	 * @param int $attr
	 * @param int $flags [optional]
	 */
	public function setExternalAttributesIndex (int $index, int $opsys, int $attr, int $flags = 0) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 * @param mixed $opsys
	 * @param mixed $attr
	 * @param int $flags [optional]
	 */
	public function getExternalAttributesName (string $name, &$opsys = null, &$attr = null, int $flags = 0) {}

	/**
	 * {@inheritdoc}
	 * @param int $index
	 * @param mixed $opsys
	 * @param mixed $attr
	 * @param int $flags [optional]
	 */
	public function getExternalAttributesIndex (int $index, &$opsys = null, &$attr = null, int $flags = 0) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 * @param int $method
	 * @param int $compflags [optional]
	 */
	public function setCompressionName (string $name, int $method, int $compflags = 0) {}

	/**
	 * {@inheritdoc}
	 * @param int $index
	 * @param int $method
	 * @param int $compflags [optional]
	 */
	public function setCompressionIndex (int $index, int $method, int $compflags = 0) {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 * @param int $method
	 * @param string|null $password [optional]
	 */
	public function setEncryptionName (string $name, int $method, ?string $password = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param int $index
	 * @param int $method
	 * @param string|null $password [optional]
	 */
	public function setEncryptionIndex (int $index, int $method, ?string $password = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param float $rate
	 * @param callable $callback
	 */
	public function registerProgressCallback (float $rate, callable $callback) {}

	/**
	 * {@inheritdoc}
	 * @param callable $callback
	 */
	public function registerCancelCallback (callable $callback) {}

	/**
	 * {@inheritdoc}
	 * @param int $method
	 * @param bool $enc [optional]
	 */
	public static function isCompressionMethodSupported (int $method, bool $enc = true): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $method
	 * @param bool $enc [optional]
	 */
	public static function isEncryptionMethodSupported (int $method, bool $enc = true): bool {}

}

/**
 * {@inheritdoc}
 * @param string $filename
 * @deprecated 
 */
function zip_open (string $filename) {}

/**
 * {@inheritdoc}
 * @param mixed $zip
 * @deprecated 
 */
function zip_close ($zip = null): void {}

/**
 * {@inheritdoc}
 * @param mixed $zip
 * @deprecated 
 */
function zip_read ($zip = null) {}

/**
 * {@inheritdoc}
 * @param mixed $zip_dp
 * @param mixed $zip_entry
 * @param string $mode [optional]
 * @deprecated 
 */
function zip_entry_open ($zip_dp = null, $zip_entry = null, string $mode = 'rb'): bool {}

/**
 * {@inheritdoc}
 * @param mixed $zip_entry
 * @deprecated 
 */
function zip_entry_close ($zip_entry = null): bool {}

/**
 * {@inheritdoc}
 * @param mixed $zip_entry
 * @param int $len [optional]
 * @deprecated 
 */
function zip_entry_read ($zip_entry = null, int $len = 1024): string|false {}

/**
 * {@inheritdoc}
 * @param mixed $zip_entry
 * @deprecated 
 */
function zip_entry_name ($zip_entry = null): string|false {}

/**
 * {@inheritdoc}
 * @param mixed $zip_entry
 * @deprecated 
 */
function zip_entry_compressedsize ($zip_entry = null): int|false {}

/**
 * {@inheritdoc}
 * @param mixed $zip_entry
 * @deprecated 
 */
function zip_entry_filesize ($zip_entry = null): int|false {}

/**
 * {@inheritdoc}
 * @param mixed $zip_entry
 * @deprecated 
 */
function zip_entry_compressionmethod ($zip_entry = null): string|false {}

// End of zip v.1.22.3
