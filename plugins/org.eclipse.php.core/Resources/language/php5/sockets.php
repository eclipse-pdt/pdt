<?php

// Start of sockets v.

/**
 * Runs the select() system call on the given arrays of sockets with a specified timeout
 * @link http://php.net/manual/en/function.socket-select.php
 * @param read array
 * @param write array
 * @param except array
 * @param tv_sec int
 * @param tv_usec int[optional]
 * @return int 
 */
function socket_select (array &$read, array &$write, array &$except, $tv_sec, $tv_usec = null) {}

/**
 * Create a socket (endpoint for communication)
 * @link http://php.net/manual/en/function.socket-create.php
 * @param domain int
 * @param type int
 * @param protocol int
 * @return resource 
 */
function socket_create ($domain, $type, $protocol) {}

/**
 * Opens a socket on port to accept connections
 * @link http://php.net/manual/en/function.socket-create-listen.php
 * @param port int
 * @param backlog int[optional]
 * @return resource 
 */
function socket_create_listen ($port, $backlog = null) {}

/**
 * Creates a pair of indistinguishable sockets and stores them in an array
 * @link http://php.net/manual/en/function.socket-create-pair.php
 * @param domain int
 * @param type int
 * @param protocol int
 * @param fd array
 * @return bool 
 */
function socket_create_pair ($domain, $type, $protocol, array &$fd) {}

/**
 * Accepts a connection on a socket
 * @link http://php.net/manual/en/function.socket-accept.php
 * @param socket resource
 * @return resource a new socket resource on success, or false on error. The actual
 */
function socket_accept ($socket) {}

/**
 * Sets nonblocking mode for file descriptor fd
 * @link http://php.net/manual/en/function.socket-set-nonblock.php
 * @param socket resource
 * @return bool 
 */
function socket_set_nonblock ($socket) {}

/**
 * Sets blocking mode on a socket resource
 * @link http://php.net/manual/en/function.socket-set-block.php
 * @param socket resource
 * @return bool 
 */
function socket_set_block ($socket) {}

/**
 * Listens for a connection on a socket
 * @link http://php.net/manual/en/function.socket-listen.php
 * @param socket resource
 * @param backlog int[optional]
 * @return bool 
 */
function socket_listen ($socket, $backlog = null) {}

/**
 * Closes a socket resource
 * @link http://php.net/manual/en/function.socket-close.php
 * @param socket resource
 * @return void 
 */
function socket_close ($socket) {}

/**
 * Write to a socket
 * @link http://php.net/manual/en/function.socket-write.php
 * @param socket resource
 * @param buffer string
 * @param length int[optional]
 * @return int the number of bytes successfully written to the socket or false
 */
function socket_write ($socket, $buffer, $length = null) {}

/**
 * Reads a maximum of length bytes from a socket
 * @link http://php.net/manual/en/function.socket-read.php
 * @param socket resource
 * @param length int
 * @param type int[optional]
 * @return string 
 */
function socket_read ($socket, $length, $type = null) {}

/**
 * Queries the local side of the given socket which may either result in host/port or in a Unix filesystem path, dependent on its type
 * @link http://php.net/manual/en/function.socket-getsockname.php
 * @param socket resource
 * @param addr string
 * @param port int[optional]
 * @return bool 
 */
function socket_getsockname ($socket, &$addr, &$port = null) {}

/**
 * Queries the remote side of the given socket which may either result in host/port or in a Unix filesystem path, dependent on its type
 * @link http://php.net/manual/en/function.socket-getpeername.php
 * @param socket resource
 * @param address string
 * @param port int[optional]
 * @return bool 
 */
function socket_getpeername ($socket, &$address, &$port = null) {}

/**
 * Initiates a connection on a socket
 * @link http://php.net/manual/en/function.socket-connect.php
 * @param socket resource
 * @param address string
 * @param port int[optional]
 * @return bool 
 */
function socket_connect ($socket, $address, $port = null) {}

/**
 * Return a string describing a socket error
 * @link http://php.net/manual/en/function.socket-strerror.php
 * @param errno int
 * @return string the error message associated with the errno
 */
function socket_strerror ($errno) {}

/**
 * Binds a name to a socket
 * @link http://php.net/manual/en/function.socket-bind.php
 * @param socket resource
 * @param address string
 * @param port int[optional]
 * @return bool 
 */
