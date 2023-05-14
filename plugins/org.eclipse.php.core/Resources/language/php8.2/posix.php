<?php

// Start of posix v.8.2.6

/**
 * Send a signal to a process
 * @link http://www.php.net/manual/en/function.posix-kill.php
 * @param int $process_id The process identifier.
 * @param int $signal One of the PCNTL signals constants.
 * @return bool true on success or false on failure
 */
function posix_kill (int $process_id, int $signal): bool {}

/**
 * Return the current process identifier
 * @link http://www.php.net/manual/en/function.posix-getpid.php
 * @return int the identifier, as an int.
 */
function posix_getpid (): int {}

/**
 * Return the parent process identifier
 * @link http://www.php.net/manual/en/function.posix-getppid.php
 * @return int the identifier, as an int.
 */
function posix_getppid (): int {}

/**
 * Return the real user ID of the current process
 * @link http://www.php.net/manual/en/function.posix-getuid.php
 * @return int the user id, as an int
 */
function posix_getuid (): int {}

/**
 * Set the UID of the current process
 * @link http://www.php.net/manual/en/function.posix-setuid.php
 * @param int $user_id The user id.
 * @return bool true on success or false on failure
 */
function posix_setuid (int $user_id): bool {}

/**
 * Return the effective user ID of the current process
 * @link http://www.php.net/manual/en/function.posix-geteuid.php
 * @return int the user id, as an int
 */
function posix_geteuid (): int {}

/**
 * Set the effective UID of the current process
 * @link http://www.php.net/manual/en/function.posix-seteuid.php
 * @param int $user_id The user id.
 * @return bool true on success or false on failure
 */
function posix_seteuid (int $user_id): bool {}

/**
 * Return the real group ID of the current process
 * @link http://www.php.net/manual/en/function.posix-getgid.php
 * @return int the real group id, as an int.
 */
function posix_getgid (): int {}

/**
 * Set the GID of the current process
 * @link http://www.php.net/manual/en/function.posix-setgid.php
 * @param int $group_id The group id.
 * @return bool true on success or false on failure
 */
function posix_setgid (int $group_id): bool {}

/**
 * Return the effective group ID of the current process
 * @link http://www.php.net/manual/en/function.posix-getegid.php
 * @return int an int of the effective group ID.
 */
function posix_getegid (): int {}

/**
 * Set the effective GID of the current process
 * @link http://www.php.net/manual/en/function.posix-setegid.php
 * @param int $group_id The group id.
 * @return bool true on success or false on failure
 */
function posix_setegid (int $group_id): bool {}

/**
 * Return the group set of the current process
 * @link http://www.php.net/manual/en/function.posix-getgroups.php
 * @return mixed an array of integers containing the numeric group ids of the group
 * set of the current process, or false on failure.
 */
function posix_getgroups (): array|false {}

/**
 * Return login name
 * @link http://www.php.net/manual/en/function.posix-getlogin.php
 * @return mixed the login name of the user, as a string, or false on failure.
 */
function posix_getlogin (): string|false {}

/**
 * Return the current process group identifier
 * @link http://www.php.net/manual/en/function.posix-getpgrp.php
 * @return int the identifier, as an int.
 */
function posix_getpgrp (): int {}

/**
 * Make the current process a session leader
 * @link http://www.php.net/manual/en/function.posix-setsid.php
 * @return int the session id, or -1 on errors.
 */
function posix_setsid (): int {}

/**
 * Set process group id for job control
 * @link http://www.php.net/manual/en/function.posix-setpgid.php
 * @param int $process_id The process id.
 * @param int $process_group_id The process group id.
 * @return bool true on success or false on failure
 */
function posix_setpgid (int $process_id, int $process_group_id): bool {}

/**
 * Get process group id for job control
 * @link http://www.php.net/manual/en/function.posix-getpgid.php
 * @param int $process_id The process id.
 * @return mixed the identifier, as an int.
 */
function posix_getpgid (int $process_id): int|false {}

/**
 * Get the current sid of the process
 * @link http://www.php.net/manual/en/function.posix-getsid.php
 * @param int $process_id The process identifier. If set to 0, the current process is
 * assumed. If an invalid process_id is
 * specified, then false is returned and an error is set which
 * can be checked with posix_get_last_error.
 * @return mixed the identifier, as an int, or false on failure.
 */
function posix_getsid (int $process_id): int|false {}

