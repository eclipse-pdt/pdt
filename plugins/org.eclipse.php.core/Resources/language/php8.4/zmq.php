<?php

// Start of zmq v.1.1.3

/**
 * @link http://www.php.net/manual/en/class.zmq.php
 */
class ZMQ  {
	/**
	 * Exclusive pair pattern
	const SOCKET_PAIR = 0;
	/**
	 * Publisher socket
	const SOCKET_PUB = 1;
	/**
	 * Subscriber socket
	const SOCKET_SUB = 2;
	/**
	 * Request socket
	const SOCKET_REQ = 3;
	/**
	 * Reply socket
	const SOCKET_REP = 4;
	/**
	 * Alias for SOCKET_DEALER
	const SOCKET_XREQ = 5;
	/**
	 * Alias for SOCKET_ROUTER
	const SOCKET_XREP = 6;
	/**
	 * Pipeline upstream push socket
	const SOCKET_PUSH = 8;
	/**
	 * Pipeline downstream pull socket
	const SOCKET_PULL = 7;
	/**
	 * Extended REQ socket that load balances to all connected peers
	const SOCKET_DEALER = 5;
	/**
	 * Extended REP socket that can route replies to requesters
	const SOCKET_ROUTER = 6;
	/**
	 * Similar to SOCKET_SUB, except you can send subscriptions as messages. See SOCKET_XPUB for format.
	const SOCKET_XSUB = 10;
	/**
	 * Similar to SOCKET_PUB, except you can receive subscriptions as messages.
	 * The subscription message is 0 (unsubscribe) or 1 (subscribe) followed by the topic.
	const SOCKET_XPUB = 9;
	/**
	 * Used to send and receive TCP data from a non-ØMQ peer. Available if compiled against ZeroMQ 4.x or higher (Value: int).
	const SOCKET_STREAM = 11;
	const SOCKET_UPSTREAM = 7;
	const SOCKET_DOWNSTREAM = 8;
	/**
	 * Poll for incoming data
	const POLL_IN = 1;
	/**
	 * Poll for outgoing data
	const POLL_OUT = 2;
	/**
	 * Send multi-part message
	const MODE_SNDMORE = 2;
	/**
	 * Non-blocking operation. Deprecated, use ZMQ::MODE_DONTWAIT instead
	const MODE_NOBLOCK = 1;
	/**
	 * Non-blocking operation
	const MODE_DONTWAIT = 1;
	/**
	 * ZMQ extension internal error
	const ERR_INTERNAL = -99;
	/**
	 * Implies that the operation would block when ZMQ::MODE_DONTWAIT is used
	const ERR_EAGAIN = 35;
	/**
	 * The operation is not supported by the socket type
	const ERR_ENOTSUP = 45;
	/**
	 * The operation can not be executed because the socket is not in correct state
	const ERR_EFSM = 156384763;
	/**
	 * The context has been terminated
	const ERR_ETERM = 156384765;
	const LIBZMQ_VER = "4.3.4";
	const LIBZMQ_VERSION = "4.3.4";
	const LIBZMQ_VERSION_ID = 40304;
	const LIBZMQ_VERSION_MAJOR = 4;
	const LIBZMQ_VERSION_MINOR = 3;
	const LIBZMQ_VERSION_PATCH = 4;
	const SOCKOPT_GSSAPI_PRINCIPAL_NAMETYPE = 90;
	const SOCKOPT_GSSAPI_SERVICE_PRINCIPAL_NAMETYPE = 91;
	const SOCKOPT_BINDTODEVICE = 92;
	const SOCKOPT_HEARTBEAT_IVL = 75;
	const SOCKOPT_HEARTBEAT_TTL = 76;
	const SOCKOPT_HEARTBEAT_TIMEOUT = 77;
	const SOCKOPT_USE_FD = 89;
	const SOCKOPT_XPUB_MANUAL = 71;
	const SOCKOPT_XPUB_WELCOME_MSG = 72;
	const SOCKOPT_STREAM_NOTIFY = 73;
	const SOCKOPT_INVERT_MATCHING = 74;
	const SOCKOPT_XPUB_VERBOSER = 78;
	const SOCKOPT_CONNECT_TIMEOUT = 79;
	const SOCKOPT_TCP_MAXRT = 80;
	const SOCKOPT_THREAD_SAFE = 81;
	const SOCKOPT_MULTICAST_MAXTPDU = 84;
	const SOCKOPT_VMCI_BUFFER_SIZE = 85;
	const SOCKOPT_VMCI_BUFFER_MIN_SIZE = 86;
	const SOCKOPT_VMCI_BUFFER_MAX_SIZE = 87;
	const SOCKOPT_VMCI_CONNECT_TIMEOUT = 88;
	const SOCKOPT_TOS = 57;
	const SOCKOPT_ROUTER_HANDOVER = 56;
	const SOCKOPT_CONNECT_RID = 61;
	const SOCKOPT_HANDSHAKE_IVL = 66;
	const SOCKOPT_SOCKS_PROXY = 68;
	const SOCKOPT_XPUB_NODROP = 69;
	const SOCKOPT_ROUTER_MANDATORY = 33;
	const SOCKOPT_PROBE_ROUTER = 51;
	const SOCKOPT_REQ_RELAXED = 53;
	const SOCKOPT_REQ_CORRELATE = 52;
	const SOCKOPT_CONFLATE = 54;
	const SOCKOPT_ZAP_DOMAIN = 55;
	const SOCKOPT_MECHANISM = 43;
	const SOCKOPT_PLAIN_SERVER = 44;
	const SOCKOPT_PLAIN_USERNAME = 45;
	const SOCKOPT_PLAIN_PASSWORD = 46;
	const SOCKOPT_CURVE_SERVER = 47;
	const SOCKOPT_CURVE_PUBLICKEY = 48;
	const SOCKOPT_CURVE_SECRETKEY = 49;
	const SOCKOPT_CURVE_SERVERKEY = 50;
	const SOCKOPT_GSSAPI_SERVER = 62;
	const SOCKOPT_GSSAPI_PLAINTEXT = 65;
	const SOCKOPT_GSSAPI_PRINCIPAL = 63;
	const SOCKOPT_GSSAPI_SERVICE_PRINCIPAL = 64;
	/**
	 * Enable IPV6. Available if compiled against ZeroMQ 4.0 or higher (Value: string)
	const SOCKOPT_IPV6 = 42;
	const SOCKOPT_IMMEDIATE = 39;
	/**
	 * The ZMQ_SNDHWM option shall set the high water mark for outbound messages on the specified socket. Available if compiled against ZeroMQ 3.x or higher (Value: int).
	const SOCKOPT_SNDHWM = 23;
	/**
	 * The SOCKOPT_RCVHWM option shall set the high water mark for inbound messages on the specified socket. Available if compiled against ZeroMQ 3.x or higher (Value: int).
	const SOCKOPT_RCVHWM = 24;
	/**
	 * Limits the maximum size of the inbound message. Value -1 means no limit. Available if compiled against ZeroMQ 3.x or higher (Value: int)
	const SOCKOPT_MAXMSGSIZE = 22;
	const SOCKOPT_MULTICAST_HOPS = 25;
	/**
	 * Set the XPUB to receive an application message on each instance of a subscription. Available if compiled against ZeroMQ 3.x or higher (Value: string)
	const SOCKOPT_XPUB_VERBOSE = 40;
	const SOCKOPT_TCP_KEEPALIVE = 34;
	/**
	 * Idle time for TCP keepalive. Available if compiled against ZeroMQ 3.x or higher (Value: int)
	const SOCKOPT_TCP_KEEPALIVE_IDLE = 36;
	/**
	 * Count time for TCP keepalive. Available if compiled against ZeroMQ 3.x or higher (Value: int)
	const SOCKOPT_TCP_KEEPALIVE_CNT = 35;
	/**
	 * Interval for TCP keepalive. Available if compiled against ZeroMQ 3.x or higher (Value: int)
	const SOCKOPT_TCP_KEEPALIVE_INTVL = 37;
	/**
	 * Set a CIDR string to match against incoming TCP connections. Available if compiled against ZeroMQ 3.x or higher (Value: string)
	const SOCKOPT_TCP_ACCEPT_FILTER = 38;
	/**
	 * Retrieve the last connected endpoint - for use with &#42; wildcard ports. Available if compiled against ZeroMQ 3.x or higher (Value: string)
	const SOCKOPT_LAST_ENDPOINT = 32;
	/**
	 * Sets the raw mode on the ROUTER, when set to 1. In raw mode when using tcp:// transport the socket will read and write without ZeroMQ framing.
	 * Available if compiled against ZeroMQ 4.0 or higher (Value: string)
	const SOCKOPT_ROUTER_RAW = 41;
	/**
	 * Disable IPV6 support if 1. Available if compiled against ZeroMQ 3.x (Value: int)
	const SOCKOPT_IPV4ONLY = 31;
	/**
	 * Set a CIDR string to match against incoming TCP connections. Available if compiled against ZeroMQ 3.x or higher (Value: string)
	const SOCKOPT_DELAY_ATTACH_ON_CONNECT = 39;
	/**
	 * The high water mark for inbound and outbound messages is a hard limit on the maximum number of outstanding messages ØMQ shall queue in memory for any single peer that the specified socket is communicating with. Setting this option on a socket will only affect connections made after the option has been set. On ZeroMQ 3.x this is a wrapper for setting both SNDHWM and RCVHWM. (Value: int).
	const SOCKOPT_HWM = 2001;
	/**
	 * Set I/O thread affinity (Value: int)
	const SOCKOPT_AFFINITY = 4;
	/**
	 * Set socket identity (Value: string)
	const SOCKOPT_IDENTITY = 5;
	/**
	 * Set rate for multicast sockets (pgm) (Value: int &gt;= 0)
	const SOCKOPT_RATE = 8;
	/**
	 * Set multicast recovery interval (Value: int &gt;= 0)
	const SOCKOPT_RECOVERY_IVL = 9;
	/**
	 * Sets the timeout for receive operation on the socket. Value -1 means no limit. Available if compiled against ZeroMQ 3.x or higher (Value: int)
	const SOCKOPT_RCVTIMEO = 27;
	/**
	 * Sets the timeout for send operation on the socket. Value -1 means no limit. Available if compiled against ZeroMQ 3.x or higher (Value: int)
	const SOCKOPT_SNDTIMEO = 28;
	/**
	 * Set kernel transmit buffer size (Value: int &gt;= 0)
	const SOCKOPT_SNDBUF = 11;
	/**
	 * Set kernel receive buffer size (Value: int &gt;= 0)
	const SOCKOPT_RCVBUF = 12;
	/**
	 * The linger value of the socket. Specifies how long the socket blocks
	 * trying flush messages after it has been closed (Value: int)
	const SOCKOPT_LINGER = 17;
	/**
	 * Set the initial reconnection interval (Value: int &gt;= 0)
	const SOCKOPT_RECONNECT_IVL = 18;
	/**
	 * Set the max reconnection interval (Value: int &gt;= 0)
	const SOCKOPT_RECONNECT_IVL_MAX = 21;
	/**
	 * The SOCKOPT_BACKLOG option shall set the maximum length of the queue of outstanding peer connections for the specified socket; this only applies to connection-oriented transports. (Value: int)
	const SOCKOPT_BACKLOG = 19;
	/**
	 * Establish message filter. Valid for subscriber socket (Value: string)
	const SOCKOPT_SUBSCRIBE = 6;
	/**
	 * Remove message filter. Valid for subscriber socket (Value: string)
	const SOCKOPT_UNSUBSCRIBE = 7;
	/**
	 * Get the socket type. Valid for getSockOpt (Value: int)
	const SOCKOPT_TYPE = 16;
	/**
	 * Receive multi-part messages (Value: int)
	const SOCKOPT_RCVMORE = 13;
	const SOCKOPT_FD = 14;
	const SOCKOPT_EVENTS = 15;
	/**
	 * The socket limit for this context. Available if compiled against ZeroMQ 3.x or higher (Value: int)
	const CTXOPT_MAX_SOCKETS = 2;
	const CTXOPT_MAX_SOCKETS_DEFAULT = 1023;
	const EVENT_CONNECTED = 1;
	const EVENT_CONNECT_DELAYED = 2;
	const EVENT_CONNECT_RETRIED = 4;
	const EVENT_LISTENING = 8;
	const EVENT_BIND_FAILED = 16;
	const EVENT_ACCEPTED = 32;
	const EVENT_ACCEPT_FAILED = 64;
	const EVENT_CLOSED = 128;
	const EVENT_CLOSE_FAILED = 256;
	const EVENT_DISCONNECTED = 512;
	const EVENT_MONITOR_STOPPED = 1024;
	const EVENT_ALL = 65535;


	/**
	 * ZMQ constructor
	 * @link http://www.php.net/manual/en/zmq.construct.php
	 * @return void 
	 */
	private function __construct (): void {}

