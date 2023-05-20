<?php

// Start of zmq v.1.1.3

/**
 * @link http://www.php.net/manual/en/class.zmq.php
 */
class ZMQ  {
	const SOCKET_PAIR = 0;
	const SOCKET_PUB = 1;
	const SOCKET_SUB = 2;
	const SOCKET_REQ = 3;
	const SOCKET_REP = 4;
	const SOCKET_XREQ = 5;
	const SOCKET_XREP = 6;
	const SOCKET_PUSH = 8;
	const SOCKET_PULL = 7;
	const SOCKET_DEALER = 5;
	const SOCKET_ROUTER = 6;
	const SOCKET_XSUB = 10;
	const SOCKET_XPUB = 9;
	const SOCKET_STREAM = 11;
	const SOCKET_UPSTREAM = 7;
	const SOCKET_DOWNSTREAM = 8;
	const POLL_IN = 1;
	const POLL_OUT = 2;
	const MODE_SNDMORE = 2;
	const MODE_NOBLOCK = 1;
	const MODE_DONTWAIT = 1;
	const ERR_INTERNAL = -99;
	const ERR_EAGAIN = 35;
	const ERR_ENOTSUP = 45;
	const ERR_EFSM = 156384763;
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
	const SOCKOPT_IPV6 = 42;
	const SOCKOPT_IMMEDIATE = 39;
	const SOCKOPT_SNDHWM = 23;
	const SOCKOPT_RCVHWM = 24;
	const SOCKOPT_MAXMSGSIZE = 22;
	const SOCKOPT_MULTICAST_HOPS = 25;
	const SOCKOPT_XPUB_VERBOSE = 40;
	const SOCKOPT_TCP_KEEPALIVE = 34;
	const SOCKOPT_TCP_KEEPALIVE_IDLE = 36;
	const SOCKOPT_TCP_KEEPALIVE_CNT = 35;
	const SOCKOPT_TCP_KEEPALIVE_INTVL = 37;
	const SOCKOPT_TCP_ACCEPT_FILTER = 38;
	const SOCKOPT_LAST_ENDPOINT = 32;
	const SOCKOPT_ROUTER_RAW = 41;
	const SOCKOPT_IPV4ONLY = 31;
	const SOCKOPT_DELAY_ATTACH_ON_CONNECT = 39;
	const SOCKOPT_HWM = 2001;
	const SOCKOPT_AFFINITY = 4;
	const SOCKOPT_IDENTITY = 5;
	const SOCKOPT_RATE = 8;
	const SOCKOPT_RECOVERY_IVL = 9;
	const SOCKOPT_RCVTIMEO = 27;
	const SOCKOPT_SNDTIMEO = 28;
	const SOCKOPT_SNDBUF = 11;
	const SOCKOPT_RCVBUF = 12;
	const SOCKOPT_LINGER = 17;
	const SOCKOPT_RECONNECT_IVL = 18;
	const SOCKOPT_RECONNECT_IVL_MAX = 21;
	const SOCKOPT_BACKLOG = 19;
	const SOCKOPT_SUBSCRIBE = 6;
	const SOCKOPT_UNSUBSCRIBE = 7;
	const SOCKOPT_TYPE = 16;
	const SOCKOPT_RCVMORE = 13;
	const SOCKOPT_FD = 14;
	const SOCKOPT_EVENTS = 15;
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
	 */
	private function __construct () {}

	public static function clock () {}

	/**
	 * @param mixed $data
	 */
	public static function z85encode ($data = null) {}

	/**
	 * @param mixed $data
	 */
	public static function z85decode ($data = null) {}

	public static function curvekeypair () {}

}

/**
 * @link http://www.php.net/manual/en/class.zmqcontext.php
 */
class ZMQContext  {

	/**
	 * Construct a new ZMQContext object
	 * @link http://www.php.net/manual/en/zmqcontext.construct.php
	 * @param mixed $io_threads [optional]
	 * @param mixed $persistent [optional]
	 */
	final public function __construct ($io_threads = null, $persistent = null) {}

	public static function acquire () {}

	public function getsocketcount () {}

	/**
	 * Create a new socket
	 * @link http://www.php.net/manual/en/zmqcontext.getsocket.php
	 * @param int $type ZMQ::SOCKET_&#42; constant to specify socket type.
	 * @param string $persistent_id [optional] If persistent_id is specified the socket will be persisted over multiple requests.
	 * @param callable $on_new_socket [optional] Callback function, which is executed when a new socket structure is created. This function does not get invoked
	 * if the underlying persistent connection is re-used. The callback takes ZMQSocket and persistent_id as two arguments.
	 * @return ZMQSocket a ZMQSocket object.
	 */
	public function getsocket (int $type, string $persistent_id = null, callable $on_new_socket = null) {}

