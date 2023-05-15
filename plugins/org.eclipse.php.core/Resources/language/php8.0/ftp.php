<?php

// Start of ftp v.8.0.28

/**
 * Opens an FTP connection
 * @link http://www.php.net/manual/en/function.ftp-connect.php
 * @param string $hostname The FTP server address. This parameter shouldn't have any trailing 
 * slashes and shouldn't be prefixed with ftp://.
 * @param int $port [optional] This parameter specifies an alternate port to connect to. If it is
 * omitted or set to zero, then the default FTP port, 21, will be used.
 * @param int $timeout [optional] This parameter specifies the timeout in seconds for all subsequent network operations.
 * If omitted, the default value is 90 seconds. The timeout can be changed and
 * queried at any time with ftp_set_option and
 * ftp_get_option.
 * @return mixed an FTP\Connection instance on success, or false on failure.
 */
function ftp_connect (string $hostname, int $port = null, int $timeout = null) {}

/**
 * Opens a Secure SSL-FTP connection
 * @link http://www.php.net/manual/en/function.ftp-ssl-connect.php
 * @param string $hostname The FTP server address. This parameter shouldn't have any trailing 
 * slashes and shouldn't be prefixed with ftp://.
 * @param int $port [optional] This parameter specifies an alternate port to connect to. If it is
 * omitted or set to zero, then the default FTP port, 21, will be used.
 * @param int $timeout [optional] This parameter specifies the timeout for all subsequent network operations.
 * If omitted, the default value is 90 seconds. The timeout can be changed and
 * queried at any time with ftp_set_option and
 * ftp_get_option.
 * @return mixed an FTP\Connection instance on success, or false on failure.
 */
function ftp_ssl_connect (string $hostname, int $port = null, int $timeout = null) {}

/**
 * Logs in to an FTP connection
 * @link http://www.php.net/manual/en/function.ftp-login.php
 * @param FTP\Connection $ftp An FTP\Connection instance.
 * @param string $username The username (USER).
 * @param string $password The password (PASS).
 * @return bool true on success or false on failure
 * If login fails, PHP will also throw a warning.
 */
function ftp_login ($ftp, string $username, string $password): bool {}

/**
 * Returns the current directory name
 * @link http://www.php.net/manual/en/function.ftp-pwd.php
 * @param FTP\Connection $ftp An FTP\Connection instance.
 * @return mixed the current directory name or false on error.
 */
function ftp_pwd ($ftp): string|false {}

/**
 * Changes to the parent directory
 * @link http://www.php.net/manual/en/function.ftp-cdup.php
 * @param FTP\Connection $ftp An FTP\Connection instance.
 * @return bool true on success or false on failure
 */
function ftp_cdup ($ftp): bool {}

/**
 * Changes the current directory on a FTP server
 * @link http://www.php.net/manual/en/function.ftp-chdir.php
 * @param FTP\Connection $ftp An FTP\Connection instance.
 * @param string $directory The target directory.
 * @return bool true on success or false on failure
 * If changing directory fails, PHP will also throw a warning.
 */
function ftp_chdir ($ftp, string $directory): bool {}

/**
 * Requests execution of a command on the FTP server
 * @link http://www.php.net/manual/en/function.ftp-exec.php
 * @param FTP\Connection $ftp An FTP\Connection instance.
 * @param string $command The command to execute.
 * @return bool true if the command was successful (server sent response code:
 * 200); otherwise returns false.
 */
function ftp_exec ($ftp, string $command): bool {}

/**
 * Sends an arbitrary command to an FTP server
 * @link http://www.php.net/manual/en/function.ftp-raw.php
 * @param FTP\Connection $ftp An FTP\Connection instance.
 * @param string $command The command to execute.
 * @return mixed the server's response as an array of strings, or null on failure.
 * No parsing is performed on the response string, nor does
 * ftp_raw determine if the command succeeded.
 */
function ftp_raw ($ftp, string $command): ?array {}

/**
 * Creates a directory
 * @link http://www.php.net/manual/en/function.ftp-mkdir.php
 * @param FTP\Connection $ftp An FTP\Connection instance.
 * @param string $directory The name of the directory that will be created.
 * @return mixed the newly created directory name on success or false on error.
 */