/**
 * Get system name
 * @link http://www.php.net/manual/en/function.posix-uname.php
 * @return mixed a hash of strings with information about the
 * system. The indices of the hash are
 * <p>
 * <br>
 * sysname - operating system name (e.g. Linux)
 * <br>
 * nodename - system name (e.g. valiant)
 * <br>
 * release - operating system release (e.g. 2.2.10)
 * <br>
 * version - operating system version (e.g. #4 Tue Jul 20
 * 17:01:36 MEST 1999)
 * <br>
 * machine - system architecture (e.g. i586)
 * <br>
 * domainname - DNS domainname (e.g. example.com)
 * </p>
 * <p>
 * domainname is a GNU extension and not part of POSIX.1, so this
 * field is only available on GNU systems or when using the GNU
 * libc.
 * </p>
 * <p>
 * The function returns false on failure.
 * </p>
 */
function posix_uname (): array|false {}

/**
 * Get process times
 * @link http://www.php.net/manual/en/function.posix-times.php
 * @return mixed a hash of strings with information about the current
 * process CPU usage. The indices of the hash are:
 * <p>
 * <br>
 * ticks - the number of clock ticks that have elapsed since
 * reboot.
 * <br>
 * utime - user time used by the current process.
 * <br>
 * stime - system time used by the current process.
 * <br>
 * cutime - user time used by current process and children.
 * <br>
 * cstime - system time used by current process and children.
 * </p>
 * The function returns false on failure.
 */
function posix_times (): array|false {}

/**
 * Get path name of controlling terminal
 * @link http://www.php.net/manual/en/function.posix-ctermid.php
 * @return mixed Upon successful completion, returns string of the pathname to 
 * the current controlling terminal. Otherwise false is returned and errno
 * is set, which can be checked with posix_get_last_error.
 */
function posix_ctermid (): string|false {}

/**
 * Determine terminal device name
 * @link http://www.php.net/manual/en/function.posix-ttyname.php
 * @param mixed $file_descriptor 
 * @return mixed On success, returns a string of the absolute path of the
 * file_descriptor. On failure, returns false
 */
function posix_ttyname ($file_descriptor): string|false {}

/**
 * Determine if a file descriptor is an interactive terminal
 * @link http://www.php.net/manual/en/function.posix-isatty.php
 * @param mixed $file_descriptor 
 * @return bool true if file_descriptor is an open descriptor connected
 * to a terminal and false otherwise.
 */
function posix_isatty ($file_descriptor): bool {}

/**
 * Pathname of current directory
 * @link http://www.php.net/manual/en/function.posix-getcwd.php
 * @return mixed a string of the absolute pathname on success. 
 * On error, returns false and sets errno which can be checked with
 * posix_get_last_error.
 */
function posix_getcwd (): string|false {}

/**
 * Create a fifo special file (a named pipe)
 * @link http://www.php.net/manual/en/function.posix-mkfifo.php
 * @param string $filename Path to the FIFO file.
 * @param int $permissions The second parameter permissions has to be given in
 * octal notation (e.g. 0644). The permission of the newly created
 * FIFO also depends on the setting of the current
 * umask. The permissions of the created file are
 * (mode &amp; ~umask).
 * @return bool true on success or false on failure
 */
function posix_mkfifo (string $filename, int $permissions): bool {}

/**
 * Create a special or ordinary file (POSIX.1)
 * @link http://www.php.net/manual/en/function.posix-mknod.php
 * @param string $filename The file to create
 * @param int $flags This parameter is constructed by a bitwise OR between file type (one of
 * the following constants: POSIX_S_IFREG,
 * POSIX_S_IFCHR, POSIX_S_IFBLK,
 * POSIX_S_IFIFO or
 * POSIX_S_IFSOCK) and permissions.
 * @param int $major [optional] The major device kernel identifier (required to pass when using
 * S_IFCHR or S_IFBLK).
 * @param int $minor [optional] The minor device kernel identifier.
 * @return bool true on success or false on failure
 */
function posix_mknod (string $filename, int $flags, int $major = null, int $minor = null): bool {}

/**
 * Determine accessibility of a file
 * @link http://www.php.net/manual/en/function.posix-access.php
 * @param string $filename The name of the file to be tested.
 * @param int $flags [optional] <p>
 * A mask consisting of one or more of POSIX_F_OK,
 * POSIX_R_OK, POSIX_W_OK and
 * POSIX_X_OK.
 * </p>
 * <p>
 * POSIX_R_OK, POSIX_W_OK and
 * POSIX_X_OK request checking whether the file
 * exists and has read, write and execute permissions, respectively.
 * POSIX_F_OK just requests checking for the
 * existence of the file.
 * </p>
 * @return bool true on success or false on failure
 */
