<?php

// Start of ftp v.8.0.28

/**
 * Opens an FTP connection
 * @link http://www.php.net/manual/en/function.ftp-connect.php
 * @param string $hostname 
 * @param int $port [optional] 
 * @param int $timeout [optional] 
 * @return resource|false Returns an FTP\Connection instance on success, or false on failure.
 */
function ftp_connect (string $hostname, int $port = 21, int $timeout = 90) {}

/**
 * Opens a Secure SSL-FTP connection
 * @link http://www.php.net/manual/en/function.ftp-ssl-connect.php
 * @param string $hostname 
 * @param int $port [optional] 
 * @param int $timeout [optional] 
 * @return resource|false Returns an FTP\Connection instance on success, or false on failure.
 */
function ftp_ssl_connect (string $hostname, int $port = 21, int $timeout = 90) {}

/**
 * Logs in to an FTP connection
 * @link http://www.php.net/manual/en/function.ftp-login.php
 * @param resource $ftp 
 * @param string $username 
 * @param string $password 
 * @return bool Returns true on success or false on failure.
 * If login fails, PHP will also throw a warning.
 */
function ftp_login ($ftp, string $username, string $password): bool {}

/**
 * Returns the current directory name
 * @link http://www.php.net/manual/en/function.ftp-pwd.php
 * @param resource $ftp 
 * @return string|false Returns the current directory name or false on error.
 */
function ftp_pwd ($ftp): string|int {}

/**
 * Changes to the parent directory
 * @link http://www.php.net/manual/en/function.ftp-cdup.php
 * @param resource $ftp 
 * @return bool Returns true on success or false on failure.
 */
function ftp_cdup ($ftp): bool {}

/**
 * Changes the current directory on a FTP server
 * @link http://www.php.net/manual/en/function.ftp-chdir.php
 * @param resource $ftp 
 * @param string $directory 
 * @return bool Returns true on success or false on failure.
 * If changing directory fails, PHP will also throw a warning.
 */
function ftp_chdir ($ftp, string $directory): bool {}

/**
 * Requests execution of a command on the FTP server
 * @link http://www.php.net/manual/en/function.ftp-exec.php
 * @param resource $ftp 
 * @param string $command 
 * @return bool Returns true if the command was successful (server sent response code:
 * 200); otherwise returns false.
 */
function ftp_exec ($ftp, string $command): bool {}

/**
 * Sends an arbitrary command to an FTP server
 * @link http://www.php.net/manual/en/function.ftp-raw.php
 * @param resource $ftp 
 * @param string $command 
 * @return array|null Returns the server's response as an array of strings, or null on failure.
 * No parsing is performed on the response string, nor does
 * ftp_raw determine if the command succeeded.
 */
function ftp_raw ($ftp, string $command): ?array {}

/**
 * Creates a directory
 * @link http://www.php.net/manual/en/function.ftp-mkdir.php
 * @param resource $ftp 
 * @param string $directory 
 * @return string|false Returns the newly created directory name on success or false on error.
 */
function ftp_mkdir ($ftp, string $directory): string|int {}

/**
 * Removes a directory
 * @link http://www.php.net/manual/en/function.ftp-rmdir.php
 * @param resource $ftp 
 * @param string $directory 
 * @return bool Returns true on success or false on failure.
 */
function ftp_rmdir ($ftp, string $directory): bool {}

/**
 * Set permissions on a file via FTP
 * @link http://www.php.net/manual/en/function.ftp-chmod.php
 * @param resource $ftp 
 * @param int $permissions 
 * @param string $filename 
 * @return int|false Returns the new file permissions on success or false on error.
 */
function ftp_chmod ($ftp, int $permissions, string $filename): int {}

/**
 * Allocates space for a file to be uploaded
 * @link http://www.php.net/manual/en/function.ftp-alloc.php
 * @param resource $ftp 
 * @param int $size 
 * @param string $response [optional] 
 * @return bool Returns true on success or false on failure.
 */