function ftp_mkdir ($ftp, string $directory): string|false {}

/**
 * Removes a directory
 * @link http://www.php.net/manual/en/function.ftp-rmdir.php
 * @param FTP\Connection $ftp An FTP\Connection instance.
 * @param string $directory The directory to delete. This must be either an absolute or relative
 * path to an empty directory.
 * @return bool true on success or false on failure
 */
function ftp_rmdir ($ftp, string $directory): bool {}

/**
 * Set permissions on a file via FTP
 * @link http://www.php.net/manual/en/function.ftp-chmod.php
 * @param FTP\Connection $ftp An FTP\Connection instance.
 * @param int $permissions The new permissions, given as an octal value.
 * @param string $filename The remote file.
 * @return mixed the new file permissions on success or false on error.
 */
function ftp_chmod ($ftp, int $permissions, string $filename): int|false {}

/**
 * Allocates space for a file to be uploaded
 * @link http://www.php.net/manual/en/function.ftp-alloc.php
 * @param FTP\Connection $ftp An FTP\Connection instance.
 * @param int $size The number of bytes to allocate.
 * @param string $response [optional] A textual representation of the servers response will be returned by 
 * reference in response if a variable is provided.
 * @return bool true on success or false on failure
 */
function ftp_alloc ($ftp, int $size, string &$response = null): bool {}

/**
 * Returns a list of files in the given directory
 * @link http://www.php.net/manual/en/function.ftp-nlist.php
 * @param FTP\Connection $ftp An FTP\Connection instance.
 * @param string $directory The directory to be listed. This parameter can also include arguments, eg.
 * ftp_nlist($ftp, "-la /your/dir");.
 * Note that this parameter isn't escaped so there may be some issues with
 * filenames containing spaces and other characters.
 * @return mixed an array of filenames from the specified directory on success or
 * false on error.
 */
function ftp_nlist ($ftp, string $directory): array|false {}

/**
 * Returns a detailed list of files in the given directory
 * @link http://www.php.net/manual/en/function.ftp-rawlist.php
 * @param FTP\Connection $ftp An FTP\Connection instance.
 * @param string $directory The directory path. May include arguments for the LIST
 * command.
 * @param bool $recursive [optional] If set to true, the issued command will be LIST -R.
 * @return mixed an array where each element corresponds to one line of text. Returns
 * false when passed directory is invalid.
 * <p>
 * The output is not parsed in any way. The system type identifier returned by
 * ftp_systype can be used to determine how the results 
 * should be interpreted.
 * </p>
 */
function ftp_rawlist ($ftp, string $directory, bool $recursive = null): array|false {}

/**
 * Returns a list of files in the given directory
 * @link http://www.php.net/manual/en/function.ftp-mlsd.php
 * @param FTP\Connection $ftp An FTP\Connection instance.
 * @param string $directory The directory to be listed.
 * @return mixed an array of arrays with file infos from the specified directory on success or
 * false on error.
 */
function ftp_mlsd ($ftp, string $directory): array|false {}

/**
 * Returns the system type identifier of the remote FTP server
 * @link http://www.php.net/manual/en/function.ftp-systype.php
 * @param FTP\Connection $ftp An FTP\Connection instance.
 * @return mixed the remote system type, or false on error.
 */
function ftp_systype ($ftp): string|false {}

/**
 * Downloads a file from the FTP server and saves to an open file
 * @link http://www.php.net/manual/en/function.ftp-fget.php
 * @param FTP\Connection $ftp An FTP\Connection instance.
 * @param resource $stream An open file pointer in which we store the data.
 * @param string $remote_filename The remote file path.
 * @param int $mode [optional] The transfer mode. Must be either FTP_ASCII or
 * FTP_BINARY.
 * @param int $offset [optional] The position in the remote file to start downloading from.
 * @return bool true on success or false on failure
 */
function ftp_fget ($ftp, $stream, string $remote_filename, int $mode = null, int $offset = null): bool {}

