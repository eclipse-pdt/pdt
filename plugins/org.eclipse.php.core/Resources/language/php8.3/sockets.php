<?php

// Start of sockets v.8.2.6

/**
 * A fully opaque class which replaces Socket resources as of PHP 8.0.0.
 * @link http://www.php.net/manual/en/class.socket.php
 */
final class Socket  {
}

/**
 * A fully opaque class which replaces AddressInfo resources as of PHP 8.0.0.
 * @link http://www.php.net/manual/en/class.addressinfo.php
 */
final class AddressInfo  {
}

/**
 * Runs the select() system call on the given arrays of sockets with a specified timeout
 * @link http://www.php.net/manual/en/function.socket-select.php
 * @param array|null $read 
 * @param array|null $write 
 * @param array|null $except 
 * @param int|null $seconds 
 * @param int $microseconds [optional] 
 * @return int|false On success socket_select returns the number of
 * sockets contained in the modified arrays, which may be zero if
 * the timeout expires before anything interesting happens.On error false
 * is returned. The error code can be retrieved with
 * socket_last_error.
 * <p>Be sure to use the === operator when checking for an
 * error. Since the socket_select may return 0 the
 * comparison with == would evaluate to true:
 * Understanding socket_select's result
 * <pre>
 * <code>&lt;?php
 * $e = NULL;
 * if (false === socket_select($r, $w, $e, 0)) {
 * echo &quot;socket_select() failed, reason: &quot; .
 * socket_strerror(socket_last_error()) . &quot;\n&quot;;
 * }
 * ?&gt;</code>
 * </pre></p>
 */
function socket_select (?array &$read, ?array &$write, ?array &$except, ?int $seconds, int $microseconds = null): int|false {}

/**
 * Opens a socket on port to accept connections
 * @link http://www.php.net/manual/en/function.socket-create-listen.php
 * @param int $port 
 * @param int $backlog [optional] 
 * @return Socket|false socket_create_listen returns a new Socket instance
 * on success or false on error. The error code can be retrieved with
 * socket_last_error. This code may be passed to
 * socket_strerror to get a textual explanation of the
 * error.
 */
function socket_create_listen (int $port, int $backlog = 128): Socket|false {}

/**
 * Accepts a connection on a socket
 * @link http://www.php.net/manual/en/function.socket-accept.php
 * @param Socket $socket 
 * @return Socket|false Returns a new Socket instance on success, or false on error. The actual
 * error code can be retrieved by calling
 * socket_last_error. This error code may be passed to
 * socket_strerror to get a textual explanation of the
 * error.
 */
function socket_accept (Socket $socket): Socket|false {}

/**
 * Sets nonblocking mode for file descriptor fd
 * @link http://www.php.net/manual/en/function.socket-set-nonblock.php
 * @param Socket $socket 
 * @return bool Returns true on success or false on failure.
 */
function socket_set_nonblock (Socket $socket): bool {}

/**
 * Sets blocking mode on a socket
 * @link http://www.php.net/manual/en/function.socket-set-block.php
 * @param Socket $socket 
 * @return bool Returns true on success or false on failure.
 */
function socket_set_block (Socket $socket): bool {}

/**
 * Listens for a connection on a socket
 * @link http://www.php.net/manual/en/function.socket-listen.php
 * @param Socket $socket 
 * @param int $backlog [optional] 
 * @return bool Returns true on success or false on failure. The error code can be retrieved with
 * socket_last_error. This code may be passed to
 * socket_strerror to get a textual explanation of the
 * error.
 */
function socket_listen (Socket $socket, int $backlog = null): bool {}

/**
 * Closes a Socket instance
 * @link http://www.php.net/manual/en/function.socket-close.php
 * @param Socket $socket 
 * @return void No value is returned.
 */
function socket_close (Socket $socket): void {}

