<?php

// Start of ftp v.

/**
 * Opens an FTP connection
 * @link http://php.net/manual/en/function.ftp-connect.php
 * @param host string
 * @param port int[optional]
 * @param timeout int[optional]
 * @return resource a FTP stream on success or false on error.
 */
function ftp_connect ($host, $port = null, $timeout = null) {}

/**
 * Opens an Secure SSL-FTP connection
 * @link http://php.net/manual/en/function.ftp-ssl-connect.php
 * @param host string
 * @param port int[optional]
 * @param timeout int[optional]
 * @return resource a SSL-FTP stream on success or false on error.
 */
function ftp_ssl_connect ($host, $port = null, $timeout = null) {}

/**
 * Logs in to an FTP connection
 * @link http://php.net/manual/en/function.ftp-login.php
 * @param ftp_stream resource
 * @param username string
 * @param password string
 * @return bool 
 */
function ftp_login ($ftp_stream, $username, $password) {}

/**
 * Returns the current directory name
 * @link http://php.net/manual/en/function.ftp-pwd.php
 * @param ftp_stream resource
 * @return string the current directory name or false on error.
 */
function ftp_pwd ($ftp_stream) {}

/**
 * Changes to the parent directory
 * @link http://php.net/manual/en/function.ftp-cdup.php
 * @param ftp_stream resource
 * @return bool 
 */
function ftp_cdup ($ftp_stream) {}

/**
 * Changes the current directory on a FTP server
 * @link http://php.net/manual/en/function.ftp-chdir.php
 * @param ftp_stream resource
 * @param directory string
 * @return bool 
 */
function ftp_chdir ($ftp_stream, $directory) {}

/**
 * Requests execution of a command on the FTP server
 * @link http://php.net/manual/en/function.ftp-exec.php
 * @param ftp_stream resource
 * @param command string
 * @return bool true if the command was successful (server sent response code:
 */
function ftp_exec ($ftp_stream, $command) {}

/**
 * Sends an arbitrary command to an FTP server
 * @link http://php.net/manual/en/function.ftp-raw.php
 * @param ftp_stream resource
 * @param command string
 * @return array the server's response as an array of strings.
 */
function ftp_raw ($ftp_stream, $command) {}

/**
 * Creates a directory
 * @link http://php.net/manual/en/function.ftp-mkdir.php
 * @param ftp_stream resource
 * @param directory string
 * @return string the newly created directory name on success or false on error.
 */
function ftp_mkdir ($ftp_stream, $directory) {}

/**
 * Removes a directory
 * @link http://php.net/manual/en/function.ftp-rmdir.php
 * @param ftp_stream resource
 * @param directory string
 * @return bool 
 */
function ftp_rmdir ($ftp_stream, $directory) {}

/**
 * Set permissions on a file via FTP
 * @link http://php.net/manual/en/function.ftp-chmod.php
 * @param ftp_stream resource
 * @param mode int
 * @param filename string
 * @return int the new file permissions on success or false on error.
 */
function ftp_chmod ($ftp_stream, $mode, $filename) {}

/**
 * Allocates space for a file to be uploaded
 * @link http://php.net/manual/en/function.ftp-alloc.php
 * @param ftp_stream resource
 * @param filesize int
 * @param result string[optional]
 * @return bool 
 */
function ftp_alloc ($ftp_stream, $filesize, &$result = null) {}

/**
 * Returns a list of files in the given directory
 * @link http://php.net/manual/en/function.ftp-nlist.php
 * @param ftp_stream resource
 * @param directory string
 * @return array an array of filenames from the specified directory on success or
 */
function ftp_nlist ($ftp_stream, $directory) {}

/**
 * Returns a detailed list of files in the given directory
 * @link http://php.net/manual/en/function.ftp-rawlist.php
 * @param ftp_stream resource
 * @param directory string
 * @param recursive bool[optional]
 * @return array an array where each element corresponds to one line of text.
 */
