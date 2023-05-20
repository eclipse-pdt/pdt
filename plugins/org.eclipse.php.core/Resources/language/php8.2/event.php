<?php

// Start of event v.3.0.8

/**
 * Event
 * class represents and event firing on a file descriptor being ready to read
 * from or write to; a file descriptor becoming ready to read from or write
 * to(edge-triggered I/O only); a timeout expiring; a signal occurring; a
 * user-triggered event.
 * <p>Every event is associated with
 * EventBase
 * . However, event will never fire until it is
 * added
 * (via
 * Event::add
 * ). An added event remains in
 * pending
 * state until the registered event occurs, thus turning it to
 * active
 * state. To handle events user may register a callback which is called when
 * event becomes active. If event is configured
 * persistent
 * , it remains pending. If it is not persistent, it stops being pending when
 * it's callback runs.
 * Event::del
 * method
 * deletes
 * event, thus making it non-pending. By means of
 * Event::add
 * method it could be added again.</p>
 * @link http://www.php.net/manual/en/class.event.php
 */
final class Event  {
	const ET = 32;
	const PERSIST = 16;
	const READ = 2;
	const WRITE = 4;
	const SIGNAL = 8;
	const TIMEOUT = 1;

	public $pending;
	public $data;


	/**
	 * Constructs Event object
	 * @link http://www.php.net/manual/en/event.construct.php
	 * @param EventBase $base
	 * @param mixed $fd
	 * @param int $what
	 * @param callable $cb
	 * @param mixed $arg [optional]
	 */
	public function __construct (EventBase $base, mixed $fd = null, int $what, callable $cb, mixed $arg = null) {}

	/**
	 * Make event non-pending and free resources allocated for this
	 * event
	 * @link http://www.php.net/manual/en/event.free.php
	 * @return void 
	 */
	public function free (): void {}

	/**
	 * Re-configures event
	 * @link http://www.php.net/manual/en/event.set.php
	 * @param EventBase $base The event base to associate the event with.
	 * @param mixed $fd Stream resource, socket resource, or numeric file descriptor. For timer
	 * events pass
	 * -1
	 * . For signal events pass the signal number, e.g.
	 * SIGHUP
	 * .
	 * @param int $what [optional] See
	 * Event flags
	 * .
	 * @param callable $cb [optional] The event callback. See
	 * Event callbacks
	 * .
	 * @param mixed $arg [optional] Custom data associated with the event. It will be passed to the callback
	 * when the event becomes active.
	 * @return bool true on success or false on failure
	 */
	public function set (EventBase $base, $fd, int $what = null, callable $cb = null, $arg = null): bool {}

	/**
	 * Returns array with of the names of the methods supported in this version of Libevent
	 * @link http://www.php.net/manual/en/event.getsupportedmethods.php
	 * @return array array.
	 */
	public static function getSupportedMethods (): array {}

	/**
	 * Makes event pending
	 * @link http://www.php.net/manual/en/event.add.php
	 * @param float $timeout [optional] Timeout in seconds.
	 * @return bool true on success or false on failure
	 */
	public function add (float $timeout = null): bool {}

	/**
	 * Makes event non-pending
	 * @link http://www.php.net/manual/en/event.del.php
	 * @return bool true on success or false on failure
	 */
	public function del (): bool {}

	/**
	 * Set event priority
	 * @link http://www.php.net/manual/en/event.setpriority.php
	 * @param int $priority The event priority.
	 * @return bool true on success or false on failure
	 */
	public function setPriority (int $priority): bool {}

	/**
	 * Detects whether event is pending or scheduled
	 * @link http://www.php.net/manual/en/event.pending.php
	 * @param int $flags One of, or a composition of the following constants:
	 * Event::READ
	 * ,
	 * Event::WRITE
	 * ,
	 * Event::TIMEOUT
	 * ,
	 * Event::SIGNAL
	 * .
	 * @return bool true if event is pending or scheduled. Otherwise false.
	 */
	public function pending (int $flags): bool {}

	public function removeTimer (): bool {}

	/**
	 * Constructs timer event object
	 * @link http://www.php.net/manual/en/event.timer.php
	 * @param EventBase $base The associated event base object.
	 * @param callable $cb The signal event callback. See
	 * Event callbacks
	 * .
	 * @param mixed $arg [optional] Custom data. If specified, it will be passed to the callback when event
	 * triggers.
	 * @return Event Event object on success. Otherwise false.
	 */
	public static function timer (EventBase $base, callable $cb, $arg = null): Event {}

	/**
	 * Re-configures timer event
	 * @link http://www.php.net/manual/en/event.settimer.php
	 * @param EventBase $base The event base to associate with.
	 * @param callable $cb The timer event callback. See
	 * Event callbacks
	 * .
	 * @param mixed $arg [optional] Custom data. If specified, it will be passed to the callback when event
	 * triggers.
	 * @return bool true on success or false on failure
	 */
	public function setTimer (EventBase $base, callable $cb, $arg = null): bool {}

	/**
	 * Constructs signal event object
	 * @link http://www.php.net/manual/en/event.signal.php
	 * @param EventBase $base The associated event base object.
	 * @param int $signum The signal number.
	 * @param callable $cb The signal event callback. See
	 * Event callbacks
	 * .
	 * @param mixed $arg [optional] Custom data. If specified, it will be passed to the callback when event
	 * triggers.
	 * @return Event Event object on success. Otherwise false.
	 */
	public static function signal (EventBase $base, int $signum, callable $cb, $arg = null): Event {}

	/**
	 * Alias: Event::add
	 * @link http://www.php.net/manual/en/event.addtimer.php
	 * @param float $timeout [optional]
	 */
	public function addTimer (float $timeout = -1): bool {}

	/**
	 * Alias: Event::del
	 * @link http://www.php.net/manual/en/event.deltimer.php
	 */
	public function delTimer (): bool {}

	/**
	 * Alias: Event::add
	 * @link http://www.php.net/manual/en/event.addsignal.php
	 * @param float $timeout [optional]
	 */
	public function addSignal (float $timeout = -1): bool {}

	/**
	 * Alias: Event::del
	 * @link http://www.php.net/manual/en/event.delsignal.php
	 */
	public function delSignal (): bool {}

}

/**
 * EventBase
 * class represents libevent's event base structure. It holds a set of events
 * and can poll to determine which events are active.
 * <p>Each event base has a
 * method
 * , or a
 * backend
 * that it uses to determine which events are ready. The recognized methods
 * are:
 * select
 * ,
 * poll
 * ,
 * epoll
 * ,
 * kqueue
 * ,
 * devpoll
 * ,
 * evport
 * and
 * win32
 * .</p>
 * <p>To configure event base to use, or avoid specific backend
 * EventConfig
 * class can be used.</p>
 * <p>Do
 * NOT
 * destroy the
 * EventBase
 * object as long as resources of the associated
 * Event
 * objects are not released. Otherwise, it will lead to unpredictable
 * results!</p>
 * @link http://www.php.net/manual/en/class.eventbase.php
 */
final class EventBase  {
	const LOOP_ONCE = 1;
	const LOOP_NONBLOCK = 2;
	const NOLOCK = 1;
	const STARTUP_IOCP = 4;
	const NO_CACHE_TIME = 8;
	const EPOLL_USE_CHANGELIST = 16;
	const IGNORE_ENV = 2;
	const PRECISE_TIMER = 32;


	/**
	 * Constructs EventBase object
	 * @link http://www.php.net/manual/en/eventbase.construct.php
	 * @param EventConfig|null $cfg [optional]
	 */
	public function __construct (EventConfig|null $cfg = null) {}

	final public function __sleep (): array {}

	final public function __wakeup (): void {}

	/**
	 * Returns event method in use
	 * @link http://www.php.net/manual/en/eventbase.getmethod.php
	 * @return string String representing used event method(backend).
	 */
	public function getMethod (): string {}

	/**
	 * Returns bitmask of features supported
	 * @link http://www.php.net/manual/en/eventbase.getfeatures.php
	 * @return int integer representing a bitmask of supported features. See
	 * EventConfig::FEATURE_&#42; constants
	 * .
	 */
	public function getFeatures (): int {}