	/**
	 * {@inheritdoc}
	 */
	public static function clock () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $data
	 */
	public static function z85encode ($data = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $data
	 */
	public static function z85decode ($data = null) {}

	/**
	 * {@inheritdoc}
	 */
	public static function curvekeypair () {}

}

/**
 * @link http://www.php.net/manual/en/class.zmqcontext.php
 */
class ZMQContext  {

	/**
	 * Construct a new ZMQContext object
	 * @link http://www.php.net/manual/en/zmqcontext.construct.php
	 * @param int $io_threads [optional] 
	 * @param bool $is_persistent [optional] 
	 * @return int 
	 */
	final public function __construct (int $io_threads = 1, bool $is_persistent = true): int {}

	/**
	 * {@inheritdoc}
	 */
	public static function acquire () {}

	/**
	 * {@inheritdoc}
	 */
	public function getsocketcount () {}

	/**
	 * Create a new socket
	 * @link http://www.php.net/manual/en/zmqcontext.getsocket.php
	 * @param int $type 
	 * @param string $persistent_id [optional] 
	 * @param callable $on_new_socket [optional] 
	 * @return ZMQSocket Returns a ZMQSocket object.
	 */
	public function getsocket (int $type, string $persistent_id = null, callable $on_new_socket = null): ZMQSocket {}