function ftp_rawlist ($ftp_stream, $directory, $recursive = null) {}

/**
 * Returns the system type identifier of the remote FTP server
 * @link http://php.net/manual/en/function.ftp-systype.php
 * @param ftp_stream resource
 * @return string the remote system type, or false on error.
 */
function ftp_systype ($ftp_stream) {}

/**
 * Turns passive mode on or off
 * @link http://php.net/manual/en/function.ftp-pasv.php
 * @param ftp_stream resource
 * @param pasv bool
 * @return bool 
 */
function ftp_pasv ($ftp_stream, $pasv) {}

/**
 * Downloads a file from the FTP server
 * @link http://php.net/manual/en/function.ftp-get.php
 * @param ftp_stream resource
 * @param local_file string
 * @param remote_file string
 * @param mode int
 * @param resumepos int[optional]
 * @return bool 
 */
function ftp_get ($ftp_stream, $local_file, $remote_file, $mode, $resumepos = null) {}

/**
 * Downloads a file from the FTP server and saves to an open file
 * @link http://php.net/manual/en/function.ftp-fget.php
 * @param ftp_stream resource
 * @param handle resource
 * @param remote_file string
 * @param mode int
 * @param resumepos int[optional]
 * @return bool 
 */
function ftp_fget ($ftp_stream, $handle, $remote_file, $mode, $resumepos = null) {}

/**
 * Uploads a file to the FTP server
 * @link http://php.net/manual/en/function.ftp-put.php
 * @param ftp_stream resource
 * @param remote_file string
 * @param local_file string
 * @param mode int
 * @param startpos int[optional]
 * @return bool 
 */
function ftp_put ($ftp_stream, $remote_file, $local_file, $mode, $startpos = null) {}

/**
 * Uploads from an open file to the FTP server
 * @link http://php.net/manual/en/function.ftp-fput.php
 * @param ftp_stream resource
 * @param remote_file string
 * @param handle resource
 * @param mode int
 * @param startpos int[optional]
 * @return bool 
 */
function ftp_fput ($ftp_stream, $remote_file, $handle, $mode, $startpos = null) {}

/**
 * Returns the size of the given file
 * @link http://php.net/manual/en/function.ftp-size.php
 * @param ftp_stream resource
 * @param remote_file string
 * @return int the file size on success, or -1 on error.
 */
function ftp_size ($ftp_stream, $remote_file) {}

/**
 * Returns the last modified time of the given file
 * @link http://php.net/manual/en/function.ftp-mdtm.php
 * @param ftp_stream resource
 * @param remote_file string
 * @return int the last modified time as a Unix timestamp on success, or -1 on
 */
function ftp_mdtm ($ftp_stream, $remote_file) {}

/**
 * Renames a file or a directory on the FTP server
 * @link http://php.net/manual/en/function.ftp-rename.php
 * @param ftp_stream resource
 * @param oldname string
 * @param newname string
 * @return bool 
 */
function ftp_rename ($ftp_stream, $oldname, $newname) {}

/**
 * Deletes a file on the FTP server
 * @link http://php.net/manual/en/function.ftp-delete.php
 * @param ftp_stream resource
 * @param path string
 * @return bool 
 */
function ftp_delete ($ftp_stream, $path) {}

/**
 * Sends a SITE command to the server
 * @link http://php.net/manual/en/function.ftp-site.php
 * @param ftp_stream resource
 * @param command string
 * @return bool 
 */
function ftp_site ($ftp_stream, $command) {}

/**
 * Closes an FTP connection
 * @link http://php.net/manual/en/function.ftp-close.php
 * @param ftp_stream resource
 * @return bool 
 */
function ftp_close ($ftp_stream) {}

/**
 * Set miscellaneous runtime FTP options
 * @link http://php.net/manual/en/function.ftp-set-option.php
 * @param ftp_stream resource
 * @param option int
 * @param value mixed
 * @return bool true if the option could be set; false if not. A warning
 */