function socket_bind ($socket, $address, $port = null) {}

/**
 * Receives data from a connected socket
 * @link http://php.net/manual/en/function.socket-recv.php
 * @param socket resource
 * @param buf string
 * @param len int
 * @param flags int
 * @return int 
 */
function socket_recv ($socket, &$buf, $len, $flags) {}

/**
 * Sends data to a connected socket
 * @link http://php.net/manual/en/function.socket-send.php
 * @param socket resource
 * @param buf string
 * @param len int
 * @param flags int
 * @return int 
 */
function socket_send ($socket, $buf, $len, $flags) {}

/**
 * Receives data from a socket whether or not it is connection-oriented
 * @link http://php.net/manual/en/function.socket-recvfrom.php
 * @param socket resource
 * @param buf string
 * @param len int
 * @param flags int
 * @param name string
 * @param port int[optional]
 * @return int 
 */
function socket_recvfrom ($socket, &$buf, $len, $flags, &$name, &$port = null) {}

/**
 * Sends a message to a socket, whether it is connected or not
 * @link http://php.net/manual/en/function.socket-sendto.php
 * @param socket resource
 * @param buf string
 * @param len int
 * @param flags int
 * @param addr string
 * @param port int[optional]
 * @return int 
 */
function socket_sendto ($socket, $buf, $len, $flags, $addr, $port = null) {}

/**
 * Gets socket options for the socket
 * @link http://php.net/manual/en/function.socket-get-option.php
 * @param socket resource
 * @param level int
 * @param optname int
 * @return mixed the value of the given option, or false on errors.
 */
function socket_get_option ($socket, $level, $optname) {}

/**
 * Sets socket options for the socket
 * @link http://php.net/manual/en/function.socket-set-option.php
 * @param socket resource
 * @param level int
 * @param optname int
 * @param optval mixed
 * @return bool 
 */
function socket_set_option ($socket, $level, $optname, $optval) {}

/**
 * Shuts down a socket for receiving, sending, or both
 * @link http://php.net/manual/en/function.socket-shutdown.php
 * @param socket resource
 * @param how int[optional]
 * @return bool 
 */
function socket_shutdown ($socket, $how = null) {}

/**
 * Returns the last error on the socket
 * @link http://php.net/manual/en/function.socket-last-error.php
 * @param socket resource[optional]
 * @return int 
 */
function socket_last_error ($socket = null) {}

/**
 * Clears the error on the socket or the last error code
 * @link http://php.net/manual/en/function.socket-clear-error.php
 * @param socket resource[optional]
 * @return void 
 */
function socket_clear_error ($socket = null) {}

function socket_getopt () {}

function socket_setopt () {}