function posix_access (string $filename, int $flags = null): bool {}

/**
 * Return info about a group by name
 * @link http://www.php.net/manual/en/function.posix-getgrnam.php
 * @param string $name The name of the group
 * @return mixed an array on success, or false on failure.
 * The array elements returned are:
 * <table>
 * The group information array
 * <table>
 * <tr valign="top">
 * <td>Element</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>name</td>
 * <td>
 * The name element contains the name of the group. This is
 * a short, usually less than 16 character "handle" of the
 * group, not the real, full name. This should be the same as
 * the name parameter used when
 * calling the function, and hence redundant.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>passwd</td>
 * <td>
 * The passwd element contains the group's password in an
 * encrypted format. Often, for example on a system employing
 * "shadow" passwords, an asterisk is returned instead.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>gid</td>
 * <td>
 * Group ID of the group in numeric form.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>members</td>
 * <td>
 * This consists of an array of
 * string's for all the members in the group.
 * </td>
 * </tr>
 * </table>
 * </table>
 */
function posix_getgrnam (string $name): array|false {}

/**
 * Return info about a group by group id
 * @link http://www.php.net/manual/en/function.posix-getgrgid.php
 * @param int $group_id The group id.
 * @return mixed The array elements returned are:
 * <table>
 * The group information array
 * <table>
 * <tr valign="top">
 * <td>Element</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>name</td>
 * <td>
 * The name element contains the name of the group. This is
 * a short, usually less than 16 character "handle" of the
 * group, not the real, full name.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>passwd</td>
 * <td>
 * The passwd element contains the group's password in an
 * encrypted format. Often, for example on a system employing
 * "shadow" passwords, an asterisk is returned instead.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>gid</td>
 * <td>
 * Group ID, should be the same as the
 * group_id parameter used when calling the
 * function, and hence redundant.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>members</td>
 * <td>
 * This consists of an array of
 * string's for all the members in the group.
 * </td>
 * </tr>
 * </table>
 * </table>
 * The function returns false on failure.
 */
function posix_getgrgid (int $group_id): array|false {}

/**
 * Return info about a user by username
 * @link http://www.php.net/manual/en/function.posix-getpwnam.php
 * @param string $username An alphanumeric username.
 * @return mixed On success an array with the following elements is returned, else 
 * false is returned:
 * <table>
 * The user information array
 * <table>
 * <tr valign="top">
 * <td>Element</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>name</td>
 * <td>
 * The name element contains the username of the user. This is
 * a short, usually less than 16 character "handle" of the
 * user, not the real, full name. This should be the same as
 * the username parameter used when
 * calling the function, and hence redundant.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>passwd</td>
 * <td>
 * The passwd element contains the user's password in an
 * encrypted format. Often, for example on a system employing
 * "shadow" passwords, an asterisk is returned instead.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>uid</td>
 * <td>
 * User ID of the user in numeric form.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>gid</td>
 * <td>
 * The group ID of the user. Use the function
 * posix_getgrgid to resolve the group
 * name and a list of its members.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>gecos</td>
 * <td>
 * GECOS is an obsolete term that refers to the finger
 * information field on a Honeywell batch processing system.
 * The field, however, lives on, and its contents have been
 * formalized by POSIX. The field contains a comma separated
 * list containing the user's full name, office phone, office
 * number, and home phone number. On most systems, only the
 * user's full name is available.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>dir</td>
 * <td>
 * This element contains the absolute path to the home
 * directory of the user.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>shell</td>
 * <td>
 * The shell element contains the absolute path to the
 * executable of the user's default shell.
 * </td>
 * </tr>
 * </table>
 * </table>
 */
function posix_getpwnam (string $username): array|false {}

