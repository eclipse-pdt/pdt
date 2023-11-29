<?php

// Start of sockets v.8.3.0

final class Socket  {
}

final class AddressInfo  {
}

/**
 * {@inheritdoc}
 * @param array|null $read
 * @param array|null $write
 * @param array|null $except
 * @param int|null $seconds
 * @param int $microseconds [optional]
 */
function socket_select (?array &$read = null, ?array &$write = null, ?array &$except = null, ?int $seconds = null, int $microseconds = 0): int|false {}

/**
 * {@inheritdoc}
 * @param int $port
 * @param int $backlog [optional]
 */
function socket_create_listen (int $port, int $backlog = 128): Socket|false {}

/**
 * {@inheritdoc}
 * @param Socket $socket
 */
function socket_accept (Socket $socket): Socket|false {}

/**
 * {@inheritdoc}
 * @param Socket $socket
 */
function socket_set_nonblock (Socket $socket): bool {}

/**
 * {@inheritdoc}
 * @param Socket $socket
 */
function socket_set_block (Socket $socket): bool {}

/**
 * {@inheritdoc}
 * @param Socket $socket
 * @param int $backlog [optional]
 */
function socket_listen (Socket $socket, int $backlog = 0): bool {}

/**
 * {@inheritdoc}
 * @param Socket $socket
 */
function socket_close (Socket $socket): void {}

/**
 * {@inheritdoc}
 * @param Socket $socket
 * @param string $data
 * @param int|null $length [optional]
 */
function socket_write (Socket $socket, string $data, ?int $length = NULL): int|false {}

/**
 * {@inheritdoc}
 * @param Socket $socket
 * @param int $length
 * @param int $mode [optional]
 */
function socket_read (Socket $socket, int $length, int $mode = 2): string|false {}

/**
 * {@inheritdoc}
 * @param Socket $socket
 * @param mixed $address
 * @param mixed $port [optional]
 */
function socket_getsockname (Socket $socket, &$address = null, &$port = NULL): bool {}

/**
 * {@inheritdoc}
 * @param Socket $socket
 * @param mixed $address
 * @param mixed $port [optional]
 */
function socket_getpeername (Socket $socket, &$address = null, &$port = NULL): bool {}

/**
 * {@inheritdoc}
 * @param int $domain
 * @param int $type
 * @param int $protocol
 */
function socket_create (int $domain, int $type, int $protocol): Socket|false {}

/**
 * {@inheritdoc}
 * @param Socket $socket
 * @param string $address
 * @param int|null $port [optional]
 */
function socket_connect (Socket $socket, string $address, ?int $port = NULL): bool {}

/**
 * {@inheritdoc}
 * @param int $error_code
 */
function socket_strerror (int $error_code): string {}

/**
 * {@inheritdoc}
 * @param Socket $socket
 * @param string $address
 * @param int $port [optional]
 */
function socket_bind (Socket $socket, string $address, int $port = 0): bool {}

/**
 * {@inheritdoc}
 * @param Socket $socket
 * @param mixed $data
 * @param int $length
 * @param int $flags
 */
function socket_recv (Socket $socket, &$data = null, int $length, int $flags): int|false {}

/**
 * {@inheritdoc}
 * @param Socket $socket
 * @param string $data
 * @param int $length
 * @param int $flags
 */
function socket_send (Socket $socket, string $data, int $length, int $flags): int|false {}

/**
 * {@inheritdoc}
 * @param Socket $socket
 * @param mixed $data
 * @param int $length
 * @param int $flags
 * @param mixed $address
 * @param mixed $port [optional]
 */
function socket_recvfrom (Socket $socket, &$data = null, int $length, int $flags, &$address = null, &$port = NULL): int|false {}

/**
 * {@inheritdoc}
 * @param Socket $socket
 * @param string $data
 * @param int $length
 * @param int $flags
 * @param string $address
 * @param int|null $port [optional]
 */
function socket_sendto (Socket $socket, string $data, int $length, int $flags, string $address, ?int $port = NULL): int|false {}

/**
 * {@inheritdoc}
 * @param Socket $socket
 * @param int $level
 * @param int $option
 */
function socket_get_option (Socket $socket, int $level, int $option): array|int|false {}

/**
 * {@inheritdoc}
 * @param Socket $socket
 * @param int $level
 * @param int $option
 */
function socket_getopt (Socket $socket, int $level, int $option): array|int|false {}

/**
 * {@inheritdoc}
 * @param Socket $socket
 * @param int $level
 * @param int $option
 * @param mixed $value
 */
function socket_set_option (Socket $socket, int $level, int $option, $value = null): bool {}

/**
 * {@inheritdoc}
 * @param Socket $socket
 * @param int $level
 * @param int $option
 * @param mixed $value
 */
function socket_setopt (Socket $socket, int $level, int $option, $value = null): bool {}