	/**
	 * Sets number of priorities per event base
	 * @link http://www.php.net/manual/en/eventbase.priorityinit.php
	 * @param int $n_priorities The number of priorities per event base.
	 * @return bool true on success or false on failure
	 */
	public function priorityInit (int $n_priorities): bool {}

	/**
	 * Dispatch pending events
	 * @link http://www.php.net/manual/en/eventbase.loop.php
	 * @param int $flags [optional] Optional flags. One of
	 * EventBase::LOOP_&#42;
	 * constants. See
	 * EventBase constants
	 * .
	 * @return bool true on success or false on failure
	 */
	public function loop (int $flags = null): bool {}

	/**
	 * Dispatch pending events
	 * @link http://www.php.net/manual/en/eventbase.dispatch.php
	 * @return void true on success or false on failure
	 */
	public function dispatch (): bool {}

	/**
	 * Stop dispatching events
	 * @link http://www.php.net/manual/en/eventbase.exit.php
	 * @param float $timeout [optional] Optional number of seconds after which the event base should stop
	 * dispatching events.
	 * @return bool true on success or false on failure
	 */
	public function exit (float $timeout = null): bool {}

	/**
	 * @param Event $event
	 */
	public function set (Event $event): bool {}

	/**
	 * Tells event_base to stop dispatching events
	 * @link http://www.php.net/manual/en/eventbase.stop.php
	 * @return bool true on success or false on failure
	 */
	public function stop (): bool {}

	/**
	 * Checks if the event loop was told to exit
	 * @link http://www.php.net/manual/en/eventbase.gotstop.php
	 * @return bool true, event loop was told to stop by
	 * EventBase::stop
	 * . Otherwise false.
	 */
	public function gotStop (): bool {}

	/**
	 * Checks if the event loop was told to exit
	 * @link http://www.php.net/manual/en/eventbase.gotexit.php
	 * @return bool true, event loop was told to exit by
	 * EventBase::exit
	 * . Otherwise false.
	 */
	public function gotExit (): bool {}

	/**
	 * Returns the current event base time
	 * @link http://www.php.net/manual/en/eventbase.gettimeofdaycached.php
	 * @return float the current
	 * event base
	 * time. On failure returns null.
	 */
	public function getTimeOfDayCached (): float {}

	/**
	 * Re-initialize event base(after a fork)
	 * @link http://www.php.net/manual/en/eventbase.reinit.php
	 * @return bool true on success or false on failure
	 */
	public function reInit (): bool {}

	/**
	 * Free resources allocated for this event base
	 * @link http://www.php.net/manual/en/eventbase.free.php
	 * @return void 
	 */
	public function free (): void {}

	public function updateCacheTime (): bool {}

	public function resume (): bool {}

}

/**
 * Represents configuration structure which could be used in construction of
 * the
 * EventBase
 * .
 * @link http://www.php.net/manual/en/class.eventconfig.php
 */
final class EventConfig  {
	const FEATURE_ET = 1;
	const FEATURE_O1 = 2;
	const FEATURE_FDS = 4;


	/**
	 * Constructs EventConfig object
	 * @link http://www.php.net/manual/en/eventconfig.construct.php
	 */
	public function __construct () {}

	final public function __sleep (): array {}

	final public function __wakeup (): void {}

	/**
	 * Tells libevent to avoid specific event method
	 * @link http://www.php.net/manual/en/eventconfig.avoidmethod.php
	 * @param string $method The backend method to avoid. See
	 * EventConfig constants
	 * .
	 * @return bool true on success or false on failure
	 */
	public function avoidMethod (string $method): bool {}

	/**
	 * Enters a required event method feature that the application demands
	 * @link http://www.php.net/manual/en/eventconfig.requirefeatures.php
	 * @param int $feature Bitmask of required features. See
	 * EventConfig::FEATURE_&#42; constants
	 * @return bool true on success or false on failure
	 */
	public function requireFeatures (int $feature): bool {}

	/**
	 * Prevents priority inversion
	 * @link http://www.php.net/manual/en/eventconfig.setmaxdispatchinterval.php
	 * @param int $max_interval An interval after which Libevent should stop running callbacks and check
	 * for more events, or
	 * 0
	 * , if there should be no such interval.
	 * @param int $max_callbacks A number of callbacks after which Libevent should stop running callbacks
	 * and check for more events, or
	 * -1
	 * , if there should be no such limit.
	 * @param int $min_priority A priority below which
	 * max_interval
	 * and
	 * max_callbacks
	 * should not be enforced. If this is set to
	 * 0
	 * , they are enforced for events of every priority; if itaposs set to
	 * 1
	 * , theyaposre enforced for events of priority
	 * 1
	 * and above, and so on.
	 * @return void true on success or false on failure
	 */
	public function setMaxDispatchInterval (int $max_interval, int $max_callbacks, int $min_priority): void {}

	/**
	 * Sets one or more flags to configure the eventual EventBase will be initialized
	 * @link http://www.php.net/manual/en/eventconfig.setflags.php
	 * @param int $flags One of EventBase::LOOP_&#42; constants. 
	 * See EventBase constants.
	 * @return bool true on success or false on failure
	 */
	public function setFlags (int $flags): bool {}

}

/**
 * Represents Libeventaposs buffer event.
 * <p>Usually an application wants to perform some amount of data buffering in
 * addition to just responding to events. When we want to write data, for
 * example, the usual pattern looks like:</p>
 * <p>Decide that we want to write some data to a connection; put that data in
 * a buffer.</p>
 * <p>Wait for the connection to become writable</p>
 * <p>Write as much of the data as we can</p>
 * <p>Remember how much we wrote, and if we still have more data to write,
 * wait for the connection to become writable again.</p>
 * <p>This buffered I/O pattern is common enough that Libevent provides a
 * generic mechanism for it. A "buffer event" consists of an underlying
 * transport (like a socket), a read buffer, and a write buffer. Instead of
 * regular events, which give callbacks when the underlying transport is
 * ready to be read or written, a buffer event invokes its user-supplied
 * callbacks when it has read or written enough data.</p>
 * @link http://www.php.net/manual/en/class.eventbufferevent.php
 */
final class EventBufferEvent  {
	const READING = 1;
	const WRITING = 2;
	const EOF = 16;
	const ERROR = 32;
	const TIMEOUT = 64;
	const CONNECTED = 128;
	const OPT_CLOSE_ON_FREE = 1;
	const OPT_THREADSAFE = 2;
	const OPT_DEFER_CALLBACKS = 4;
	const OPT_UNLOCK_CALLBACKS = 8;
	const SSL_OPEN = 0;
	const SSL_CONNECTING = 1;
	const SSL_ACCEPTING = 2;

	public $priority;
	public $fd;
	public $input;
	public $output;
	public $allow_ssl_dirty_shutdown;


	/**
	 * Constructs EventBufferEvent object
	 * @link http://www.php.net/manual/en/eventbufferevent.construct.php
	 * @param EventBase $base
	 * @param mixed $socket [optional]
	 * @param int $options [optional]
	 * @param callable|null $readcb [optional]
	 * @param callable|null $writecb [optional]
	 * @param callable|null $eventcb [optional]
	 * @param mixed $arg [optional]
	 */
	public function __construct (EventBase $base, mixed $socket = null, int $options = 0, callable|null $readcb = null, callable|null $writecb = null, callable|null $eventcb = null, mixed $arg = null) {}

	/**
	 * Free a buffer event
	 * @link http://www.php.net/manual/en/eventbufferevent.free.php
	 * @return void 
	 */
	public function free (): void {}

	/**
	 * Closes file descriptor associated with the current buffer event
	 * @link http://www.php.net/manual/en/eventbufferevent.close.php
	 * @return void 
	 */
	public function close (): void {}