/**
 * Retrieves a file from the FTP server and writes it to an open file (non-blocking)
 * @link http://www.php.net/manual/en/function.ftp-nb-fget.php
 * @param FTP\Connection $ftp An FTP\Connection instance.
 * @param resource $stream An open file pointer in which we store the data.
 * @param string $remote_filename The remote file path.
 * @param int $mode [optional] The transfer mode. Must be either FTP_ASCII or
 * FTP_BINARY.
 * @param int $offset [optional] The position in the remote file to start downloading from.
 * @return int FTP_FAILED or FTP_FINISHED
 * or FTP_MOREDATA.
 */
function ftp_nb_fget ($ftp, $stream, string $remote_filename, int $mode = null, int $offset = null): int {}

/**
 * Turns passive mode on or off
 * @link http://www.php.net/manual/en/function.ftp-pasv.php
 * @param FTP\Connection $ftp An FTP\Connection instance.
 * @param bool $enable If true, the passive mode is turned on, else it's turned off.
 * @return bool true on success or false on failure
 */
function ftp_pasv ($ftp, bool $enable): bool {}

/**
 * Downloads a file from the FTP server
 * @link http://www.php.net/manual/en/function.ftp-get.php
 * @param FTP\Connection $ftp An FTP\Connection instance.
 * @param string $local_filename The local file path (will be overwritten if the file already exists).
 * @param string $remote_filename The remote file path.
 * @param int $mode [optional] The transfer mode. Must be either FTP_ASCII or
 * FTP_BINARY.
 * @param int $offset [optional] The position in the remote file to start downloading from.
 * @return bool true on success or false on failure
 */
function ftp_get ($ftp, string $local_filename, string $remote_filename, int $mode = null, int $offset = null): bool {}

/**
 * Retrieves a file from the FTP server and writes it to a local file (non-blocking)
 * @link http://www.php.net/manual/en/function.ftp-nb-get.php
 * @param FTP\Connection $ftp An FTP\Connection instance.
 * @param string $local_filename The local file path (will be overwritten if the file already exists).
 * @param string $remote_filename The remote file path.
 * @param int $mode [optional] The transfer mode. Must be either FTP_ASCII or
 * FTP_BINARY.
 * @param int $offset [optional] The position in the remote file to start downloading from.
 * @return mixed FTP_FAILED or FTP_FINISHED
 * or FTP_MOREDATA, or false on failure to open the local file.
 */
function ftp_nb_get ($ftp, string $local_filename, string $remote_filename, int $mode = null, int $offset = null): int {}

/**
 * Continues retrieving/sending a file (non-blocking)
 * @link http://www.php.net/manual/en/function.ftp-nb-continue.php
 * @param FTP\Connection $ftp An FTP\Connection instance.
 * @return int FTP_FAILED or FTP_FINISHED
 * or FTP_MOREDATA.
 */
function ftp_nb_continue ($ftp): int {}

/**
 * Uploads from an open file to the FTP server
 * @link http://www.php.net/manual/en/function.ftp-fput.php
 * @param FTP\Connection $ftp An FTP\Connection instance.
 * @param string $remote_filename The remote file path.
 * @param resource $stream An open file pointer on the local file. Reading stops at end of file.
 * @param int $mode [optional] The transfer mode. Must be either FTP_ASCII or
 * FTP_BINARY.
 * @param int $offset [optional] The position in the remote file to start uploading to.
 * @return bool true on success or false on failure
 */
function ftp_fput ($ftp, string $remote_filename, $stream, int $mode = null, int $offset = null): bool {}

/**
 * Stores a file from an open file to the FTP server (non-blocking)
 * @link http://www.php.net/manual/en/function.ftp-nb-fput.php
 * @param FTP\Connection $ftp An FTP\Connection instance.
 * @param string $remote_filename The remote file path.
 * @param resource $stream An open file pointer on the local file. Reading stops at end of file.
 * @param int $mode [optional] The transfer mode. Must be either FTP_ASCII or
 * FTP_BINARY.
 * @param int $offset [optional] The position in the remote file to start uploading to.
 * @return int FTP_FAILED or FTP_FINISHED
 * or FTP_MOREDATA.
 */
function ftp_nb_fput ($ftp, string $remote_filename, $stream, int $mode = null, int $offset = null): int {}

