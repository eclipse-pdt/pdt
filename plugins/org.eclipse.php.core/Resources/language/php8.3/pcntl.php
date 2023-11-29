<?php

// Start of pcntl v.8.3.0

/**
 * {@inheritdoc}
 */
function pcntl_fork (): int {}

/**
 * {@inheritdoc}
 * @param int $process_id
 * @param mixed $status
 * @param int $flags [optional]
 * @param mixed $resource_usage [optional]
 */
function pcntl_waitpid (int $process_id, &$status = null, int $flags = 0, &$resource_usage = array (
)): int {}

/**
 * {@inheritdoc}
 * @param mixed $status
 * @param int $flags [optional]
 * @param mixed $resource_usage [optional]
 */
function pcntl_wait (&$status = null, int $flags = 0, &$resource_usage = array (
)): int {}

/**
 * {@inheritdoc}
 * @param int $signal
 * @param mixed $handler
 * @param bool $restart_syscalls [optional]
 */
function pcntl_signal (int $signal, $handler = null, bool $restart_syscalls = true): bool {}

/**
 * {@inheritdoc}
 * @param int $signal
 */
function pcntl_signal_get_handler (int $signal) {}

/**
 * {@inheritdoc}
 */
function pcntl_signal_dispatch (): bool {}

/**
 * {@inheritdoc}
 * @param int $mode
 * @param array $signals
 * @param mixed $old_signals [optional]
 */
function pcntl_sigprocmask (int $mode, array $signals, &$old_signals = NULL): bool {}

/**
 * {@inheritdoc}
 * @param int $status
 */
function pcntl_wifexited (int $status): bool {}

/**
 * {@inheritdoc}
 * @param int $status
 */
function pcntl_wifstopped (int $status): bool {}

/**
 * {@inheritdoc}
 * @param int $status
 */
function pcntl_wifcontinued (int $status): bool {}

/**
 * {@inheritdoc}
 * @param int $status
 */
function pcntl_wifsignaled (int $status): bool {}

/**
 * {@inheritdoc}
 * @param int $status
 */
function pcntl_wexitstatus (int $status): int|false {}

/**
 * {@inheritdoc}
 * @param int $status
 */
function pcntl_wtermsig (int $status): int|false {}

/**
 * {@inheritdoc}
 * @param int $status
 */
function pcntl_wstopsig (int $status): int|false {}

/**
 * {@inheritdoc}
 * @param string $path
 * @param array $args [optional]
 * @param array $env_vars [optional]
 */
function pcntl_exec (string $path, array $args = array (
), array $env_vars = array (
)): bool {}

/**
 * {@inheritdoc}
 * @param int $seconds
 */
function pcntl_alarm (int $seconds): int {}

/**
 * {@inheritdoc}
 */
function pcntl_get_last_error (): int {}

/**
 * {@inheritdoc}
 */
function pcntl_errno (): int {}

/**
 * {@inheritdoc}
 * @param int|null $process_id [optional]
 * @param int $mode [optional]
 */
function pcntl_getpriority (?int $process_id = NULL, int $mode = 0): int|false {}

/**
 * {@inheritdoc}
 * @param int $priority
 * @param int|null $process_id [optional]
 * @param int $mode [optional]
 */
function pcntl_setpriority (int $priority, ?int $process_id = NULL, int $mode = 0): bool {}

/**
 * {@inheritdoc}
 * @param int $error_code
 */
function pcntl_strerror (int $error_code): string {}

/**
 * {@inheritdoc}
 * @param bool|null $enable [optional]
 */
function pcntl_async_signals (?bool $enable = NULL): bool {}

define ('WNOHANG', 1);
define ('WUNTRACED', 2);
define ('WCONTINUED', 16);
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
define ('SIGBUS', 10);
define ('SIGFPE', 8);
define ('SIGKILL', 9);
define ('SIGUSR1', 30);
define ('SIGSEGV', 11);
define ('SIGUSR2', 31);
define ('SIGPIPE', 13);
define ('SIGALRM', 14);
define ('SIGTERM', 15);
define ('SIGCHLD', 20);
define ('SIGCONT', 19);
define ('SIGSTOP', 17);
define ('SIGTSTP', 18);
define ('SIGTTIN', 21);
define ('SIGTTOU', 22);
define ('SIGURG', 16);
define ('SIGXCPU', 24);
define ('SIGXFSZ', 25);
define ('SIGVTALRM', 26);
define ('SIGPROF', 27);
define ('SIGWINCH', 28);
define ('SIGIO', 23);
define ('SIGINFO', 29);
define ('SIGSYS', 12);
define ('SIGBABY', 12);
define ('PRIO_PGRP', 1);
define ('PRIO_USER', 2);
define ('PRIO_PROCESS', 0);
define ('PRIO_DARWIN_BG', 4096);
define ('PRIO_DARWIN_THREAD', 3);
define ('SIG_BLOCK', 1);
define ('SIG_UNBLOCK', 2);
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

// End of pcntl v.8.3.0
