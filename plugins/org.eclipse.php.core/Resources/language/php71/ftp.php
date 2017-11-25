<?php

// Start of ftp v.7.1.1

/**
 * Opens an FTP connection
 * @link http://www.php.net/manual/en/function.ftp-connect.php
 * @param string $host The FTP server address. This parameter shouldn't have any trailing 
 * slashes and shouldn't be prefixed with ftp://.
 * @param int $port [optional] This parameter specifies an alternate port to connect to. If it is
 * omitted or set to zero, then the default FTP port, 21, will be used.
 * @param int $timeout [optional] This parameter specifies the timeout for all subsequent network operations.
 * If omitted, the default value is 90 seconds. The timeout can be changed and
 * queried at any time with ftp_set_option and
 * ftp_get_option.
 * @return resource a FTP stream on success or false on error.
 */
function ftp_connect (string $host, int $port = null, int $timeout = null) {}

/**
 * Opens a Secure SSL-FTP connection
 * @link http://www.php.net/manual/en/function.ftp-ssl-connect.php
 * @param string $host The FTP server address. This parameter shouldn't have any trailing 
 * slashes and shouldn't be prefixed with ftp://.
 * @param int $port [optional] This parameter specifies an alternate port to connect to. If it is
 * omitted or set to zero, then the default FTP port, 21, will be used.
 * @param int $timeout [optional] This parameter specifies the timeout for all subsequent network operations.
 * If omitted, the default value is 90 seconds. The timeout can be changed and
 * queried at any time with ftp_set_option and
 * ftp_get_option.
 * @return resource a SSL-FTP stream on success or false on error.
 */
function ftp_ssl_connect (string $host, int $port = null, int $timeout = null) {}

/**
 * Logs in to an FTP connection
 * @link http://www.php.net/manual/en/function.ftp-login.php
 * @param resource $ftp_stream The link identifier of the FTP connection.
 * @param string $username The username (USER).
 * @param string $password The password (PASS).
 * @return bool true on success or false on failure
 * If login fails, PHP will also throw a warning.
 */
function ftp_login ($ftp_stream, string $username, string $password) {}

/**
 * Returns the current directory name
 * @link http://www.php.net/manual/en/function.ftp-pwd.php
 * @param resource $ftp_stream The link identifier of the FTP connection.
 * @return string the current directory name or false on error.
 */
function ftp_pwd ($ftp_stream) {}

/**
 * Changes to the parent directory
 * @link http://www.php.net/manual/en/function.ftp-cdup.php
 * @param resource $ftp_stream The link identifier of the FTP connection.
 * @return bool true on success or false on failure
 */
function ftp_cdup ($ftp_stream) {}

/**
 * Changes the current directory on a FTP server
 * @link http://www.php.net/manual/en/function.ftp-chdir.php
 * @param resource $ftp_stream The link identifier of the FTP connection.
 * @param string $directory The target directory.
 * @return bool true on success or false on failure
 * If changing directory fails, PHP will also throw a warning.
 */
function ftp_chdir ($ftp_stream, string $directory) {}

/**
 * Requests execution of a command on the FTP server
 * @link http://www.php.net/manual/en/function.ftp-exec.php
 * @param resource $ftp_stream The link identifier of the FTP connection.
 * @param string $command The command to execute.
 * @return bool true if the command was successful (server sent response code:
 * 200); otherwise returns false.
 */
function ftp_exec ($ftp_stream, string $command) {}

/**
 * Sends an arbitrary command to an FTP server
 * @link http://www.php.net/manual/en/function.ftp-raw.php
 * @param resource $ftp_stream The link identifier of the FTP connection.
 * @param string $command The command to execute.
 * @return array the server's response as an array of strings.
 * No parsing is performed on the response string, nor does 
 * ftp_raw determine if the command succeeded.
 */
function ftp_raw ($ftp_stream, string $command) {}

/**
 * Creates a directory
 * @link http://www.php.net/manual/en/function.ftp-mkdir.php
 * @param resource $ftp_stream The link identifier of the FTP connection.
 * @param string $directory The name of the directory that will be created.
 * @return string the newly created directory name on success or false on error.
 */