	/**
	 * Whether the context is persistent
	 * @link http://www.php.net/manual/en/zmqcontext.ispersistent.php
	 * @return bool true if the context is persistent and false if the context is non-persistent.
	 */
	public function ispersistent () {}

	final private function __clone () {}

	/**
	 * Set a socket option
	 * @link http://www.php.net/manual/en/zmqcontext.setopt.php
	 * @param int $key One of the ZMQ::CTXOPT_&#42; constants.
	 * @param mixed $value The value of the parameter.
	 * @return ZMQContext the current object. Throws ZMQContextException on error.
	 */
	public function setOpt (int $key, $value) {}

	/**
	 * Get context option
	 * @link http://www.php.net/manual/en/zmqcontext.getopt.php
	 * @param string $key An integer representing the option.
	 * See the ZMQ::CTXOPT_&#42; constants.
	 * @return mixed either a string or an integer depending on key. Throws
	 * ZMQContextException on error.
	 */
	public function getOpt (string $key) {}

}

/**
 * @link http://www.php.net/manual/en/class.zmqsocket.php
 */
class ZMQSocket  {

	/**
	 * Construct a new ZMQSocket
	 * @link http://www.php.net/manual/en/zmqsocket.construct.php
	 * @param ZMQContext $ZMQContext
	 * @param mixed $type
	 * @param mixed $persistent_id [optional]
	 * @param mixed $on_new_socket [optional]
	 */
	final public function __construct (ZMQContext $ZMQContext, $type = null, $persistent_id = null, $on_new_socket = null) {}

	/**
	 * Sends a message
	 * @link http://www.php.net/manual/en/zmqsocket.send.php
	 * @param string $message The message to send.
	 * @param int $mode [optional] Pass mode flags to receive multipart messages or non-blocking operation. 
	 * See ZMQ::MODE_&#42; constants.
	 * @return ZMQSocket the current object. Throws ZMQSocketException on error. 
	 * If ZMQ::MODE_NOBLOCK is used and the operation would 
	 * block boolean false shall be returned.
	 */
	public function send (string $message, int $mode = null) {}

	/**
	 * Receives a message
	 * @link http://www.php.net/manual/en/zmqsocket.recv.php
	 * @param int $mode [optional] Pass mode flags to receive multipart messages or non-blocking operation. 
	 * See ZMQ::MODE_&#42; constants.
	 * @return string the message. If ZMQ::MODE_DONTWAIT 
	 * is used and the operation would block false shall be returned.
	 */
	public function recv (int $mode = null) {}

	/**
	 * Sends a multipart message
	 * @link http://www.php.net/manual/en/zmqsocket.sendmulti.php
	 * @param array $message The message to send - an array of strings
	 * @param int $mode [optional] Pass mode flags to receive multipart messages or non-blocking operation. 
	 * See ZMQ::MODE_&#42; constants.
	 * @return ZMQSocket the current object. Throws ZMQSocketException on error. 
	 * If ZMQ::MODE_NOBLOCK is used and the operation would 
	 * block boolean false shall be returned.
	 */
	public function sendmulti (array $message, int $mode = null) {}

	/**
	 * Receives a multipart message
	 * @link http://www.php.net/manual/en/zmqsocket.recvmulti.php
	 * @param int $mode [optional] Pass mode flags to receive multipart messages or non-blocking operation. 
	 * See ZMQ::MODE_&#42; constants.
	 * @return array the array of message parts. Throws ZMQSocketException in error. 
	 * If ZMQ::MODE_NOBLOCK is used and the operation would 
	 * block boolean false shall be returned.
	 */
	public function recvmulti (int $mode = null) {}

	/**
	 * Bind the socket
	 * @link http://www.php.net/manual/en/zmqsocket.bind.php
	 * @param string $dsn The bind dsn, for example transport://address.
	 * @param bool $force [optional] Tries to bind even if the socket has already been bound to the given endpoint.
	 * @return ZMQSocket the current object. Throws ZMQSocketException on error.
	 */
	public function bind (string $dsn, bool $force = null) {}

