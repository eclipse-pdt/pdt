<?php

// Start of posix v.

/**
 * Send a signal to a process
 * @link http://php.net/manual/en/function.posix-kill.php
 * @param pid int
 * @param sig int
 * @return bool 
 */
function posix_kill ($pid, $sig) {}

/**
 * Return the current process identifier
 * @link http://php.net/manual/en/function.posix-getpid.php
 * @return int the identifier, as an integer.
 */
function posix_getpid () {}

/**
 * Return the parent process identifier
 * @link http://php.net/manual/en/function.posix-getppid.php
 * @return int the identifier, as an integer.
 */
function posix_getppid () {}

/**
 * Return the real user ID of the current process
 * @link http://php.net/manual/en/function.posix-getuid.php
 * @return int the user id, as an integer
 */
function posix_getuid () {}

/**
 * Set the UID of the current process
 * @link http://php.net/manual/en/function.posix-setuid.php
 * @param uid int
 * @return bool 
 */
function posix_setuid ($uid) {}

/**
 * Return the effective user ID of the current process
 * @link http://php.net/manual/en/function.posix-geteuid.php
 * @return int the user id, as an integer
 */
function posix_geteuid () {}

/**
 * Set the effective UID of the current process
 * @link http://php.net/manual/en/function.posix-seteuid.php
 * @param uid int
 * @return bool 
 */
function posix_seteuid ($uid) {}

/**
 * Return the real group ID of the current process
 * @link http://php.net/manual/en/function.posix-getgid.php
 * @return int the real group id, as an integer.
 */
function posix_getgid () {}

/**
 * Set the GID of the current process
 * @link http://php.net/manual/en/function.posix-setgid.php
 * @param gid int
 * @return bool 
 */
function posix_setgid ($gid) {}

/**
 * Return the effective group ID of the current process
 * @link http://php.net/manual/en/function.posix-getegid.php
 * @return int an integer of the effective group ID.
 */
function posix_getegid () {}

/**
 * Set the effective GID of the current process
 * @link http://php.net/manual/en/function.posix-setegid.php
 * @param gid int
 * @return bool 
 */
function posix_setegid ($gid) {}

/**
 * Return the group set of the current process
 * @link http://php.net/manual/en/function.posix-getgroups.php
 * @return array an array of integers containing the numeric group ids of the group
 */
function posix_getgroups () {}

/**
 * Return login name
 * @link http://php.net/manual/en/function.posix-getlogin.php
 * @return string the login name of the user, as a string.
 */
function posix_getlogin () {}

/**
 * Return the current process group identifier
 * @link http://php.net/manual/en/function.posix-getpgrp.php
 * @return int the identifier, as an integer.
 */
function posix_getpgrp () {}

/**
 * Make the current process a session leader
 * @link http://php.net/manual/en/function.posix-setsid.php
 * @return int the session id, or -1 on errors.
 */
function posix_setsid () {}

/**
 * Set process group id for job control
 * @link http://php.net/manual/en/function.posix-setpgid.php
 * @param pid int
 * @param pgid int
 * @return bool 
 */
function posix_setpgid ($pid, $pgid) {}

/**
 * Get process group id for job control
 * @link http://php.net/manual/en/function.posix-getpgid.php
 * @param pid int
 * @return int the identifier, as an integer.
 */
function posix_getpgid ($pid) {}

/**
 * Get the current sid of the process
 * @link http://php.net/manual/en/function.posix-getsid.php
 * @param pid int
 * @return int the identifier, as an integer.
 */
function posix_getsid ($pid) {}

/**
 * Get system name
 * @link http://php.net/manual/en/function.posix-uname.php
 * @return array a hash of strings with information about the
 */
function posix_uname () {}

/**
 * Get process times
 * @link http://php.net/manual/en/function.posix-times.php
 * @return array a hash of strings with information about the current
 */
function posix_times () {}

/**
 * Get path name of controlling terminal
 * @link http://php.net/manual/en/function.posix-ctermid.php
 * @return string 
 */
function posix_ctermid () {}

/**
 * Determine terminal device name
 * @link http://php.net/manual/en/function.posix-ttyname.php
 * @param fd int
 * @return string 
 */
function posix_ttyname ($fd) {}

