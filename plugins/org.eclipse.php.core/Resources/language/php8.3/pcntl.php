<?php

// Start of pcntl v.8.2.6

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
 * @return int Returns error code.
 */
function pcntl_get_last_error (): int {}

/**
 * Alias of pcntl_get_last_error
 * @link http://www.php.net/manual/en/function.pcntl-errno.php
 * @return int Returns error code.
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
 * @param int $error_code 
 * @return string Returns error description.
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
define ('WCONTINUED', 16);

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
define ('PCNTL_EINTR', 4);
define ('PCNTL_ECHILD', 10);
define ('PCNTL_EINVAL', 22);
define ('PCNTL_EAGAIN', 35);
define ('PCNTL_ESRCH', 3);
define ('PCNTL_EACCES', 13);
define ('PCNTL_EPERM', 1);
define ('PCNTL_ENOMEM', 12);
define ('PCNTL_E2BIG', 7);
define ('PCNTL_EFAULT', 14);
define ('PCNTL_EIO', 5);
define ('PCNTL_EISDIR', 21);
define ('PCNTL_ELOOP', 62);
define ('PCNTL_EMFILE', 24);
define ('PCNTL_ENAMETOOLONG', 63);
define ('PCNTL_ENFILE', 23);
define ('PCNTL_ENOENT', 2);
define ('PCNTL_ENOEXEC', 8);
define ('PCNTL_ENOTDIR', 20);
define ('PCNTL_ETXTBSY', 26);
define ('PCNTL_ENOSPC', 28);
define ('PCNTL_EUSERS', 68);

// End of pcntl v.8.2.6