	/**
	 * Connect buffer eventaposs file descriptor to given address or
	 * UNIX socket
	 * @link http://www.php.net/manual/en/eventbufferevent.connect.php
	 * @param string $addr Should contain an IP address with optional port number, or a path to
	 * UNIX domain socket. Recognized formats are:
	 * <pre>
	 * [IPv6Address]:port
	 * [IPv6Address]
	 * IPv6Address
	 * IPv4Address:port
	 * IPv4Address
	 * unix:path
	 * </pre>
	 * Note,
	 * 'unix:'
	 * prefix is currently not case sensitive.
	 * @return bool true on success or false on failure
	 */
	public function connect (string $addr): bool {}

	/**
	 * Connects to a hostname with optionally asyncronous DNS resolving
	 * @link http://www.php.net/manual/en/eventbufferevent.connecthost.php
	 * @param EventDnsBase $dns_base Object of
	 * EventDnsBase
	 * in case if DNS is to be resolved asyncronously. Otherwise null.
	 * @param string $hostname Hostname to connect to. Recognized formats are:
	 * <pre>
	 * www.example.com (hostname)
	 * 1.2.3.4 (ipv4address)
	 * ::1 (ipv6address)
	 * [::1] ([ipv6address])
	 * </pre>
	 * @param int $port Port number
	 * @param int $family [optional] Address family.
	 * EventUtil::AF_UNSPEC
	 * ,
	 * EventUtil::AF_INET
	 * , or
	 * EventUtil::AF_INET6
	 * . See
	 * EventUtil constants
	 * .
	 * @return bool true on success or false on failure
	 */
	public function connectHost (EventDnsBase $dns_base, string $hostname, int $port, int $family = null): bool {}

	/**
	 * Returns string describing the last failed DNS lookup attempt
	 * @link http://www.php.net/manual/en/eventbufferevent.getdnserrorstring.php
	 * @return string a string describing DNS lookup error, or an empty string for no
	 * error.
	 */
	public function getDnsErrorString (): string {}

	/**
	 * Assigns read, write and event(status) callbacks
	 * @link http://www.php.net/manual/en/eventbufferevent.setcallbacks.php
	 * @param callable $readcb Read event callback. See
	 * About buffer event
	 * callbacks
	 * .
	 * @param callable $writecb Write event callback. See
	 * About buffer event
	 * callbacks
	 * .
	 * @param callable $eventcb Status-change event callback. See
	 * About buffer event
	 * callbacks
	 * .
	 * @param mixed $arg [optional] A variable that will be passed to all the callbacks.
	 * @return void 
	 */
	public function setCallbacks (callable $readcb, callable $writecb, callable $eventcb, $arg = null): void {}

	/**
	 * Enable events read, write, or both on a buffer event
	 * @link http://www.php.net/manual/en/eventbufferevent.enable.php
	 * @param int $events Event::READ
	 * ,
	 * Event::WRITE
	 * , or
	 * Event::READ
	 * |
	 * Event::WRITE
	 * on a buffer event.
	 * @return bool true on success or false on failure
	 */
	public function enable (int $events): bool {}

	/**
	 * Disable events read, write, or both on a buffer event
	 * @link http://www.php.net/manual/en/eventbufferevent.disable.php
	 * @param int $events 
	 * @return bool true on success or false on failure
	 */
	public function disable (int $events): bool {}

	/**
	 * Returns bitmask of events currently enabled on the buffer event
	 * @link http://www.php.net/manual/en/eventbufferevent.getenabled.php
	 * @return int integer representing a bitmask of events currently enabled on the
	 * buffer event
	 */
	public function getEnabled (): int {}

	/**
	 * Returns underlying input buffer associated with current buffer
	 * event
	 * @link http://www.php.net/manual/en/eventbufferevent.getinput.php
	 * @return EventBuffer instance of
	 * EventBuffer
	 * input buffer associated with current buffer event.
	 */
	public function getInput (): EventBuffer {}

	/**
	 * Returns underlying output buffer associated with current buffer
	 * event
	 * @link http://www.php.net/manual/en/eventbufferevent.getoutput.php
	 * @return EventBuffer instance of
	 * EventBuffer
	 * output buffer associated with current buffer event.
	 */
	public function getOutput (): EventBuffer {}

	/**
	 * Adjusts read and/or write watermarks
	 * @link http://www.php.net/manual/en/eventbufferevent.setwatermark.php
	 * @param int $events Bitmask of
	 * Event::READ
	 * ,
	 * Event::WRITE
	 * , or both.
	 * @param int $lowmark Minimum watermark value.
	 * @param int $highmark Maximum watermark value.
	 * 0
	 * means "unlimited".
	 * @return void 
	 */
	public function setWatermark (int $events, int $lowmark, int $highmark): void {}

	/**
	 * Adds data to a buffer eventaposs output buffer
	 * @link http://www.php.net/manual/en/eventbufferevent.write.php
	 * @param string $data Data to be added to the underlying buffer.
	 * @return bool true on success or false on failure
	 */
	public function write (string $data): bool {}

	/**
	 * Adds contents of the entire buffer to a buffer eventaposs output
	 * buffer
	 * @link http://www.php.net/manual/en/eventbufferevent.writebuffer.php
	 * @param EventBuffer $buf Source
	 * EventBuffer
	 * object.
	 * @return bool true on success or false on failure
	 */
	public function writeBuffer (EventBuffer $buf): bool {}

	/**
	 * Read bufferaposs data
	 * @link http://www.php.net/manual/en/eventbufferevent.read.php
	 * @param int $size Maximum number of bytes to read
	 * @return string string of data read from the input buffer.
	 */
	public function read (int $size): ?string {}

	/**
	 * Drains the entire contents of the input buffer and places them into buf
	 * @link http://www.php.net/manual/en/eventbufferevent.readbuffer.php
	 * @param EventBuffer $buf Target buffer
	 * @return bool true on success or false on failure
	 */
	public function readBuffer (EventBuffer $buf): bool {}

	/**
	 * Creates two buffer events connected to each other
	 * @link http://www.php.net/manual/en/eventbufferevent.createpair.php
	 * @param EventBase $base Associated event base
	 * @param int $options [optional] EventBufferEvent::OPT_&#42; constants
	 * combined with bitwise
	 * OR
	 * operator.
	 * @return array array of two
	 * EventBufferEvent
	 * objects connected to each other.
	 */
	public static function createPair (EventBase $base, int $options = null): array|false {}

	/**
	 * Assign a priority to a bufferevent
	 * @link http://www.php.net/manual/en/eventbufferevent.setpriority.php
	 * @param int $priority Priority value.
	 * @return bool true on success or false on failure
	 */
	public function setPriority (int $priority): bool {}

	/**
	 * Set the read and write timeout for a buffer event
	 * @link http://www.php.net/manual/en/eventbufferevent.settimeouts.php
	 * @param float $timeout_read Read timeout
	 * @param float $timeout_write Write timeout
	 * @return bool true on success or false on failure
	 */
	public function setTimeouts (float $timeout_read, float $timeout_write): bool {}

	/**
	 * @param EventBufferEvent $unnderlying
	 * @param EventSslContext $ctx
	 * @param int $state
	 * @param int $options [optional]
	 */
	public static function createSslFilter (EventBufferEvent $unnderlying, EventSslContext $ctx, int $state, int $options = 0): EventBufferEvent {}

	/**
	 * Creates a new SSL buffer event to send its data over an SSL on a socket
	 * @link http://www.php.net/manual/en/eventbufferevent.sslsocket.php
	 * @param EventBase $base Associated event base.
	 * @param mixed $socket Socket to use for this SSL. Can be stream or socket resource, numeric
	 * file descriptor, or null. If
	 * socket
	 * is null, it is assumed that the file descriptor for the socket will be
	 * assigned later, for instance, by means of
	 * EventBufferEvent::connectHost
	 * method.
	 * @param EventSslContext $ctx Object of
	 * EventSslContext
	 * class.
	 * @param int $state The current state of SSL connection:
	 * EventBufferEvent::SSL_OPEN
	 * ,
	 * EventBufferEvent::SSL_ACCEPTING
	 * or
	 * EventBufferEvent::SSL_CONNECTING
	 * .
	 * @param int $options [optional] The buffer event options.
	 * @return EventBufferEvent EventBufferEvent
	 * object.
	 */
	public static function sslSocket (EventBase $base, $socket, EventSslContext $ctx, int $state, int $options = null): EventBufferEvent {}

