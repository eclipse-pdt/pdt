<?php

// Start of pcntl v.

/**
 * Forks the currently running process
 * @link http://php.net/manual/en/function.pcntl-fork.php
 * @return int 
 */
function pcntl_fork () {}

/**
 * Waits on or returns the status of a forked child
 * @link http://php.net/manual/en/function.pcntl-waitpid.php
 * @param pid int
 * @param status int
 * @param options int[optional]
 * @return int 
 */
function pcntl_waitpid ($pid, &$status, $options = null) {}

/**
 * Waits on or returns the status of a forked child
 * @link http://php.net/manual/en/function.pcntl-wait.php
 * @param status int
 * @param options int[optional]
 * @return int 
 */
function pcntl_wait (&$status, $options = null) {}

/**
 * Installs a signal handler
 * @link http://php.net/manual/en/function.pcntl-signal.php
 * @param signo int
 * @param handler callback
 * @param restart_syscalls bool[optional]
 * @return bool 
 */
function pcntl_signal ($signo, $handler, $restart_syscalls = null) {}

/**
 * Checks if status code represents a normal exit
 * @link http://php.net/manual/en/function.pcntl-wifexited.php
 * @param status int
 * @return bool true if the child status code represents a normal exit, false
 */
function pcntl_wifexited ($status) {}

/**
 * Checks whether the child process is currently stopped
 * @link http://php.net/manual/en/function.pcntl-wifstopped.php
 * @param status int
 * @return bool true if the child process which caused the return is
 */
function pcntl_wifstopped ($status) {}

/**
 * Checks whether the status code represents a termination due to a signal
 * @link http://php.net/manual/en/function.pcntl-wifsignaled.php
 * @param status int
 * @return bool true if the child process exited because of a signal which was
 */
function pcntl_wifsignaled ($status) {}

/**
 * Returns the return code of a terminated child
 * @link http://php.net/manual/en/function.pcntl-wexitstatus.php
 * @param status int
 * @return int the return code, as an integer.
 */
function pcntl_wexitstatus ($status) {}

/**
 * Returns the signal which caused the child to terminate
 * @link http://php.net/manual/en/function.pcntl-wtermsig.php
 * @param status int
 * @return int the signal number, as an integer.
 */
function pcntl_wtermsig ($status) {}

/**
 * Returns the signal which caused the child to stop
 * @link http://php.net/manual/en/function.pcntl-wstopsig.php
 * @param status int
 * @return int the signal number.
 */
function pcntl_wstopsig ($status) {}

/**
 * Executes specified program in current process space
 * @link http://php.net/manual/en/function.pcntl-exec.php
 * @param path string
 * @param args array[optional]
 * @param envs array[optional]
 * @return void false on error and does not return on success.
 */
function pcntl_exec ($path, array $args = null, array $envs = null) {}

/**
 * Set an alarm clock for delivery of a signal
 * @link http://php.net/manual/en/function.pcntl-alarm.php
 * @param seconds int
 * @return int the time in seconds that any previously scheduled alarm had
 */
function pcntl_alarm ($seconds) {}

/**
 * Get the priority of any process
 * @link http://php.net/manual/en/function.pcntl-getpriority.php
 * @param pid int[optional]
 * @param process_identifier int[optional]
 * @return int 
 */
function pcntl_getpriority ($pid = null, $process_identifier = null) {}

/**
 * Change the priority of any process
 * @link http://php.net/manual/en/function.pcntl-setpriority.php
 * @param priority int
 * @param pid int[optional]
 * @param process_identifier int[optional]
 * @return bool 
 */
function pcntl_setpriority ($priority, $pid = null, $process_identifier = null) {}

define ('WNOHANG', 1);
define ('WUNTRACED', 2);
define ('SIG_IGN', 1);
define ('SIG_DFL', 0);
define ('SIG_ERR', -1);
define ('SIGHUP', 1);
define ('SIGINT', 2);
define ('SIGQUIT', 3);
define ('SIGILL', 4);
define ('SIGTRAP', 5);
define ('SIGABRT', 6);
define ('SIGIOT', 6);
define ('SIGBUS', 7);
define ('SIGFPE', 8);
define ('SIGKILL', 9);
define ('SIGUSR1', 10);
define ('SIGSEGV', 11);
define ('SIGUSR2', 12);
define ('SIGPIPE', 13);
define ('SIGALRM', 14);
define ('SIGTERM', 15);
define ('SIGSTKFLT', 16);
define ('SIGCLD', 17);
define ('SIGCHLD', 17);
define ('SIGCONT', 18);
define ('SIGSTOP', 19);
define ('SIGTSTP', 20);
define ('SIGTTIN', 21);
define ('SIGTTOU', 22);
define ('SIGURG', 23);
define ('SIGXCPU', 24);
define ('SIGXFSZ', 25);
define ('SIGVTALRM', 26);
define ('SIGPROF', 27);
define ('SIGWINCH', 28);
define ('SIGPOLL', 29);
define ('SIGIO', 29);
define ('SIGPWR', 30);
define ('SIGSYS', 31);
define ('SIGBABY', 31);
define ('PRIO_PGRP', 1);
define ('PRIO_USER', 2);
define ('PRIO_PROCESS', 0);

// End of pcntl v.
?>