	/**
	 * Connect the socket
	 * @link http://www.php.net/manual/en/zmqsocket.connect.php
	 * @param string $dsn The connect dsn, for example transport://address.
	 * @param bool $force [optional] Tries to connect even if the socket has already been connected to given endpoint.
	 * @return ZMQSocket the current object.
	 */
	public function connect (string $dsn, bool $force = null) {}

	/**
	 * @param mixed $dsn
	 * @param mixed $events [optional]
	 */
	public function monitor ($dsn = null, $events = null) {}

	/**
	 * @param mixed $flags [optional]
	 */
	public function recvevent ($flags = null) {}

	/**
	 * Unbind the socket
	 * @link http://www.php.net/manual/en/zmqsocket.unbind.php
	 * @param string $dsn The previously bound dsn, for example transport://address.
	 * @return ZMQSocket the current object. Throws ZMQSocketException on error.
	 */
	public function unbind (string $dsn) {}

	/**
	 * Disconnect a socket
	 * @link http://www.php.net/manual/en/zmqsocket.disconnect.php
	 * @param string $dsn The connect dsn, for example transport://address.
	 * @return ZMQSocket the current object. Throws ZMQSocketException on error.
	 */
	public function disconnect (string $dsn) {}

	/**
	 * Set a socket option
	 * @link http://www.php.net/manual/en/zmqsocket.setsockopt.php
	 * @param int $key One of the ZMQ::SOCKOPT_&#42; constants.
	 * @param mixed $value The value of the parameter.
	 * @return ZMQSocket the current object. Throws ZMQSocketException on error.
	 */
	public function setsockopt (int $key, $value) {}

	/**
	 * Get list of endpoints
	 * @link http://www.php.net/manual/en/zmqsocket.getendpoints.php
	 * @return array an array containing elements 'bind' and 'connect'.
	 */
	public function getendpoints () {}

	/**
	 * Get the socket type
	 * @link http://www.php.net/manual/en/zmqsocket.getsockettype.php
	 * @return int an integer representing the socket type. The integer can be compared against
	 * ZMQ::SOCKET_&#42; constants.
	 */
	public function getsockettype () {}

	/**
	 * Whether the socket is persistent
	 * @link http://www.php.net/manual/en/zmqsocket.ispersistent.php
	 * @return bool a boolean based on whether the socket is persistent or not.
	 */
	public function ispersistent () {}

	/**
	 * Get the persistent id
	 * @link http://www.php.net/manual/en/zmqsocket.getpersistentid.php
	 * @return string the persistent id string assigned of the object and null if socket is not persistent.
	 */
	public function getpersistentid () {}

	/**
	 * Get socket option
	 * @link http://www.php.net/manual/en/zmqsocket.getsockopt.php
	 * @param string $key An integer representing the option.
	 * See the ZMQ::SOCKOPT_&#42; constants.
	 * @return mixed either a string or an integer depending on key. Throws
	 * ZMQSocketException on error.
	 */
	public function getsockopt (string $key) {}

	final private function __clone () {}

	/**
	 * @param mixed $message
	 * @param mixed $mode [optional]
	 */
	public function sendmsg ($message = null, $mode = null) {}

	/**
	 * @param mixed $mode [optional]
	 */
	public function recvmsg ($mode = null) {}

}

/**
 * @link http://www.php.net/manual/en/class.zmqpoll.php
 */
class ZMQPoll  {

	/**
	 * Add item to the poll set
	 * @link http://www.php.net/manual/en/zmqpoll.add.php
	 * @param mixed $entry ZMQSocket object or a PHP stream resource
	 * @param int $type Defines what activity the socket is polled for. 
	 * See ZMQ::POLL_IN and ZMQ::POLL_OUT constants.
	 * @return string a string id of the added item which can be later used to remove the item. 
	 * Throws ZMQPollException on error.
	 */
	public function add ($entry, int $type) {}

	/**
	 * Poll the items
	 * @link http://www.php.net/manual/en/zmqpoll.poll.php
	 * @param array $readable Array where readable ZMQSockets/PHP streams are returned. The array
	 * will be cleared at the beginning of the operation.
	 * @param array $writable Array where writable ZMQSockets/PHP streams are returned. The array
	 * will be cleared at the beginning of the operation.
	 * @param int $timeout [optional] Timeout for the operation. -1 means that poll waits until
	 * at least one item has activity. Please note that starting from 
	 * version 1.0.0 the poll timeout is defined in milliseconds, rather
	 * than microseconds.
	 * @return int an integer representing the amount of items with activity.
	 */
	public function poll (array &$readable, array &$writable, int $timeout = null) {}