	/**
	 * Returns most recent OpenSSL error reported on the buffer event
	 * @link http://www.php.net/manual/en/eventbufferevent.sslerror.php
	 * @return string OpenSSL error string reported on the buffer event, or false, if
	 * there is no more error to return.
	 */
	public function sslError (): string {}

	/**
	 * Tells a bufferevent to begin SSL renegotiation
	 * @link http://www.php.net/manual/en/eventbufferevent.sslrenegotiate.php
	 * @return void 
	 */
	public function sslRenegotiate (): void {}

	/**
	 * Returns a textual description of the cipher
	 * @link http://www.php.net/manual/en/eventbufferevent.sslgetcipherinfo.php
	 * @return string a textual description of the cipher on success, or false on error.
	 */
	public function sslGetCipherInfo (): string {}

	/**
	 * Returns the current cipher name of the SSL connection
	 * @link http://www.php.net/manual/en/eventbufferevent.sslgetciphername.php
	 * @return string the current cipher name of the SSL connection, or false on error.
	 */
	public function sslGetCipherName (): string {}

	/**
	 * Returns version of cipher used by current SSL connection
	 * @link http://www.php.net/manual/en/eventbufferevent.sslgetcipherversion.php
	 * @return string the current cipher version of the SSL connection, or false on error.
	 */
	public function sslGetCipherVersion (): string {}

	/**
	 * Returns the name of the protocol used for current SSL connection
	 * @link http://www.php.net/manual/en/eventbufferevent.sslgetprotocol.php
	 * @return string the name of the protocol used for current SSL connection.
	 */
	public function sslGetProtocol (): string {}

}

/**
 * EventBuffer
 * represents Libevent's "evbuffer", an utility functionality for buffered
 * I/O.
 * <p>Event buffers are meant to be generally useful for doing the "buffer" part
 * of buffered network I/O.</p>
 * @link http://www.php.net/manual/en/class.eventbuffer.php
 */
class EventBuffer  {
	const EOL_ANY = 0;
	const EOL_CRLF = 1;
	const EOL_CRLF_STRICT = 2;
	const EOL_LF = 3;
	const EOL_NUL = 4;
	const PTR_SET = 0;
	const PTR_ADD = 1;

	public $length;
	public $contiguous_space;


	/**
	 * Constructs EventBuffer object
	 * @link http://www.php.net/manual/en/eventbuffer.construct.php
	 */
	public function __construct () {}

	/**
	 * Prevent calls that modify an event buffer from succeeding
	 * @link http://www.php.net/manual/en/eventbuffer.freeze.php
	 * @param bool $at_front Whether to disable changes to the front or end of the buffer.
	 * @return bool true on success or false on failure
	 */
	public function freeze (bool $at_front): bool {}

	/**
	 * Re-enable calls that modify an event buffer
	 * @link http://www.php.net/manual/en/eventbuffer.unfreeze.php
	 * @param bool $at_front Whether to enable events at the front or at the end of the buffer.
	 * @return bool true on success or false on failure
	 */
	public function unfreeze (bool $at_front): bool {}

	/**
	 * Acquires a lock on buffer
	 * @link http://www.php.net/manual/en/eventbuffer.lock.php
	 * @param bool $at_front
	 * @return void 
	 */
	public function lock (bool $at_front): void {}

	/**
	 * Releases lock acquired by EventBuffer::lock
	 * @link http://www.php.net/manual/en/eventbuffer.unlock.php
	 * @param bool $at_front
	 * @return bool true on success or false on failure
	 */
	public function unlock (bool $at_front): void {}

	/**
	 * @link http://www.php.net/manual/en/eventbuffer.enablelocking.php
	 * @return void 
	 */
	public function enableLocking (): void {}

	/**
	 * Append data to the end of an event buffer
	 * @link http://www.php.net/manual/en/eventbuffer.add.php
	 * @param string $data String to be appended to the end of the buffer.
	 * @return bool true on success or false on failure
	 */
	public function add (string $data): bool {}

	/**
	 * Read data from an evbuffer and drain the bytes read
	 * @link http://www.php.net/manual/en/eventbuffer.read.php
	 * @param int $max_bytes Maxmimum number of bytes to read from the buffer.
	 * @return string string read, or false on failure.
	 */
	public function read (int $max_bytes): string {}

	/**
	 * Move all data from a buffer provided to the current instance of EventBuffer
	 * @link http://www.php.net/manual/en/eventbuffer.addbuffer.php
	 * @param EventBuffer $buf The source EventBuffer object.
	 * @return bool true on success or false on failure
	 */
	public function addBuffer (EventBuffer $buf): bool {}

	/**
	 * Moves the specified number of bytes from a source buffer to the
	 * end of the current buffer
	 * @link http://www.php.net/manual/en/eventbuffer.appendfrom.php
	 * @param EventBuffer $buf Source buffer.
	 * @param int $len 
	 * @return int the number of bytes read.
	 */
	public function appendFrom (EventBuffer $buf, int $len): int {}

	/**
	 * Reserves space in buffer
	 * @link http://www.php.net/manual/en/eventbuffer.expand.php
	 * @param int $len The number of bytes to reserve for the buffer
	 * @return bool true on success or false on failure
	 */
	public function expand (int $len): bool {}

	/**
	 * Prepend data to the front of the buffer
	 * @link http://www.php.net/manual/en/eventbuffer.prepend.php
	 * @param string $data String to be prepended to the front of the buffer.
	 * @return bool true on success or false on failure
	 */
	public function prepend (string $data): bool {}

	/**
	 * Moves all data from source buffer to the front of current buffer
	 * @link http://www.php.net/manual/en/eventbuffer.prependbuffer.php
	 * @param EventBuffer $buf Source buffer.
	 * @return bool true on success or false on failure
	 */
	public function prependBuffer (EventBuffer $buf): bool {}

	/**
	 * Removes specified number of bytes from the front of the buffer
	 * without copying it anywhere
	 * @link http://www.php.net/manual/en/eventbuffer.drain.php
	 * @param int $len The number of bytes to remove from the buffer.
	 * @return bool true on success or false on failure
	 */
	public function drain (int $len): bool {}

	/**
	 * Copies out specified number of bytes from the front of the buffer
	 * @link http://www.php.net/manual/en/eventbuffer.copyout.php
	 * @param string $data Output string.
	 * @param int $max_bytes The number of bytes to copy.
	 * @return int the number of bytes copied, or
	 * -1
	 * on failure.
	 */
	public function copyout (string &$data, int $max_bytes): int {}

	/**
	 * Extracts a line from the front of the buffer
	 * @link http://www.php.net/manual/en/eventbuffer.readline.php
	 * @param int $eol_style One of
	 * EventBuffer:EOL_&#42; constants
	 * .
	 * @return string On success returns the line read from the buffer, otherwise null.
	 */
	public function readLine (int $eol_style): ?string {}

	/**
	 * Scans the buffer for an occurrence of a string
	 * @link http://www.php.net/manual/en/eventbuffer.search.php
	 * @param string $what String to search.
	 * @param int $start [optional] Start search position.
	 * @param int $end [optional] End search position.
	 * @return mixed numeric position of the first occurrence of the string in the
	 * buffer, or false if string is not found.
	 */
	public function search (string $what, int $start = null, int $end = null): int|false {}

	/**
	 * Scans the buffer for an occurrence of an end of line
	 * @link http://www.php.net/manual/en/eventbuffer.searcheol.php
	 * @param int $start [optional] Start search position.
	 * @param int $eol_style [optional] One of
	 * EventBuffer:EOL_&#42; constants
	 * .
	 * @return mixed numeric position of the first occurrence of end-of-line symbol in
	 * the buffer, or false if not found.
	 */
	public function searchEol (int $start = null, int $eol_style = null): int|false {}

