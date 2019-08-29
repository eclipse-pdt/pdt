<?php

// Start of pcntl v.7.1.1

/**
 * Forks the currently running process
 * @link http://www.php.net/manual/en/function.pcntl-fork.php
 * @return int On success, the PID of the child process is returned in the
 * parent's thread of execution, and a 0 is returned in the child's
 * thread of execution. On failure, a -1 will be returned in the
 * parent's context, no child process will be created, and a PHP
 * error is raised.
 */
function pcntl_fork () {}

/**
 * Waits on or returns the status of a forked child
 * @link http://www.php.net/manual/en/function.pcntl-waitpid.php
 * @param int $pid <p>
 * The value of pid can be one of the following:
 * <table>
 * possible values for pid
 * <table>
 * <tr valign="top">
 * <td>&lt; -1</td>
 * <td>
 * wait for any child process whose process group ID is equal to
 * the absolute value of pid.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>-1</td>
 * <td>
 * wait for any child process; this is the same behaviour that
 * the wait function exhibits.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>0</td>
 * <td>
 * wait for any child process whose process group ID is equal to
 * that of the calling process.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>&gt; 0</td>
 * <td>
 * wait for the child whose process ID is equal to the value of
 * pid.
 * </td>
 * </tr>
 * </table>
 * </table>
 * </p>
 * <p>
 * Specifying -1 as the pid is
 * equivalent to the functionality pcntl_wait provides
 * (minus options).
 * </p>
 * @param int $status pcntl_waitpid will store status information
 * in the status parameter which can be
 * evaluated using the following functions:
 * pcntl_wifexited,
 * pcntl_wifstopped,
 * pcntl_wifsignaled,
 * pcntl_wexitstatus,
 * pcntl_wtermsig and
 * pcntl_wstopsig.
 * @param int $options [optional] The value of options is the value of zero
 * or more of the following two global constants
 * OR'ed together:
 * <table>
 * possible values for options
 * <table>
 * <tr valign="top">
 * <td>WNOHANG</td>
 * <td>
 * return immediately if no child has exited.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>WUNTRACED</td>
 * <td>
 * return for children which are stopped, and whose status has
 * not been reported.
 * </td>
 * </tr>
 * </table>
 * </table>
 * @return int pcntl_waitpid returns the process ID of the
 * child which exited, -1 on error or zero if WNOHANG was used and no
 * child was available
 */
function pcntl_waitpid (int $pid, int &$status, int $options = null) {}

/**
 * Waits on or returns the status of a forked child
 * @link http://www.php.net/manual/en/function.pcntl-wait.php
 * @param int $status pcntl_wait will store status information
 * in the status parameter which can be
 * evaluated using the following functions:
 * pcntl_wifexited,
 * pcntl_wifstopped,
 * pcntl_wifsignaled,
 * pcntl_wexitstatus,
 * pcntl_wtermsig and
 * pcntl_wstopsig.
 * @param int $options [optional] If wait3 is available on your system (mostly BSD-style systems), you can
 * provide the optional options parameter. If this
 * parameter is not provided, wait will be used for the system call. If
 * wait3 is not available, providing a value for options
 * will have no effect. The value of options
 * is the value of zero or more of the following two constants
 * OR'ed together:
 * <table>
 * Possible values for options
 * <table>
 * <tr valign="top">
 * <td>WNOHANG</td>
 * <td>
 * Return immediately if no child has exited.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>WUNTRACED</td>
 * <td>
 * Return for children which are stopped, and whose status has
 * not been reported.
 * </td>
 * </tr>
 * </table>
 * </table>
 * @return int pcntl_wait returns the process ID of the
 * child which exited, -1 on error or zero if WNOHANG was provided as an
 * option (on wait3-available systems) and no child was available.
 */
function pcntl_wait (int &$status, int $options = null) {}

/**
 * Installs a signal handler
 * @link http://www.php.net/manual/en/function.pcntl-signal.php
 * @param int $signo The signal number.
 * @param callable|int $handler <p>
 * The signal handler. This may be either a callable, which
 * will be invoked to handle the signal, or either of the two global
 * constants SIG_IGN or SIG_DFL,
 * which will ignore the signal or restore the default signal handler
 * respectively. 
 * </p>
 * <p>
 * If a callable is given, it must implement the following
 * signature:
 * </p>
 * <p>
 * voidhandler
 * intsigno
 * mixedsigninfo
 * <p>
 * signo
 * <br>
 * The signal being handled.
 * siginfo
 * <br>
 * If operating systems supports siginfo_t structures, this will be an array of signal information dependent on the signal.
 * </p>
 * </p>
 * <p>
 * Note that when you set a handler to an object method, that object's
 * reference count is increased which makes it persist until you either
 * change the handler to something else, or your script ends.
 * </p>
 * @param bool $restart_syscalls [optional] Specifies whether system call restarting should be used when this
 * signal arrives.
 * @return bool true on success or false on failure
 */