	/**
	 * Whether the context is persistent
	 * @link http://www.php.net/manual/en/zmqcontext.ispersistent.php
	 * @return bool Returns true if the context is persistent and false if the context is non-persistent.
	 */
	public function ispersistent (): bool {}

	/**
	 * {@inheritdoc}
	 */
	final private function __clone () {}

	/**
	 * Set a socket option
	 * @link http://www.php.net/manual/en/zmqcontext.setopt.php
	 * @param int $key 
	 * @param mixed $value 
	 * @return ZMQContext Returns the current object. Throws ZMQContextException on error.
	 */
	public function setOpt (int $key, mixed $value): ZMQContext {}

	/**
	 * Get context option
	 * @link http://www.php.net/manual/en/zmqcontext.getopt.php
	 * @param string $key 
	 * @return mixed Returns either a string or an int depending on key. Throws
	 * ZMQContextException on error.
	 */
	public function getOpt (string $key): mixed {}

}

/**
 * @link http://www.php.net/manual/en/class.zmqsocket.php
 */
class ZMQSocket  {

	/**
	 * Construct a new ZMQSocket
	 * @link http://www.php.net/manual/en/zmqsocket.construct.php
	 * @param ZMQContext $context 
	 * @param int $type 
	 * @param string $persistent_id [optional] 
	 * @param callable $on_new_socket [optional] 
	 * @return ZMQContext 
	 */
	final public function __construct (ZMQContext $context, int $type, string $persistent_id = null, callable $on_new_socket = null): ZMQContext {}