/**
 * Write to a socket
 * @link http://www.php.net/manual/en/function.socket-write.php
 * @param Socket $socket 
 * @param string $data 
 * @param int|null $length [optional] 
 * @return int|false Returns the number of bytes successfully written to the socket or false on failure.
 * The error code can be retrieved with
 * socket_last_error. This code may be passed to
 * socket_strerror to get a textual explanation of the
 * error.
 * <p>It is perfectly valid for socket_write to
 * return zero which means no bytes have been written. Be sure to use the
 * === operator to check for false in case of an
 * error.</p>
 */
function socket_write (Socket $socket, string $data, ?int $length = null): int|false {}

/**
 * Reads a maximum of length bytes from a socket
 * @link http://www.php.net/manual/en/function.socket-read.php
 * @param Socket $socket 
 * @param int $length 
 * @param int $mode [optional] 
 * @return string|false socket_read returns the data as a string on success,
 * or false on error (including if the remote host has closed the
 * connection). The error code can be retrieved with
 * socket_last_error. This code may be passed to
 * socket_strerror to get a textual representation of
 * the error.
 * <p>socket_read returns a zero length string ("")
 * when there is no more data to read.</p>
 */
function socket_read (Socket $socket, int $length, int $mode = PHP_BINARY_READ): string|false {}

/**
 * Queries the local side of the given socket which may either result in host/port or in a Unix filesystem path, dependent on its type
 * @link http://www.php.net/manual/en/function.socket-getsockname.php
 * @param Socket $socket 
 * @param string $address 
 * @param int $port [optional] 
 * @return bool Returns true on success or false on failure. socket_getsockname may also return
 * false if the socket type is not any of AF_INET,
 * AF_INET6, or AF_UNIX, in which
 * case the last socket error code is not updated.
 */
function socket_getsockname (Socket $socket, string &$address, int &$port = null): bool {}

/**
 * Queries the remote side of the given socket which may either result in host/port or in a Unix filesystem path, dependent on its type
 * @link http://www.php.net/manual/en/function.socket-getpeername.php
 * @param Socket $socket 
 * @param string $address 
 * @param int $port [optional] 
 * @return bool Returns true on success or false on failure. socket_getpeername may also return
 * false if the socket type is not any of AF_INET,
 * AF_INET6, or AF_UNIX, in which
 * case the last socket error code is not updated.
 */
function socket_getpeername (Socket $socket, string &$address, int &$port = null): bool {}

/**
 * Create a socket (endpoint for communication)
 * @link http://www.php.net/manual/en/function.socket-create.php
 * @param int $domain 
 * @param int $type 
 * @param int $protocol 
 * @return Socket|false socket_create returns a Socket instance on success,
 * or false on error. The actual error code can be retrieved by calling
 * socket_last_error. This error code may be passed to
 * socket_strerror to get a textual explanation of the
 * error.
 */
function socket_create (int $domain, int $type, int $protocol): Socket|false {}

/**
 * Initiates a connection on a socket
 * @link http://www.php.net/manual/en/function.socket-connect.php
 * @param Socket $socket 
 * @param string $address 
 * @param int|null $port [optional] 
 * @return bool Returns true on success or false on failure. The error code can be retrieved with
 * socket_last_error. This code may be passed to
 * socket_strerror to get a textual explanation of the
 * error.
 * <p>If the socket is non-blocking then this function returns false with an
 * error Operation now in progress.</p>
 */
function socket_connect (Socket $socket, string $address, ?int $port = null): bool {}

/**
 * Return a string describing a socket error
 * @link http://www.php.net/manual/en/function.socket-strerror.php
 * @param int $error_code 
 * @return string Returns the error message associated with the error_code
 * parameter.
 */
function socket_strerror (int $error_code): string {}

/**
 * Binds a name to a socket
 * @link http://www.php.net/manual/en/function.socket-bind.php
 * @param Socket $socket 
 * @param string $address 
 * @param int $port [optional] 
 * @return bool Returns true on success or false on failure.
 * <p>The error code can be retrieved with socket_last_error.
 * This code may be passed to socket_strerror to get a
 * textual explanation of the error.</p>
 */
function socket_bind (Socket $socket, string $address, int $port = null): bool {}