/**
 * Return info about a user by user id
 * @link http://www.php.net/manual/en/function.posix-getpwuid.php
 * @param int $user_id The user identifier.
 * @return mixed an associative array with the following elements:
 * <table>
 * The user information array
 * <table>
 * <tr valign="top">
 * <td>Element</td>
 * <td>Description</td>
 * </tr>
 * <tr valign="top">
 * <td>name</td>
 * <td>
 * The name element contains the username of the user. This is
 * a short, usually less than 16 character "handle" of the
 * user, not the real, full name.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>passwd</td>
 * <td>
 * The passwd element contains the user's password in an
 * encrypted format. Often, for example on a system employing
 * "shadow" passwords, an asterisk is returned instead.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>uid</td>
 * <td>
 * User ID, should be the same as the
 * user_id parameter used when calling the
 * function, and hence redundant.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>gid</td>
 * <td>
 * The group ID of the user. Use the function
 * posix_getgrgid to resolve the group
 * name and a list of its members.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>gecos</td>
 * <td>
 * GECOS is an obsolete term that refers to the finger
 * information field on a Honeywell batch processing system.
 * The field, however, lives on, and its contents have been
 * formalized by POSIX. The field contains a comma separated
 * list containing the user's full name, office phone, office
 * number, and home phone number. On most systems, only the
 * user's full name is available.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>dir</td>
 * <td>
 * This element contains the absolute path to the
 * home directory of the user.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>shell</td>
 * <td>
 * The shell element contains the absolute path to the
 * executable of the user's default shell.
 * </td>
 * </tr>
 * </table>
 * </table>
 * The function returns false on failure.
 */
function posix_getpwuid (int $user_id): array|false {}

/**
 * Return info about system resource limits
 * @link http://www.php.net/manual/en/function.posix-getrlimit.php
 * @return mixed an associative array of elements for each
 * limit that is defined. Each limit has a soft and a hard limit.
 * <table>
 * List of possible limits returned
 * <table>
 * <tr valign="top">
 * <td>Limit name</td>
 * <td>Limit description</td>
 * </tr>
 * <tr valign="top">
 * <td>core</td>
 * <td>
 * The maximum size of the core file. When 0, not core files are
 * created. When core files are larger than this size, they will
 * be truncated at this size.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>totalmem</td>
 * <td>
 * The maximum size of the memory of the process, in bytes.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>virtualmem</td>
 * <td>
 * The maximum size of the virtual memory for the process, in bytes.
 * </td>
 * </tr> 
 * <tr valign="top">
 * <td>data</td>
 * <td>
 * The maximum size of the data segment for the process, in bytes.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>stack</td>
 * <td>
 * The maximum size of the process stack, in bytes.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>rss</td>
 * <td>
 * The maximum number of virtual pages resident in RAM
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>maxproc</td>
 * <td>
 * The maximum number of processes that can be created for the
 * real user ID of the calling process.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>memlock</td>
 * <td>
 * The maximum number of bytes of memory that may be locked into RAM.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>cpu</td>
 * <td>
 * The amount of time the process is allowed to use the CPU.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>filesize</td>
 * <td>
 * The maximum size of the data segment for the process, in bytes.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>openfiles</td>
 * <td>
 * One more than the maximum number of open file descriptors.
 * </td>
 * </tr>
 * </table>
 * </table>
 * The function returns false on failure.
 */
function posix_getrlimit (): array|false {}

/**
 * Set system resource limits
 * @link http://www.php.net/manual/en/function.posix-setrlimit.php
 * @param int $resource The
 * resource limit constant
 * corresponding to the limit that is being set.
 * @param int $soft_limit The soft limit, in whatever unit the resource limit requires, or
 * POSIX_RLIMIT_INFINITY.
 * @param int $hard_limit The hard limit, in whatever unit the resource limit requires, or
 * POSIX_RLIMIT_INFINITY.
 * @return bool true on success or false on failure
 */
function posix_setrlimit (int $resource, int $soft_limit, int $hard_limit): bool {}

/**
 * Retrieve the error number set by the last posix function that failed
 * @link http://www.php.net/manual/en/function.posix-get-last-error.php
 * @return int the errno (error number) set by the last posix function that
 * failed. If no errors exist, 0 is returned.
 */
function posix_get_last_error (): int {}

/**
 * Alias: posix_get_last_error
 * @link http://www.php.net/manual/en/function.posix-errno.php
 */
function posix_errno (): int {}

/**
 * Retrieve the system error message associated with the given errno
 * @link http://www.php.net/manual/en/function.posix-strerror.php
 * @param int $error_code A POSIX error number, returned by 
 * posix_get_last_error. If set to 0, then the
 * string "Success" is returned.
 * @return string the error message, as a string.
 */
function posix_strerror (int $error_code): string {}