function ftp_mkdir ($ftp_stream, string $directory) {}

/**
 * Removes a directory
 * @link http://www.php.net/manual/en/function.ftp-rmdir.php
 * @param resource $ftp_stream The link identifier of the FTP connection.
 * @param string $directory The directory to delete. This must be either an absolute or relative
 * path to an empty directory.
 * @return bool true on success or false on failure
 */
function ftp_rmdir ($ftp_stream, string $directory) {}

/**
 * Set permissions on a file via FTP
 * @link http://www.php.net/manual/en/function.ftp-chmod.php
 * @param resource $ftp_stream The link identifier of the FTP connection.
 * @param int $mode The new permissions, given as an octal value.
 * @param string $filename The remote file.
 * @return int the new file permissions on success or false on error.
 */
function ftp_chmod ($ftp_stream, int $mode, string $filename) {}

/**
 * Allocates space for a file to be uploaded
 * @link http://www.php.net/manual/en/function.ftp-alloc.php
 * @param resource $ftp_stream The link identifier of the FTP connection.
 * @param int $filesize The number of bytes to allocate.
 * @param string $result [optional] A textual representation of the servers response will be returned by 
 * reference in result if a variable is provided.
 * @return bool true on success or false on failure
 */
function ftp_alloc ($ftp_stream, int $filesize, string &$result = null) {}

/**
 * Returns a list of files in the given directory
 * @link http://www.php.net/manual/en/function.ftp-nlist.php
 * @param resource $ftp_stream The link identifier of the FTP connection.
 * @param string $directory The directory to be listed. This parameter can also include arguments, eg.
 * ftp_nlist($conn_id, "-la /your/dir");
 * Note that this parameter isn't escaped so there may be some issues with
 * filenames containing spaces and other characters.
 * @return array an array of filenames from the specified directory on success or
 * false on error.
 */
function ftp_nlist ($ftp_stream, string $directory) {}

/**
 * Returns a detailed list of files in the given directory
 * @link http://www.php.net/manual/en/function.ftp-rawlist.php
 * @param resource $ftp_stream The link identifier of the FTP connection.
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
function ftp_rawlist ($ftp_stream, string $directory, bool $recursive = null) {}

/**
 * Returns the system type identifier of the remote FTP server
 * @link http://www.php.net/manual/en/function.ftp-systype.php
 * @param resource $ftp_stream The link identifier of the FTP connection.
 * @return string the remote system type, or false on error.
 */
function ftp_systype ($ftp_stream) {}

/**
 * Turns passive mode on or off
 * @link http://www.php.net/manual/en/function.ftp-pasv.php
 * @param resource $ftp_stream The link identifier of the FTP connection.
 * @param bool $pasv If true, the passive mode is turned on, else it's turned off.
 * @return bool true on success or false on failure
 */
function ftp_pasv ($ftp_stream, bool $pasv) {}

/**
 * Downloads a file from the FTP server
 * @link http://www.php.net/manual/en/function.ftp-get.php
 * @param resource $ftp_stream The link identifier of the FTP connection.
 * @param string $local_file The local file path (will be overwritten if the file already exists).
 * @param string $remote_file The remote file path.
 * @param int $mode The transfer mode. Must be either FTP_ASCII or
 * FTP_BINARY.
 * @param int $resumepos [optional] The position in the remote file to start downloading from.
 * @return bool true on success or false on failure
 */
function ftp_get ($ftp_stream, string $local_file, string $remote_file, int $mode, int $resumepos = null) {}

/**
 * Downloads a file from the FTP server and saves to an open file
 * @link http://www.php.net/manual/en/function.ftp-fget.php
 * @param resource $ftp_stream The link identifier of the FTP connection.
 * @param resource $handle An open file pointer in which we store the data.
 * @param string $remote_file The remote file path.
 * @param int $mode The transfer mode. Must be either FTP_ASCII or
 * FTP_BINARY.
 * @param int $resumepos [optional] The position in the remote file to start downloading from.
 * @return bool true on success or false on failure
 */