function pcntl_signal (int $signo, $handler, bool $restart_syscalls = null) {}

/**
 * Get the current handler for specified signal
 * @link http://www.php.net/manual/en/function.pcntl-signal-get-handler.php
 * @param int $signo The signal number.
 * @return int|string This function may return an integer value that refers to SIG_DFL or SIG_IGN. If you set a custom handler a string value containing the function name is returned.
 */
function pcntl_signal_get_handler (int $signo) {}

/**
 * Calls signal handlers for pending signals
 * @link http://www.php.net/manual/en/function.pcntl-signal-dispatch.php
 * @return bool true on success or false on failure
 */
function pcntl_signal_dispatch () {}

/**
 * Checks if status code represents a normal exit
 * @link http://www.php.net/manual/en/function.pcntl-wifexited.php
 * @param int $status pcntl.parameter.status
 * @return bool true if the child status code represents a normal exit, false
 * otherwise.
 */
function pcntl_wifexited (int $status) {}

/**
 * Checks whether the child process is currently stopped
 * @link http://www.php.net/manual/en/function.pcntl-wifstopped.php
 * @param int $status pcntl.parameter.status
 * @return bool true if the child process which caused the return is
 * currently stopped, false otherwise.
 */
function pcntl_wifstopped (int $status) {}

/**
 * Checks whether the status code represents a termination due to a signal
 * @link http://www.php.net/manual/en/function.pcntl-wifsignaled.php
 * @param int $status pcntl.parameter.status
 * @return bool true if the child process exited because of a signal which was
 * not caught, false otherwise.
 */
function pcntl_wifsignaled (int $status) {}

/**
 * Returns the return code of a terminated child
 * @link http://www.php.net/manual/en/function.pcntl-wexitstatus.php
 * @param int $status pcntl.parameter.status
 * @return int the return code, as an integer.
 */
function pcntl_wexitstatus (int $status) {}

/**
 * Returns the signal which caused the child to terminate
 * @link http://www.php.net/manual/en/function.pcntl-wtermsig.php
 * @param int $status pcntl.parameter.status
 * @return int the signal number, as an integer.
 */
function pcntl_wtermsig (int $status) {}

/**
 * Returns the signal which caused the child to stop
 * @link http://www.php.net/manual/en/function.pcntl-wstopsig.php
 * @param int $status pcntl.parameter.status
 * @return int the signal number.
 */
function pcntl_wstopsig (int $status) {}

/**
 * Executes specified program in current process space
 * @link http://www.php.net/manual/en/function.pcntl-exec.php
 * @param string $path path must be the path to a binary executable or a
 * script with a valid path pointing to an executable in the shebang (
 * #!/usr/local/bin/perl for example) as the first line. See your system's
 * man execve(2) page for additional information.
 * @param array $args [optional] args is an array of argument strings passed to the
 * program.
 * @param array $envs [optional] envs is an array of strings which are passed as
 * environment to the program. The array is in the format of name => value,
 * the key being the name of the environmental variable and the value being
 * the value of that variable.
 * @return bool false on error and does not return on success.
 */
function pcntl_exec (string $path, array $args = null, array $envs = null) {}

/**
 * Set an alarm clock for delivery of a signal
 * @link http://www.php.net/manual/en/function.pcntl-alarm.php
 * @param int $seconds The number of seconds to wait. If seconds is
 * zero, no new alarm is created.
 * @return int the time in seconds that any previously scheduled alarm had
 * remaining before it was to be delivered, or 0 if there
 * was no previously scheduled alarm.
 */
function pcntl_alarm (int $seconds) {}

/**
 * Retrieve the error number set by the last pcntl function which failed
 * @link http://www.php.net/manual/en/function.pcntl-get-last-error.php
 * @return int error code.
 */
function pcntl_get_last_error () {}

/**
 * Alias: pcntl_get_last_error
 * @link http://www.php.net/manual/en/function.pcntl-errno.php
 */
function pcntl_errno () {}

/**
 * Retrieve the system error message associated with the given errno
 * @link http://www.php.net/manual/en/function.pcntl-strerror.php
 * @param int $errno 
 * @return string error description on success or false on failure.
 */
function pcntl_strerror (int $errno) {}