	/**
	 * Linearizes data within buffer
	 * and returns it's contents as a string
	 * @link http://www.php.net/manual/en/eventbuffer.pullup.php
	 * @param int $size The number of bytes required to be contiguous within the buffer.
	 * @return string If
	 * size
	 * is greater than the number of bytes in the buffer, the function returns
	 * null. Otherwise,
	 * EventBuffer::pullup
	 * returns string.
	 */
	public function pullup (int $size): ?string {}

	/**
	 * Write contents of the buffer to a file or socket
	 * @link http://www.php.net/manual/en/eventbuffer.write.php
	 * @param mixed $fd Socket resource, stream or numeric file descriptor associated normally
	 * associated with a socket.
	 * @param int $howmuch [optional] The maximum number of bytes to write.
	 * @return int the number of bytes written, or false on error.
	 */
	public function write ($fd, int $howmuch = null): int|false {}

	/**
	 * Read data from a file onto the end of the buffer
	 * @link http://www.php.net/manual/en/eventbuffer.readfrom.php
	 * @param mixed $fd Socket resource, stream, or numeric file descriptor.
	 * @param int $howmuch Maxmimum number of bytes to read.
	 * @return int the number of bytes read, or false on failure.
	 */
	public function readFrom ($fd, int $howmuch): int|false {}

	/**
	 * Substracts a portion of the buffer data
	 * @link http://www.php.net/manual/en/eventbuffer.substr.php
	 * @param int $start The start position of data to be substracted.
	 * @param int $length [optional] Maximum number of bytes to substract.
	 * @return string the data substracted as a
	 * string
	 * on success, or false on failure.
	 */
	public function substr (int $start, int $length = null): string|false {}

}

/**
 * Represents Libeventaposs DNS base structure. Used to resolve DNS
 * asyncronously, parse configuration files like resolv.conf etc.
 * @link http://www.php.net/manual/en/class.eventdnsbase.php
 */
final class EventDnsBase  {
	const OPTION_SEARCH = 1;
	const OPTION_NAMESERVERS = 2;
	const OPTION_MISC = 4;
	const OPTION_HOSTSFILE = 8;
	const OPTIONS_ALL = 15;


	/**
	 * Constructs EventDnsBase object
	 * @link http://www.php.net/manual/en/eventdnsbase.construct.php
	 * @param EventBase $base
	 * @param bool $initialize
	 */
	public function __construct (EventBase $base, bool $initialize) {}

	/**
	 * Scans the resolv.conf-formatted file
	 * @link http://www.php.net/manual/en/eventdnsbase.parseresolvconf.php
	 * @param int $flags <p>
	 * Determines what information is parsed from the
	 * resolv.conf
	 * file. See the man page for
	 * resolv.conf
	 * for the format of this file.
	 * </p>
	 * <p>
	 * The following directives are not parsed from the file:
	 * sortlist, rotate, no-check-names, inet6, debug
	 * .
	 * </p>
	 * <p>
	 * If this function encounters an error, the possible return values are:
	 * <p>
	 * 1 = failed to open file
	 * 2 = failed to stat file
	 * 3 = file too large
	 * 4 = out of memory
	 * 5 = short read from file
	 * 6 = no nameservers listed in the file
	 * </p>
	 * </p>
	 * @param string $filename Path to
	 * resolv.conf
	 * file.
	 * @return bool true on success or false on failure
	 */
	public function parseResolvConf (int $flags, string $filename): bool {}

	/**
	 * Adds a nameserver to the DNS base
	 * @link http://www.php.net/manual/en/eventdnsbase.addnameserverip.php
	 * @param string $ip The nameserver string, either as an IPv4 address, an IPv6 address, an
	 * IPv4 address with a port (
	 * IPv4:Port
	 * ), or an IPv6 address with a port (
	 * [IPv6]:Port
	 * ).
	 * @return bool true on success or false on failure
	 */
	public function addNameserverIp (string $ip): bool {}

	/**
	 * Loads a hosts file (in the same format as /etc/hosts) from hosts file
	 * @link http://www.php.net/manual/en/eventdnsbase.loadhosts.php
	 * @param string $hosts Path to the hosts' file.
	 * @return bool true on success or false on failure
	 */
	public function loadHosts (string $hosts): bool {}

	/**
	 * Removes all current search suffixes
	 * @link http://www.php.net/manual/en/eventdnsbase.clearsearch.php
	 * @return void 
	 */
	public function clearSearch (): void {}

	/**
	 * Adds a domain to the list of search domains
	 * @link http://www.php.net/manual/en/eventdnsbase.addsearch.php
	 * @param string $domain Search domain.
	 * @return void 
	 */
	public function addSearch (string $domain): void {}

	/**
	 * Set the 'ndots' parameter for searches
	 * @link http://www.php.net/manual/en/eventdnsbase.setsearchndots.php
	 * @param int $ndots The number of dots.
	 * @return bool true on success or false on failure
	 */
	public function setSearchNdots (int $ndots): void {}

	/**
	 * Set the value of a configuration option
	 * @link http://www.php.net/manual/en/eventdnsbase.setoption.php
	 * @param string $option The currently available configuration options are:
	 * "ndots"
	 * ,
	 * "timeout"
	 * ,
	 * "max-timeouts"
	 * ,
	 * "max-inflight"
	 * , and
	 * "attempts"
	 * .
	 * @param string $value Option value.
	 * @return bool true on success or false on failure
	 */
	public function setOption (string $option, string $value): bool {}

	/**
	 * Gets the number of configured nameservers
	 * @link http://www.php.net/manual/en/eventdnsbase.countnameservers.php
	 * @return int the number of configured nameservers(not necessarily the number of
	 * running nameservers). This is useful for double-checking whether our calls
	 * to the various nameserver configuration functions have been successful.
	 */
	public function countNameservers (): int {}

}

/**
 * Represents a connection listener.
 * @link http://www.php.net/manual/en/class.eventlistener.php
 */
final class EventListener  {
	const OPT_LEAVE_SOCKETS_BLOCKING = 1;
	const OPT_CLOSE_ON_FREE = 2;
	const OPT_CLOSE_ON_EXEC = 4;
	const OPT_REUSEABLE = 8;
	const OPT_DISABLED = 32;
	const OPT_THREADSAFE = 16;
	const OPT_DEFERRED_ACCEPT = 64;

	public $fd;


	/**
	 * Creates new connection listener associated with an event base
	 * @link http://www.php.net/manual/en/eventlistener.construct.php
	 * @param EventBase $base
	 * @param callable $cb
	 * @param mixed $data
	 * @param int $flags
	 * @param int $backlog
	 * @param mixed $target
	 */
	public function __construct (EventBase $base, callable $cb, mixed $data = null, int $flags, int $backlog, mixed $target = null) {}

	final public function __sleep (): array {}

	final public function __wakeup (): void {}

	public function free (): void {}

	/**
	 * Enables an event connect listener object
	 * @link http://www.php.net/manual/en/eventlistener.enable.php
	 * @return bool true on success or false on failure
	 */
	public function enable (): bool {}

	/**
	 * Disables an event connect listener object
	 * @link http://www.php.net/manual/en/eventlistener.disable.php
	 * @return bool true on success or false on failure
	 */
	public function disable (): bool {}

	/**
	 * The setCallback purpose
	 * @link http://www.php.net/manual/en/eventlistener.setcallback.php
	 * @param callable $cb <p>
	 * The new callback for new connections. Ignored if null.
	 * </p>
	 * <p>
	 * Should match the following prototype:
	 * </p>
	 * void
	 * callback
	 * EventListener
	 * listener
	 * null
	 * mixed
	 * fd
	 * null
	 * array
	 * address
	 * null
	 * mixed
	 * arg
	 * null
	 * <p>
	 * <p>
	 * listener
	 * <br>
	 * <p>
	 * The
	 * EventListener
	 * object.
	 * </p>
	 * fd
	 * <br>
	 * <p>
	 * The file descriptor or a resource associated with the listener.
	 * </p>
	 * address
	 * <br>
	 * <p>
	 * Array of two elements: IP address and the
	 * server
	 * port.
	 * </p>
	 * arg
	 * <br>
	 * <p>
	 * User custom data attached to the callback.
	 * </p>
	 * </p>
	 * </p>
	 * @param mixed $arg [optional] Custom user data attached to the callback. Ignored if null.
	 * @return void 
	 */
	public function setCallback (callable $cb, $arg = null): void {}