function ftp_alloc ($ftp, int $size, string &$response = null): bool {}

/**
 * Returns a list of files in the given directory
 * @link http://www.php.net/manual/en/function.ftp-nlist.php
 * @param resource $ftp 
 * @param string $directory 
 * @return array|false Returns an array of filenames from the specified directory on success or
 * false on error.
 */
function ftp_nlist ($ftp, string $directory): array|int {}

/**
 * Returns a detailed list of files in the given directory
 * @link http://www.php.net/manual/en/function.ftp-rawlist.php
 * @param resource $ftp 
 * @param string $directory 
 * @param bool $recursive [optional] 
 * @return array|false Returns an array where each element corresponds to one line of text. Returns
 * false when passed directory is invalid.
 * <p>The output is not parsed in any way. The system type identifier returned by
 * ftp_systype can be used to determine how the results 
 * should be interpreted.</p>
 */
function ftp_rawlist ($ftp, string $directory, bool $recursive = false): array|int {}

/**
 * Returns a list of files in the given directory
 * @link http://www.php.net/manual/en/function.ftp-mlsd.php
 * @param resource $ftp 
 * @param string $directory 
 * @return array|false Returns an array of arrays with file infos from the specified directory on success or
 * false on error.
 */
function ftp_mlsd ($ftp, string $directory): array|int {}

/**
 * Returns the system type identifier of the remote FTP server
 * @link http://www.php.net/manual/en/function.ftp-systype.php
 * @param resource $ftp 
 * @return string|false Returns the remote system type, or false on error.
 */
function ftp_systype ($ftp): string|int {}

/**
 * Downloads a file from the FTP server and saves to an open file
 * @link http://www.php.net/manual/en/function.ftp-fget.php
 * @param resource $ftp 
 * @param resource $stream 
 * @param string $remote_filename 
 * @param int $mode [optional] 
 * @param int $offset [optional] 
 * @return bool Returns true on success or false on failure.
 */
function ftp_fget ($ftp, $stream, string $remote_filename, int $mode = FTP_BINARY, int $offset = null): bool {}

/**
 * Retrieves a file from the FTP server and writes it to an open file (non-blocking)
 * @link http://www.php.net/manual/en/function.ftp-nb-fget.php
 * @param resource $ftp 
 * @param resource $stream 
 * @param string $remote_filename 
 * @param int $mode [optional] 
 * @param int $offset [optional] 
 * @return int Returns FTP_FAILED or FTP_FINISHED
 * or FTP_MOREDATA.
 */
function ftp_nb_fget ($ftp, $stream, string $remote_filename, int $mode = FTP_BINARY, int $offset = null): int {}

/**
 * Turns passive mode on or off
 * @link http://www.php.net/manual/en/function.ftp-pasv.php
 * @param resource $ftp 
 * @param bool $enable 
 * @return bool Returns true on success or false on failure.
 */
function ftp_pasv ($ftp, bool $enable): bool {}

/**
 * Downloads a file from the FTP server
 * @link http://www.php.net/manual/en/function.ftp-get.php
 * @param resource $ftp 
 * @param string $local_filename 
 * @param string $remote_filename 
 * @param int $mode [optional] 
 * @param int $offset [optional] 
 * @return bool Returns true on success or false on failure.
 */
function ftp_get ($ftp, string $local_filename, string $remote_filename, int $mode = FTP_BINARY, int $offset = null): bool {}

/**
 * Retrieves a file from the FTP server and writes it to a local file (non-blocking)
 * @link http://www.php.net/manual/en/function.ftp-nb-get.php
 * @param resource $ftp 
 * @param string $local_filename 
 * @param string $remote_filename 
 * @param int $mode [optional] 
 * @param int $offset [optional] 
 * @return int|false Returns FTP_FAILED or FTP_FINISHED
 * or FTP_MOREDATA, or false on failure to open the local file.
 */