define ('AF_UNIX', 1);
define ('AF_INET', 2);
define ('AF_INET6', 10);
define ('SOCK_STREAM', 1);
define ('SOCK_DGRAM', 2);
define ('SOCK_RAW', 3);
define ('SOCK_SEQPACKET', 5);
define ('SOCK_RDM', 4);
define ('MSG_OOB', 1);
define ('MSG_WAITALL', 256);
define ('MSG_PEEK', 2);
define ('MSG_DONTROUTE', 4);
define ('MSG_EOR', 128);
define ('MSG_EOF', 512);
define ('SO_DEBUG', 1);
define ('SO_REUSEADDR', 2);
define ('SO_KEEPALIVE', 9);
define ('SO_DONTROUTE', 5);
define ('SO_LINGER', 13);
define ('SO_BROADCAST', 6);
define ('SO_OOBINLINE', 10);
define ('SO_SNDBUF', 7);
define ('SO_RCVBUF', 8);
define ('SO_SNDLOWAT', 19);
define ('SO_RCVLOWAT', 18);
define ('SO_SNDTIMEO', 21);
define ('SO_RCVTIMEO', 20);
define ('SO_TYPE', 3);
define ('SO_ERROR', 4);
define ('SOL_SOCKET', 1);
define ('SOMAXCONN', 128);
define ('PHP_NORMAL_READ', 1);
define ('PHP_BINARY_READ', 2);
define ('SOCKET_EPERM', 1);
define ('SOCKET_ENOENT', 2);
define ('SOCKET_EINTR', 4);
define ('SOCKET_EIO', 5);
define ('SOCKET_ENXIO', 6);
define ('SOCKET_E2BIG', 7);
define ('SOCKET_EBADF', 9);
define ('SOCKET_EAGAIN', 11);
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
define ('SOCKET_ENAMETOOLONG', 36);
define ('SOCKET_ENOLCK', 37);
define ('SOCKET_ENOSYS', 38);
define ('SOCKET_ENOTEMPTY', 39);
define ('SOCKET_ELOOP', 40);
define ('SOCKET_EWOULDBLOCK', 11);
define ('SOCKET_ENOMSG', 42);
define ('SOCKET_EIDRM', 43);
define ('SOCKET_ECHRNG', 44);
define ('SOCKET_EL2NSYNC', 45);
define ('SOCKET_EL3HLT', 46);
define ('SOCKET_EL3RST', 47);
define ('SOCKET_ELNRNG', 48);
define ('SOCKET_EUNATCH', 49);
define ('SOCKET_ENOCSI', 50);
define ('SOCKET_EL2HLT', 51);
define ('SOCKET_EBADE', 52);
define ('SOCKET_EBADR', 53);
define ('SOCKET_EXFULL', 54);
define ('SOCKET_ENOANO', 55);
define ('SOCKET_EBADRQC', 56);
define ('SOCKET_EBADSLT', 57);
define ('SOCKET_ENOSTR', 60);
define ('SOCKET_ENODATA', 61);
define ('SOCKET_ETIME', 62);
define ('SOCKET_ENOSR', 63);
define ('SOCKET_ENONET', 64);
define ('SOCKET_EREMOTE', 66);
define ('SOCKET_ENOLINK', 67);
define ('SOCKET_EADV', 68);
define ('SOCKET_ESRMNT', 69);
define ('SOCKET_ECOMM', 70);
define ('SOCKET_EPROTO', 71);
define ('SOCKET_EMULTIHOP', 72);
define ('SOCKET_EBADMSG', 74);
define ('SOCKET_ENOTUNIQ', 76);
define ('SOCKET_EBADFD', 77);
define ('SOCKET_EREMCHG', 78);
define ('SOCKET_ERESTART', 85);
define ('SOCKET_ESTRPIPE', 86);
define ('SOCKET_EUSERS', 87);
define ('SOCKET_ENOTSOCK', 88);
define ('SOCKET_EDESTADDRREQ', 89);
define ('SOCKET_EMSGSIZE', 90);
define ('SOCKET_EPROTOTYPE', 91);
define ('SOCKET_ENOPROTOOPT', 92);
define ('SOCKET_EPROTONOSUPPORT', 93);
define ('SOCKET_ESOCKTNOSUPPORT', 94);
define ('SOCKET_EOPNOTSUPP', 95);
define ('SOCKET_EPFNOSUPPORT', 96);
define ('SOCKET_EAFNOSUPPORT', 97);
define ('SOCKET_EADDRINUSE', 98);
define ('SOCKET_EADDRNOTAVAIL', 99);
define ('SOCKET_ENETDOWN', 100);
define ('SOCKET_ENETUNREACH', 101);
define ('SOCKET_ENETRESET', 102);
define ('SOCKET_ECONNABORTED', 103);
define ('SOCKET_ECONNRESET', 104);
define ('SOCKET_ENOBUFS', 105);
define ('SOCKET_EISCONN', 106);
define ('SOCKET_ENOTCONN', 107);
define ('SOCKET_ESHUTDOWN', 108);
define ('SOCKET_ETOOMANYREFS', 109);
define ('SOCKET_ETIMEDOUT', 110);
define ('SOCKET_ECONNREFUSED', 111);
define ('SOCKET_EHOSTDOWN', 112);
define ('SOCKET_EHOSTUNREACH', 113);
define ('SOCKET_EALREADY', 114);
define ('SOCKET_EINPROGRESS', 115);
define ('SOCKET_EISNAM', 120);
define ('SOCKET_EREMOTEIO', 121);
define ('SOCKET_EDQUOT', 122);
define ('SOCKET_ENOMEDIUM', 123);
define ('SOCKET_EMEDIUMTYPE', 124);
define ('SOL_TCP', 6);
define ('SOL_UDP', 17);

// End of sockets v.
?>