	/**
	 * Set event listener's error callback
	 * @link http://www.php.net/manual/en/eventlistener.seterrorcallback.php
	 * @param string $cb <p>
	 * The error callback. Should match the following prototype:
	 * </p>
	 * void
	 * callback
	 * EventListener
	 * listener
	 * null
	 * mixed
	 * data
	 * null
	 * <p>
	 * <p>
	 * listener
	 * <br>
	 * <p>
	 * The
	 * EventListener
	 * object.
	 * </p>
	 * data
	 * <br>
	 * <p>
	 * User custom data attached to the callback.
	 * </p>
	 * </p>
	 * </p>
	 * @return void 
	 */
	public function setErrorCallback (string $cb): void {}

	/**
	 * Returns event base associated with the event listener
	 * @link http://www.php.net/manual/en/eventlistener.getbase.php
	 * @return void event base associated with the event listener.
	 */
	public function getBase (): EventBase {}

	/**
	 * Retreives the current address to which the
	 * listeneraposs socket is bound
	 * @link http://www.php.net/manual/en/eventlistener.getsocketname.php
	 * @param string $address Output parameter. IP-address depending on the socket address family.
	 * @param mixed $port [optional] Output parameter. The port the socket is bound to.
	 * @return bool true on success or false on failure
	 */
	public function getSocketName (string &$address, &$port = null): bool {}

}

/**
 * Represents an HTTP connection.
 * @link http://www.php.net/manual/en/class.eventhttpconnection.php
 */
final class EventHttpConnection  {

	/**
	 * Constructs EventHttpConnection object
	 * @link http://www.php.net/manual/en/eventhttpconnection.construct.php
	 * @param EventBase $base
	 * @param EventDnsBase|null $dns_base
	 * @param string $address
	 * @param int $port
	 * @param EventSslContext|null $ctx [optional]
	 */
	public function __construct (EventBase $base, EventDnsBase|null $dns_base = null, string $address, int $port, EventSslContext|null $ctx = null) {}

	final public function __sleep (): array {}

	final public function __wakeup (): void {}

	/**
	 * Returns event base associated with the connection
	 * @link http://www.php.net/manual/en/eventhttpconnection.getbase.php
	 * @return EventBase On success returns
	 * EventBase
	 * object associated with the connection. Otherwise false.
	 */
	public function getBase (): EventBase|false {}

	/**
	 * Gets the remote address and port associated with the connection
	 * @link http://www.php.net/manual/en/eventhttpconnection.getpeer.php
	 * @param string $address Address of the peer.
	 * @param int $port Port of the peer.
	 * @return void 
	 */
	public function getPeer (string &$address, int &$port): void {}

	/**
	 * Sets the IP address from which HTTP connections are made
	 * @link http://www.php.net/manual/en/eventhttpconnection.setlocaladdress.php
	 * @param string $address The IP address from which HTTP connections are made.
	 * @return void 
	 */
	public function setLocalAddress (string $address): void {}

	/**
	 * Sets the local port from which connections are made
	 * @link http://www.php.net/manual/en/eventhttpconnection.setlocalport.php
	 * @param int $port The port number.
	 * @return void 
	 */
	public function setLocalPort (int $port): void {}

	/**
	 * Sets the timeout for the connection
	 * @link http://www.php.net/manual/en/eventhttpconnection.settimeout.php
	 * @param int $timeout Timeout in seconds.
	 * @return void 
	 */
	public function setTimeout (int $timeout): void {}

	/**
	 * Sets maximum header size
	 * @link http://www.php.net/manual/en/eventhttpconnection.setmaxheaderssize.php
	 * @param string $max_size The maximum header size in bytes.
	 * @return void 
	 */
	public function setMaxHeadersSize (string $max_size): void {}

	/**
	 * Sets maximum body size for the connection
	 * @link http://www.php.net/manual/en/eventhttpconnection.setmaxbodysize.php
	 * @param string $max_size The maximum body size in bytes.
	 * @return void 
	 */
	public function setMaxBodySize (string $max_size): void {}

	/**
	 * Sets the retry limit for the connection
	 * @link http://www.php.net/manual/en/eventhttpconnection.setretries.php
	 * @param int $retries The retry limit.
	 * -1
	 * means infinity.
	 * @return void 
	 */
	public function setRetries (int $retries): void {}

	/**
	 * Makes an HTTP request over the specified connection
	 * @link http://www.php.net/manual/en/eventhttpconnection.makerequest.php
	 * @param EventHttpRequest $req The connection object over which to send the request.
	 * @param int $type One of
	 * EventHttpRequest::CMD_&#42; constants
	 * .
	 * @param string $uri The URI associated with the request.
	 * @return bool true on success or false on failure
	 */
	public function makeRequest (EventHttpRequest $req, int $type, string $uri): ?bool {}

	/**
	 * Set callback for connection close
	 * @link http://www.php.net/manual/en/eventhttpconnection.setclosecallback.php
	 * @param callable $callback <p>
	 * Callback which is called when connection is closed. Should match the
	 * following prototype:
	 * </p>
	 * void
	 * callback
	 * EventHttpConnection
	 * conn
	 * null
	 * mixed
	 * arg
	 * null
	 * @param mixed $data [optional] 
	 * @return void 
	 */
	public function setCloseCallback (callable $callback, $data = null): void {}

}

/**
 * Represents HTTP server.
 * @link http://www.php.net/manual/en/class.eventhttp.php
 */
final class EventHttp  {

	/**
	 * Constructs EventHttp object(the HTTP server)
	 * @link http://www.php.net/manual/en/eventhttp.construct.php
	 * @param EventBase $base
	 * @param EventSslContext|null $ctx [optional]
	 */
	public function __construct (EventBase $base, EventSslContext|null $ctx = null) {}

	final public function __sleep (): array {}

	final public function __wakeup (): void {}

	/**
	 * Makes an HTTP server accept connections on the specified socket stream or resource
	 * @link http://www.php.net/manual/en/eventhttp.accept.php
	 * @param mixed $socket Socket resource, stream or numeric file descriptor representing a socket
	 * ready to accept connections.
	 * @return bool true on success or false on failure
	 */
	public function accept ($socket): bool {}

	/**
	 * Binds an HTTP server on the specified address and port
	 * @link http://www.php.net/manual/en/eventhttp.bind.php
	 * @param string $address A string containing the IP address to
	 * listen(2)
	 * on.
	 * @param int $port The port number to listen on.
	 * @return void true on success or false on failure
	 */
	public function bind (string $address, int $port): bool {}

	/**
	 * Sets a callback for specified URI
	 * @link http://www.php.net/manual/en/eventhttp.setcallback.php
	 * @param string $path The path for which to invoke the callback.
	 * @param string $cb <p>
	 * The callback
	 * callable
	 * that gets invoked on requested
	 * path
	 * . It should match the following prototype:
	 * </p>
	 * void
	 * callback
	 * EventHttpRequest
	 * req
	 * NULL
	 * mixed
	 * arg
	 * NULL
	 * <p>
	 * <p>
	 * req
	 * <br>
	 * <p>
	 * EventHttpRequest
	 * object.
	 * </p>
	 * arg
	 * <br>
	 * <p>
	 * Custom data.
	 * </p>
	 * </p>
	 * </p>
	 * @param string $arg [optional] Custom data.
	 * @return void true on success or false on failure
	 */
	public function setCallback (string $path, string $cb, string $arg = null): bool {}

	/**
	 * Sets default callback to handle requests that are not caught by specific callbacks
	 * @link http://www.php.net/manual/en/eventhttp.setdefaultcallback.php
	 * @param string $cb <p>
	 * The callback
	 * callable
	 * . It should match the following prototype:
	 * </p>
	 * void
	 * callback
	 * EventHttpRequest
	 * req
	 * NULL
	 * mixed
	 * arg
	 * NULL
	 * <p>
	 * <p>
	 * req
	 * <br>
	 * <p>
	 * EventHttpRequest
	 * object.
	 * </p>
	 * arg
	 * <br>
	 * <p>
	 * Custom data.
	 * </p>
	 * </p>
	 * </p>
	 * @param string $arg [optional] User custom data passed to the callback.
	 * @return void true on success or false on failure
	 */
	public function setDefaultCallback (string $cb, string $arg = null): void {}