	/**
	 * Get poll errors
	 * @link http://www.php.net/manual/en/zmqpoll.getlasterrors.php
	 * @return array an array containing ids for the items that had errors in the last poll. Empty array is 
	 * returned if there were no errors.
	 */
	public function getlasterrors () {}

	/**
	 * Remove item from poll set
	 * @link http://www.php.net/manual/en/zmqpoll.remove.php
	 * @param mixed $item The ZMQSocket object, PHP stream or string id of the item.
	 * @return bool true if the item was removed and false if the object 
	 * with given id does not exist in the poll set.
	 */
	public function remove ($item) {}

	/**
	 * Count items in the poll set
	 * @link http://www.php.net/manual/en/zmqpoll.count.php
	 * @return int an integer representing the amount of items in the poll set.
	 */
	public function count () {}

	/**
	 * Clear the poll set
	 * @link http://www.php.net/manual/en/zmqpoll.clear.php
	 * @return ZMQPoll the current object.
	 */
	public function clear () {}

	public function items () {}

	final private function __clone () {}

}

/**
 * @link http://www.php.net/manual/en/class.zmqdevice.php
 */
class ZMQDevice  {

	/**
	 * Construct a new device
	 * @link http://www.php.net/manual/en/zmqdevice.construct.php
	 * @param ZMQSocket $frontend
	 * @param ZMQSocket $backend
	 * @param ZMQSocket $capture [optional]
	 */
	final public function __construct (ZMQSocket $frontend, ZMQSocket $backend, ZMQSocket $capture = null) {}

	/**
	 * Run the new device
	 * @link http://www.php.net/manual/en/zmqdevice.run.php
	 * @return void Call to this method will block until the device is running. It is not recommended
	 * that devices are used from interactive scripts. On failure this method will throw
	 * ZMQDeviceException.
	 */
	public function run () {}

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
	public function setidlecallback (callable $cb_func, int $timeout, $user_data = null) {}

	/**
	 * Set the idle timeout
	 * @link http://www.php.net/manual/en/zmqdevice.setidletimeout.php
	 * @param int $timeout The idle callback timeout value.
	 * @return ZMQDevice On success this method returns the current object.
	 */
	public function setidletimeout (int $timeout) {}

	/**
	 * Get the idle timeout
	 * @link http://www.php.net/manual/en/zmqdevice.getidletimeout.php
	 * @return ZMQDevice This method returns the idle callback timeout value.
	 */
	public function getidletimeout () {}

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
	public function settimercallback (callable $cb_func, int $timeout, $user_data = null) {}

	/**
	 * Set the timer timeout
	 * @link http://www.php.net/manual/en/zmqdevice.settimertimeout.php
	 * @param int $timeout The timer callback timeout value.
	 * @return ZMQDevice On success this method returns the current object.
	 */
	public function settimertimeout (int $timeout) {}

	/**
	 * Get the timer timeout
	 * @link http://www.php.net/manual/en/zmqdevice.gettimertimeout.php
	 * @return ZMQDevice This method returns the timer timeout value.
	 */
	public function gettimertimeout () {}

	final private function __clone () {}

}

/**
 * @link http://www.php.net/manual/en/class.zmqexception.php
 */
class ZMQException extends Exception implements Throwable, Stringable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

/**
 * @link http://www.php.net/manual/en/class.zmqcontextexception.php
 */
final class ZMQContextException extends ZMQException implements Stringable, Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

/**
 * @link http://www.php.net/manual/en/class.zmqsocketexception.php
 */
final class ZMQSocketException extends ZMQException implements Stringable, Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

/**
 * @link http://www.php.net/manual/en/class.zmqpollexception.php
 */
final class ZMQPollException extends ZMQException implements Stringable, Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}

/**
 * @link http://www.php.net/manual/en/class.zmqdeviceexception.php
 */
final class ZMQDeviceException extends ZMQException implements Stringable, Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	/**
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, Throwable|null $previous = null) {}

	public function __wakeup () {}

	final public function getMessage (): string {}

	final public function getCode () {}

	final public function getFile (): string {}

	final public function getLine (): int {}

	final public function getTrace (): array {}

	final public function getPrevious (): ?Throwable {}

	final public function getTraceAsString (): string {}

	public function __toString (): string {}

}
// End of zmq v.1.1.3