/**
 * {@inheritdoc}
 * @param int $domain
 * @param int $type
 * @param int $protocol
 * @param mixed $pair
 */
function socket_create_pair (int $domain, int $type, int $protocol, &$pair = null): bool {}

/**
 * {@inheritdoc}
 * @param Socket $socket
 * @param int $mode [optional]
 */
function socket_shutdown (Socket $socket, int $mode = 2): bool {}

/**
 * {@inheritdoc}
 * @param Socket $socket
 */
function socket_atmark (Socket $socket): bool {}

/**
 * {@inheritdoc}
 * @param Socket|null $socket [optional]
 */
function socket_last_error (?Socket $socket = NULL): int {}

/**
 * {@inheritdoc}
 * @param Socket|null $socket [optional]
 */
function socket_clear_error (?Socket $socket = NULL): void {}

/**
 * {@inheritdoc}
 * @param mixed $stream
 */
function socket_import_stream ($stream = null): Socket|false {}

/**
 * {@inheritdoc}
 * @param Socket $socket
 */
function socket_export_stream (Socket $socket) {}

/**
 * {@inheritdoc}
 * @param Socket $socket
 * @param array $message
 * @param int $flags [optional]
 */
function socket_sendmsg (Socket $socket, array $message, int $flags = 0): int|false {}

/**
 * {@inheritdoc}
 * @param Socket $socket
 * @param array $message
 * @param int $flags [optional]
 */
function socket_recvmsg (Socket $socket, array &$message, int $flags = 0): int|false {}

/**
 * {@inheritdoc}
 * @param int $level
 * @param int $type
 * @param int $num [optional]
 */
function socket_cmsg_space (int $level, int $type, int $num = 0): ?int {}

/**
 * {@inheritdoc}
 * @param string $host
 * @param string|null $service [optional]
 * @param array $hints [optional]
 */
function socket_addrinfo_lookup (string $host, ?string $service = NULL, array $hints = array (
)): array|false {}

/**
 * {@inheritdoc}
 * @param AddressInfo $address
 */
function socket_addrinfo_connect (AddressInfo $address): Socket|false {}

/**
 * {@inheritdoc}
 * @param AddressInfo $address
 */
function socket_addrinfo_bind (AddressInfo $address): Socket|false {}

/**
 * {@inheritdoc}
 * @param AddressInfo $address
 */
function socket_addrinfo_explain (AddressInfo $address): array {}