/**
 * Get the priority of any process
 * @link http://www.php.net/manual/en/function.pcntl-getpriority.php
 * @param int $pid [optional] If not specified, the pid of the current process is used.
 * @param int $process_identifier [optional] One of PRIO_PGRP, PRIO_USER
 * or PRIO_PROCESS.
 * @return int pcntl_getpriority returns the priority of the process
 * or false on error. A lower numerical value causes more favorable
 * scheduling.
 */
function pcntl_getpriority (int $pid = null, int $process_identifier = null) {}

/**
 * Change the priority of any process
 * @link http://www.php.net/manual/en/function.pcntl-setpriority.php
 * @param int $priority priority is generally a value in the range
 * -20 to 20. The default priority
 * is 0 while a lower numerical value causes more
 * favorable scheduling. Because priority levels can differ between
 * system types and kernel versions, please see your system's setpriority(2)
 * man page for specific details.
 * @param int $pid [optional] If not specified, the pid of the current process is used.
 * @param int $process_identifier [optional] One of PRIO_PGRP, PRIO_USER
 * or PRIO_PROCESS.
 * @return bool true on success or false on failure
 */
function pcntl_setpriority (int $priority, int $pid = null, int $process_identifier = null) {}

/**
 * Sets and retrieves blocked signals
 * @link http://www.php.net/manual/en/function.pcntl-sigprocmask.php
 * @param int $how <p>
 * Sets the behavior of pcntl_sigprocmask. Possible
 * values: 
 * <p>
 * SIG_BLOCK: Add the signals to the
 * currently blocked signals.
 * SIG_UNBLOCK: Remove the signals from the
 * currently blocked signals.
 * SIG_SETMASK: Replace the currently
 * blocked signals by the given list of signals.
 * </p>
 * </p>
 * @param array $set List of signals.
 * @param array $oldset [optional] The oldset parameter is set to an array
 * containing the list of the previously blocked signals.
 * @return bool true on success or false on failure
 */
function pcntl_sigprocmask (int $how, array $set, array &$oldset = null) {}

/**
 * Waits for signals
 * @link http://www.php.net/manual/en/function.pcntl-sigwaitinfo.php
 * @param array $set Array of signals to wait for.
 * @param array $siginfo [optional] <p>
 * The siginfo parameter is set to an array containing
 * informations about the signal.
 * </p>
 * <p>
 * The following elements are set for all signals:
 * <p>
 * signo: Signal number
 * errno: An error number
 * code: Signal code
 * </p>
 * </p>
 * <p>
 * The following elements may be set for the SIGCHLD signal:
 * <p>
 * status: Exit value or signal
 * utime: User time consumed
 * stime: System time consumed
 * pid: Sending process ID
 * uid: Real user ID of sending process
 * </p>
 * </p>
 * <p>
 * The following elements may be set for the SIGILL,
 * SIGFPE, SIGSEGV and
 * SIGBUS signals:
 * <p>
 * addr: Memory location which caused fault
 * </p>
 * </p>
 * <p>
 * The following element may be set for the SIGPOLL
 * signal:
 * <p>
 * band: Band event
 * fd: File descriptor number
 * </p>
 * </p>
 * @return int On success, pcntl_sigwaitinfo returns a signal number.
 */
function pcntl_sigwaitinfo (array $set, array &$siginfo = null) {}

/**
 * Waits for signals, with a timeout
 * @link http://www.php.net/manual/en/function.pcntl-sigtimedwait.php
 * @param array $set Array of signals to wait for.
 * @param array $siginfo [optional] The siginfo is set to an array containing
 * informations about the signal. See
 * pcntl_sigwaitinfo.
 * @param int $seconds [optional] Timeout in seconds.
 * @param int $nanoseconds [optional] Timeout in nanoseconds.
 * @return int On success, pcntl_sigtimedwait returns a signal number.
 */
function pcntl_sigtimedwait (array $set, array &$siginfo = null, int $seconds = null, int $nanoseconds = null) {}

/**
 * @param $status
 */
function pcntl_wifcontinued ($status) {}

/**
 * Enable/disable asynchronous signal handling or return the old setting
 * @link http://www.php.net/manual/en/function.pcntl-async-signals.php
 * @param bool $on [optional] Whether asynchronous signal handling should be enabled.
 * @return bool When used as getter (that is without the optional parameter) it returns
 * whether asynchronous signal handling is enabled. When used as setter (that is
 * with the optional parameter given), it returns whether asynchronous signal
 * handling was enabled before the function call.
 */
function pcntl_async_signals (bool $on = null) {}