function ftp_fget ($ftp_stream, $handle, string $remote_file, int $mode, int $resumepos = null) {}

/**
 * Uploads a file to the FTP server
 * @link http://www.php.net/manual/en/function.ftp-put.php
 * @param resource $ftp_stream The link identifier of the FTP connection.
 * @param string $remote_file The remote file path.
 * @param string $local_file The local file path.
 * @param int $mode The transfer mode. Must be either FTP_ASCII or
 * FTP_BINARY.
 * @param int $startpos [optional] The position in the remote file to start uploading to.
 * @return bool true on success or false on failure
 */
function ftp_put ($ftp_stream, string $remote_file, string $local_file, int $mode, int $startpos = null) {}

/**
 * Uploads from an open file to the FTP server
 * @link http://www.php.net/manual/en/function.ftp-fput.php
 * @param resource $ftp_stream The link identifier of the FTP connection.
 * @param string $remote_file The remote file path.
 * @param resource $handle An open file pointer on the local file. Reading stops at end of file.
 * @param int $mode The transfer mode. Must be either FTP_ASCII or
 * FTP_BINARY.
 * @param int $startpos [optional] The position in the remote file to start uploading to.
 * @return bool true on success or false on failure
 */
function ftp_fput ($ftp_stream, string $remote_file, $handle, int $mode, int $startpos = null) {}

/**
 * Returns the size of the given file
 * @link http://www.php.net/manual/en/function.ftp-size.php
 * @param resource $ftp_stream The link identifier of the FTP connection.
 * @param string $remote_file The remote file.
 * @return int the file size on success, or -1 on error.
 */
function ftp_size ($ftp_stream, string $remote_file) {}

/**
 * Returns the last modified time of the given file
 * @link http://www.php.net/manual/en/function.ftp-mdtm.php
 * @param resource $ftp_stream The link identifier of the FTP connection.
 * @param string $remote_file The file from which to extract the last modification time.
 * @return int the last modified time as a Unix timestamp on success, or -1 on 
 * error.
 */
function ftp_mdtm ($ftp_stream, string $remote_file) {}

/**
 * Renames a file or a directory on the FTP server
 * @link http://www.php.net/manual/en/function.ftp-rename.php
 * @param resource $ftp_stream The link identifier of the FTP connection.
 * @param string $oldname The old file/directory name.
 * @param string $newname The new name.
 * @return bool true on success or false on failure Upon failure (such as attempting to rename a non-existent
 * file), an E_WARNING error will be emitted.
 */
function ftp_rename ($ftp_stream, string $oldname, string $newname) {}

/**
 * Deletes a file on the FTP server
 * @link http://www.php.net/manual/en/function.ftp-delete.php
 * @param resource $ftp_stream The link identifier of the FTP connection.
 * @param string $path The file to delete.
 * @return bool true on success or false on failure
 */
function ftp_delete ($ftp_stream, string $path) {}

/**
 * Sends a SITE command to the server
 * @link http://www.php.net/manual/en/function.ftp-site.php
 * @param resource $ftp_stream The link identifier of the FTP connection.
 * @param string $command The SITE command. Note that this parameter isn't escaped so there may
 * be some issues with filenames containing spaces and other characters.
 * @return bool true on success or false on failure
 */
function ftp_site ($ftp_stream, string $command) {}

/**
 * Closes an FTP connection
 * @link http://www.php.net/manual/en/function.ftp-close.php
 * @param resource $ftp_stream The link identifier of the FTP connection.
 * @return bool true on success or false on failure
 */
function ftp_close ($ftp_stream) {}

/**
 * Set miscellaneous runtime FTP options
 * @link http://www.php.net/manual/en/function.ftp-set-option.php
 * @param resource $ftp_stream The link identifier of the FTP connection.
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
 * </table>
 * </table>
 * @param mixed $value This parameter depends on which option is chosen
 * to be altered.
 * @return bool true if the option could be set; false if not. A warning
 * message will be thrown if the option is not
 * supported or the passed value doesn't match the
 * expected value for the given option.
 */
