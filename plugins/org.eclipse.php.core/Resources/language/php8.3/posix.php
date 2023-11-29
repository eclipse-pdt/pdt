<?php

// Start of posix v.8.3.0

/**
 * {@inheritdoc}
 * @param int $process_id
 * @param int $signal
 */
function posix_kill (int $process_id, int $signal): bool {}

/**
 * {@inheritdoc}
 */
function posix_getpid (): int {}

/**
 * {@inheritdoc}
 */
function posix_getppid (): int {}

/**
 * {@inheritdoc}
 */
function posix_getuid (): int {}

/**
 * {@inheritdoc}
 * @param int $user_id
 */
function posix_setuid (int $user_id): bool {}

/**
 * {@inheritdoc}
 */
function posix_geteuid (): int {}

/**
 * {@inheritdoc}
 * @param int $user_id
 */
function posix_seteuid (int $user_id): bool {}

/**
 * {@inheritdoc}
 */
function posix_getgid (): int {}

/**
 * {@inheritdoc}
 * @param int $group_id
 */
function posix_setgid (int $group_id): bool {}

/**
 * {@inheritdoc}
 */
function posix_getegid (): int {}

/**
 * {@inheritdoc}
 * @param int $group_id
 */
function posix_setegid (int $group_id): bool {}

/**
 * {@inheritdoc}
 */
function posix_getgroups (): array|false {}

/**
 * {@inheritdoc}
 */
function posix_getlogin (): string|false {}

/**
 * {@inheritdoc}
 */
function posix_getpgrp (): int {}

/**
 * {@inheritdoc}
 */
function posix_setsid (): int {}

/**
 * {@inheritdoc}
 * @param int $process_id
 * @param int $process_group_id
 */
function posix_setpgid (int $process_id, int $process_group_id): bool {}

/**
 * {@inheritdoc}
 * @param int $process_id
 */
function posix_getpgid (int $process_id): int|false {}

/**
 * {@inheritdoc}
 * @param int $process_id
 */
function posix_getsid (int $process_id): int|false {}

/**
 * {@inheritdoc}
 */
function posix_uname (): array|false {}

/**
 * {@inheritdoc}
 */
function posix_times (): array|false {}

/**
 * {@inheritdoc}
 */
function posix_ctermid (): string|false {}

/**
 * {@inheritdoc}
 * @param mixed $file_descriptor
 */
function posix_ttyname ($file_descriptor = null): string|false {}

/**
 * {@inheritdoc}
 * @param mixed $file_descriptor
 */
function posix_isatty ($file_descriptor = null): bool {}

/**
 * {@inheritdoc}
 */
function posix_getcwd (): string|false {}

/**
 * {@inheritdoc}
 * @param string $filename
 * @param int $permissions
 */
function posix_mkfifo (string $filename, int $permissions): bool {}

/**
 * {@inheritdoc}
 * @param string $filename
 * @param int $flags
 * @param int $major [optional]
 * @param int $minor [optional]
 */
function posix_mknod (string $filename, int $flags, int $major = 0, int $minor = 0): bool {}

/**
 * {@inheritdoc}
 * @param string $filename
 * @param int $flags [optional]
 */
function posix_access (string $filename, int $flags = 0): bool {}

/**
 * {@inheritdoc}
 * @param string $name
 */
function posix_getgrnam (string $name): array|false {}

/**
 * {@inheritdoc}
 * @param int $group_id
 */
function posix_getgrgid (int $group_id): array|false {}

/**
 * {@inheritdoc}
 * @param string $username
 */
function posix_getpwnam (string $username): array|false {}

/**
 * {@inheritdoc}
 * @param int $user_id
 */
function posix_getpwuid (int $user_id): array|false {}

/**
 * {@inheritdoc}
 * @param int|null $resource [optional]
 */
function posix_getrlimit (?int $resource = NULL): array|false {}

/**
 * {@inheritdoc}
 * @param int $resource
 * @param int $soft_limit
 * @param int $hard_limit
 */
function posix_setrlimit (int $resource, int $soft_limit, int $hard_limit): bool {}

/**
 * {@inheritdoc}
 */
function posix_get_last_error (): int {}

/**
 * {@inheritdoc}
 */
function posix_errno (): int {}

/**
 * {@inheritdoc}
 * @param int $error_code
 */
function posix_strerror (int $error_code): string {}

/**
 * {@inheritdoc}
 * @param string $username
 * @param int $group_id
 */
function posix_initgroups (string $username, int $group_id): bool {}

/**
 * {@inheritdoc}
 * @param int $conf_id
 */
function posix_sysconf (int $conf_id): int {}

/**
 * {@inheritdoc}
 * @param string $path
 * @param int $name
 */
function posix_pathconf (string $path, int $name): int|false {}

/**
 * {@inheritdoc}
 * @param mixed $file_descriptor
 * @param int $name
 */
function posix_fpathconf ($file_descriptor = null, int $name): int|false {}

define ('POSIX_F_OK', 0);
define ('POSIX_X_OK', 1);
define ('POSIX_W_OK', 2);
define ('POSIX_R_OK', 4);
define ('POSIX_S_IFREG', 32768);
define ('POSIX_S_IFCHR', 8192);
define ('POSIX_S_IFBLK', 24576);
define ('POSIX_S_IFIFO', 4096);
define ('POSIX_S_IFSOCK', 49152);
define ('POSIX_RLIMIT_AS', 5);
define ('POSIX_RLIMIT_CORE', 4);
define ('POSIX_RLIMIT_CPU', 0);
define ('POSIX_RLIMIT_DATA', 2);
define ('POSIX_RLIMIT_FSIZE', 1);
define ('POSIX_RLIMIT_MEMLOCK', 6);
define ('POSIX_RLIMIT_NOFILE', 8);
define ('POSIX_RLIMIT_NPROC', 7);
define ('POSIX_RLIMIT_RSS', 5);
define ('POSIX_RLIMIT_STACK', 3);
define ('POSIX_RLIMIT_INFINITY', 9223372036854775807);
define ('POSIX_SC_ARG_MAX', 1);
define ('POSIX_SC_PAGESIZE', 29);
define ('POSIX_SC_NPROCESSORS_CONF', 57);
define ('POSIX_SC_NPROCESSORS_ONLN', 58);
define ('POSIX_PC_LINK_MAX', 1);
define ('POSIX_PC_MAX_CANON', 2);
define ('POSIX_PC_MAX_INPUT', 3);
define ('POSIX_PC_NAME_MAX', 4);
define ('POSIX_PC_PATH_MAX', 5);
define ('POSIX_PC_PIPE_BUF', 6);
define ('POSIX_PC_CHOWN_RESTRICTED', 7);
define ('POSIX_PC_NO_TRUNC', 8);
define ('POSIX_PC_ALLOC_SIZE_MIN', 16);
define ('POSIX_PC_SYMLINK_MAX', 24);

// End of posix v.8.3.0