/**
 * Uploads a file to the FTP server
 * @link http://www.php.net/manual/en/function.ftp-put.php
 * @param FTP\Connection $ftp An FTP\Connection instance.
 * @param string $remote_filename The remote file path.
 * @param string $local_filename The local file path.
 * @param int $mode [optional] The transfer mode. Must be either FTP_ASCII or
 * FTP_BINARY.
 * @param int $offset [optional] The position in the remote file to start uploading to.
 * @return bool true on success or false on failure
 */
function ftp_put ($ftp, string $remote_filename, string $local_filename, int $mode = null, int $offset = null): bool {}

/**
 * Append the contents of a file to another file on the FTP server
 * @link http://www.php.net/manual/en/function.ftp-append.php
 * @param FTP\Connection $ftp An FTP\Connection instance.
 * @param string $remote_filename 
 * @param string $local_filename 
 * @param int $mode [optional] 
 * @return bool true on success or false on failure
 */
function ftp_append ($ftp, string $remote_filename, string $local_filename, int $mode = null): bool {}

/**
 * Stores a file on the FTP server (non-blocking)
 * @link http://www.php.net/manual/en/function.ftp-nb-put.php
 * @param FTP\Connection $ftp An FTP\Connection instance.
 * @param string $remote_filename The remote file path.
 * @param string $local_filename The local file path.
 * @param int $mode [optional] The transfer mode. Must be either FTP_ASCII or
 * FTP_BINARY.
 * @param int $offset [optional] The position in the remote file to start uploading to.
 * @return mixed FTP_FAILED or FTP_FINISHED
 * or FTP_MOREDATA, or false on failure to open the local file.
 */
function ftp_nb_put ($ftp, string $remote_filename, string $local_filename, int $mode = null, int $offset = null): int|false {}

/**
 * Returns the size of the given file
 * @link http://www.php.net/manual/en/function.ftp-size.php
 * @param FTP\Connection $ftp An FTP\Connection instance.
 * @param string $filename The remote file.
 * @return int the file size on success, or -1 on error.
 */
function ftp_size ($ftp, string $filename): int {}

/**
 * Returns the last modified time of the given file
 * @link http://www.php.net/manual/en/function.ftp-mdtm.php
 * @param FTP\Connection $ftp An FTP\Connection instance.
 * @param string $filename The file from which to extract the last modification time.
 * @return int the last modified time as a local Unix timestamp on success, or -1 on 
 * error.
 */
function ftp_mdtm ($ftp, string $filename): int {}

/**
 * Renames a file or a directory on the FTP server
 * @link http://www.php.net/manual/en/function.ftp-rename.php
 * @param FTP\Connection $ftp An FTP\Connection instance.
 * @param string $from The old file/directory name.
 * @param string $to The new name.
 * @return bool true on success or false on failure Upon failure (such as attempting to rename a non-existent
 * file), an E_WARNING error will be emitted.
 */
function ftp_rename ($ftp, string $from, string $to): bool {}

/**
 * Deletes a file on the FTP server
 * @link http://www.php.net/manual/en/function.ftp-delete.php
 * @param FTP\Connection $ftp An FTP\Connection instance.
 * @param string $filename The file to delete.
 * @return bool true on success or false on failure
 */
function ftp_delete ($ftp, string $filename): bool {}

/**
 * Sends a SITE command to the server
 * @link http://www.php.net/manual/en/function.ftp-site.php
 * @param FTP\Connection $ftp An FTP\Connection instance.
 * @param string $command The SITE command. Note that this parameter isn't escaped so there may
 * be some issues with filenames containing spaces and other characters.
 * @return bool true on success or false on failure
 */
function ftp_site ($ftp, string $command): bool {}

/**
 * Closes an FTP connection
 * @link http://www.php.net/manual/en/function.ftp-close.php
 * @param FTP\Connection $ftp An FTP\Connection instance.
 * @return bool true on success or false on failure
 */
function ftp_close ($ftp): bool {}

/**
 * Alias: ftp_close
 * @link http://www.php.net/manual/en/function.ftp-quit.php
 * @param mixed $ftp
 */