/**
 * Receives data from a connected socket
 * @link http://www.php.net/manual/en/function.socket-recv.php
 * @param Socket $socket 
 * @param string|null $data 
 * @param int $length 
 * @param int $flags 
 * @return int|false socket_recv returns the number of bytes received,
 * or false if there was an error. The actual error code can be retrieved by 
 * calling socket_last_error. This error code may be
 * passed to socket_strerror to get a textual explanation
 * of the error.
 */
function socket_recv (Socket $socket, ?string &$data, int $length, int $flags): int|false {}

/**
 * Sends data to a connected socket
 * @link http://www.php.net/manual/en/function.socket-send.php
 * @param Socket $socket 
 * @param string $data 
 * @param int $length 
 * @param int $flags 
 * @return int|false socket_send returns the number of bytes sent, or false on error.
 */
function socket_send (Socket $socket, string $data, int $length, int $flags): int|false {}

/**
 * Receives data from a socket whether or not it is connection-oriented
 * @link http://www.php.net/manual/en/function.socket-recvfrom.php
 * @param Socket $socket 
 * @param string $data 
 * @param int $length 
 * @param int $flags 
 * @param string $address 
 * @param int $port [optional] 
 * @return int|false socket_recvfrom returns the number of bytes received,
 * or false if there was an error. The actual error code can be retrieved by 
 * calling socket_last_error. This error code may be
 * passed to socket_strerror to get a textual explanation
 * of the error.
 */
function socket_recvfrom (Socket $socket, string &$data, int $length, int $flags, string &$address, int &$port = null): int|false {}

/**
 * Sends a message to a socket, whether it is connected or not
 * @link http://www.php.net/manual/en/function.socket-sendto.php
 * @param Socket $socket 
 * @param string $data 
 * @param int $length 
 * @param int $flags 
 * @param string $address 
 * @param int|null $port [optional] 
 * @return int|false socket_sendto returns the number of bytes sent to the
 * remote host, or false if an error occurred.
 */
function socket_sendto (Socket $socket, string $data, int $length, int $flags, string $address, ?int $port = null): int|false {}

/**
 * Gets socket options for the socket
 * @link http://www.php.net/manual/en/function.socket-get-option.php
 * @param Socket $socket 
 * @param int $level 
 * @param int $option 
 * @return array|int|false Returns the value of the given option, or false on failure.
 */
function socket_get_option (Socket $socket, int $level, int $option): array|int|false {}

/**
 * Alias of socket_get_option
 * @link http://www.php.net/manual/en/function.socket-getopt.php
 * @param Socket $socket 
 * @param int $level 
 * @param int $option 
 * @return array|int|false Returns the value of the given option, or false on failure.
 */
function socket_getopt (Socket $socket, int $level, int $option): array|int|false {}

/**
 * Sets socket options for the socket
 * @link http://www.php.net/manual/en/function.socket-set-option.php
 * @param Socket $socket 
 * @param int $level 
 * @param int $option 
 * @param array|string|int $value 
 * @return bool Returns true on success or false on failure.
 */
function socket_set_option (Socket $socket, int $level, int $option, array|string|int $value): bool {}

/**
 * Alias of socket_set_option
 * @link http://www.php.net/manual/en/function.socket-setopt.php
 * @param Socket $socket 
 * @param int $level 
 * @param int $option 
 * @param array|string|int $value 
 * @return bool Returns true on success or false on failure.
 */
function socket_setopt (Socket $socket, int $level, int $option, array|string|int $value): bool {}

/**
 * Creates a pair of indistinguishable sockets and stores them in an array
 * @link http://www.php.net/manual/en/function.socket-create-pair.php
 * @param int $domain 
 * @param int $type 
 * @param int $protocol 
 * @param array $pair 
 * @return bool Returns true on success or false on failure.
 */
function socket_create_pair (int $domain, int $type, int $protocol, array &$pair): bool {}

/**
 * Shuts down a socket for receiving, sending, or both
 * @link http://www.php.net/manual/en/function.socket-shutdown.php
 * @param Socket $socket 
 * @param int $mode [optional] 
 * @return bool Returns true on success or false on failure.
 */