	/**
	 * Sends a message
	 * @link http://www.php.net/manual/en/zmqsocket.send.php
	 * @param string $message 
	 * @param int $mode [optional] 
	 * @return ZMQSocket Returns the current object. Throws ZMQSocketException on error. 
	 * If ZMQ::MODE_NOBLOCK is used and the operation would 
	 * block bool false shall be returned.
	 */
	public function send (string $message, int $mode = null): ZMQSocket {}

	/**
	 * Receives a message
	 * @link http://www.php.net/manual/en/zmqsocket.recv.php
	 * @param int $mode [optional] 
	 * @return string Returns the message. If ZMQ::MODE_DONTWAIT 
	 * is used and the operation would block false shall be returned.
	 */
	public function recv (int $mode = null): string {}

	/**
	 * Sends a multipart message
	 * @link http://www.php.net/manual/en/zmqsocket.sendmulti.php
	 * @param array $message 
	 * @param int $mode [optional] 
	 * @return ZMQSocket Returns the current object. Throws ZMQSocketException on error. 
	 * If ZMQ::MODE_NOBLOCK is used and the operation would 
	 * block bool false shall be returned.
	 */
	public function sendmulti (array $message, int $mode = null): ZMQSocket {}

	/**
	 * Receives a multipart message
	 * @link http://www.php.net/manual/en/zmqsocket.recvmulti.php
	 * @param int $mode [optional] 
	 * @return array Returns the array of message parts. Throws ZMQSocketException in error. 
	 * If ZMQ::MODE_NOBLOCK is used and the operation would 
	 * block bool false shall be returned.
	 */
	public function recvmulti (int $mode = null): array {}

	/**
	 * Bind the socket
	 * @link http://www.php.net/manual/en/zmqsocket.bind.php
	 * @param string $dsn 
	 * @param bool $force [optional] 
	 * @return ZMQSocket Returns the current object. Throws ZMQSocketException on error.
	 */
	public function bind (string $dsn, bool $force = false): ZMQSocket {}

	/**
	 * Connect the socket
	 * @link http://www.php.net/manual/en/zmqsocket.connect.php
	 * @param string $dsn 
	 * @param bool $force [optional] 
	 * @return ZMQSocket Returns the current object.
	 */
	public function connect (string $dsn, bool $force = false): ZMQSocket {}