	/**
	 * Sets the what HTTP methods are supported in requests accepted by this server, and passed to user callbacks
	 * @link http://www.php.net/manual/en/eventhttp.setallowedmethods.php
	 * @param int $methods A bit mask of
	 * EventHttpRequest::CMD_&#42;
	 * constants
	 * .
	 * @return void 
	 */
	public function setAllowedMethods (int $methods): void {}

	/**
	 * Sets maximum request body size
	 * @link http://www.php.net/manual/en/eventhttp.setmaxbodysize.php
	 * @param int $value The body size in bytes.
	 * @return void 
	 */
	public function setMaxBodySize (int $value): void {}

	/**
	 * Sets maximum HTTP header size
	 * @link http://www.php.net/manual/en/eventhttp.setmaxheaderssize.php
	 * @param int $value The header size in bytes.
	 * @return void 
	 */
	public function setMaxHeadersSize (int $value): void {}

	/**
	 * Sets the timeout for an HTTP request
	 * @link http://www.php.net/manual/en/eventhttp.settimeout.php
	 * @param int $value The timeout in seconds.
	 * @return void 
	 */
	public function setTimeout (int $value): void {}

	/**
	 * Adds a server alias to the HTTP server object
	 * @link http://www.php.net/manual/en/eventhttp.addserveralias.php
	 * @param string $alias The alias to add.
	 * @return bool true on success or false on failure
	 */
	public function addServerAlias (string $alias): bool {}

	/**
	 * Removes server alias
	 * @link http://www.php.net/manual/en/eventhttp.removeserveralias.php
	 * @param string $alias The alias to remove.
	 * @return bool true on success or false on failure
	 */
	public function removeServerAlias (string $alias): bool {}

}

/**
 * Represents an HTTP request.
 * @link http://www.php.net/manual/en/class.eventhttprequest.php
 */
final class EventHttpRequest  {
	const CMD_GET = 1;
	const CMD_POST = 2;
	const CMD_HEAD = 4;
	const CMD_PUT = 8;
	const CMD_DELETE = 16;
	const CMD_OPTIONS = 32;
	const CMD_TRACE = 64;
	const CMD_CONNECT = 128;
	const CMD_PATCH = 256;
	const INPUT_HEADER = 1;
	const OUTPUT_HEADER = 2;


	/**
	 * Constructs EventHttpRequest object
	 * @link http://www.php.net/manual/en/eventhttprequest.construct.php
	 * @param callable $callback
	 * @param mixed $data [optional]
	 */
	public function __construct (callable $callback, mixed $data = null) {}

	final public function __sleep (): array {}

	final public function __wakeup (): void {}

	/**
	 * Frees the object and removes associated events
	 * @link http://www.php.net/manual/en/eventhttprequest.free.php
	 * @return void 
	 */
	public function free (): void {}

	/**
	 * Returns the request command(method)
	 * @link http://www.php.net/manual/en/eventhttprequest.getcommand.php
	 * @return void the request command, one of
	 * EventHttpRequest::CMD_&#42;
	 * constants.
	 */
	public function getCommand (): int {}

	/**
	 * Returns the request host
	 * @link http://www.php.net/manual/en/eventhttprequest.gethost.php
	 * @return string the request host.
	 */
	public function getHost (): string {}

	/**
	 * Returns the request URI
	 * @link http://www.php.net/manual/en/eventhttprequest.geturi.php
	 * @return string the request URI
	 */
	public function getUri (): string {}

	/**
	 * Returns the response code
	 * @link http://www.php.net/manual/en/eventhttprequest.getresponsecode.php
	 * @return int the response code of the request.
	 */
	public function getResponseCode (): int {}

	/**
	 * Returns associative array of the input headers
	 * @link http://www.php.net/manual/en/eventhttprequest.getinputheaders.php
	 * @return array associative array of the input headers.
	 */
	public function getInputHeaders (): array {}

	/**
	 * Returns associative array of the output headers
	 * @link http://www.php.net/manual/en/eventhttprequest.getoutputheaders.php
	 * @return void 
	 */
	public function getOutputHeaders (): array {}

	/**
	 * Returns the input buffer
	 * @link http://www.php.net/manual/en/eventhttprequest.getinputbuffer.php
	 * @return EventBuffer the input buffer.
	 */
	public function getInputBuffer (): EventBuffer {}

	/**
	 * Returns the output buffer of the request
	 * @link http://www.php.net/manual/en/eventhttprequest.getoutputbuffer.php
	 * @return EventBuffer the output buffer of the request.
	 */
	public function getOutputBuffer (): EventBuffer {}

	/**
	 * Returns EventBufferEvent object
	 * @link http://www.php.net/manual/en/eventhttprequest.getbufferevent.php
	 * @return EventBufferEvent EventBufferEvent
	 * object.
	 */
	public function getBufferEvent (): ?EventBufferEvent {}

	/**
	 * Returns EventHttpConnection object
	 * @link http://www.php.net/manual/en/eventhttprequest.getconnection.php
	 * @return EventHttpConnection EventHttpConnection
	 * object.
	 */
	public function getConnection (): ?EventHttpConnection {}

	/**
	 * Closes associated HTTP connection
	 * @link http://www.php.net/manual/en/eventhttprequest.closeconnection.php
	 * @return void 
	 */
	public function closeConnection (): void {}

	/**
	 * Send an HTML error message to the client
	 * @link http://www.php.net/manual/en/eventhttprequest.senderror.php
	 * @param int $error The HTTP error code.
	 * @param string $reason [optional] A brief explanation ofthe error. If null, the standard meaning of the
	 * error code will be used.
	 * @return void 
	 */
	public function sendError (int $error, string $reason = null): void {}

	/**
	 * Send an HTML reply to the client
	 * @link http://www.php.net/manual/en/eventhttprequest.sendreply.php
	 * @param int $code The HTTP response code to send.
	 * @param string $reason A brief message to send with the response code.
	 * @param EventBuffer $buf [optional] The body of the response.
	 * @return void 
	 */
	public function sendReply (int $code, string $reason, EventBuffer $buf = null): void {}

	/**
	 * Send another data chunk as part of an ongoing chunked reply
	 * @link http://www.php.net/manual/en/eventhttprequest.sendreplychunk.php
	 * @param EventBuffer $buf The data chunk to send as part of the reply.
	 * @return void 
	 */
	public function sendReplyChunk (EventBuffer $buf): void {}

	/**
	 * Complete a chunked reply, freeing the request as appropriate
	 * @link http://www.php.net/manual/en/eventhttprequest.sendreplyend.php
	 * @return void 
	 */
	public function sendReplyEnd (): void {}

	/**
	 * Initiate a chunked reply
	 * @link http://www.php.net/manual/en/eventhttprequest.sendreplystart.php
	 * @param int $code The HTTP response code to send.
	 * @param string $reason A brief message to send with the response code.
	 * @return void 
	 */
	public function sendReplyStart (int $code, string $reason): void {}

	/**
	 * Cancels a pending HTTP request
	 * @link http://www.php.net/manual/en/eventhttprequest.cancel.php
	 * @return void 
	 */
	public function cancel (): void {}

	/**
	 * Adds an HTTP header to the headers of the request
	 * @link http://www.php.net/manual/en/eventhttprequest.addheader.php
	 * @param string $key Header name.
	 * @param string $value Header value.
	 * @param int $type One of
	 * EventHttpRequest::&#42;_HEADER constants
	 * .
	 * @return bool true on success or false on failure
	 */
	public function addHeader (string $key, string $value, int $type): bool {}

	/**
	 * Removes all output headers from the header list of the request
	 * @link http://www.php.net/manual/en/eventhttprequest.clearheaders.php
	 * @return void 
	 */
	public function clearHeaders (): void {}