function socket_shutdown (Socket $socket, int $mode = 2): bool {}

/**
 * Returns the last error on the socket
 * @link http://www.php.net/manual/en/function.socket-last-error.php
 * @param Socket|null $socket [optional] 
 * @return int This function returns a socket error code.
 */
function socket_last_error (?Socket $socket = null): int {}

/**
 * Clears the error on the socket or the last error code
 * @link http://www.php.net/manual/en/function.socket-clear-error.php
 * @param Socket|null $socket [optional] 
 * @return void No value is returned.
 */
function socket_clear_error (?Socket $socket = null): void {}

/**
 * Import a stream
 * @link http://www.php.net/manual/en/function.socket-import-stream.php
 * @param resource $stream 
 * @return Socket|false Returns false on failure.
 */
function socket_import_stream ($stream): Socket|false {}

/**
 * Export a socket into a stream that encapsulates a socket
 * @link http://www.php.net/manual/en/function.socket-export-stream.php
 * @param Socket $socket 
 * @return resource|false Return resource or false on failure.
 */
function socket_export_stream (Socket $socket) {}

/**
 * Send a message
 * @link http://www.php.net/manual/en/function.socket-sendmsg.php
 * @param Socket $socket 
 * @param array $message 
 * @param int $flags [optional] 
 * @return int|false Returns the number of bytes sent, or false on failure.
 */
function socket_sendmsg (Socket $socket, array $message, int $flags = null): int|false {}

/**
 * Read a message
 * @link http://www.php.net/manual/en/function.socket-recvmsg.php
 * @param Socket $socket 
 * @param array $message 
 * @param int $flags [optional] 
 * @return int|false 
 */
function socket_recvmsg (Socket $socket, array &$message, int $flags = null): int|false {}

/**
 * Calculate message buffer size
 * @link http://www.php.net/manual/en/function.socket-cmsg-space.php
 * @param int $level 
 * @param int $type 
 * @param int $num [optional] 
 * @return int|null 
 */
function socket_cmsg_space (int $level, int $type, int $num = null): ?int {}

/**
 * Get array with contents of getaddrinfo about the given hostname
 * @link http://www.php.net/manual/en/function.socket-addrinfo-lookup.php
 * @param string $host Hostname to search.
 * @param string|null $service [optional] The service to connect to. If service is a numeric string, it designates the port.
 * Otherwise it designates a network service name, which is mapped to a port by the operating system.
 * @param array $hints [optional] Hints provide criteria for selecting addresses returned. You may specify the
 * hints as defined by getaddrinfo.
 * @return array|false Returns an array of AddressInfo instances that can be used with the other socket_addrinfo functions.
 * On failure, false is returned.
 */
function socket_addrinfo_lookup (string $host, ?string $service = null, array $hints = '[]'): array|false {}

/**
 * Create and connect to a socket from a given addrinfo
 * @link http://www.php.net/manual/en/function.socket-addrinfo-connect.php
 * @param AddressInfo $address AddressInfo instance created from socket_addrinfo_lookup
 * @return Socket|false Returns a Socket instance on success or false on failure.
 */
function socket_addrinfo_connect (AddressInfo $address): Socket|false {}

/**
 * Create and bind to a socket from a given addrinfo
 * @link http://www.php.net/manual/en/function.socket-addrinfo-bind.php
 * @param AddressInfo $address AddressInfo instance created from socket_addrinfo_lookup.
 * @return Socket|false Returns a Socket instance on success or false on failure.
 */
function socket_addrinfo_bind (AddressInfo $address): Socket|false {}

/**
 * Get information about addrinfo
 * @link http://www.php.net/manual/en/function.socket-addrinfo-explain.php
 * @param AddressInfo $address AddressInfo instance created from socket_addrinfo_lookup
 * @return array Returns an array containing the fields in the addrinfo structure.
 */
function socket_addrinfo_explain (AddressInfo $address): array {}