	/**
	 * {@inheritdoc}
	 * @param mixed $dsn
	 * @param mixed $events [optional]
	 */
	public function monitor ($dsn = null, $events = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $flags [optional]
	 */
	public function recvevent ($flags = NULL) {}

	/**
	 * Unbind the socket
	 * @link http://www.php.net/manual/en/zmqsocket.unbind.php
	 * @param string $dsn 
	 * @return ZMQSocket Returns the current object. Throws ZMQSocketException on error.
	 */
	public function unbind (string $dsn): ZMQSocket {}

	/**
	 * Disconnect a socket
	 * @link http://www.php.net/manual/en/zmqsocket.disconnect.php
	 * @param string $dsn 
	 * @return ZMQSocket Returns the current object. Throws ZMQSocketException on error.
	 */
	public function disconnect (string $dsn): ZMQSocket {}

	/**
	 * Set a socket option
	 * @link http://www.php.net/manual/en/zmqsocket.setsockopt.php
	 * @param int $key 
	 * @param mixed $value 
	 * @return ZMQSocket Returns the current object. Throws ZMQSocketException on error.
	 */
	public function setsockopt (int $key, mixed $value): ZMQSocket {}

	/**
	 * Get list of endpoints
	 * @link http://www.php.net/manual/en/zmqsocket.getendpoints.php
	 * @return array Returns an array containing elements 'bind' and 'connect'.
	 */
	public function getendpoints (): array {}

	/**
	 * Get the socket type
	 * @link http://www.php.net/manual/en/zmqsocket.getsockettype.php
	 * @return int Returns an integer representing the socket type. The integer can be compared against
	 * ZMQ::SOCKET_&#42; constants.
	 */
	public function getsockettype (): int {}

	/**
	 * Whether the socket is persistent
	 * @link http://www.php.net/manual/en/zmqsocket.ispersistent.php
	 * @return bool Returns a bool based on whether the socket is persistent or not.
	 */
	public function ispersistent (): bool {}

	/**
	 * Get the persistent id
	 * @link http://www.php.net/manual/en/zmqsocket.getpersistentid.php
	 * @return string Returns the persistent id string assigned of the object and null if socket is not persistent.
	 */
	public function getpersistentid (): string {}

	/**
	 * Get socket option
	 * @link http://www.php.net/manual/en/zmqsocket.getsockopt.php
	 * @param string $key 
	 * @return mixed Returns either a string or an int depending on key. Throws
	 * ZMQSocketException on error.
	 */
	public function getsockopt (string $key): mixed {}

	/**
	 * {@inheritdoc}
	 */
	final private function __clone () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $message
	 * @param mixed $mode [optional]
	 */
	public function sendmsg ($message = null, $mode = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $mode [optional]
	 */
	public function recvmsg ($mode = NULL) {}

}

/**
 * @link http://www.php.net/manual/en/class.zmqpoll.php
 */
class ZMQPoll  {

	/**
	 * Add item to the poll set
	 * @link http://www.php.net/manual/en/zmqpoll.add.php
	 * @param mixed $entry 
	 * @param int $type 
	 * @return string Returns a string id of the added item which can be later used to remove the item. 
	 * Throws ZMQPollException on error.
	 */
	public function add (mixed $entry, int $type): string {}

	/**
	 * Poll the items
	 * @link http://www.php.net/manual/en/zmqpoll.poll.php
	 * @param array $readable 
	 * @param array $writable 
	 * @param int $timeout [optional] 
	 * @return int Returns an integer representing the amount of items with activity.
	 */
	public function poll (array &$readable, array &$writable, int $timeout = -1): int {}

	/**
	 * Get poll errors
	 * @link http://www.php.net/manual/en/zmqpoll.getlasterrors.php
	 * @return array Returns an array containing ids for the items that had errors in the last poll. Empty array is 
	 * returned if there were no errors.
	 */
	public function getlasterrors (): array {}

	/**
	 * Remove item from poll set
	 * @link http://www.php.net/manual/en/zmqpoll.remove.php
	 * @param mixed $item 
	 * @return bool Returns true if the item was removed and false if the object 
	 * with given id does not exist in the poll set.
	 */
	public function remove (mixed $item): bool {}

	/**
	 * Count items in the poll set
	 * @link http://www.php.net/manual/en/zmqpoll.count.php
	 * @return int Returns an int representing the amount of items in the poll set.
	 */
	public function count (): int {}

	/**
	 * Clear the poll set
	 * @link http://www.php.net/manual/en/zmqpoll.clear.php
	 * @return ZMQPoll Returns the current object.
	 */
	public function clear (): ZMQPoll {}

	/**
	 * {@inheritdoc}
	 */
	public function items () {}

	/**
	 * {@inheritdoc}
	 */
	final private function __clone () {}

}

/**
 * @link http://www.php.net/manual/en/class.zmqdevice.php
 */
class ZMQDevice  {

	/**
	 * Construct a new device
	 * @link http://www.php.net/manual/en/zmqdevice.construct.php
	 * @param ZMQSocket $frontend Frontend parameter for the devices. Usually where there messages
	 * are coming.
	 * @param ZMQSocket $backend Backend parameter for the devices. Usually where there messages
	 * going to.
	 * @param ZMQSocket $listener [optional] Listener socket, which receives a copy of all messages going both directions.
	 * The type of this socket should be SUB, PULL or DEALER.
	 * @return ZMQSocket Call to this method will prepare the device. Usually devices are very long
	 * running processes so running this method from interactive script is not recommended. This
	 * method throw ZMQDeviceException if the device cannot be started.
	 */
	final public function __construct (ZMQSocket $frontend, ZMQSocket $backend, ZMQSocket $listener = null): ZMQSocket {}

	/**
	 * Run the new device
	 * @link http://www.php.net/manual/en/zmqdevice.run.php
	 * @return void Call to this method will block until the device is running. It is not recommended
	 * that devices are used from interactive scripts. On failure this method will throw
	 * ZMQDeviceException.
	 */
	public function run (): void {}

	/**
	 * Set the idle callback function
	 * @link http://www.php.net/manual/en/zmqdevice.setidlecallback.php
	 * @param callable $cb_func Callback function to invoke when the device is idle. Returning false
	 * or a value that evaluates to false from this function will cause the 
	 * device to stop.
	 * @param int $timeout How often to invoke the idle callback in milliseconds. The idle callback is invoked
	 * periodically when there is no activity on the device.
	 * The timeout value guarantees that there is at least this amount of milliseconds between
	 * invocations of the callback function.
	 * @param mixed $user_data [optional] Additional data to pass to the callback function.
	 * @return ZMQDevice On success this method returns the current object.
	 */
	public function setidlecallback (callable $cb_func, int $timeout, mixed $user_data = null): ZMQDevice {}

	/**
	 * Set the idle timeout
	 * @link http://www.php.net/manual/en/zmqdevice.setidletimeout.php
	 * @param int $timeout The idle callback timeout value.
	 * @return ZMQDevice On success this method returns the current object.
	 */
	public function setidletimeout (int $timeout): ZMQDevice {}

	/**
	 * Get the idle timeout
	 * @link http://www.php.net/manual/en/zmqdevice.getidletimeout.php
	 * @return ZMQDevice This method returns the idle callback timeout value.
	 */
	public function getidletimeout (): ZMQDevice {}

	/**
	 * Set the timer callback function
	 * @link http://www.php.net/manual/en/zmqdevice.settimercallback.php
	 * @param callable $cb_func Callback function to invoke when the device is idle. Returning false
	 * or a value that evaluates to false from this function will cause the 
	 * device to stop.
	 * @param int $timeout How often to invoke the idle callback in milliseconds. The idle callback is invoked
	 * periodically when there is no activity on the device.
	 * The timeout value guarantees that there is at least this amount of milliseconds between
	 * invocations of the callback function.
	 * @param mixed $user_data [optional] Additional data to pass to the callback function.
	 * @return ZMQDevice On success this method returns the current object.
	 */
	public function settimercallback (callable $cb_func, int $timeout, mixed $user_data = null): ZMQDevice {}

	/**
	 * Set the timer timeout
	 * @link http://www.php.net/manual/en/zmqdevice.settimertimeout.php
	 * @param int $timeout The timer callback timeout value.
	 * @return ZMQDevice On success this method returns the current object.
	 */
	public function settimertimeout (int $timeout): ZMQDevice {}

	/**
	 * Get the timer timeout
	 * @link http://www.php.net/manual/en/zmqdevice.gettimertimeout.php
	 * @return ZMQDevice This method returns the timer timeout value.
	 */
	public function gettimertimeout (): ZMQDevice {}

	/**
	 * {@inheritdoc}
	 */
	final private function __clone () {}

}

class ZMQException extends Exception implements Throwable, Stringable {

	/**
	 * Construct the exception
	 * @link http://www.php.net/manual/en/exception.construct.php
	 * @param string $message [optional] 
	 * @param int $code [optional] 
	 * @param Throwable|null $previous [optional] 
	 * @return string 
	 */
	public function __construct (string $message = '""', int $code = null, ?Throwable $previous = null): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * Gets the Exception message
	 * @link http://www.php.net/manual/en/exception.getmessage.php
	 * @return string Returns the Exception message as a string.
	 */
	final public function getMessage (): string {}

	/**
	 * Gets the Exception code
	 * @link http://www.php.net/manual/en/exception.getcode.php
	 * @return int Returns the exception code as int in
	 * Exception but possibly as other type in
	 * Exception descendants (for example as
	 * string in PDOException).
	 */
	final public function getCode (): int {}

	/**
	 * Gets the file in which the exception was created
	 * @link http://www.php.net/manual/en/exception.getfile.php
	 * @return string Returns the filename in which the exception was created.
	 */
	final public function getFile (): string {}

	/**
	 * Gets the line in which the exception was created
	 * @link http://www.php.net/manual/en/exception.getline.php
	 * @return int Returns the line number where the exception was created.
	 */
	final public function getLine (): int {}

	/**
	 * Gets the stack trace
	 * @link http://www.php.net/manual/en/exception.gettrace.php
	 * @return array Returns the Exception stack trace as an array.
	 */
	final public function getTrace (): array {}

	/**
	 * Returns previous Throwable
	 * @link http://www.php.net/manual/en/exception.getprevious.php
	 * @return Throwable|null Returns the previous Throwable if available 
	 * or null otherwise.
	 */
	final public function getPrevious (): ?Throwable {}

	/**
	 * Gets the stack trace as a string
	 * @link http://www.php.net/manual/en/exception.gettraceasstring.php
	 * @return string Returns the Exception stack trace as a string.
	 */
	final public function getTraceAsString (): string {}

	/**
	 * String representation of the exception
	 * @link http://www.php.net/manual/en/exception.tostring.php
	 * @return string Returns the string representation of the exception.
	 */
	public function __toString (): string {}

}

final class ZMQContextException extends ZMQException implements Stringable, Throwable {

	/**
	 * Construct the exception
	 * @link http://www.php.net/manual/en/exception.construct.php
	 * @param string $message [optional] 
	 * @param int $code [optional] 
	 * @param Throwable|null $previous [optional] 
	 * @return string 
	 */
	public function __construct (string $message = '""', int $code = null, ?Throwable $previous = null): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * Gets the Exception message
	 * @link http://www.php.net/manual/en/exception.getmessage.php
	 * @return string Returns the Exception message as a string.
	 */
	final public function getMessage (): string {}

	/**
	 * Gets the Exception code
	 * @link http://www.php.net/manual/en/exception.getcode.php
	 * @return int Returns the exception code as int in
	 * Exception but possibly as other type in
	 * Exception descendants (for example as
	 * string in PDOException).
	 */
	final public function getCode (): int {}

	/**
	 * Gets the file in which the exception was created
	 * @link http://www.php.net/manual/en/exception.getfile.php
	 * @return string Returns the filename in which the exception was created.
	 */
	final public function getFile (): string {}

	/**
	 * Gets the line in which the exception was created
	 * @link http://www.php.net/manual/en/exception.getline.php
	 * @return int Returns the line number where the exception was created.
	 */
	final public function getLine (): int {}

	/**
	 * Gets the stack trace
	 * @link http://www.php.net/manual/en/exception.gettrace.php
	 * @return array Returns the Exception stack trace as an array.
	 */
	final public function getTrace (): array {}

	/**
	 * Returns previous Throwable
	 * @link http://www.php.net/manual/en/exception.getprevious.php
	 * @return Throwable|null Returns the previous Throwable if available 
	 * or null otherwise.
	 */
	final public function getPrevious (): ?Throwable {}

	/**
	 * Gets the stack trace as a string
	 * @link http://www.php.net/manual/en/exception.gettraceasstring.php
	 * @return string Returns the Exception stack trace as a string.
	 */
	final public function getTraceAsString (): string {}

	/**
	 * String representation of the exception
	 * @link http://www.php.net/manual/en/exception.tostring.php
	 * @return string Returns the string representation of the exception.
	 */
	public function __toString (): string {}

}

final class ZMQSocketException extends ZMQException implements Stringable, Throwable {

	/**
	 * Construct the exception
	 * @link http://www.php.net/manual/en/exception.construct.php
	 * @param string $message [optional] 
	 * @param int $code [optional] 
	 * @param Throwable|null $previous [optional] 
	 * @return string 
	 */
	public function __construct (string $message = '""', int $code = null, ?Throwable $previous = null): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * Gets the Exception message
	 * @link http://www.php.net/manual/en/exception.getmessage.php
	 * @return string Returns the Exception message as a string.
	 */
	final public function getMessage (): string {}

	/**
	 * Gets the Exception code
	 * @link http://www.php.net/manual/en/exception.getcode.php
	 * @return int Returns the exception code as int in
	 * Exception but possibly as other type in
	 * Exception descendants (for example as
	 * string in PDOException).
	 */
	final public function getCode (): int {}

	/**
	 * Gets the file in which the exception was created
	 * @link http://www.php.net/manual/en/exception.getfile.php
	 * @return string Returns the filename in which the exception was created.
	 */
	final public function getFile (): string {}

	/**
	 * Gets the line in which the exception was created
	 * @link http://www.php.net/manual/en/exception.getline.php
	 * @return int Returns the line number where the exception was created.
	 */
	final public function getLine (): int {}

	/**
	 * Gets the stack trace
	 * @link http://www.php.net/manual/en/exception.gettrace.php
	 * @return array Returns the Exception stack trace as an array.
	 */
	final public function getTrace (): array {}

	/**
	 * Returns previous Throwable
	 * @link http://www.php.net/manual/en/exception.getprevious.php
	 * @return Throwable|null Returns the previous Throwable if available 
	 * or null otherwise.
	 */
	final public function getPrevious (): ?Throwable {}

	/**
	 * Gets the stack trace as a string
	 * @link http://www.php.net/manual/en/exception.gettraceasstring.php
	 * @return string Returns the Exception stack trace as a string.
	 */
	final public function getTraceAsString (): string {}

	/**
	 * String representation of the exception
	 * @link http://www.php.net/manual/en/exception.tostring.php
	 * @return string Returns the string representation of the exception.
	 */
	public function __toString (): string {}

}

final class ZMQPollException extends ZMQException implements Stringable, Throwable {

	/**
	 * Construct the exception
	 * @link http://www.php.net/manual/en/exception.construct.php
	 * @param string $message [optional] 
	 * @param int $code [optional] 
	 * @param Throwable|null $previous [optional] 
	 * @return string 
	 */
	public function __construct (string $message = '""', int $code = null, ?Throwable $previous = null): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * Gets the Exception message
	 * @link http://www.php.net/manual/en/exception.getmessage.php
	 * @return string Returns the Exception message as a string.
	 */
	final public function getMessage (): string {}

	/**
	 * Gets the Exception code
	 * @link http://www.php.net/manual/en/exception.getcode.php
	 * @return int Returns the exception code as int in
	 * Exception but possibly as other type in
	 * Exception descendants (for example as
	 * string in PDOException).
	 */
	final public function getCode (): int {}

	/**
	 * Gets the file in which the exception was created
	 * @link http://www.php.net/manual/en/exception.getfile.php
	 * @return string Returns the filename in which the exception was created.
	 */
	final public function getFile (): string {}

	/**
	 * Gets the line in which the exception was created
	 * @link http://www.php.net/manual/en/exception.getline.php
	 * @return int Returns the line number where the exception was created.
	 */
	final public function getLine (): int {}

	/**
	 * Gets the stack trace
	 * @link http://www.php.net/manual/en/exception.gettrace.php
	 * @return array Returns the Exception stack trace as an array.
	 */
	final public function getTrace (): array {}

	/**
	 * Returns previous Throwable
	 * @link http://www.php.net/manual/en/exception.getprevious.php
	 * @return Throwable|null Returns the previous Throwable if available 
	 * or null otherwise.
	 */
	final public function getPrevious (): ?Throwable {}

	/**
	 * Gets the stack trace as a string
	 * @link http://www.php.net/manual/en/exception.gettraceasstring.php
	 * @return string Returns the Exception stack trace as a string.
	 */
	final public function getTraceAsString (): string {}

	/**
	 * String representation of the exception
	 * @link http://www.php.net/manual/en/exception.tostring.php
	 * @return string Returns the string representation of the exception.
	 */
	public function __toString (): string {}

}

final class ZMQDeviceException extends ZMQException implements Stringable, Throwable {

	/**
	 * Construct the exception
	 * @link http://www.php.net/manual/en/exception.construct.php
	 * @param string $message [optional] 
	 * @param int $code [optional] 
	 * @param Throwable|null $previous [optional] 
	 * @return string 
	 */
	public function __construct (string $message = '""', int $code = null, ?Throwable $previous = null): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * Gets the Exception message
	 * @link http://www.php.net/manual/en/exception.getmessage.php
	 * @return string Returns the Exception message as a string.
	 */
	final public function getMessage (): string {}

	/**
	 * Gets the Exception code
	 * @link http://www.php.net/manual/en/exception.getcode.php
	 * @return int Returns the exception code as int in
	 * Exception but possibly as other type in
	 * Exception descendants (for example as
	 * string in PDOException).
	 */
	final public function getCode (): int {}

	/**
	 * Gets the file in which the exception was created
	 * @link http://www.php.net/manual/en/exception.getfile.php
	 * @return string Returns the filename in which the exception was created.
	 */
	final public function getFile (): string {}

	/**
	 * Gets the line in which the exception was created
	 * @link http://www.php.net/manual/en/exception.getline.php
	 * @return int Returns the line number where the exception was created.
	 */
	final public function getLine (): int {}

	/**
	 * Gets the stack trace
	 * @link http://www.php.net/manual/en/exception.gettrace.php
	 * @return array Returns the Exception stack trace as an array.
	 */
	final public function getTrace (): array {}

	/**
	 * Returns previous Throwable
	 * @link http://www.php.net/manual/en/exception.getprevious.php
	 * @return Throwable|null Returns the previous Throwable if available 
	 * or null otherwise.
	 */
	final public function getPrevious (): ?Throwable {}

	/**
	 * Gets the stack trace as a string
	 * @link http://www.php.net/manual/en/exception.gettraceasstring.php
	 * @return string Returns the Exception stack trace as a string.
	 */
	final public function getTraceAsString (): string {}

	/**
	 * String representation of the exception
	 * @link http://www.php.net/manual/en/exception.tostring.php
	 * @return string Returns the string representation of the exception.
	 */
	public function __toString (): string {}

}
// End of zmq v.1.1.3
