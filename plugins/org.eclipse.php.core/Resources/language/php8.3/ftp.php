<?php

// Start of ftp v.8.2.6

namespace FTP {

/**
 * A fully opaque class which replaces a ftp resource as of PHP 8.1.0.
 * @link http://www.php.net/manual/en/class.ftp-connection.php
 */
final class Connection  {
}


}


namespace {

/**
 * Opens an FTP connection
 * @link http://www.php.net/manual/en/function.ftp-connect.php
 * @param string $hostname 
 * @param int $port [optional] 
 * @param int $timeout [optional] 
 * @return FTP\Connection|false Returns an FTP\Connection instance on success, or false on failure.
 */
function ftp_connect (string $hostname, int $port = 21, int $timeout = 90): FTP\Connection|false {}

/**
 * Opens a Secure SSL-FTP connection
 * @link http://www.php.net/manual/en/function.ftp-ssl-connect.php
 * @param string $hostname 
 * @param int $port [optional] 
 * @param int $timeout [optional] 
 * @return FTP\Connection|false Returns an FTP\Connection instance on success, or false on failure.
 */
function ftp_ssl_connect (string $hostname, int $port = 21, int $timeout = 90): FTP\Connection|false {}

/**
 * Logs in to an FTP connection
 * @link http://www.php.net/manual/en/function.ftp-login.php
 * @param FTP\Connection $ftp 
 * @param string $username 
 * @param string $password 
 * @return bool Returns true on success or false on failure.
 * If login fails, PHP will also throw a warning.
 */
function ftp_login (FTP\Connection $ftp, string $username, string $password): bool {}

/**
 * Returns the current directory name
 * @link http://www.php.net/manual/en/function.ftp-pwd.php
 * @param FTP\Connection $ftp 
 * @return string|false Returns the current directory name or false on error.
 */
function ftp_pwd (FTP\Connection $ftp): string|false {}

/**
 * Changes to the parent directory
 * @link http://www.php.net/manual/en/function.ftp-cdup.php
 * @param FTP\Connection $ftp 
 * @return bool Returns true on success or false on failure.
 */
function ftp_cdup (FTP\Connection $ftp): bool {}

/**
 * Changes the current directory on a FTP server
 * @link http://www.php.net/manual/en/function.ftp-chdir.php
 * @param FTP\Connection $ftp 
 * @param string $directory 
 * @return bool Returns true on success or false on failure.
 * If changing directory fails, PHP will also throw a warning.
 */
function ftp_chdir (FTP\Connection $ftp, string $directory): bool {}

/**
 * Requests execution of a command on the FTP server
 * @link http://www.php.net/manual/en/function.ftp-exec.php
 * @param FTP\Connection $ftp 
 * @param string $command 
 * @return bool Returns true if the command was successful (server sent response code:
 * 200); otherwise returns false.
 */
function ftp_exec (FTP\Connection $ftp, string $command): bool {}

/**
 * Sends an arbitrary command to an FTP server
 * @link http://www.php.net/manual/en/function.ftp-raw.php
 * @param FTP\Connection $ftp 
 * @param string $command 
 * @return array|null Returns the server's response as an array of strings, or null on failure.
 * No parsing is performed on the response string, nor does
 * ftp_raw determine if the command succeeded.
 */
function ftp_raw (FTP\Connection $ftp, string $command): ?array {}

/**
 * Creates a directory
 * @link http://www.php.net/manual/en/function.ftp-mkdir.php
 * @param FTP\Connection $ftp 
 * @param string $directory 
 * @return string|false Returns the newly created directory name on success or false on error.
 */
function ftp_mkdir (FTP\Connection $ftp, string $directory): string|false {}

/**
 * Removes a directory
 * @link http://www.php.net/manual/en/function.ftp-rmdir.php
 * @param FTP\Connection $ftp 
 * @param string $directory 
 * @return bool Returns true on success or false on failure.
 */
function ftp_rmdir (FTP\Connection $ftp, string $directory): bool {}

/**
 * Set permissions on a file via FTP
 * @link http://www.php.net/manual/en/function.ftp-chmod.php
 * @param FTP\Connection $ftp 
 * @param int $permissions 
 * @param string $filename 
 * @return int|false Returns the new file permissions on success or false on error.
 */
function ftp_chmod (FTP\Connection $ftp, int $permissions, string $filename): int|false {}

/**
 * Allocates space for a file to be uploaded
 * @link http://www.php.net/manual/en/function.ftp-alloc.php
 * @param FTP\Connection $ftp 
 * @param int $size 
 * @param string $response [optional] 
 * @return bool Returns true on success or false on failure.
 */
function ftp_alloc (FTP\Connection $ftp, int $size, string &$response = null): bool {}

/**
 * Returns a list of files in the given directory
 * @link http://www.php.net/manual/en/function.ftp-nlist.php
 * @param FTP\Connection $ftp 
 * @param string $directory 
 * @return array|false Returns an array of filenames from the specified directory on success or
 * false on error.
 */
function ftp_nlist (FTP\Connection $ftp, string $directory): array|false {}

/**
 * Returns a detailed list of files in the given directory
 * @link http://www.php.net/manual/en/function.ftp-rawlist.php
 * @param FTP\Connection $ftp 
 * @param string $directory 
 * @param bool $recursive [optional] 
 * @return array|false Returns an array where each element corresponds to one line of text. Returns
 * false when passed directory is invalid.
 * <p>The output is not parsed in any way. The system type identifier returned by
 * ftp_systype can be used to determine how the results 
 * should be interpreted.</p>
 */
function ftp_rawlist (FTP\Connection $ftp, string $directory, bool $recursive = false): array|false {}

/**
 * Returns a list of files in the given directory
 * @link http://www.php.net/manual/en/function.ftp-mlsd.php
 * @param FTP\Connection $ftp 
 * @param string $directory 
 * @return array|false Returns an array of arrays with file infos from the specified directory on success or
 * false on error.
 */
function ftp_mlsd (FTP\Connection $ftp, string $directory): array|false {}

/**
 * Returns the system type identifier of the remote FTP server
 * @link http://www.php.net/manual/en/function.ftp-systype.php
 * @param FTP\Connection $ftp 
 * @return string|false Returns the remote system type, or false on error.
 */
function ftp_systype (FTP\Connection $ftp): string|false {}

/**
 * Downloads a file from the FTP server and saves to an open file
 * @link http://www.php.net/manual/en/function.ftp-fget.php
 * @param FTP\Connection $ftp 
 * @param resource $stream 
 * @param string $remote_filename 
 * @param int $mode [optional] 
 * @param int $offset [optional] 
 * @return bool Returns true on success or false on failure.
 */
function ftp_fget (FTP\Connection $ftp, $stream, string $remote_filename, int $mode = FTP_BINARY, int $offset = null): bool {}

/**
 * Retrieves a file from the FTP server and writes it to an open file (non-blocking)
 * @link http://www.php.net/manual/en/function.ftp-nb-fget.php
 * @param FTP\Connection $ftp 
 * @param resource $stream 
 * @param string $remote_filename 
 * @param int $mode [optional] 
 * @param int $offset [optional] 
 * @return int Returns FTP_FAILED or FTP_FINISHED
 * or FTP_MOREDATA.
 */
function ftp_nb_fget (FTP\Connection $ftp, $stream, string $remote_filename, int $mode = FTP_BINARY, int $offset = null): int {}

/**
 * Turns passive mode on or off
 * @link http://www.php.net/manual/en/function.ftp-pasv.php
 * @param FTP\Connection $ftp 
 * @param bool $enable 
 * @return bool Returns true on success or false on failure.
 */
function ftp_pasv (FTP\Connection $ftp, bool $enable): bool {}

/**
 * Downloads a file from the FTP server
 * @link http://www.php.net/manual/en/function.ftp-get.php
 * @param FTP\Connection $ftp 
 * @param string $local_filename 
 * @param string $remote_filename 
 * @param int $mode [optional] 
 * @param int $offset [optional] 
 * @return bool Returns true on success or false on failure.
 */
function ftp_get (FTP\Connection $ftp, string $local_filename, string $remote_filename, int $mode = FTP_BINARY, int $offset = null): bool {}

/**
 * Retrieves a file from the FTP server and writes it to a local file (non-blocking)
 * @link http://www.php.net/manual/en/function.ftp-nb-get.php
 * @param FTP\Connection $ftp 
 * @param string $local_filename 
 * @param string $remote_filename 
 * @param int $mode [optional] 
 * @param int $offset [optional] 
 * @return int|false Returns FTP_FAILED or FTP_FINISHED
 * or FTP_MOREDATA, or false on failure to open the local file.
 */
function ftp_nb_get (FTP\Connection $ftp, string $local_filename, string $remote_filename, int $mode = FTP_BINARY, int $offset = null): int|false {}

/**
 * Continues retrieving/sending a file (non-blocking)
 * @link http://www.php.net/manual/en/function.ftp-nb-continue.php
 * @param FTP\Connection $ftp 
 * @return int Returns FTP_FAILED or FTP_FINISHED
 * or FTP_MOREDATA.
 */
function ftp_nb_continue (FTP\Connection $ftp): int {}

/**
 * Uploads from an open file to the FTP server
 * @link http://www.php.net/manual/en/function.ftp-fput.php
 * @param FTP\Connection $ftp 
 * @param string $remote_filename 
 * @param resource $stream 
 * @param int $mode [optional] 
 * @param int $offset [optional] 
 * @return bool Returns true on success or false on failure.
 */
function ftp_fput (FTP\Connection $ftp, string $remote_filename, $stream, int $mode = FTP_BINARY, int $offset = null): bool {}

/**
 * Stores a file from an open file to the FTP server (non-blocking)
 * @link http://www.php.net/manual/en/function.ftp-nb-fput.php
 * @param FTP\Connection $ftp 
 * @param string $remote_filename 
 * @param resource $stream 
 * @param int $mode [optional] 
 * @param int $offset [optional] 
 * @return int Returns FTP_FAILED or FTP_FINISHED
 * or FTP_MOREDATA.
 */
function ftp_nb_fput (FTP\Connection $ftp, string $remote_filename, $stream, int $mode = FTP_BINARY, int $offset = null): int {}

/**
 * Uploads a file to the FTP server
 * @link http://www.php.net/manual/en/function.ftp-put.php
 * @param FTP\Connection $ftp 
 * @param string $remote_filename 
 * @param string $local_filename 
 * @param int $mode [optional] 
 * @param int $offset [optional] 
 * @return bool Returns true on success or false on failure.
 */
function ftp_put (FTP\Connection $ftp, string $remote_filename, string $local_filename, int $mode = FTP_BINARY, int $offset = null): bool {}

/**
 * Append the contents of a file to another file on the FTP server
 * @link http://www.php.net/manual/en/function.ftp-append.php
 * @param FTP\Connection $ftp >An FTP\Connection instance.
 * @param string $remote_filename 
 * @param string $local_filename 
 * @param int $mode [optional] 
 * @return bool Returns true on success or false on failure.
 */
function ftp_append (FTP\Connection $ftp, string $remote_filename, string $local_filename, int $mode = FTP_BINARY): bool {}

/**
 * Stores a file on the FTP server (non-blocking)
 * @link http://www.php.net/manual/en/function.ftp-nb-put.php
 * @param FTP\Connection $ftp 
 * @param string $remote_filename 
 * @param string $local_filename 
 * @param int $mode [optional] 
 * @param int $offset [optional] 
 * @return int|false Returns FTP_FAILED or FTP_FINISHED
 * or FTP_MOREDATA, or false on failure to open the local file.
 */
function ftp_nb_put (FTP\Connection $ftp, string $remote_filename, string $local_filename, int $mode = FTP_BINARY, int $offset = null): int|false {}

/**
 * Returns the size of the given file
 * @link http://www.php.net/manual/en/function.ftp-size.php
 * @param FTP\Connection $ftp 
 * @param string $filename 
 * @return int Returns the file size on success, or -1 on error.
 */
function ftp_size (FTP\Connection $ftp, string $filename): int {}

/**
 * Returns the last modified time of the given file
 * @link http://www.php.net/manual/en/function.ftp-mdtm.php
 * @param FTP\Connection $ftp 
 * @param string $filename 
 * @return int Returns the last modified time as a local Unix timestamp on success, or -1 on 
 * error.
 */
function ftp_mdtm (FTP\Connection $ftp, string $filename): int {}

/**
 * Renames a file or a directory on the FTP server
 * @link http://www.php.net/manual/en/function.ftp-rename.php
 * @param FTP\Connection $ftp 
 * @param string $from 
 * @param string $to 
 * @return bool Returns true on success or false on failure. Upon failure (such as attempting to rename a non-existent
 * file), an E_WARNING error will be emitted.
 */
function ftp_rename (FTP\Connection $ftp, string $from, string $to): bool {}

/**
 * Deletes a file on the FTP server
 * @link http://www.php.net/manual/en/function.ftp-delete.php
 * @param FTP\Connection $ftp 
 * @param string $filename 
 * @return bool Returns true on success or false on failure.
 */
function ftp_delete (FTP\Connection $ftp, string $filename): bool {}

/**
 * Sends a SITE command to the server
 * @link http://www.php.net/manual/en/function.ftp-site.php
 * @param FTP\Connection $ftp 
 * @param string $command 
 * @return bool Returns true on success or false on failure.
 */
function ftp_site (FTP\Connection $ftp, string $command): bool {}

/**
 * Closes an FTP connection
 * @link http://www.php.net/manual/en/function.ftp-close.php
 * @param FTP\Connection $ftp 
 * @return bool Returns true on success or false on failure.
 */
function ftp_close (FTP\Connection $ftp): bool {}

/**
 * Alias of ftp_close
 * @link http://www.php.net/manual/en/function.ftp-quit.php
 * @param FTP\Connection $ftp 
 * @return bool Returns true on success or false on failure.
 */
function ftp_quit (FTP\Connection $ftp): bool {}

/**
 * Set miscellaneous runtime FTP options
 * @link http://www.php.net/manual/en/function.ftp-set-option.php
 * @param FTP\Connection $ftp 
 * @param int $option 
 * @param int|bool $value 
 * @return bool Returns true if the option could be set; false if not. A warning
 * message will be thrown if the option is not
 * supported or the passed value doesn't match the
 * expected value for the given option.
 */
function ftp_set_option (FTP\Connection $ftp, int $option, int|bool $value): bool {}

/**
 * Retrieves various runtime behaviours of the current FTP connection
 * @link http://www.php.net/manual/en/function.ftp-get-option.php
 * @param FTP\Connection $ftp 
 * @param int $option 
 * @return int|bool Returns the value on success or false if the given 
 * option is not supported. In the latter case, a
 * warning message is also thrown.
 */
function ftp_get_option (FTP\Connection $ftp, int $option): int|bool {}


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


}

// End of ftp v.8.2.6