/**
 * Calculate the group access list
 * @link http://www.php.net/manual/en/function.posix-initgroups.php
 * @param string $username The user to calculate the list for.
 * @param int $group_id Typically the group number from the password file.
 * @return bool true on success or false on failure
 */
function posix_initgroups (string $username, int $group_id): bool {}


/**
 * Check whether the file exists.
 * @link http://www.php.net/manual/en/posix.constants.php
 */
define ('POSIX_F_OK', 0);

/**
 * Check whether the file exists and has execute permissions.
 * @link http://www.php.net/manual/en/posix.constants.php
 */
define ('POSIX_X_OK', 1);

/**
 * Check whether the file exists and has write permissions.
 * @link http://www.php.net/manual/en/posix.constants.php
 */
define ('POSIX_W_OK', 2);

/**
 * Check whether the file exists and has read permissions.
 * @link http://www.php.net/manual/en/posix.constants.php
 */
define ('POSIX_R_OK', 4);

/**
 * Normal file
 * @link http://www.php.net/manual/en/posix.constants.php
 */
define ('POSIX_S_IFREG', 32768);

/**
 * Character special file
 * @link http://www.php.net/manual/en/posix.constants.php
 */
define ('POSIX_S_IFCHR', 8192);

/**
 * Block special file
 * @link http://www.php.net/manual/en/posix.constants.php
 */
define ('POSIX_S_IFBLK', 24576);

/**
 * FIFO (named pipe) special file
 * @link http://www.php.net/manual/en/posix.constants.php
 */
define ('POSIX_S_IFIFO', 4096);

/**
 * Socket
 * @link http://www.php.net/manual/en/posix.constants.php
 */
define ('POSIX_S_IFSOCK', 49152);

/**
 * The maximum size of the process's address space in bytes. See also PHP's
 * memory_limit configuration
 * directive.
 * @link http://www.php.net/manual/en/posix.constants.php
 */
define ('POSIX_RLIMIT_AS', 5);

/**
 * The maximum size of a core file. If the limit is set to 0, no core file
 * will be generated.
 * @link http://www.php.net/manual/en/posix.constants.php
 */
define ('POSIX_RLIMIT_CORE', 4);

/**
 * The maximum amount of CPU time that the process can use, in seconds.
 * When the soft limit is hit, a SIGXCPU signal will be
 * sent, which can be caught with pcntl_signal.
 * Depending on the operating system, additional SIGXCPU
 * signals may be sent each second until the hard limit is hit, at which
 * point an uncatchable SIGKILL signal is sent.
 * See also set_time_limit.
 * @link http://www.php.net/manual/en/posix.constants.php
 */
define ('POSIX_RLIMIT_CPU', 0);

/**
 * The maximum size of the process's data segment, in bytes. It is
 * extremely unlikely that this will have any effect on the execution of
 * PHP unless an extension is in use that calls brk or
 * sbrk.
 * @link http://www.php.net/manual/en/posix.constants.php
 */
define ('POSIX_RLIMIT_DATA', 2);

/**
 * The maximum size of files that the process can create, in bytes.
 * @link http://www.php.net/manual/en/posix.constants.php
 */
define ('POSIX_RLIMIT_FSIZE', 1);

/**
 * The maximum number of bytes that can be locked into memory.
 * @link http://www.php.net/manual/en/posix.constants.php
 */
define ('POSIX_RLIMIT_MEMLOCK', 6);

/**
 * A value one greater than the maximum file descriptor number that can be
 * opened by this process.
 * @link http://www.php.net/manual/en/posix.constants.php
 */
define ('POSIX_RLIMIT_NOFILE', 8);

/**
 * The maximum number of processes (and/or threads, on some operating
 * systems) that can be created for the real user ID of the process.
 * @link http://www.php.net/manual/en/posix.constants.php
 */
define ('POSIX_RLIMIT_NPROC', 7);

/**
 * The maximum size of the process's resident set, in pages.
 * @link http://www.php.net/manual/en/posix.constants.php
 */
define ('POSIX_RLIMIT_RSS', 5);

/**
 * The maximum size of the process stack, in bytes.
 * @link http://www.php.net/manual/en/posix.constants.php
 */
define ('POSIX_RLIMIT_STACK', 3);

/**
 * Used to indicate an infinite value for a resource limit.
 * @link http://www.php.net/manual/en/posix.constants.php
 */
define ('POSIX_RLIMIT_INFINITY', 9223372036854775807);

// End of posix v.8.2.6