/**
 * Determine if a file descriptor is an interactive terminal
 * @link http://php.net/manual/en/function.posix-isatty.php
 * @param fd int
 * @return bool true if fd is an open descriptor connected
 */
function posix_isatty ($fd) {}

/**
 * Pathname of current directory
 * @link http://php.net/manual/en/function.posix-getcwd.php
 * @return string a string of the absolute pathname on success.
 */
function posix_getcwd () {}

/**
 * Create a fifo special file (a named pipe)
 * @link http://php.net/manual/en/function.posix-mkfifo.php
 * @param pathname string
 * @param mode int
 * @return bool 
 */
function posix_mkfifo ($pathname, $mode) {}

/**
 * Create a special or ordinary file (POSIX.1)
 * @link http://php.net/manual/en/function.posix-mknod.php
 * @param pathname string
 * @param mode int
 * @param major int[optional]
 * @param minor int[optional]
 * @return bool 
 */
function posix_mknod ($pathname, $mode, $major = null, $minor = null) {}

/**
 * Determine accessibility of a file
 * @link http://php.net/manual/en/function.posix-access.php
 * @param file string
 * @param mode int[optional]
 * @return bool 
 */
function posix_access ($file, $mode = null) {}

/**
 * Return info about a group by name
 * @link http://php.net/manual/en/function.posix-getgrnam.php
 * @param name string
 * @return array 
 */
function posix_getgrnam ($name) {}

/**
 * Return info about a group by group id
 * @link http://php.net/manual/en/function.posix-getgrgid.php
 * @param gid int
 * @return array 
 */
function posix_getgrgid ($gid) {}

/**
 * Return info about a user by username
 * @link http://php.net/manual/en/function.posix-getpwnam.php
 * @param username string
 * @return array 
 */
function posix_getpwnam ($username) {}

/**
 * Return info about a user by user id
 * @link http://php.net/manual/en/function.posix-getpwuid.php
 * @param uid int
 * @return array an associative array with the following elements:
 */
function posix_getpwuid ($uid) {}

/**
 * Return info about system resource limits
 * @link http://php.net/manual/en/function.posix-getrlimit.php
 * @return array an associative array of elements for each
 */
function posix_getrlimit () {}

/**
 * Retrieve the error number set by the last posix function that failed
 * @link http://php.net/manual/en/function.posix-get-last-error.php
 * @return int the errno (error number) set by the last posix function that
 */
function posix_get_last_error () {}

function posix_errno () {}

/**
 * Retrieve the system error message associated with the given errno
 * @link http://php.net/manual/en/function.posix-strerror.php
 * @param errno int
 * @return string the error message, as a string.
 */
function posix_strerror ($errno) {}

/**
 * Calculate the group access list
 * @link http://php.net/manual/en/function.posix-initgroups.php
 * @param name string
 * @param base_group_id int
 * @return bool 
 */
function posix_initgroups ($name, $base_group_id) {}


/**
 * Check whether the file exists.
 * @link http://php.net/manual/en/posix.constants.php
 */
define ('POSIX_F_OK', 0);

/**
 * Check whether the file exists and has execute permissions.
 * @link http://php.net/manual/en/posix.constants.php
 */
define ('POSIX_X_OK', 1);

/**
 * Check whether the file exists and has write permissions.
 * @link http://php.net/manual/en/posix.constants.php
 */
define ('POSIX_W_OK', 2);

/**
 * Check whether the file exists and has read permissions.
 * @link http://php.net/manual/en/posix.constants.php
 */
define ('POSIX_R_OK', 4);

/**
 * Normal file
 * @link http://php.net/manual/en/posix.constants.php
 */
define ('POSIX_S_IFREG', 32768);

/**
 * Character special file
 * @link http://php.net/manual/en/posix.constants.php
 */
define ('POSIX_S_IFCHR', 8192);

/**
 * Block special file
 * @link http://php.net/manual/en/posix.constants.php
 */
define ('POSIX_S_IFBLK', 24576);

/**
 * FIFO (named pipe) special file
 * @link http://php.net/manual/en/posix.constants.php
 */
define ('POSIX_S_IFIFO', 4096);

/**
 * Socket
 * @link http://php.net/manual/en/posix.constants.php
 */
define ('POSIX_S_IFSOCK', 49152);

// End of posix v.
?>