function ftp_set_option ($ftp_stream, $option, $value) {}

/**
 * Retrieves various runtime behaviours of the current FTP stream
 * @link http://php.net/manual/en/function.ftp-get-option.php
 * @param ftp_stream resource
 * @param option int
 * @return mixed the value on success or false if the given
 */
function ftp_get_option ($ftp_stream, $option) {}

/**
 * Retrieves a file from the FTP server and writes it to an open file (non-blocking)
 * @link http://php.net/manual/en/function.ftp-nb-fget.php
 * @param ftp_stream resource
 * @param handle resource
 * @param remote_file string
 * @param mode int
 * @param resumepos int[optional]
 * @return int FTP_FAILED or FTP_FINISHED
 */
function ftp_nb_fget ($ftp_stream, $handle, $remote_file, $mode, $resumepos = null) {}

/**
 * Retrieves a file from the FTP server and writes it to a local file (non-blocking)
 * @link http://php.net/manual/en/function.ftp-nb-get.php
 * @param ftp_stream resource
 * @param local_file string
 * @param remote_file string
 * @param mode int
 * @param resumepos int[optional]
 * @return int FTP_FAILED or FTP_FINISHED
 */
function ftp_nb_get ($ftp_stream, $local_file, $remote_file, $mode, $resumepos = null) {}

/**
 * Continues retrieving/sending a file (non-blocking)
 * @link http://php.net/manual/en/function.ftp-nb-continue.php
 * @param ftp_stream resource
 * @return int FTP_FAILED or FTP_FINISHED
 */
function ftp_nb_continue ($ftp_stream) {}

/**
 * Stores a file on the FTP server (non-blocking)
 * @link http://php.net/manual/en/function.ftp-nb-put.php
 * @param ftp_stream resource
 * @param remote_file string
 * @param local_file string
 * @param mode int
 * @param startpos int[optional]
 * @return int FTP_FAILED or FTP_FINISHED
 */
function ftp_nb_put ($ftp_stream, $remote_file, $local_file, $mode, $startpos = null) {}

/**
 * Stores a file from an open file to the FTP server (non-blocking)
 * @link http://php.net/manual/en/function.ftp-nb-fput.php
 * @param ftp_stream resource
 * @param remote_file string
 * @param handle resource
 * @param mode int
 * @param startpos int[optional]
 * @return int FTP_FAILED or FTP_FINISHED
 */
function ftp_nb_fput ($ftp_stream, $remote_file, $handle, $mode, $startpos = null) {}

/**
 * &Alias; <function>ftp_close</function>
 * @link http://php.net/manual/en/function.ftp-quit.php
 * @param ftp
 */
function ftp_quit ($ftp) {}

define ('FTP_ASCII', 1);
define ('FTP_TEXT', 1);
define ('FTP_BINARY', 2);
define ('FTP_IMAGE', 2);

/**
 * Automatically determine resume position and start position for GET and PUT requests
 * (only works if FTP_AUTOSEEK is enabled)
 * @link http://php.net/manual/en/ftp.constants.php
 */
define ('FTP_AUTORESUME', -1);

/**
 * See ftp_set_option for information.
 * @link http://php.net/manual/en/ftp.constants.php
 */
define ('FTP_TIMEOUT_SEC', 0);

/**
 * See ftp_set_option for information.
 * @link http://php.net/manual/en/ftp.constants.php
 */
define ('FTP_AUTOSEEK', 1);

/**
 * Asynchronous transfer has failed
 * @link http://php.net/manual/en/ftp.constants.php
 */
define ('FTP_FAILED', 0);

/**
 * Asynchronous transfer has finished
 * @link http://php.net/manual/en/ftp.constants.php
 */
define ('FTP_FINISHED', 1);

/**
 * Asynchronous transfer is still active
 * @link http://php.net/manual/en/ftp.constants.php
 */
define ('FTP_MOREDATA', 2);

// End of ftp v.
?>