function ftp_nb_get ($ftp, string $local_filename, string $remote_filename, int $mode = FTP_BINARY, int $offset = null): int {}

/**
 * Continues retrieving/sending a file (non-blocking)
 * @link http://www.php.net/manual/en/function.ftp-nb-continue.php
 * @param resource $ftp 
 * @return int Returns FTP_FAILED or FTP_FINISHED
 * or FTP_MOREDATA.
 */
function ftp_nb_continue ($ftp): int {}

/**
 * Uploads from an open file to the FTP server
 * @link http://www.php.net/manual/en/function.ftp-fput.php
 * @param resource $ftp 
 * @param string $remote_filename 
 * @param resource $stream 
 * @param int $mode [optional] 
 * @param int $offset [optional] 
 * @return bool Returns true on success or false on failure.
 */
function ftp_fput ($ftp, string $remote_filename, $stream, int $mode = FTP_BINARY, int $offset = null): bool {}

/**
 * Stores a file from an open file to the FTP server (non-blocking)
 * @link http://www.php.net/manual/en/function.ftp-nb-fput.php
 * @param resource $ftp 
 * @param string $remote_filename 
 * @param resource $stream 
 * @param int $mode [optional] 
 * @param int $offset [optional] 
 * @return int Returns FTP_FAILED or FTP_FINISHED
 * or FTP_MOREDATA.
 */
function ftp_nb_fput ($ftp, string $remote_filename, $stream, int $mode = FTP_BINARY, int $offset = null): int {}

/**
 * Uploads a file to the FTP server
 * @link http://www.php.net/manual/en/function.ftp-put.php
 * @param resource $ftp 
 * @param string $remote_filename 
 * @param string $local_filename 
 * @param int $mode [optional] 
 * @param int $offset [optional] 
 * @return bool Returns true on success or false on failure.
 */
function ftp_put ($ftp, string $remote_filename, string $local_filename, int $mode = FTP_BINARY, int $offset = null): bool {}

/**
 * Append the contents of a file to another file on the FTP server
 * @link http://www.php.net/manual/en/function.ftp-append.php
 * @param resource $ftp >An FTP\Connection instance.
 * @param string $remote_filename 
 * @param string $local_filename 
 * @param int $mode [optional] 
 * @return bool Returns true on success or false on failure.
 */
function ftp_append ($ftp, string $remote_filename, string $local_filename, int $mode = FTP_BINARY): bool {}

/**
 * Stores a file on the FTP server (non-blocking)
 * @link http://www.php.net/manual/en/function.ftp-nb-put.php
 * @param resource $ftp 
 * @param string $remote_filename 
 * @param string $local_filename 
 * @param int $mode [optional] 
 * @param int $offset [optional] 
 * @return int|false Returns FTP_FAILED or FTP_FINISHED
 * or FTP_MOREDATA, or false on failure to open the local file.
 */
function ftp_nb_put ($ftp, string $remote_filename, string $local_filename, int $mode = FTP_BINARY, int $offset = null): int {}

/**
 * Returns the size of the given file
 * @link http://www.php.net/manual/en/function.ftp-size.php
 * @param resource $ftp 
 * @param string $filename 
 * @return int Returns the file size on success, or -1 on error.
 */
function ftp_size ($ftp, string $filename): int {}

/**
 * Returns the last modified time of the given file
 * @link http://www.php.net/manual/en/function.ftp-mdtm.php
 * @param resource $ftp 
 * @param string $filename 
 * @return int Returns the last modified time as a local Unix timestamp on success, or -1 on 
 * error.
 */
function ftp_mdtm ($ftp, string $filename): int {}

/**
 * Renames a file or a directory on the FTP server
 * @link http://www.php.net/manual/en/function.ftp-rename.php
 * @param resource $ftp 
 * @param string $from 
 * @param string $to 
 * @return bool Returns true on success or false on failure. Upon failure (such as attempting to rename a non-existent
 * file), an E_WARNING error will be emitted.
 */
function ftp_rename ($ftp, string $from, string $to): bool {}