/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('WNOHANG', 1);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('WUNTRACED', 2);
define ('WCONTINUED', 8);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SIG_IGN', 1);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SIG_DFL', 0);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SIG_ERR', -1);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SIGHUP', 1);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SIGINT', 2);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SIGQUIT', 3);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SIGILL', 4);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SIGTRAP', 5);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SIGABRT', 6);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SIGIOT', 6);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SIGBUS', 7);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SIGFPE', 8);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SIGKILL', 9);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SIGUSR1', 10);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SIGSEGV', 11);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SIGUSR2', 12);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SIGPIPE', 13);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SIGALRM', 14);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SIGTERM', 15);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SIGSTKFLT', 16);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SIGCLD', 17);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SIGCHLD', 17);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SIGCONT', 18);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SIGSTOP', 19);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SIGTSTP', 20);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SIGTTIN', 21);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SIGTTOU', 22);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SIGURG', 23);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SIGXCPU', 24);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SIGXFSZ', 25);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SIGVTALRM', 26);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SIGPROF', 27);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SIGWINCH', 28);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SIGPOLL', 29);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SIGIO', 29);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SIGPWR', 30);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SIGSYS', 31);

/**
 * 
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SIGBABY', 31);
define ('PRIO_PGRP', 1);
define ('PRIO_USER', 2);
define ('PRIO_PROCESS', 0);

/**
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SIG_BLOCK', 0);

/**
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SIG_UNBLOCK', 1);

/**
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SIG_SETMASK', 2);

/**
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SI_USER', 0);

/**
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SI_KERNEL', 128);

/**
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SI_QUEUE', -1);

/**
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SI_TIMER', -2);
define ('SI_MESGQ', -3);

/**
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SI_ASYNCIO', -4);

/**
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SI_SIGIO', -5);

/**
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SI_TKILL', -6);

/**
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('CLD_EXITED', 1);

/**
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('CLD_KILLED', 2);

/**
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('CLD_DUMPED', 3);

/**
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('CLD_TRAPPED', 4);

/**
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('CLD_STOPPED', 5);

/**
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('CLD_CONTINUED', 6);

/**
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('TRAP_BRKPT', 1);

/**
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('TRAP_TRACE', 2);

/**
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('POLL_IN', 1);

/**
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('POLL_OUT', 2);

/**
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('POLL_MSG', 3);

/**
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('POLL_ERR', 4);

/**
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('POLL_PRI', 5);

/**
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('POLL_HUP', 6);

/**
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('ILL_ILLOPC', 1);

/**
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('ILL_ILLOPN', 2);

/**
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('ILL_ILLADR', 3);

/**
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('ILL_ILLTRP', 4);

/**
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('ILL_PRVOPC', 5);

/**
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('ILL_PRVREG', 6);

/**
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('ILL_COPROC', 7);

/**
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('ILL_BADSTK', 8);

/**
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('FPE_INTDIV', 1);

/**
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('FPE_INTOVF', 2);

/**
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('FPE_FLTDIV', 3);

/**
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('FPE_FLTOVF', 4);

/**
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('FPE_FLTUND', 7);

/**
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('FPE_FLTRES', 6);

/**
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('FPE_FLTINV', 7);

/**
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('FPE_FLTSUB', 8);

/**
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SEGV_MAPERR', 1);

/**
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('SEGV_ACCERR', 2);

/**
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('BUS_ADRALN', 1);

/**
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('BUS_ADRERR', 2);

/**
 * Available since PHP 5.3.0.
 * @link http://www.php.net/manual/en/pcntl.constants.php
 */
define ('BUS_OBJERR', 3);
define ('PCNTL_EINTR', 4);
define ('PCNTL_ECHILD', 10);
define ('PCNTL_EINVAL', 22);
define ('PCNTL_EAGAIN', 11);
define ('PCNTL_ESRCH', 3);
define ('PCNTL_EACCES', 13);
define ('PCNTL_EPERM', 1);
define ('PCNTL_ENOMEM', 12);
define ('PCNTL_E2BIG', 7);
define ('PCNTL_EFAULT', 14);
define ('PCNTL_EIO', 5);
define ('PCNTL_EISDIR', 21);
define ('PCNTL_ELIBBAD', 80);
define ('PCNTL_ELOOP', 40);
define ('PCNTL_EMFILE', 24);
define ('PCNTL_ENAMETOOLONG', 36);
define ('PCNTL_ENFILE', 23);
define ('PCNTL_ENOENT', 2);
define ('PCNTL_ENOEXEC', 8);
define ('PCNTL_ENOTDIR', 20);
define ('PCNTL_ETXTBSY', 26);

// End of pcntl v.7.1.1