	/**
	 * Removes an HTTP header from the headers of the request
	 * @link http://www.php.net/manual/en/eventhttprequest.removeheader.php
	 * @param string $key The header name.
	 * @param string $type type
	 * is one of
	 * EventHttpRequest::&#42;_HEADER
	 * constants.
	 * @return void Removes an HTTP header from the headers of the request.
	 */
	public function removeHeader (string $key, string $type): bool {}

	/**
	 * Finds the value belonging a header
	 * @link http://www.php.net/manual/en/eventhttprequest.findheader.php
	 * @param string $key The header name.
	 * @param string $type One of
	 * EventHttpRequest::&#42;_HEADER constants
	 * .
	 * @return void null if header not found.
	 */
	public function findHeader (string $key, string $type): ?string {}

}

/**
 * EventUtil
 * is a singleton with supplimentary methods and constants.
 * @link http://www.php.net/manual/en/class.eventutil.php
 */
final class EventUtil  {
	const AF_INET = 2;
	const AF_INET6 = 30;
	const AF_UNIX = 1;
	const AF_UNSPEC = 0;
	const SO_DEBUG = 1;
	const SO_REUSEADDR = 4;
	const SO_KEEPALIVE = 8;
	const SO_DONTROUTE = 16;
	const SO_LINGER = 128;
	const SO_BROADCAST = 32;
	const SO_OOBINLINE = 256;
	const SO_SNDBUF = 4097;
	const SO_RCVBUF = 4098;
	const SO_SNDLOWAT = 4099;
	const SO_RCVLOWAT = 4100;
	const SO_SNDTIMEO = 4101;
	const SO_RCVTIMEO = 4102;
	const SO_TYPE = 4104;
	const SO_ERROR = 4103;
	const TCP_NODELAY = 1;
	const SOL_SOCKET = 65535;
	const SOL_TCP = 6;
	const SOL_UDP = 17;
	const SOCK_RAW = 3;
	const IPPROTO_IP = 0;
	const IPPROTO_IPV6 = 41;
	const LIBEVENT_VERSION_NUMBER = 33623040;


	/**
	 * The abstract constructor
	 * @link http://www.php.net/manual/en/eventutil.construct.php
	 */
	private function __construct () {}

	/**
	 * Returns the most recent socket error number
	 * @link http://www.php.net/manual/en/eventutil.getlastsocketerrno.php
	 * @param mixed $socket [optional] Socket resource, stream or a file descriptor of a socket.
	 * @return int the most recent socket error number(
	 * errno
	 * ).
	 */
	public static function getLastSocketErrno ($socket = null): int|false {}

	/**
	 * Returns the most recent socket error
	 * @link http://www.php.net/manual/en/eventutil.getlastsocketerror.php
	 * @param mixed $socket [optional] Socket resource, stream or a file descriptor of a socket.
	 * @return string the most recent socket error.
	 */
	public static function getLastSocketError ($socket = null): string|false {}

	/**
	 * Generates entropy by means of OpenSSL's RAND_poll()
	 * @link http://www.php.net/manual/en/eventutil.sslrandpoll.php
	 * @return void 
	 */
	public static function sslRandPoll (): bool {}

	/**
	 * Retreives the current address to which the
	 * socket is bound
	 * @link http://www.php.net/manual/en/eventutil.getsocketname.php
	 * @param mixed $socket Socket resource, stream or a file descriptor of a socket.
	 * @param string $address Output parameter. IP-address, or the UNIX domain socket path depending
	 * on the socket address family.
	 * @param mixed $port [optional] Output parameter. The port the socket is bound to. Has no meaning for
	 * UNIX domain sockets.
	 * @return bool true on success or false on failure
	 */
	public static function getSocketName ($socket, string &$address, &$port = null): bool {}

	/**
	 * Returns numeric file descriptor of a socket, or stream
	 * @link http://www.php.net/manual/en/eventutil.getsocketfd.php
	 * @param mixed $socket Socket resource or stream.
	 * @return int numeric file descriptor of a socket, or stream.
	 * EventUtil::getSocketFd
	 * returns false in case if it is whether failed to recognize the type of
	 * the underlying file, or detected that the file descriptor associated with
	 * socket
	 * is not valid.
	 */
	public static function getSocketFd ($socket): int {}

	/**
	 * Sets socket options
	 * @link http://www.php.net/manual/en/eventutil.setsocketoption.php
	 * @param mixed $socket Socket resource, stream, or numeric file descriptor associated with the
	 * socket.
	 * @param int $level One of
	 * EventUtil::SOL_&#42;
	 * constants. Specifies the protocol level at which the option resides. For
	 * example, to retrieve options at the socket level, a
	 * level
	 * parameter of
	 * EventUtil::SOL_SOCKET
	 * would be used. Other levels, such as TCP, can be used by specifying the
	 * protocol number of that level. Protocol numbers can be found by using
	 * the
	 * getprotobyname
	 * function. See
	 * EventUtil constants
	 * .
	 * @param int $optname Option name(type). Has the same meaning as corresponding parameter of
	 * socket_get_option
	 * function. See
	 * EventUtil constants
	 * .
	 * @param mixed $optval Accepts the same values as
	 * optval
	 * parameter of the
	 * socket_get_option
	 * function.
	 * @return bool true on success or false on failure
	 */
	public static function setSocketOption ($socket, int $level, int $optname, $optval): bool {}

	/**
	 * @param int $fd
	 */
	public static function createSocket (int $fd): Socket|false {}

}

/**
 * Represents
 * SSL_CTX
 * structure. Provides methods and properties to configure the SSL context.
 * @link http://www.php.net/manual/en/class.eventsslcontext.php
 */
final class EventSslContext  {
	const TLS_CLIENT_METHOD = 4;
	const TLS_SERVER_METHOD = 8;
	const TLSv11_CLIENT_METHOD = 9;
	const TLSv11_SERVER_METHOD = 10;
	const TLSv12_CLIENT_METHOD = 11;
	const TLSv12_SERVER_METHOD = 12;
	const OPT_LOCAL_CERT = 1;
	const OPT_LOCAL_PK = 2;
	const OPT_PASSPHRASE = 3;
	const OPT_CA_FILE = 4;
	const OPT_CA_PATH = 5;
	const OPT_ALLOW_SELF_SIGNED = 6;
	const OPT_VERIFY_PEER = 7;
	const OPT_VERIFY_DEPTH = 8;
	const OPT_CIPHERS = 9;
	const OPT_NO_SSLv2 = 10;
	const OPT_NO_SSLv3 = 11;
	const OPT_NO_TLSv1 = 12;
	const OPT_NO_TLSv1_1 = 13;
	const OPT_NO_TLSv1_2 = 14;
	const OPT_CIPHER_SERVER_PREFERENCE = 15;
	const OPT_REQUIRE_CLIENT_CERT = 16;
	const OPT_VERIFY_CLIENT_ONCE = 17;
	const OPENSSL_VERSION_TEXT = "OpenSSL 1.1.1q  5 Jul 2022";
	const OPENSSL_VERSION_NUMBER = 269488415;
	const SSL3_VERSION = 768;
	const TLS1_VERSION = 769;
	const TLS1_1_VERSION = 770;
	const TLS1_2_VERSION = 771;
	const DTLS1_VERSION = 65279;
	const DTLS1_2_VERSION = 65277;

	public $local_cert;
	public $local_pk;


	/**
	 * Constructs an OpenSSL context for use with Event classes
	 * @link http://www.php.net/manual/en/eventsslcontext.construct.php
	 * @param int $method
	 * @param array[] $options
	 */
	public function __construct (int $method, array $options) {}

	/**
	 * @param int $proto
	 */
	public function setMinProtoVersion (int $proto): bool {}

	/**
	 * @param int $proto
	 */
	public function setMaxProtoVersion (int $proto): bool {}

}

class EventException extends RuntimeException implements Stringable, Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;
	public $errorInfo;


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
define ('EVENT_NS', "");

// End of event v.3.0.8
