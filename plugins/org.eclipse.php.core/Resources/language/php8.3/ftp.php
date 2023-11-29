<?php

// Start of ftp v.8.3.0

namespace FTP {

final class Connection  {
}


}


namespace {

/**
 * {@inheritdoc}
 * @param string $hostname
 * @param int $port [optional]
 * @param int $timeout [optional]
 */
function ftp_connect (string $hostname, int $port = 21, int $timeout = 90): FTP\Connection|false {}

/**
 * {@inheritdoc}
 * @param string $hostname
 * @param int $port [optional]
 * @param int $timeout [optional]
 */
function ftp_ssl_connect (string $hostname, int $port = 21, int $timeout = 90): FTP\Connection|false {}

/**
 * {@inheritdoc}
 * @param FTP\Connection $ftp
 * @param string $username
 * @param string $password
 */
function ftp_login (FTP\Connection $ftp, string $username, string $password): bool {}

/**
 * {@inheritdoc}
 * @param FTP\Connection $ftp
 */
function ftp_pwd (FTP\Connection $ftp): string|false {}

/**
 * {@inheritdoc}
 * @param FTP\Connection $ftp
 */
function ftp_cdup (FTP\Connection $ftp): bool {}

/**
 * {@inheritdoc}
 * @param FTP\Connection $ftp
 * @param string $directory
 */
function ftp_chdir (FTP\Connection $ftp, string $directory): bool {}

/**
 * {@inheritdoc}
 * @param FTP\Connection $ftp
 * @param string $command
 */
function ftp_exec (FTP\Connection $ftp, string $command): bool {}

/**
 * {@inheritdoc}
 * @param FTP\Connection $ftp
 * @param string $command
 */
function ftp_raw (FTP\Connection $ftp, string $command): ?array {}

/**
 * {@inheritdoc}
 * @param FTP\Connection $ftp
 * @param string $directory
 */
function ftp_mkdir (FTP\Connection $ftp, string $directory): string|false {}

/**
 * {@inheritdoc}
 * @param FTP\Connection $ftp
 * @param string $directory
 */
function ftp_rmdir (FTP\Connection $ftp, string $directory): bool {}

/**
 * {@inheritdoc}
 * @param FTP\Connection $ftp
 * @param int $permissions
 * @param string $filename
 */
function ftp_chmod (FTP\Connection $ftp, int $permissions, string $filename): int|false {}

/**
 * {@inheritdoc}
 * @param FTP\Connection $ftp
 * @param int $size
 * @param mixed $response [optional]
 */
function ftp_alloc (FTP\Connection $ftp, int $size, &$response = NULL): bool {}

/**
 * {@inheritdoc}
 * @param FTP\Connection $ftp
 * @param string $directory
 */
function ftp_nlist (FTP\Connection $ftp, string $directory): array|false {}

/**
 * {@inheritdoc}
 * @param FTP\Connection $ftp
 * @param string $directory
 * @param bool $recursive [optional]
 */
function ftp_rawlist (FTP\Connection $ftp, string $directory, bool $recursive = false): array|false {}

/**
 * {@inheritdoc}
 * @param FTP\Connection $ftp
 * @param string $directory
 */
function ftp_mlsd (FTP\Connection $ftp, string $directory): array|false {}

/**
 * {@inheritdoc}
 * @param FTP\Connection $ftp
 */
function ftp_systype (FTP\Connection $ftp): string|false {}

/**
 * {@inheritdoc}
 * @param FTP\Connection $ftp
 * @param mixed $stream
 * @param string $remote_filename
 * @param int $mode [optional]
 * @param int $offset [optional]
 */
function ftp_fget (FTP\Connection $ftp, $stream = null, string $remote_filename, int $mode = 2, int $offset = 0): bool {}

/**
 * {@inheritdoc}
 * @param FTP\Connection $ftp
 * @param mixed $stream
 * @param string $remote_filename
 * @param int $mode [optional]
 * @param int $offset [optional]
 */
function ftp_nb_fget (FTP\Connection $ftp, $stream = null, string $remote_filename, int $mode = 2, int $offset = 0): int {}

/**
 * {@inheritdoc}
 * @param FTP\Connection $ftp
 * @param bool $enable
 */
function ftp_pasv (FTP\Connection $ftp, bool $enable): bool {}

/**
 * {@inheritdoc}
 * @param FTP\Connection $ftp
 * @param string $local_filename
 * @param string $remote_filename
 * @param int $mode [optional]
 * @param int $offset [optional]
 */
function ftp_get (FTP\Connection $ftp, string $local_filename, string $remote_filename, int $mode = 2, int $offset = 0): bool {}

/**
 * {@inheritdoc}
 * @param FTP\Connection $ftp
 * @param string $local_filename
 * @param string $remote_filename
 * @param int $mode [optional]
 * @param int $offset [optional]
 */
function ftp_nb_get (FTP\Connection $ftp, string $local_filename, string $remote_filename, int $mode = 2, int $offset = 0): int|false {}

/**
 * {@inheritdoc}
 * @param FTP\Connection $ftp
 */
function ftp_nb_continue (FTP\Connection $ftp): int {}

/**
 * {@inheritdoc}
 * @param FTP\Connection $ftp
 * @param string $remote_filename
 * @param mixed $stream
 * @param int $mode [optional]
 * @param int $offset [optional]
 */
function ftp_fput (FTP\Connection $ftp, string $remote_filename, $stream = null, int $mode = 2, int $offset = 0): bool {}

/**
 * {@inheritdoc}
 * @param FTP\Connection $ftp
 * @param string $remote_filename
 * @param mixed $stream
 * @param int $mode [optional]
 * @param int $offset [optional]
 */
function ftp_nb_fput (FTP\Connection $ftp, string $remote_filename, $stream = null, int $mode = 2, int $offset = 0): int {}

/**
 * {@inheritdoc}
 * @param FTP\Connection $ftp
 * @param string $remote_filename
 * @param string $local_filename
 * @param int $mode [optional]
 * @param int $offset [optional]
 */
function ftp_put (FTP\Connection $ftp, string $remote_filename, string $local_filename, int $mode = 2, int $offset = 0): bool {}

/**
 * {@inheritdoc}
 * @param FTP\Connection $ftp
 * @param string $remote_filename
 * @param string $local_filename
 * @param int $mode [optional]
 */
function ftp_append (FTP\Connection $ftp, string $remote_filename, string $local_filename, int $mode = 2): bool {}

/**
 * {@inheritdoc}
 * @param FTP\Connection $ftp
 * @param string $remote_filename
 * @param string $local_filename
 * @param int $mode [optional]
 * @param int $offset [optional]
 */
function ftp_nb_put (FTP\Connection $ftp, string $remote_filename, string $local_filename, int $mode = 2, int $offset = 0): int|false {}

/**
 * {@inheritdoc}
 * @param FTP\Connection $ftp
 * @param string $filename
 */
function ftp_size (FTP\Connection $ftp, string $filename): int {}

/**
 * {@inheritdoc}
 * @param FTP\Connection $ftp
 * @param string $filename
 */
function ftp_mdtm (FTP\Connection $ftp, string $filename): int {}

/**
 * {@inheritdoc}
 * @param FTP\Connection $ftp
 * @param string $from
 * @param string $to
 */
function ftp_rename (FTP\Connection $ftp, string $from, string $to): bool {}

/**
 * {@inheritdoc}
 * @param FTP\Connection $ftp
 * @param string $filename
 */
function ftp_delete (FTP\Connection $ftp, string $filename): bool {}

/**
 * {@inheritdoc}
 * @param FTP\Connection $ftp
 * @param string $command
 */
function ftp_site (FTP\Connection $ftp, string $command): bool {}

/**
 * {@inheritdoc}
 * @param FTP\Connection $ftp
 */
function ftp_close (FTP\Connection $ftp): bool {}

/**
 * {@inheritdoc}
 * @param FTP\Connection $ftp
 */
function ftp_quit (FTP\Connection $ftp): bool {}

/**
 * {@inheritdoc}
 * @param FTP\Connection $ftp
 * @param int $option
 * @param mixed $value
 */
function ftp_set_option (FTP\Connection $ftp, int $option, $value = null): bool {}

/**
 * {@inheritdoc}
 * @param FTP\Connection $ftp
 * @param int $option
 */
function ftp_get_option (FTP\Connection $ftp, int $option): int|bool {}

define ('FTP_ASCII', 1);
define ('FTP_TEXT', 1);
define ('FTP_BINARY', 2);
define ('FTP_IMAGE', 2);
define ('FTP_AUTORESUME', -1);
define ('FTP_TIMEOUT_SEC', 0);
define ('FTP_AUTOSEEK', 1);
define ('FTP_USEPASVADDRESS', 2);
define ('FTP_FAILED', 0);
define ('FTP_FINISHED', 1);
define ('FTP_MOREDATA', 2);


}

// End of ftp v.8.3.0