define ('AF_UNIX', 1);
define ('AF_INET', 2);
define ('AF_INET6', 30);
define ('SOCK_STREAM', 1);
define ('SOCK_DGRAM', 2);
define ('SOCK_RAW', 3);
define ('SOCK_SEQPACKET', 5);
define ('SOCK_RDM', 4);
define ('MSG_OOB', 1);
define ('MSG_WAITALL', 64);
define ('MSG_CTRUNC', 32);
define ('MSG_TRUNC', 16);
define ('MSG_PEEK', 2);
define ('MSG_DONTROUTE', 4);
define ('MSG_EOR', 8);
define ('MSG_EOF', 256);
define ('MSG_NOSIGNAL', 524288);
define ('MSG_DONTWAIT', 128);
define ('SO_DEBUG', 1);
define ('SO_REUSEADDR', 4);
define ('SO_REUSEPORT', 512);
define ('SO_KEEPALIVE', 8);
define ('SO_DONTROUTE', 16);
define ('SO_LINGER', 128);
define ('SO_BROADCAST', 32);
define ('SO_OOBINLINE', 256);
define ('SO_SNDBUF', 4097);
define ('SO_RCVBUF', 4098);
define ('SO_SNDLOWAT', 4099);
define ('SO_RCVLOWAT', 4100);
define ('SO_SNDTIMEO', 4101);
define ('SO_RCVTIMEO', 4102);
define ('SO_TYPE', 4104);
define ('SO_ERROR', 4103);
define ('SO_DONTTRUNC', 8192);
define ('SO_WANTMORE', 16384);
define ('SOL_SOCKET', 65535);
define ('SOMAXCONN', 128);
define ('TCP_NODELAY', 1);
define ('TCP_NOTSENT_LOWAT', 513);
define ('TCP_KEEPALIVE', 16);
define ('PHP_NORMAL_READ', 1);
define ('PHP_BINARY_READ', 2);
define ('MCAST_JOIN_GROUP', 12);
define ('MCAST_LEAVE_GROUP', 13);
define ('IP_MULTICAST_IF', 9);
define ('IP_MULTICAST_TTL', 10);
define ('IP_MULTICAST_LOOP', 11);
define ('IPV6_MULTICAST_IF', 9);
define ('IPV6_MULTICAST_HOPS', 10);
define ('IPV6_MULTICAST_LOOP', 11);
define ('IPV6_V6ONLY', 27);
define ('SOCKET_EPERM', 1);
define ('SOCKET_ENOENT', 2);
define ('SOCKET_EINTR', 4);
define ('SOCKET_EIO', 5);
define ('SOCKET_ENXIO', 6);
define ('SOCKET_E2BIG', 7);
define ('SOCKET_EBADF', 9);
define ('SOCKET_EAGAIN', 35);
define ('SOCKET_ENOMEM', 12);
define ('SOCKET_EACCES', 13);
define ('SOCKET_EFAULT', 14);
define ('SOCKET_ENOTBLK', 15);
define ('SOCKET_EBUSY', 16);
define ('SOCKET_EEXIST', 17);
define ('SOCKET_EXDEV', 18);
define ('SOCKET_ENODEV', 19);
define ('SOCKET_ENOTDIR', 20);
define ('SOCKET_EISDIR', 21);
define ('SOCKET_EINVAL', 22);
define ('SOCKET_ENFILE', 23);
define ('SOCKET_EMFILE', 24);
define ('SOCKET_ENOTTY', 25);
define ('SOCKET_ENOSPC', 28);
define ('SOCKET_ESPIPE', 29);
define ('SOCKET_EROFS', 30);
define ('SOCKET_EMLINK', 31);
define ('SOCKET_EPIPE', 32);
define ('SOCKET_ENAMETOOLONG', 63);
define ('SOCKET_ENOLCK', 77);
define ('SOCKET_ENOSYS', 78);
define ('SOCKET_ENOTEMPTY', 66);
define ('SOCKET_ELOOP', 62);
define ('SOCKET_EWOULDBLOCK', 35);
define ('SOCKET_ENOMSG', 91);
define ('SOCKET_EIDRM', 90);
define ('SOCKET_ENOSTR', 99);
define ('SOCKET_ENODATA', 96);
define ('SOCKET_ETIME', 101);
define ('SOCKET_ENOSR', 98);
define ('SOCKET_EREMOTE', 71);
define ('SOCKET_ENOLINK', 97);
define ('SOCKET_EPROTO', 100);
define ('SOCKET_EMULTIHOP', 95);
define ('SOCKET_EBADMSG', 94);
define ('SOCKET_EUSERS', 68);
define ('SOCKET_ENOTSOCK', 38);
define ('SOCKET_EDESTADDRREQ', 39);
define ('SOCKET_EMSGSIZE', 40);
define ('SOCKET_EPROTOTYPE', 41);
define ('SOCKET_ENOPROTOOPT', 42);
define ('SOCKET_EPROTONOSUPPORT', 43);
define ('SOCKET_ESOCKTNOSUPPORT', 44);
define ('SOCKET_EOPNOTSUPP', 102);
define ('SOCKET_EPFNOSUPPORT', 46);
define ('SOCKET_EAFNOSUPPORT', 47);
define ('SOCKET_EADDRINUSE', 48);
define ('SOCKET_EADDRNOTAVAIL', 49);
define ('SOCKET_ENETDOWN', 50);
define ('SOCKET_ENETUNREACH', 51);
define ('SOCKET_ENETRESET', 52);
define ('SOCKET_ECONNABORTED', 53);
define ('SOCKET_ECONNRESET', 54);
define ('SOCKET_ENOBUFS', 55);
define ('SOCKET_EISCONN', 56);
define ('SOCKET_ENOTCONN', 57);
define ('SOCKET_ESHUTDOWN', 58);
define ('SOCKET_ETOOMANYREFS', 59);
define ('SOCKET_ETIMEDOUT', 60);
define ('SOCKET_ECONNREFUSED', 61);
define ('SOCKET_EHOSTDOWN', 64);
define ('SOCKET_EHOSTUNREACH', 65);
define ('SOCKET_EALREADY', 37);
define ('SOCKET_EINPROGRESS', 36);
define ('SOCKET_EDQUOT', 69);
define ('IPPROTO_IP', 0);
define ('IPPROTO_IPV6', 41);
define ('SOL_TCP', 6);
define ('SOL_UDP', 17);
define ('IPV6_UNICAST_HOPS', 4);
define ('AI_PASSIVE', 1);
define ('AI_CANONNAME', 2);
define ('AI_NUMERICHOST', 4);
define ('AI_V4MAPPED', 2048);
define ('AI_ALL', 256);
define ('AI_ADDRCONFIG', 1024);
define ('AI_NUMERICSERV', 4096);
define ('SOL_LOCAL', 0);
define ('IPV6_RECVPKTINFO', 61);
define ('IPV6_PKTINFO', 46);
define ('IPV6_RECVHOPLIMIT', 37);
define ('IPV6_HOPLIMIT', 47);
define ('IPV6_RECVTCLASS', 35);
define ('IPV6_TCLASS', 36);
define ('SCM_RIGHTS', 1);
define ('IP_DONTFRAG', 28);

// End of sockets v.8.3.0
