<?php

// Start of pcntl v.8.5.0-dev

namespace Pcntl {

enum QosClass implements \UnitEnum {
	const UserInteractive = ;
	const UserInitiated = ;
	const Default = ;
	const Utility = ;
	const Background = ;


	public readonly string $name;

	/**
	 * {@inheritdoc}
	 */
	public static function cases (): array {}

}


}


namespace {

/**
 * Forks the currently running process
 * @link http://www.php.net/manual/en/function.pcntl-fork.php
 * @return int On success, the PID of the child process is returned in the
 * parent's thread of execution, and a 0 is returned in the child's
 * thread of execution. On failure, a -1 will be returned in the
 * parent's context, no child process will be created, and a PHP
 * error is raised.
 */
function pcntl_fork (): int {}

/**
 * Waits on or returns the status of a forked child
 * @link http://www.php.net/manual/en/function.pcntl-waitpid.php
 * @param int $process_id 
 * @param int $status 
 * @param int $flags [optional] 
 * @param array $resource_usage [optional] 
 * @return int pcntl_waitpid returns the process ID of the
 * child which exited, -1 on error or zero if WNOHANG was used and no
 * child was available
 */
function pcntl_waitpid (int $process_id, int &$status, int $flags = null, array &$resource_usage = '[]'): int {}

/**
 * Waits for a child process to change state
 * @link http://www.php.net/manual/en/function.pcntl-waitid.php
 * @param int $idtype [optional] The idtype and id arguments
 * are used to specify which children to wait for.
 * @param int|null $id [optional] 
 * @param array $info [optional] The info parameter is set to an array
 * containing information about the signal.
 * <p>info array may contain the following keys:
 * <p>
 * signo: Signal number
 * errno: System error number
 * code: Signal code
 * status: Exit value or signal
 * pid: Sending process ID
 * uid: Real user ID of sending process
 * utime: User time consumed
 * stime: System time consumed
 * </p></p>
 * @param int $flags [optional] The value of flags is the value of zero or more of
 * the following constants OR'ed together:
 * <table>
 * possible values for flags
 * <table>
 * <tr valign="top">
 * <td>WCONTINUED</td>
 * <td>
 * Status shall be returned for any continued child process whose
 * status either has not been reported since it continued from a job
 * control stop or has been reported only by calls to
 * pcntl_waitid with the
 * WNOWAIT flag set.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>WEXITED</td>
 * <td>
 * Wait for processes that have exited.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>WNOHANG</td>
 * <td>
 * Do not hang if no status is available; return immediately.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>WNOWAIT</td>
 * <td>
 * Keep the process whose status is returned in
 * info in a waitable state. This shall not
 * affect the state of the process; the process may be waited for again
 * after this call completes.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>WSTOPPED</td>
 * <td>
 * Status shall be returned for any child that has stopped upon receipt
 * of a signal, and whose status either has not been reported since it
 * stopped or has been reported only by calls to
 * pcntl_waitid with the
 * WNOWAIT flag set.
 * </td>
 * </tr>
 * </table>
 * </table>
 * @param array $resource_usage [optional] The resource_usage parameter is set to an array
 * containing resource usage statistics from the child process.
 * This is supported either if the wait6 system call is available
 * (e.g. on FreeBSD), or on Linux through the raw waitid system call.
 * @return bool pcntl_waitid returns true if
 * WNOHANG was specified and status is not available for
 * any process specified by idtype and
 * id.
 * <p>pcntl_waitid returns true due to the change of state
 * of one of its children.</p>
 * <p>Otherwise, false is returned and pcntl_get_last_error
 * can be used to get the errno error number.</p>
 * <p>Once an errno error number has been obtained,
 * pcntl_strerror can be used to get the text message
 * associated with it.</p>
 */
function pcntl_waitid (int $idtype = P_ALL, ?int $id = null, array &$info = '[]', int $flags = WEXITED, array &$resource_usage = '[]'): bool {}

/**
 * Waits on or returns the status of a forked child
 * @link http://www.php.net/manual/en/function.pcntl-wait.php
 * @param int $status 
 * @param int $flags [optional] 
 * @param array $resource_usage [optional] 
 * @return int pcntl_wait returns the process ID of the
 * child which exited, -1 on error or zero if WNOHANG was provided as an
 * option (on wait3-available systems) and no child was available.
 */
function pcntl_wait (int &$status, int $flags = null, array &$resource_usage = '[]'): int {}

/**
 * Installs a signal handler
 * @link http://www.php.net/manual/en/function.pcntl-signal.php
 * @param int $signal 
 * @param callable|int $handler 
 * @param bool $restart_syscalls [optional] 
 * @return bool Returns true on success or false on failure.
 */
function pcntl_signal (int $signal, callable|int $handler, bool $restart_syscalls = true): bool {}

/**
 * Get the current handler for specified signal
 * @link http://www.php.net/manual/en/function.pcntl-signal-get-handler.php
 * @param int $signal The signal number.
 * @return callable|int This function may return an integer value that refers to SIG_DFL or SIG_IGN.
 * If a custom handler has been set, that callable is returned.
 */
function pcntl_signal_get_handler (int $signal): callable|int {}

/**
 * Calls signal handlers for pending signals
 * @link http://www.php.net/manual/en/function.pcntl-signal-dispatch.php
 * @return bool Returns true on success or false on failure.
 */
function pcntl_signal_dispatch (): bool {}

/**
 * Sets and retrieves blocked signals
 * @link http://www.php.net/manual/en/function.pcntl-sigprocmask.php
 * @param int $mode 
 * @param array $signals 
 * @param array $old_signals [optional] 
 * @return bool Returns true on success or false on failure.
 */
function pcntl_sigprocmask (int $mode, array $signals, array &$old_signals = null): bool {}

/**
 * Checks if status code represents a normal exit
 * @link http://www.php.net/manual/en/function.pcntl-wifexited.php
 * @param int $status 
 * @return bool Returns true if the child status code represents a normal exit, false
 * otherwise.
 */
function pcntl_wifexited (int $status): bool {}

/**
 * Checks whether the child process is currently stopped
 * @link http://www.php.net/manual/en/function.pcntl-wifstopped.php
 * @param int $status 
 * @return bool Returns true if the child process which caused the return is
 * currently stopped, false otherwise.
 */
function pcntl_wifstopped (int $status): bool {}

/**
 * {@inheritdoc}
 * @param int $status
 */
function pcntl_wifcontinued (int $status): bool {}

/**
 * Checks whether the status code represents a termination due to a signal
 * @link http://www.php.net/manual/en/function.pcntl-wifsignaled.php
 * @param int $status 
 * @return bool Returns true if the child process exited because of a signal which was
 * not caught, false otherwise.
 */
function pcntl_wifsignaled (int $status): bool {}

/**
 * Returns the return code of a terminated child
 * @link http://www.php.net/manual/en/function.pcntl-wexitstatus.php
 * @param int $status 
 * @return int|false Returns the return code.
 * If the functionality is not supported by the OS, false is returned.
 */
function pcntl_wexitstatus (int $status): int|false {}

/**
 * Returns the signal which caused the child to terminate
 * @link http://www.php.net/manual/en/function.pcntl-wtermsig.php
 * @param int $status 
 * @return int|false Returns the signal number.
 * If the functionality is not supported by the OS, false is returned.
 */
function pcntl_wtermsig (int $status): int|false {}

/**
 * Returns the signal which caused the child to stop
 * @link http://www.php.net/manual/en/function.pcntl-wstopsig.php
 * @param int $status 
 * @return int|false Returns the signal number.
 * If the functionality is not supported by the OS, false is returned.
 */
function pcntl_wstopsig (int $status): int|false {}

/**
 * Executes specified program in current process space
 * @link http://www.php.net/manual/en/function.pcntl-exec.php
 * @param string $path 
 * @param array $args [optional] 
 * @param array $env_vars [optional] 
 * @return bool Returns false.
 */
function pcntl_exec (string $path, array $args = '[]', array $env_vars = '[]'): bool {}

/**
 * Set an alarm clock for delivery of a signal
 * @link http://www.php.net/manual/en/function.pcntl-alarm.php
 * @param int $seconds 
 * @return int Returns the time in seconds that any previously scheduled alarm had
 * remaining before it was to be delivered, or 0 if there
 * was no previously scheduled alarm.
 */
function pcntl_alarm (int $seconds): int {}

/**
 * Retrieve the error number set by the last pcntl function which failed
 * @link http://www.php.net/manual/en/function.pcntl-get-last-error.php
 * @return int Returns the error number (errno) set by the last
 * pcntl function that failed. If there was no error, 0 is returned.
 */
function pcntl_get_last_error (): int {}

/**
 * Alias of pcntl_get_last_error
 * @link http://www.php.net/manual/en/function.pcntl-errno.php
 * @return int Returns the error number (errno) set by the last
 * pcntl function that failed. If there was no error, 0 is returned.
 */
function pcntl_errno (): int {}

/**
 * Get the priority of any process
 * @link http://www.php.net/manual/en/function.pcntl-getpriority.php
 * @param int|null $process_id [optional] 
 * @param int $mode [optional] 
 * @return int|false pcntl_getpriority returns the priority of the process
 * or false on error. A lower numerical value causes more favorable
 * scheduling.
 */
function pcntl_getpriority (?int $process_id = null, int $mode = PRIO_PROCESS): int|false {}

/**
 * Change the priority of any process
 * @link http://www.php.net/manual/en/function.pcntl-setpriority.php
 * @param int $priority 
 * @param int|null $process_id [optional] 
 * @param int $mode [optional] 
 * @return bool Returns true on success or false on failure.
 */
function pcntl_setpriority (int $priority, ?int $process_id = null, int $mode = PRIO_PROCESS): bool {}

/**
 * Retrieve the system error message associated with the given errno
 * @link http://www.php.net/manual/en/function.pcntl-strerror.php
 * @param int $error_code An error number (errno),
 * returned by pcntl_get_last_error.
 * @return string Returns the error message, as a string.
 */
function pcntl_strerror (int $error_code): string {}

/**
 * Enable/disable asynchronous signal handling or return the old setting
 * @link http://www.php.net/manual/en/function.pcntl-async-signals.php
 * @param bool|null $enable [optional] Whether asynchronous signal handling should be enabled.
 * @return bool When used as getter (enable parameter is null) it returns
 * whether asynchronous signal handling is enabled. When used as setter (enable
 * parameter is not null), it returns whether asynchronous signal
 * handling was enabled before the function call.
 */
function pcntl_async_signals (?bool $enable = null): bool {}

/**
 * {@inheritdoc}
 */
function pcntl_getqos_class (): Pcntl\QosClass {}

/**
 * {@inheritdoc}
 * @param Pcntl\QosClass $qos_class [optional]
 */
function pcntl_setqos_class (Pcntl\QosClass $qos_class = \Pcntl\QosClass::Default): void {}


/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('WNOHANG', 1);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('WUNTRACED', 2);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('WCONTINUED', 16);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('WEXITED', 4);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('WSTOPPED', 8);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('WNOWAIT', 32);

/**
 * Select any children.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('P_ALL', 0);

/**
 * Select by process ID.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('P_PID', 1);

/**
 * Select by process group ID.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('P_PGID', 2);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('SIG_IGN', 1);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('SIG_DFL', 0);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('SIG_ERR', -1);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('SIGHUP', 1);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('SIGINT', 2);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('SIGQUIT', 3);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('SIGILL', 4);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('SIGTRAP', 5);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('SIGABRT', 6);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('SIGIOT', 6);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('SIGBUS', 10);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('SIGFPE', 8);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('SIGKILL', 9);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('SIGUSR1', 30);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('SIGSEGV', 11);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('SIGUSR2', 31);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('SIGPIPE', 13);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('SIGALRM', 14);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('SIGTERM', 15);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('SIGCHLD', 20);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('SIGCONT', 19);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('SIGSTOP', 17);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('SIGTSTP', 18);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('SIGTTIN', 21);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('SIGTTOU', 22);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('SIGURG', 16);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('SIGXCPU', 24);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('SIGXFSZ', 25);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('SIGVTALRM', 26);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('SIGPROF', 27);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('SIGWINCH', 28);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('SIGIO', 23);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('SIGINFO', 29);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('SIGSYS', 12);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('SIGBABY', 12);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('PRIO_PGRP', 1);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('PRIO_USER', 2);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('PRIO_PROCESS', 0);

/**
 * Available as of PHP 8.1.0.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('PRIO_DARWIN_BG', 4096);

/**
 * Available as of PHP 8.1.0.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('PRIO_DARWIN_THREAD', 3);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('SIG_BLOCK', 1);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('SIG_UNBLOCK', 2);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('SIG_SETMASK', 3);

/**
 * Interrupted function call
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('PCNTL_EINTR', 4);

/**
 * No child processes
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('PCNTL_ECHILD', 10);

/**
 * Invalid argument
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('PCNTL_EINVAL', 22);

/**
 * Resource temporarily unavailable
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('PCNTL_EAGAIN', 35);

/**
 * No such process
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('PCNTL_ESRCH', 3);

/**
 * Permission denied
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('PCNTL_EACCES', 13);

/**
 * Operation not permitted
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('PCNTL_EPERM', 1);

/**
 * Not enough space/cannot allocate memory
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('PCNTL_ENOMEM', 12);

/**
 * Argument list too long
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('PCNTL_E2BIG', 7);

/**
 * Bad address
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('PCNTL_EFAULT', 14);

/**
 * Input/output error
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('PCNTL_EIO', 5);

/**
 * Is a directory
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('PCNTL_EISDIR', 21);

/**
 * Too many levels of symbolic links
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('PCNTL_ELOOP', 62);

/**
 * Too many open files. Commonly caused by exceeding
 * the RLIMIT_NOFILE resource limit.
 * Can also be caused by exceeding the limit specified in
 * /proc/sys/fs/nr_open.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('PCNTL_EMFILE', 24);

/**
 * Filename too long
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('PCNTL_ENAMETOOLONG', 63);

/**
 * Too many open files in system.
 * On Linux, this is probably a result of encountering
 * the /proc/sys/fs/file-max limit.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('PCNTL_ENFILE', 23);

/**
 * No such file or directory.
 * Typically, this error results when a specified pathname
 * does not exist, or one of the components in the directory
 * prefix of a pathname does not exist, or the specified
 * pathname is a dangling symbolic link.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('PCNTL_ENOENT', 2);

/**
 * Exec format error
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('PCNTL_ENOEXEC', 8);

/**
 * Not a directory
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('PCNTL_ENOTDIR', 20);

/**
 * Text file busy
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('PCNTL_ETXTBSY', 26);

/**
 * No space left on device
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('PCNTL_ENOSPC', 28);

/**
 * Too many users
 * @link http://www.php.net/manual/en/pcntl.constants.php
 * @var int
 */
define ('PCNTL_EUSERS', 68);


}

// End of pcntl v.8.5.0-dev