function ftp_set_option ($ftp_stream, int $option, $value) {}

/**
 * Retrieves various runtime behaviours of the current FTP stream
 * @link http://www.php.net/manual/en/function.ftp-get-option.php
 * @param resource $ftp_stream The link identifier of the FTP connection.
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
function ftp_get_option ($ftp_stream, int $option) {}

/**
 * Retrieves a file from the FTP server and writes it to an open file (non-blocking)
 * @link http://www.php.net/manual/en/function.ftp-nb-fget.php
 * @param resource $ftp_stream The link identifier of the FTP connection.
 * @param resource $handle An open file pointer in which we store the data.
 * @param string $remote_file The remote file path.
 * @param int $mode The transfer mode. Must be either FTP_ASCII or
 * FTP_BINARY.
 * @param int $resumepos [optional] The position in the remote file to start downloading from.
 * @return int FTP_FAILED or FTP_FINISHED
 * or FTP_MOREDATA.
 */
function ftp_nb_fget ($ftp_stream, $handle, string $remote_file, int $mode, int $resumepos = null) {}

/**
 * Retrieves a file from the FTP server and writes it to a local file (non-blocking)
 * @link http://www.php.net/manual/en/function.ftp-nb-get.php
 * @param resource $ftp_stream The link identifier of the FTP connection.
 * @param string $local_file The local file path (will be overwritten if the file already exists).
 * @param string $remote_file The remote file path.
 * @param int $mode The transfer mode. Must be either FTP_ASCII or
 * FTP_BINARY.
 * @param int $resumepos [optional] The position in the remote file to start downloading from.
 * @return int FTP_FAILED or FTP_FINISHED
 * or FTP_MOREDATA.
 */
function ftp_nb_get ($ftp_stream, string $local_file, string $remote_file, int $mode, int $resumepos = null) {}

/**
 * Continues retrieving/sending a file (non-blocking)
 * @link http://www.php.net/manual/en/function.ftp-nb-continue.php
 * @param resource $ftp_stream The link identifier of the FTP connection.
 * @return int FTP_FAILED or FTP_FINISHED
 * or FTP_MOREDATA.
 */
function ftp_nb_continue ($ftp_stream) {}

/**
 * Stores a file on the FTP server (non-blocking)
 * @link http://www.php.net/manual/en/function.ftp-nb-put.php
 * @param resource $ftp_stream The link identifier of the FTP connection.
 * @param string $remote_file The remote file path.
 * @param string $local_file The local file path.
 * @param int $mode The transfer mode. Must be either FTP_ASCII or
 * FTP_BINARY.
 * @param int $startpos [optional] The position in the remote file to start uploading to.
 * @return int FTP_FAILED or FTP_FINISHED
 * or FTP_MOREDATA.
 */
function ftp_nb_put ($ftp_stream, string $remote_file, string $local_file, int $mode, int $startpos = null) {}

/**
 * Stores a file from an open file to the FTP server (non-blocking)
 * @link http://www.php.net/manual/en/function.ftp-nb-fput.php
 * @param resource $ftp_stream The link identifier of the FTP connection.
 * @param string $remote_file The remote file path.
 * @param resource $handle An open file pointer on the local file. Reading stops at end of file.
 * @param int $mode The transfer mode. Must be either FTP_ASCII or
 * FTP_BINARY.
 * @param int $startpos [optional] The position in the remote file to start uploading to.
 * @return int FTP_FAILED or FTP_FINISHED
 * or FTP_MOREDATA.
 */
function ftp_nb_fput ($ftp_stream, string $remote_file, $handle, int $mode, int $startpos = null) {}

/**
 * Alias: ftp_close
 * @link http://www.php.net/manual/en/function.ftp-quit.php
 * @param $ftp
 */
function ftp_quit ($ftp) {}


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

// End of ftp v.7.1.1