function ftp_quit ($ftp = null): bool {}

/**
 * Set miscellaneous runtime FTP options
 * @link http://www.php.net/manual/en/function.ftp-set-option.php
 * @param FTP\Connection $ftp An FTP\Connection instance.
 * @param int $option Currently, the following options are supported:
 * <table>
 * Supported runtime FTP options
 * <table>
 * <tr valign="top">
 * <td>FTP_TIMEOUT_SEC</td>
 * <td>
 * Changes the timeout in seconds used for all network related 
 * functions. value must be an integer that
 * is greater than 0. The default timeout is 90 seconds.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>FTP_AUTOSEEK</td>
 * <td>
 * When enabled, GET or PUT requests with a 
 * resumepos or startpos
 * parameter will first seek to the requested position within the file.
 * This is enabled by default.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>FTP_USEPASVADDRESS</td>
 * <td>
 * When disabled, PHP will ignore the IP address returned by the FTP server in response to the PASV command and instead use the IP address that was supplied in the ftp_connect().
 * value must be a boolean.
 * </td>
 * </tr>
 * </table>
 * </table>
 * @param mixed $value This parameter depends on which option is chosen
 * to be altered.
 * @return bool true if the option could be set; false if not. A warning
 * message will be thrown if the option is not
 * supported or the passed value doesn't match the
 * expected value for the given option.
 */
function ftp_set_option ($ftp, int $option, $value): bool {}

/**
 * Retrieves various runtime behaviours of the current FTP connection
 * @link http://www.php.net/manual/en/function.ftp-get-option.php
 * @param FTP\Connection $ftp An FTP\Connection instance.
 * @param int $option Currently, the following options are supported:
 * <table>
 * Supported runtime FTP options
 * <table>
 * <tr valign="top">
 * <td>FTP_TIMEOUT_SEC</td>
 * <td>
 * Returns the current timeout used for network related operations.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>FTP_AUTOSEEK</td>
 * <td>
 * Returns true if this option is on, false otherwise. 
 * </td>
 * </tr>
 * </table>
 * </table>
 * @return mixed the value on success or false if the given 
 * option is not supported. In the latter case, a
 * warning message is also thrown.
 */
function ftp_get_option ($ftp, int $option): int|bool {}


/**
 * 
 * @link http://www.php.net/manual/en/ftp.constants.php
 */
define ('FTP_ASCII', 1);

/**
 * Alias of FTP_ASCII.
 * @link http://www.php.net/manual/en/ftp.constants.php
 */
define ('FTP_TEXT', 1);

/**
 * 
 * @link http://www.php.net/manual/en/ftp.constants.php
 */
define ('FTP_BINARY', 2);

/**
 * Alias of FTP_BINARY.
 * @link http://www.php.net/manual/en/ftp.constants.php
 */
define ('FTP_IMAGE', 2);

/**
 * Automatically determine resume position and start position for GET and PUT requests
 * (only works if FTP_AUTOSEEK is enabled)
 * @link http://www.php.net/manual/en/ftp.constants.php
 */
define ('FTP_AUTORESUME', -1);

/**
 * See ftp_set_option for information.
 * @link http://www.php.net/manual/en/ftp.constants.php
 */
define ('FTP_TIMEOUT_SEC', 0);

/**
 * See ftp_set_option for information.
 * @link http://www.php.net/manual/en/ftp.constants.php
 */
define ('FTP_AUTOSEEK', 1);

/**
 * See ftp_set_option for information.
 * @link http://www.php.net/manual/en/ftp.constants.php
 */
define ('FTP_USEPASVADDRESS', 2);

/**
 * Asynchronous transfer has failed
 * @link http://www.php.net/manual/en/ftp.constants.php
 */
define ('FTP_FAILED', 0);

/**
 * Asynchronous transfer has finished
 * @link http://www.php.net/manual/en/ftp.constants.php
 */
define ('FTP_FINISHED', 1);

/**
 * Asynchronous transfer is still active
 * @link http://www.php.net/manual/en/ftp.constants.php
 */
define ('FTP_MOREDATA', 2);

// End of ftp v.8.0.28