/**
 * Deletes a file on the FTP server
 * @link http://www.php.net/manual/en/function.ftp-delete.php
 * @param resource $ftp 
 * @param string $filename 
 * @return bool Returns true on success or false on failure.
 */
function ftp_delete ($ftp, string $filename): bool {}

/**
 * Sends a SITE command to the server
 * @link http://www.php.net/manual/en/function.ftp-site.php
 * @param resource $ftp 
 * @param string $command 
 * @return bool Returns true on success or false on failure.
 */
function ftp_site ($ftp, string $command): bool {}

/**
 * Closes an FTP connection
 * @link http://www.php.net/manual/en/function.ftp-close.php
 * @param resource $ftp 
 * @return bool Returns true on success or false on failure.
 */
function ftp_close ($ftp): bool {}

/**
 * Alias of ftp_close
 * @link http://www.php.net/manual/en/function.ftp-quit.php
 * @param resource $ftp 
 * @return bool Returns true on success or false on failure.
 */
function ftp_quit ($ftp): bool {}

/**
 * Set miscellaneous runtime FTP options
 * @link http://www.php.net/manual/en/function.ftp-set-option.php
 * @param resource $ftp 
 * @param int $option 
 * @param int|bool $value 
 * @return bool Returns true if the option could be set; false if not. A warning
 * message will be thrown if the option is not
 * supported or the passed value doesn't match the
 * expected value for the given option.
 */
function ftp_set_option ($ftp, int $option, int|bool $value): bool {}

/**
 * Retrieves various runtime behaviours of the current FTP connection
 * @link http://www.php.net/manual/en/function.ftp-get-option.php
 * @param resource $ftp 
 * @param int $option 
 * @return int|bool Returns the value on success or false if the given 
 * option is not supported. In the latter case, a
 * warning message is also thrown.
 */
function ftp_get_option ($ftp, int $option): int|bool {}


/**
 * 
 * @link http://www.php.net/manual/en/ftp.constants.php
 * @var int
 */
define ('FTP_ASCII', 1);

/**
 * Alias of FTP_ASCII.
 * @link http://www.php.net/manual/en/ftp.constants.php
 * @var int
 */
define ('FTP_TEXT', 1);

/**
 * 
 * @link http://www.php.net/manual/en/ftp.constants.php
 * @var int
 */
define ('FTP_BINARY', 2);

/**
 * Alias of FTP_BINARY.
 * @link http://www.php.net/manual/en/ftp.constants.php
 * @var int
 */
define ('FTP_IMAGE', 2);

/**
 * Automatically determine resume position and start position for GET and PUT requests
 * (only works if FTP_AUTOSEEK is enabled)
 * @link http://www.php.net/manual/en/ftp.constants.php
 * @var int
 */
define ('FTP_AUTORESUME', -1);

/**
 * See ftp_set_option for information.
 * @link http://www.php.net/manual/en/ftp.constants.php
 * @var int
 */
define ('FTP_TIMEOUT_SEC', 0);

/**
 * See ftp_set_option for information.
 * @link http://www.php.net/manual/en/ftp.constants.php
 * @var int
 */
define ('FTP_AUTOSEEK', 1);

/**
 * See ftp_set_option for information.
 * @link http://www.php.net/manual/en/ftp.constants.php
 * @var bool
 */
define ('FTP_USEPASVADDRESS', 2);

/**
 * Asynchronous transfer has failed
 * @link http://www.php.net/manual/en/ftp.constants.php
 * @var int
 */
define ('FTP_FAILED', 0);

/**
 * Asynchronous transfer has finished
 * @link http://www.php.net/manual/en/ftp.constants.php
 * @var int
 */
define ('FTP_FINISHED', 1);

/**
 * Asynchronous transfer is still active
 * @link http://www.php.net/manual/en/ftp.constants.php
 * @var int
 */
define ('FTP_MOREDATA', 2);

// End of ftp v.8.0.28