/**
 * 
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('AF_UNIX', 1);

/**
 * 
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('AF_INET', 2);

/**
 * Only available if compiled with IPv6 support.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('AF_INET6', 30);

/**
 * 
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCK_STREAM', 1);

/**
 * 
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCK_DGRAM', 2);

/**
 * 
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCK_RAW', 3);

/**
 * 
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCK_SEQPACKET', 5);

/**
 * 
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCK_RDM', 4);

/**
 * 
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('MSG_OOB', 1);

/**
 * 
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('MSG_WAITALL', 64);
define ('MSG_CTRUNC', 32);
define ('MSG_TRUNC', 16);

/**
 * 
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('MSG_PEEK', 2);

/**
 * 
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('MSG_DONTROUTE', 4);

/**
 * Not available on Windows platforms.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('MSG_EOR', 8);

/**
 * Not available on Windows platforms.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('MSG_EOF', 256);
define ('MSG_NOSIGNAL', 524288);
define ('MSG_DONTWAIT', 128);

/**
 * 
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SO_DEBUG', 1);

/**
 * 
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SO_REUSEADDR', 4);

/**
 * This constant is only available on platforms that
 * support the SO_REUSEPORT socket option: this
 * includes Linux, macOS and &#42;BSD, but does not include Windows.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SO_REUSEPORT', 512);

/**
 * 
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SO_KEEPALIVE', 8);

/**
 * 
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SO_DONTROUTE', 16);

/**
 * 
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SO_LINGER', 128);

/**
 * 
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SO_BROADCAST', 32);

/**
 * 
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SO_OOBINLINE', 256);

/**
 * 
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SO_SNDBUF', 4097);

/**
 * 
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SO_RCVBUF', 4098);

/**
 * 
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SO_SNDLOWAT', 4099);

/**
 * 
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SO_RCVLOWAT', 4100);

/**
 * 
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SO_SNDTIMEO', 4101);

/**
 * 
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SO_RCVTIMEO', 4102);

/**
 * 
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SO_TYPE', 4104);

/**
 * 
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SO_ERROR', 4103);

/**
 * Available as of PHP 8.1.0
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SO_DONTTRUNC', 8192);

/**
 * Available as of PHP 8.1.0
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SO_WANTMORE', 16384);

/**
 * 
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOL_SOCKET', 65535);
define ('SOMAXCONN', 128);

/**
 * Used to disable Nagle TCP algorithm.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('TCP_NODELAY', 1);

/**
 * Available as of PHP 8.2.0
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('TCP_NOTSENT_LOWAT', 513);

/**
 * Available as of PHP 8.2.0
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('TCP_KEEPALIVE', 16);

/**
 * 
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('PHP_NORMAL_READ', 1);

/**
 * 
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
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

/**
 * Operation not permitted.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_EPERM', 1);

/**
 * No such file or directory.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_ENOENT', 2);

/**
 * Interrupted system call.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_EINTR', 4);

/**
 * I/O error.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_EIO', 5);

/**
 * No such device or address.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_ENXIO', 6);

/**
 * Arg list too long.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_E2BIG', 7);

/**
 * Bad file descriptor number.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_EBADF', 9);

/**
 * Try again.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_EAGAIN', 35);

/**
 * Out of memory.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_ENOMEM', 12);

/**
 * Permission denied.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_EACCES', 13);

/**
 * Bad address.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_EFAULT', 14);

/**
 * Block device required.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_ENOTBLK', 15);

/**
 * Device or resource busy.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_EBUSY', 16);

/**
 * File exists.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_EEXIST', 17);

/**
 * Cross-device link.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_EXDEV', 18);

/**
 * No such device.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_ENODEV', 19);

/**
 * Not a directory.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_ENOTDIR', 20);

/**
 * Is a directory.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_EISDIR', 21);

/**
 * Invalid argument.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_EINVAL', 22);

/**
 * File table overflow.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_ENFILE', 23);

/**
 * Too many open files.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_EMFILE', 24);

/**
 * Not a typewriter.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_ENOTTY', 25);

/**
 * No space left on device.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_ENOSPC', 28);

/**
 * Illegal seek.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_ESPIPE', 29);

/**
 * Read-only file system.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_EROFS', 30);

/**
 * Too many links.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_EMLINK', 31);

/**
 * Broken pipe.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_EPIPE', 32);

/**
 * File name too long.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_ENAMETOOLONG', 63);

/**
 * No record locks available.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_ENOLCK', 77);

/**
 * Function not implemented.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_ENOSYS', 78);

/**
 * Directory not empty.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_ENOTEMPTY', 66);

/**
 * Too many symbolic links encountered.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_ELOOP', 62);

/**
 * Operation would block.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_EWOULDBLOCK', 35);

/**
 * No message of desired type.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_ENOMSG', 91);

/**
 * Identifier removed.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_EIDRM', 90);

/**
 * Device not a stream.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_ENOSTR', 99);

/**
 * No data available.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_ENODATA', 96);

/**
 * Timer expired.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_ETIME', 101);

/**
 * Out of streams resources.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_ENOSR', 98);

/**
 * Object is remote.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_EREMOTE', 71);

/**
 * Link has been severed.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_ENOLINK', 97);

/**
 * Protocol error.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_EPROTO', 100);

/**
 * Multihop attempted.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_EMULTIHOP', 95);

/**
 * Not a data message.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_EBADMSG', 94);

/**
 * Too many users.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_EUSERS', 68);

/**
 * Socket operation on non-socket.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_ENOTSOCK', 38);

/**
 * Destination address required.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_EDESTADDRREQ', 39);

/**
 * Message too long.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_EMSGSIZE', 40);

/**
 * Protocol wrong type for socket.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_EPROTOTYPE', 41);

/**
 * 
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_ENOPROTOOPT', 42);

/**
 * Protocol not supported.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_EPROTONOSUPPORT', 43);

/**
 * Socket type not supported.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_ESOCKTNOSUPPORT', 44);

/**
 * Operation not supported on transport endpoint.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_EOPNOTSUPP', 102);

/**
 * Protocol family not supported.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_EPFNOSUPPORT', 46);

/**
 * Address family not supported by protocol.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_EAFNOSUPPORT', 47);

/**
 * 
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_EADDRINUSE', 48);

/**
 * Cannot assign requested address.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_EADDRNOTAVAIL', 49);

/**
 * Network is down.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_ENETDOWN', 50);

/**
 * Network is unreachable.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_ENETUNREACH', 51);

/**
 * Network dropped connection because of reset.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_ENETRESET', 52);

/**
 * Software caused connection abort.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_ECONNABORTED', 53);

/**
 * Connection reset by peer.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_ECONNRESET', 54);

/**
 * No buffer space available.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_ENOBUFS', 55);

/**
 * Transport endpoint is already connected.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_EISCONN', 56);

/**
 * Transport endpoint is not connected.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_ENOTCONN', 57);

/**
 * Cannot send after transport endpoint shutdown.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_ESHUTDOWN', 58);

/**
 * Too many references: cannot splice.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_ETOOMANYREFS', 59);

/**
 * Connection timed out.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_ETIMEDOUT', 60);

/**
 * Connection refused.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_ECONNREFUSED', 61);

/**
 * Host is down.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_EHOSTDOWN', 64);

/**
 * No route to host.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_EHOSTUNREACH', 65);

/**
 * Operation already in progress.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_EALREADY', 37);

/**
 * Operation now in progress.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_EINPROGRESS', 36);

/**
 * Quota exceeded.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOCKET_EDQUOT', 69);
define ('IPPROTO_IP', 0);
define ('IPPROTO_IPV6', 41);

/**
 * 
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SOL_TCP', 6);

/**
 * 
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
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

/**
 * Send or receive a set of open file descriptors from another process.
 * @link http://www.php.net/manual/en/sockets.constants.php
 * @var int
 */
define ('SCM_RIGHTS', 1);

// End of sockets v.8.2.6
