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
	/**
	 * Indicates that the event should be edge-triggered, if the underlying
	 * event base backend supports edge-triggered events. This affects the
	 * semantics of
	 * Event::READ
	 * and
	 * Event::WRITE
	 * .
	const ET = 32;
	/**
	 * Indicates that the event is persistent. See
	 * About event persistence
	 * .
	const PERSIST = 16;
	/**
	 * This flag indicates an event that becomes active when the provided file
	 * descriptor(usually a stream resource, or socket) is ready for reading.
	const READ = 2;
	/**
	 * This flag indicates an event that becomes active when the provided file
	 * descriptor(usually a stream resource, or socket) is ready for reading.
	const WRITE = 4;
	/**
	 * Used to implement signal detection. See "Constructing signal events"
	 * below.
	const SIGNAL = 8;
	/**
	 * This flag indicates an event that becomes active after a timeout
	 * elapses.
	 * <p>The
	 * Event::TIMEOUT
	 * flag is ignored when constructing an event: one can either set a
	 * timeout when event is
	 * added
	 * , or not. It is set in the
	 * $what
	 * argument to the callback function when a timeout has occurred.</p>
	const TIMEOUT = 1;


	/**
	 * Whether event is pending. See
	 * About event persistence
	 * .
	 * @var bool
	 * @link http://www.php.net/manual/en/class.event.php#event.props.pending
	 */
	public readonly bool $pending;

	public $data;

	/**
	 * Constructs Event object
	 * @link http://www.php.net/manual/en/event.construct.php
	 * @param EventBase $base The event base to associate with.
	 * @param mixed $fd stream resource, socket resource, or numeric file descriptor. For timer
	 * events pass
	 * -1
	 * . For signal events pass the signal number, e.g.
	 * SIGHUP
	 * .
	 * @param int $what Event flags. See
	 * Event flags
	 * .
	 * @param callable $cb The event callback. See
	 * Event callbacks
	 * .
	 * @param mixed $arg [optional] Custom data. If specified, it will be passed to the callback when event
	 * triggers.
	 * @return EventBase Returns Event object.
	 */
	public function __construct (EventBase $base, mixed $fd, int $what, callable $cb, mixed $arg = NULL): EventBase {}

	/**
	 * Make event non-pending and free resources allocated for this
	 * event
	 * @link http://www.php.net/manual/en/event.free.php
	 * @return void No value is returned.
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
	 * @return bool Returns true on success or false on failure.
	 */
	public function set (EventBase $base, mixed $fd, int $what = null, callable $cb = null, mixed $arg = null): bool {}

	/**
	 * Returns array with of the names of the methods supported in this version of Libevent
	 * @link http://www.php.net/manual/en/event.getsupportedmethods.php
	 * @return array Returns array.
	 */
	public static function getSupportedMethods (): array {}

	/**
	 * Makes event pending
	 * @link http://www.php.net/manual/en/event.add.php
	 * @param float $timeout [optional] Timeout in seconds.
	 * @return bool Returns true on success or false on failure.
	 */
	public function add (float $timeout = null): bool {}

	/**
	 * Makes event non-pending
	 * @link http://www.php.net/manual/en/event.del.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function del (): bool {}

	/**
	 * Set event priority
	 * @link http://www.php.net/manual/en/event.setpriority.php
	 * @param int $priority The event priority.
	 * @return bool Returns true on success or false on failure.
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
	 * @return bool Returns true if event is pending or scheduled. Otherwise false.
	 */
	public function pending (int $flags): bool {}

	/**
	 * {@inheritdoc}
	 */
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
	 * @return Event Returns Event object on success. Otherwise false.
	 */
	public static function timer (EventBase $base, callable $cb, mixed $arg = null): Event {}

	/**
	 * Re-configures timer event
	 * @link http://www.php.net/manual/en/event.settimer.php
	 * @param EventBase $base The event base to associate with.
	 * @param callable $cb The timer event callback. See
	 * Event callbacks
	 * .
	 * @param mixed $arg [optional] Custom data. If specified, it will be passed to the callback when event
	 * triggers.
	 * @return bool Returns true on success or false on failure.
	 */
	public function setTimer (EventBase $base, callable $cb, mixed $arg = null): bool {}

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
	 * @return Event Returns Event object on success. Otherwise false.
	 */
	public static function signal (EventBase $base, int $signum, callable $cb, mixed $arg = null): Event {}

	/**
	 * {@inheritdoc}
	 * @param float $timeout [optional]
	 */
	public function addTimer (float $timeout = -1): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function delTimer (): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $timeout [optional]
	 */
	public function addSignal (float $timeout = -1): bool {}

	/**
	 * {@inheritdoc}
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
	/**
	 * Flag used with
	 * EventBase::loop
	 * method which means: "block until libevent has an active event, then
	 * exit once all active events have had their callbacks run".
	const LOOP_ONCE = 1;
	/**
	 * Flag used with
	 * EventBase::loop
	 * method which means: "do not block: see which events are ready now, run
	 * the callbacks of the highest-priority ones, then exit".
	const LOOP_NONBLOCK = 2;
	/**
	 * Configuration flag. Do not allocate a lock for the event base, even if
	 * we have locking set up".
	const NOLOCK = 1;
	/**
	 * Windows-only configuration flag. Enables the IOCP dispatcher at
	 * startup.
	const STARTUP_IOCP = 4;
	/**
	 * Configuration flag. Instead of checking the current time every time the
	 * event loop is ready to run timeout callbacks, check after each timeout
	 * callback.
	const NO_CACHE_TIME = 8;
	/**
	 * If we are using the
	 * epoll
	 * backend, this flag says that it is safe to use Libevent's internal
	 * change-list code to batch up adds and deletes in order to try to do as
	 * few syscalls as possible.
	 * <p>Setting this flag can make code run faster, but it may trigger a Linux
	 * bug: it is not safe to use this flag if one has any fds cloned by
	 * dup(), or its variants. Doing so will produce strange and
	 * hard-to-diagnose bugs.</p>
	 * <p>This flag can also be activated by settnig the
	 * EVENT_EPOLL_USE_CHANGELIST
	 * environment variable.</p>
	 * <p>This flag has no effect if one winds up using a backend other than
	 * epoll
	 * .</p>
	const EPOLL_USE_CHANGELIST = 16;
	const IGNORE_ENV = 2;
	const PRECISE_TIMER = 32;


	/**
	 * Constructs EventBase object
	 * @link http://www.php.net/manual/en/eventbase.construct.php
	 * @param EventConfig $cfg [optional] Optional
	 * EventConfig
	 * object.
	 * @return EventConfig Returns EventBase object.
	 */
	public function __construct (EventConfig $cfg = null): EventConfig {}

	/**
	 * {@inheritdoc}
	 */
	final public function __sleep (): array {}

	/**
	 * {@inheritdoc}
	 */
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
	 * @return int Returns integer representing a bitmask of supported features. See
	 * EventConfig::FEATURE_&#42; constants
	 * .
	 */
	public function getFeatures (): int {}

	/**
	 * Sets number of priorities per event base
	 * @link http://www.php.net/manual/en/eventbase.priorityinit.php
	 * @param int $n_priorities The number of priorities per event base.
	 * @return bool Returns true on success or false on failure.
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
	 * @return bool Returns true on success or false on failure.
	 */
	public function loop (int $flags = null): bool {}

	/**
	 * Dispatch pending events
	 * @link http://www.php.net/manual/en/eventbase.dispatch.php
	 * @return void Returns true on success or false on failure.
	 */
	public function dispatch (): void {}

	/**
	 * Stop dispatching events
	 * @link http://www.php.net/manual/en/eventbase.exit.php
	 * @param float $timeout [optional] Optional number of seconds after which the event base should stop
	 * dispatching events.
	 * @return bool Returns true on success or false on failure.
	 */
	public function exit (float $timeout = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param Event $event
	 */
	public function set (Event $event): bool {}

	/**
	 * Tells event_base to stop dispatching events
	 * @link http://www.php.net/manual/en/eventbase.stop.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function stop (): bool {}

	/**
	 * Checks if the event loop was told to exit
	 * @link http://www.php.net/manual/en/eventbase.gotstop.php
	 * @return bool Returns true, event loop was told to stop by
	 * EventBase::stop
	 * . Otherwise false.
	 */
	public function gotStop (): bool {}

	/**
	 * Checks if the event loop was told to exit
	 * @link http://www.php.net/manual/en/eventbase.gotexit.php
	 * @return bool Returns true, event loop was told to exit by
	 * EventBase::exit
	 * . Otherwise false.
	 */
	public function gotExit (): bool {}

	/**
	 * Returns the current event base time
	 * @link http://www.php.net/manual/en/eventbase.gettimeofdaycached.php
	 * @return float Returns the current
	 * event base
	 * time. On failure returns null.
	 */
	public function getTimeOfDayCached (): float {}

	/**
	 * Re-initialize event base(after a fork)
	 * @link http://www.php.net/manual/en/eventbase.reinit.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function reInit (): bool {}

	/**
	 * Free resources allocated for this event base
	 * @link http://www.php.net/manual/en/eventbase.free.php
	 * @return void No value is returned.
	 */
	public function free (): void {}

	/**
	 * {@inheritdoc}
	 */
	public function updateCacheTime (): bool {}

	/**
	 * {@inheritdoc}
	 */
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
	/**
	 * Requires a backend method that supports edge-triggered I/O.
	const FEATURE_ET = 1;
	/**
	 * Requires a backend method where adding or deleting a single event, or
	 * having a single event become active, is an O(1) operation.
	const FEATURE_O1 = 2;
	/**
	 * Requires a backend method that can support arbitrary file descriptor
	 * types, and not just sockets.
	const FEATURE_FDS = 4;


	/**
	 * Constructs EventConfig object
	 * @link http://www.php.net/manual/en/eventconfig.construct.php
	 * @return void Returns
	 * EventConfig
	 * object.
	 */
	public function __construct (): void {}

	/**
	 * {@inheritdoc}
	 */
	final public function __sleep (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

	/**
	 * Tells libevent to avoid specific event method
	 * @link http://www.php.net/manual/en/eventconfig.avoidmethod.php
	 * @param string $method The backend method to avoid. See
	 * EventConfig constants
	 * .
	 * @return bool Returns true on success or false on failure.
	 */
	public function avoidMethod (string $method): bool {}

	/**
	 * Enters a required event method feature that the application demands
	 * @link http://www.php.net/manual/en/eventconfig.requirefeatures.php
	 * @param int $feature Bitmask of required features. See
	 * EventConfig::FEATURE_&#42; constants
	 * @return bool Returns true on success or false on failure.
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
	 * , they are enforced for events of every priority; if it's set to
	 * 1
	 * , they're enforced for events of priority
	 * 1
	 * and above, and so on.
	 * @return void Returns true on success or false on failure.
	 */
	public function setMaxDispatchInterval (int $max_interval, int $max_callbacks, int $min_priority): void {}

	/**
	 * Sets one or more flags to configure the eventual EventBase will be initialized
	 * @link http://www.php.net/manual/en/eventconfig.setflags.php
	 * @param int $flags One of EventBase::LOOP_&#42; constants. 
	 * See EventBase constants.
	 * @return bool Returns true on success or false on failure.
	 */
	public function setFlags (int $flags): bool {}

}

/**
 * Represents Libevent's buffer event.
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
	/**
	 * An event occurred during a read operation on the bufferevent. See the
	 * other flags for which event it was.
	const READING = 1;
	/**
	 * An event occurred during a write operation on the bufferevent. See the
	 * other flags for which event it was.
	const WRITING = 2;
	/**
	 * Got an end-of-file indication on the buffer event.
	const EOF = 16;
	/**
	 * An error occurred during a bufferevent operation. For more information
	 * on what the error was, call
	 * EventUtil::getLastSocketErrno
	 * and/or
	 * EventUtil::getLastSocketError
	 * .
	const ERROR = 32;
	const TIMEOUT = 64;
	/**
	 * Finished a requested connection on the bufferevent.
	const CONNECTED = 128;
	/**
	 * When the buffer event is freed, close the underlying transport. This
	 * will close an underlying socket, free an underlying buffer event, etc.
	const OPT_CLOSE_ON_FREE = 1;
	/**
	 * Automatically allocate locks for the bufferevent, so that it’s safe
	 * to use from multiple threads.
	const OPT_THREADSAFE = 2;
	/**
	 * When this flag is set, the bufferevent defers all of its callbacks. See
	 * Fast
	 * portable non-blocking network programming with Libevent, Deferred callbacks
	 * .
	const OPT_DEFER_CALLBACKS = 4;
	/**
	 * By default, when the bufferevent is set up to be threadsafe, the buffer
	 * event’s locks are held whenever the any user-provided callback is
	 * invoked. Setting this option makes Libevent release the buffer
	 * event’s lock when it’s invoking the callbacks.
	const OPT_UNLOCK_CALLBACKS = 8;
	/**
	 * The SSL handshake is done
	const SSL_OPEN = 0;
	/**
	 * SSL is currently performing negotiation as a client
	const SSL_CONNECTING = 1;
	/**
	 * SSL is currently performing negotiation as a server
	const SSL_ACCEPTING = 2;


	/**
	 * Numeric file descriptor associated with the buffer event. Normally
	 * represents a bound socket. Equals to null, if there is no file
	 * descriptor(socket) associated with the buffer event.
	 * @var int
	 * @link http://www.php.net/manual/en/class.eventbufferevent.php#eventbufferevent.props.fd
	 */
	public int $fd;

	/**
	 * The priority of the events used to implement the buffer event.
	 * @var int
	 * @link http://www.php.net/manual/en/class.eventbufferevent.php#eventbufferevent.props.priority
	 */
	public int $priority;

	/**
	 * Underlying input buffer object(
	 * EventBuffer
	 * )
	 * @var EventBuffer
	 * @link http://www.php.net/manual/en/class.eventbufferevent.php#eventbufferevent.props.input
	 */
	public readonly EventBuffer $input;

	/**
	 * Underlying output buffer object(
	 * EventBuffer
	 * )
	 * @var EventBuffer
	 * @link http://www.php.net/manual/en/class.eventbufferevent.php#eventbufferevent.props.output
	 */
	public readonly EventBuffer $output;

	public $allow_ssl_dirty_shutdown;

	/**
	 * Constructs EventBufferEvent object
	 * @link http://www.php.net/manual/en/eventbufferevent.construct.php
	 * @param EventBase $base Event base that should be associated with the new buffer event.
	 * @param mixed $socket [optional] May be created as a stream(not necessarily by means of
	 * sockets
	 * extension)
	 * @param int $options [optional] One of
	 * EventBufferEvent::OPT_&#42;
	 * constants
	 * , or
	 * 0
	 * .
	 * @param callable $readcb [optional] Read event callback. See
	 * About buffer event
	 * callbacks
	 * .
	 * @param callable $writecb [optional] Write event callback. See
	 * About buffer event
	 * callbacks
	 * .
	 * @param callable $eventcb [optional] Status-change event callback. See
	 * About buffer event
	 * callbacks
	 * .
	 * @param mixed $arg [optional] A variable that will be passed to all the callbacks.
	 * @return EventBase Returns buffer event resource optionally associated with socket resource.
	 * &#42;/
	 */
	public function __construct (EventBase $base, mixed $socket = null, int $options = null, callable $readcb = null, callable $writecb = null, callable $eventcb = null, mixed $arg = null): EventBase {}

	/**
	 * Free a buffer event
	 * @link http://www.php.net/manual/en/eventbufferevent.free.php
	 * @return void No value is returned.
	 */
	public function free (): void {}

	/**
	 * Closes file descriptor associated with the current buffer event
	 * @link http://www.php.net/manual/en/eventbufferevent.close.php
	 * @return void No value is returned.
	 */
	public function close (): void {}

	/**
	 * Connect buffer event's file descriptor to given address or
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
	 * @return bool Returns true on success or false on failure.
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
	 * @return bool Returns true on success or false on failure.
	 */
	public function connectHost (EventDnsBase $dns_base, string $hostname, int $port, int $family = \EventUtil::AF_UNSPEC): bool {}

	/**
	 * Returns string describing the last failed DNS lookup attempt
	 * @link http://www.php.net/manual/en/eventbufferevent.getdnserrorstring.php
	 * @return string Returns a string describing DNS lookup error, or an empty string for no
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
	 * @return void No value is returned.
	 */
	public function setCallbacks (callable $readcb, callable $writecb, callable $eventcb, mixed $arg = null): void {}

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
	 * @return bool Returns true on success or false on failure.
	 */
	public function enable (int $events): bool {}

	/**
	 * Disable events read, write, or both on a buffer event
	 * @link http://www.php.net/manual/en/eventbufferevent.disable.php
	 * @param int $events 
	 * @return bool Returns true on success or false on failure.
	 */
	public function disable (int $events): bool {}

	/**
	 * Returns bitmask of events currently enabled on the buffer event
	 * @link http://www.php.net/manual/en/eventbufferevent.getenabled.php
	 * @return int Returns integer representing a bitmask of events currently enabled on the
	 * buffer event
	 */
	public function getEnabled (): int {}

	/**
	 * Returns underlying input buffer associated with current buffer
	 * event
	 * @link http://www.php.net/manual/en/eventbufferevent.getinput.php
	 * @return EventBuffer Returns instance of
	 * EventBuffer
	 * input buffer associated with current buffer event.
	 */
	public function getInput (): EventBuffer {}

	/**
	 * Returns underlying output buffer associated with current buffer
	 * event
	 * @link http://www.php.net/manual/en/eventbufferevent.getoutput.php
	 * @return EventBuffer Returns instance of
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
	 * @return void No value is returned.
	 */
	public function setWatermark (int $events, int $lowmark, int $highmark): void {}

	/**
	 * Adds data to a buffer event's output buffer
	 * @link http://www.php.net/manual/en/eventbufferevent.write.php
	 * @param string $data Data to be added to the underlying buffer.
	 * @return bool Returns true on success or false on failure.
	 */
	public function write (string $data): bool {}

	/**
	 * Adds contents of the entire buffer to a buffer event's output
	 * buffer
	 * @link http://www.php.net/manual/en/eventbufferevent.writebuffer.php
	 * @param EventBuffer $buf Source
	 * EventBuffer
	 * object.
	 * @return bool Returns true on success or false on failure.
	 */
	public function writeBuffer (EventBuffer $buf): bool {}

	/**
	 * Read buffer's data
	 * @link http://www.php.net/manual/en/eventbufferevent.read.php
	 * @param int $size Maximum number of bytes to read
	 * @return string Returns string of data read from the input buffer.
	 */
	public function read (int $size): string {}

	/**
	 * Drains the entire contents of the input buffer and places them into buf
	 * @link http://www.php.net/manual/en/eventbufferevent.readbuffer.php
	 * @param EventBuffer $buf Target buffer
	 * @return bool Returns true on success or false on failure.
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
	 * @return array Returns array of two
	 * EventBufferEvent
	 * objects connected to each other.
	 */
	public static function createPair (EventBase $base, int $options = null): array {}

	/**
	 * Assign a priority to a bufferevent
	 * @link http://www.php.net/manual/en/eventbufferevent.setpriority.php
	 * @param int $priority Priority value.
	 * @return bool Returns true on success or false on failure.
	 */
	public function setPriority (int $priority): bool {}

	/**
	 * Set the read and write timeout for a buffer event
	 * @link http://www.php.net/manual/en/eventbufferevent.settimeouts.php
	 * @param float $timeout_read Read timeout
	 * @param float $timeout_write Write timeout
	 * @return bool Returns true on success or false on failure.
	 */
	public function setTimeouts (float $timeout_read, float $timeout_write): bool {}

	/**
	 * {@inheritdoc}
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
	 * @return EventBufferEvent Returns
	 * EventBufferEvent
	 * object.
	 */
	public static function sslSocket (EventBase $base, mixed $socket, EventSslContext $ctx, int $state, int $options = null): EventBufferEvent {}

	/**
	 * Returns most recent OpenSSL error reported on the buffer event
	 * @link http://www.php.net/manual/en/eventbufferevent.sslerror.php
	 * @return string Returns OpenSSL error string reported on the buffer event, or false, if
	 * there is no more error to return.
	 */
	public function sslError (): string {}

	/**
	 * Tells a bufferevent to begin SSL renegotiation
	 * @link http://www.php.net/manual/en/eventbufferevent.sslrenegotiate.php
	 * @return void No value is returned.
	 */
	public function sslRenegotiate (): void {}

	/**
	 * Returns a textual description of the cipher
	 * @link http://www.php.net/manual/en/eventbufferevent.sslgetcipherinfo.php
	 * @return string Returns a textual description of the cipher on success, or false on error.
	 */
	public function sslGetCipherInfo (): string {}

	/**
	 * Returns the current cipher name of the SSL connection
	 * @link http://www.php.net/manual/en/eventbufferevent.sslgetciphername.php
	 * @return string Returns the current cipher name of the SSL connection, or false on error.
	 */
	public function sslGetCipherName (): string {}

	/**
	 * Returns version of cipher used by current SSL connection
	 * @link http://www.php.net/manual/en/eventbufferevent.sslgetcipherversion.php
	 * @return string Returns the current cipher version of the SSL connection, or false on error.
	 */
	public function sslGetCipherVersion (): string {}

	/**
	 * Returns the name of the protocol used for current SSL connection
	 * @link http://www.php.net/manual/en/eventbufferevent.sslgetprotocol.php
	 * @return string Returns the name of the protocol used for current SSL connection.
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
	/**
	 * The end of line is any sequence of any number of carriage return and
	 * linefeed characters. This format is not very useful; it exists mainly
	 * for backward compatibility.
	const EOL_ANY = 0;
	/**
	 * The end of the line is an optional carriage return, followed by a
	 * linefeed. (In other words, it is either a
	 * "\r\n"
	 * or a
	 * "\n"
	 * .) This format is useful in parsing text-based Internet protocols,
	 * since the standards generally prescribe a
	 * "\r\n"
	 * line-terminator, but nonconformant clients sometimes say just
	 * "\n"
	 * .
	const EOL_CRLF = 1;
	/**
	 * The end of a line is a single carriage return, followed by a single
	 * linefeed. (This is also known as
	 * "\r\n"
	 * . The ASCII values are
	 * 0x0D
	 * 0x0A
	 * ).
	const EOL_CRLF_STRICT = 2;
	/**
	 * The end of a line is a single linefeed character. (This is also known
	 * as
	 * "\n"
	 * . It is ASCII value is
	 * 0x0A
	 * .)
	const EOL_LF = 3;
	const EOL_NUL = 4;
	/**
	 * Flag used as argument of
	 * EventBuffer::setPosition
	 * method. If this flag specified, the position pointer is moved to an
	 * absolute position within the buffer.
	const PTR_SET = 0;
	/**
	 * The same as
	 * EventBuffer::PTR_SET
	 * , except this flag causes
	 * EventBuffer::setPosition
	 * method to move position forward up to the specified number of
	 * bytes(instead of setting absolute position).
	const PTR_ADD = 1;


	/**
	 * The number of bytes stored in an event buffer.
	 * @var int
	 * @link http://www.php.net/manual/en/class.eventbuffer.php#eventbuffer.props.length
	 */
	public readonly int $length;

	/**
	 * The number of bytes stored contiguously at the front of the buffer. The
	 * bytes in a buffer may be stored in multiple separate chunks of memory;
	 * the property returns the number of bytes currently stored in the first
	 * chunk.
	 * @var int
	 * @link http://www.php.net/manual/en/class.eventbuffer.php#eventbuffer.props.contiguous_space
	 */
	public readonly int $contiguous_space;

	/**
	 * Constructs EventBuffer object
	 * @link http://www.php.net/manual/en/eventbuffer.construct.php
	 * @return void Returns EventBuffer object.
	 */
	public function __construct (): void {}

	/**
	 * Prevent calls that modify an event buffer from succeeding
	 * @link http://www.php.net/manual/en/eventbuffer.freeze.php
	 * @param bool $at_front Whether to disable changes to the front or end of the buffer.
	 * @return bool Returns true on success or false on failure.
	 */
	public function freeze (bool $at_front): bool {}

	/**
	 * Re-enable calls that modify an event buffer
	 * @link http://www.php.net/manual/en/eventbuffer.unfreeze.php
	 * @param bool $at_front Whether to enable events at the front or at the end of the buffer.
	 * @return bool Returns true on success or false on failure.
	 */
	public function unfreeze (bool $at_front): bool {}

	/**
	 * Acquires a lock on buffer
	 * @link http://www.php.net/manual/en/eventbuffer.lock.php
	 * @param bool $at_front
	 * @return void No value is returned.
	 */
	public function lock (bool $at_front): void {}

	/**
	 * Releases lock acquired by EventBuffer::lock
	 * @link http://www.php.net/manual/en/eventbuffer.unlock.php
	 * @param bool $at_front
	 * @return bool Returns true on success or false on failure.
	 */
	public function unlock (bool $at_front): bool {}

	/**
	 * @link http://www.php.net/manual/en/eventbuffer.enablelocking.php
	 * @return void No value is returned.
	 */
	public function enableLocking (): void {}

	/**
	 * Append data to the end of an event buffer
	 * @link http://www.php.net/manual/en/eventbuffer.add.php
	 * @param string $data String to be appended to the end of the buffer.
	 * @return bool Returns true on success or false on failure.
	 */
	public function add (string $data): bool {}

	/**
	 * Read data from an evbuffer and drain the bytes read
	 * @link http://www.php.net/manual/en/eventbuffer.read.php
	 * @param int $max_bytes Maxmimum number of bytes to read from the buffer.
	 * @return string Returns string read, or false on failure.
	 */
	public function read (int $max_bytes): string {}

	/**
	 * Move all data from a buffer provided to the current instance of EventBuffer
	 * @link http://www.php.net/manual/en/eventbuffer.addbuffer.php
	 * @param EventBuffer $buf The source EventBuffer object.
	 * @return bool Returns true on success or false on failure.
	 */
	public function addBuffer (EventBuffer $buf): bool {}

	/**
	 * Moves the specified number of bytes from a source buffer to the
	 * end of the current buffer
	 * @link http://www.php.net/manual/en/eventbuffer.appendfrom.php
	 * @param EventBuffer $buf Source buffer.
	 * @param int $len 
	 * @return int Returns the number of bytes read.
	 */
	public function appendFrom (EventBuffer $buf, int $len): int {}

	/**
	 * Reserves space in buffer
	 * @link http://www.php.net/manual/en/eventbuffer.expand.php
	 * @param int $len The number of bytes to reserve for the buffer
	 * @return bool Returns true on success or false on failure.
	 */
	public function expand (int $len): bool {}

	/**
	 * Prepend data to the front of the buffer
	 * @link http://www.php.net/manual/en/eventbuffer.prepend.php
	 * @param string $data String to be prepended to the front of the buffer.
	 * @return bool Returns true on success or false on failure.
	 */
	public function prepend (string $data): bool {}

	/**
	 * Moves all data from source buffer to the front of current buffer
	 * @link http://www.php.net/manual/en/eventbuffer.prependbuffer.php
	 * @param EventBuffer $buf Source buffer.
	 * @return bool Returns true on success or false on failure.
	 */
	public function prependBuffer (EventBuffer $buf): bool {}

	/**
	 * Removes specified number of bytes from the front of the buffer
	 * without copying it anywhere
	 * @link http://www.php.net/manual/en/eventbuffer.drain.php
	 * @param int $len The number of bytes to remove from the buffer.
	 * @return bool Returns true on success or false on failure.
	 */
	public function drain (int $len): bool {}

	/**
	 * Copies out specified number of bytes from the front of the buffer
	 * @link http://www.php.net/manual/en/eventbuffer.copyout.php
	 * @param string $data Output string.
	 * @param int $max_bytes The number of bytes to copy.
	 * @return int Returns the number of bytes copied, or
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
	public function readLine (int $eol_style): string {}

	/**
	 * Scans the buffer for an occurrence of a string
	 * @link http://www.php.net/manual/en/eventbuffer.search.php
	 * @param string $what String to search.
	 * @param int $start [optional] Start search position.
	 * @param int $end [optional] End search position.
	 * @return mixed Returns numeric position of the first occurrence of the string in the
	 * buffer, or false if string is not found.
	 */
	public function search (string $what, int $start = -1, int $end = -1): mixed {}

	/**
	 * Scans the buffer for an occurrence of an end of line
	 * @link http://www.php.net/manual/en/eventbuffer.searcheol.php
	 * @param int $start [optional] Start search position.
	 * @param int $eol_style [optional] One of
	 * EventBuffer:EOL_&#42; constants
	 * .
	 * @return mixed Returns numeric position of the first occurrence of end-of-line symbol in
	 * the buffer, or false if not found.
	 */
	public function searchEol (int $start = -1, int $eol_style = '
     EventBuffer::EOL_ANY
    '): mixed {}

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
	public function pullup (int $size): string {}

	/**
	 * Write contents of the buffer to a file or socket
	 * @link http://www.php.net/manual/en/eventbuffer.write.php
	 * @param mixed $fd Socket resource, stream or numeric file descriptor associated normally
	 * associated with a socket.
	 * @param int $howmuch [optional] The maximum number of bytes to write.
	 * @return int Returns the number of bytes written, or false on error.
	 */
	public function write (mixed $fd, int $howmuch = null): int {}

	/**
	 * Read data from a file onto the end of the buffer
	 * @link http://www.php.net/manual/en/eventbuffer.readfrom.php
	 * @param mixed $fd Socket resource, stream, or numeric file descriptor.
	 * @param int $howmuch Maxmimum number of bytes to read.
	 * @return int Returns the number of bytes read, or false on failure.
	 */
	public function readFrom (mixed $fd, int $howmuch): int {}

	/**
	 * Substracts a portion of the buffer data
	 * @link http://www.php.net/manual/en/eventbuffer.substr.php
	 * @param int $start The start position of data to be substracted.
	 * @param int $length [optional] Maximum number of bytes to substract.
	 * @return string Returns the data substracted as a
	 * string
	 * on success, or false on failure.
	 */
	public function substr (int $start, int $length = null): string {}

}

/**
 * Represents Libevent's DNS base structure. Used to resolve DNS
 * asyncronously, parse configuration files like resolv.conf etc.
 * @link http://www.php.net/manual/en/class.eventdnsbase.php
 */
final class EventDnsBase  {
	/**
	 * Tells to read the domain and search fields from the
	 * resolv.conf
	 * file and the
	 * ndots
	 * option, and use them to decide which domains(if any) to search for
	 * hostnames that aren’t fully-qualified.
	const OPTION_SEARCH = 1;
	/**
	 * Tells to learn the nameservers from the
	 * resolv.conf
	 * file.
	const OPTION_NAMESERVERS = 2;
	const OPTION_MISC = 4;
	/**
	 * Tells to read a list of hosts from
	 * /etc/hosts
	 * as part of loading the
	 * resolv.conf
	 * file.
	const OPTION_HOSTSFILE = 8;
	/**
	 * Tells to learn as much as it can from the
	 * resolv.conf
	 * file.
	const OPTIONS_ALL = 15;


	/**
	 * Constructs EventDnsBase object
	 * @link http://www.php.net/manual/en/eventdnsbase.construct.php
	 * @param EventBase $base Event base.
	 * @param bool $initialize If the
	 * initialize
	 * argument is true, it tries to configure the DNS base sensibly given
	 * your operating system’s default. Otherwise, it leaves the event DNS
	 * base empty, with no nameservers or options configured. In the latter
	 * case DNS base should be configured manually, e.g. with
	 * EventDnsBase::parseResolvConf
	 * .
	 * @return EventBase Returns EventDnsBase object.
	 */
	public function __construct (EventBase $base, bool $initialize): EventBase {}

	/**
	 * Scans the resolv.conf-formatted file
	 * @link http://www.php.net/manual/en/eventdnsbase.parseresolvconf.php
	 * @param int $flags Determines what information is parsed from the
	 * resolv.conf
	 * file. See the man page for
	 * resolv.conf
	 * for the format of this file.
	 * <p>The following directives are not parsed from the file:
	 * sortlist, rotate, no-check-names, inet6, debug
	 * .</p>
	 * <p>If this function encounters an error, the possible return values are:
	 * <p>
	 * 1 = failed to open file
	 * 2 = failed to stat file
	 * 3 = file too large
	 * 4 = out of memory
	 * 5 = short read from file
	 * 6 = no nameservers listed in the file
	 * </p></p>
	 * @param string $filename Path to
	 * resolv.conf
	 * file.
	 * @return bool Returns true on success or false on failure.
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
	 * @return bool Returns true on success or false on failure.
	 */
	public function addNameserverIp (string $ip): bool {}

	/**
	 * Loads a hosts file (in the same format as /etc/hosts) from hosts file
	 * @link http://www.php.net/manual/en/eventdnsbase.loadhosts.php
	 * @param string $hosts Path to the hosts' file.
	 * @return bool Returns true on success or false on failure.
	 */
	public function loadHosts (string $hosts): bool {}

	/**
	 * Removes all current search suffixes
	 * @link http://www.php.net/manual/en/eventdnsbase.clearsearch.php
	 * @return void No value is returned.
	 */
	public function clearSearch (): void {}

	/**
	 * Adds a domain to the list of search domains
	 * @link http://www.php.net/manual/en/eventdnsbase.addsearch.php
	 * @param string $domain Search domain.
	 * @return void No value is returned.
	 */
	public function addSearch (string $domain): void {}

	/**
	 * Set the 'ndots' parameter for searches
	 * @link http://www.php.net/manual/en/eventdnsbase.setsearchndots.php
	 * @param int $ndots The number of dots.
	 * @return bool Returns true on success or false on failure.
	 */
	public function setSearchNdots (int $ndots): bool {}

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
	 * @return bool Returns true on success or false on failure.
	 */
	public function setOption (string $option, string $value): bool {}

	/**
	 * Gets the number of configured nameservers
	 * @link http://www.php.net/manual/en/eventdnsbase.countnameservers.php
	 * @return int Returns the number of configured nameservers(not necessarily the number of
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
	/**
	 * By default Libevent turns underlying file descriptors, or sockets, to
	 * non-blocking mode. This flag tells Libevent to leave them in blocking
	 * mode.
	const OPT_LEAVE_SOCKETS_BLOCKING = 1;
	/**
	 * If this option is set, the connection listener closes its underlying
	 * socket when the
	 * EventListener
	 * object is freed.
	const OPT_CLOSE_ON_FREE = 2;
	/**
	 * If this option is set, the connection listener sets the close-on-exec
	 * flag on the underlying listener socket. See platform documentation for
	 * fcntl
	 * and
	 * FD_CLOEXEC
	 * for more information.
	const OPT_CLOSE_ON_EXEC = 4;
	/**
	 * By default on some platforms, once a listener socket is closed, no
	 * other socket can bind to the same port until a while has passed.
	 * Setting this option makes Libevent mark the socket as reusable, so that
	 * once it is closed, another socket can be opened to listen on the same
	 * port.
	const OPT_REUSEABLE = 8;
	const OPT_DISABLED = 32;
	/**
	 * Allocate locks for the listener, so that it’s safe to use it from
	 * multiple threads.
	const OPT_THREADSAFE = 16;
	const OPT_DEFERRED_ACCEPT = 64;


	/**
	 * Numeric file descriptor of the underlying socket. (Added in
	 * event-1.6.0
	 * .)
	 * @var int
	 * @link http://www.php.net/manual/en/class.eventlistener.php#eventlistener.props.fd
	 */
	public readonly int $fd;

	/**
	 * Creates new connection listener associated with an event base
	 * @link http://www.php.net/manual/en/eventlistener.construct.php
	 * @param EventBase $base Associated event base.
	 * @param callable $cb A
	 * callable
	 * that will be invoked when new connection received.
	 * @param mixed $data Custom user data attached to
	 * cb
	 * .
	 * @param int $flags Bit mask of
	 * EventListener::OPT_&#42;
	 * constants. See
	 * EventListener constants
	 * .
	 * @param int $backlog Controls the maximum number of pending connections that the network
	 * stack should allow to wait in a not-yet-accepted state at any time; see
	 * documentation for your system’s
	 * listen
	 * function for more details. If
	 * backlog
	 * is negative, Libevent tries to pick a good value for the
	 * backlog
	 * ; if it is zero, Event assumes that
	 * listen
	 * is already called on the socket(
	 * target
	 * )
	 * @param mixed $target May be string, socket resource, or a stream associated with a socket. In
	 * case if
	 * target
	 * is a string, the string will be parsed as network address. It will be
	 * interpreted as a UNIX domain socket path, if prefixed with
	 * 'unix:'
	 * , e.g.
	 * 'unix:/tmp/my.sock'
	 * .
	 * @return EventBase Returns
	 * EventListener
	 * object representing the event connection listener.
	 */
	public function __construct (EventBase $base, callable $cb, mixed $data, int $flags, int $backlog, mixed $target): EventBase {}

	/**
	 * {@inheritdoc}
	 */
	final public function __sleep (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

	/**
	 * {@inheritdoc}
	 */
	public function free (): void {}

	/**
	 * Enables an event connect listener object
	 * @link http://www.php.net/manual/en/eventlistener.enable.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function enable (): bool {}

	/**
	 * Disables an event connect listener object
	 * @link http://www.php.net/manual/en/eventlistener.disable.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function disable (): bool {}

	/**
	 * The setCallback purpose
	 * @link http://www.php.net/manual/en/eventlistener.setcallback.php
	 * @param callable $cb The new callback for new connections. Ignored if null.
	 * <p>Should match the following prototype:</p>
	 * <p><p>
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
	 * </p></p>
	 * <p>The
	 * EventListener
	 * object.</p>
	 * <p>The file descriptor or a resource associated with the listener.</p>
	 * <p>Array of two elements: IP address and the
	 * server
	 * port.</p>
	 * <p>User custom data attached to the callback.</p>
	 * @param mixed $arg [optional] Custom user data attached to the callback. Ignored if null.
	 * @return void No value is returned.
	 */
	public function setCallback (callable $cb, mixed $arg = null): void {}

	/**
	 * Set event listener's error callback
	 * @link http://www.php.net/manual/en/eventlistener.seterrorcallback.php
	 * @param string $cb The error callback. Should match the following prototype:
	 * <p><p>
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
	 * </p></p>
	 * <p>The
	 * EventListener
	 * object.</p>
	 * <p>User custom data attached to the callback.</p>
	 * @return void 
	 */
	public function setErrorCallback (string $cb): void {}

	/**
	 * Returns event base associated with the event listener
	 * @link http://www.php.net/manual/en/eventlistener.getbase.php
	 * @return void Returns event base associated with the event listener.
	 */
	public function getBase (): void {}

	/**
	 * Retreives the current address to which the
	 * listener's socket is bound
	 * @link http://www.php.net/manual/en/eventlistener.getsocketname.php
	 * @param string $address Output parameter. IP-address depending on the socket address family.
	 * @param mixed $port [optional] Output parameter. The port the socket is bound to.
	 * @return bool Returns true on success or false on failure.
	 */
	public function getSocketName (string &$address, mixed &$port = null): bool {}

}

/**
 * Represents an HTTP connection.
 * @link http://www.php.net/manual/en/class.eventhttpconnection.php
 */
final class EventHttpConnection  {

	/**
	 * Constructs EventHttpConnection object
	 * @link http://www.php.net/manual/en/eventhttpconnection.construct.php
	 * @param EventBase $base Associated event base.
	 * @param EventDnsBase $dns_base If
	 * dns_base
	 * is null, hostname resolution will block.
	 * @param string $address The address to connect to.
	 * @param int $port The port to connect to.
	 * @param EventSslContext $ctx [optional] EventSslContext
	 * class object. Enables OpenSSL.
	 * <p>This parameter is available only if
	 * Event
	 * is compiled with OpenSSL support and only with
	 * Libevent
	 * 2.1.0-alpha
	 * and higher.</p>
	 * @return EventBase Returns
	 * EventHttpConnection
	 * object.
	 */
	public function __construct (EventBase $base, EventDnsBase $dns_base, string $address, int $port, EventSslContext $ctx = null): EventBase {}

	/**
	 * {@inheritdoc}
	 */
	final public function __sleep (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

	/**
	 * Returns event base associated with the connection
	 * @link http://www.php.net/manual/en/eventhttpconnection.getbase.php
	 * @return EventBase On success returns
	 * EventBase
	 * object associated with the connection. Otherwise false.
	 */
	public function getBase (): EventBase {}

	/**
	 * Gets the remote address and port associated with the connection
	 * @link http://www.php.net/manual/en/eventhttpconnection.getpeer.php
	 * @param string $address Address of the peer.
	 * @param int $port Port of the peer.
	 * @return void No value is returned.
	 */
	public function getPeer (string &$address, int &$port): void {}

	/**
	 * Sets the IP address from which HTTP connections are made
	 * @link http://www.php.net/manual/en/eventhttpconnection.setlocaladdress.php
	 * @param string $address The IP address from which HTTP connections are made.
	 * @return void No value is returned.
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
	 * @return void No value is returned.
	 */
	public function setTimeout (int $timeout): void {}

	/**
	 * Sets maximum header size
	 * @link http://www.php.net/manual/en/eventhttpconnection.setmaxheaderssize.php
	 * @param string $max_size The maximum header size in bytes.
	 * @return void No value is returned.
	 */
	public function setMaxHeadersSize (string $max_size): void {}

	/**
	 * Sets maximum body size for the connection
	 * @link http://www.php.net/manual/en/eventhttpconnection.setmaxbodysize.php
	 * @param string $max_size The maximum body size in bytes.
	 * @return void No value is returned.
	 */
	public function setMaxBodySize (string $max_size): void {}

	/**
	 * Sets the retry limit for the connection
	 * @link http://www.php.net/manual/en/eventhttpconnection.setretries.php
	 * @param int $retries The retry limit.
	 * -1
	 * means infinity.
	 * @return void No value is returned.
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
	 * @return bool Returns true on success or false on failure.
	 */
	public function makeRequest (EventHttpRequest $req, int $type, string $uri): bool {}

	/**
	 * Set callback for connection close
	 * @link http://www.php.net/manual/en/eventhttpconnection.setclosecallback.php
	 * @param callable $callback Callback which is called when connection is closed. Should match the
	 * following prototype:
	 * @param mixed $data [optional] 
	 * @return void No value is returned.
	 */
	public function setCloseCallback (callable $callback, mixed $data = null): void {}

}

/**
 * Represents HTTP server.
 * @link http://www.php.net/manual/en/class.eventhttp.php
 */
final class EventHttp  {

	/**
	 * Constructs EventHttp object(the HTTP server)
	 * @link http://www.php.net/manual/en/eventhttp.construct.php
	 * @param EventBase $base Associated event base.
	 * @param EventSslContext $ctx [optional] EventSslContext
	 * class object. Turns plain HTTP server into HTTPS server. It means that
	 * if
	 * ctx
	 * is configured correctly, then the underlying buffer events will be based
	 * on OpenSSL sockets. Thus, all traffic will pass through the SSL or TLS.
	 * <p>This parameter is available only if
	 * Event
	 * is compiled with OpenSSL support and only with
	 * Libevent
	 * 2.1.0-alpha
	 * and higher.</p>
	 * @return EventBase Returns
	 * EventHttp
	 * object.
	 */
	public function __construct (EventBase $base, EventSslContext $ctx = null): EventBase {}

	/**
	 * {@inheritdoc}
	 */
	final public function __sleep (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

	/**
	 * Makes an HTTP server accept connections on the specified socket stream or resource
	 * @link http://www.php.net/manual/en/eventhttp.accept.php
	 * @param mixed $socket Socket resource, stream or numeric file descriptor representing a socket
	 * ready to accept connections.
	 * @return bool Returns true on success or false on failure.
	 */
	public function accept (mixed $socket): bool {}

	/**
	 * Binds an HTTP server on the specified address and port
	 * @link http://www.php.net/manual/en/eventhttp.bind.php
	 * @param string $address A string containing the IP address to
	 * listen(2)
	 * on.
	 * @param int $port The port number to listen on.
	 * @return void Returns true on success or false on failure.
	 */
	public function bind (string $address, int $port): void {}

	/**
	 * Sets a callback for specified URI
	 * @link http://www.php.net/manual/en/eventhttp.setcallback.php
	 * @param string $path The path for which to invoke the callback.
	 * @param string $cb The callback
	 * callable
	 * that gets invoked on requested
	 * path
	 * . It should match the following prototype:
	 * <p><p>
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
	 * </p></p>
	 * <p>EventHttpRequest
	 * object.</p>
	 * <p>Custom data.</p>
	 * @param string $arg [optional] Custom data.
	 * @return void Returns true on success or false on failure.
	 */
	public function setCallback (string $path, string $cb, string $arg = null): void {}

	/**
	 * Sets default callback to handle requests that are not caught by specific callbacks
	 * @link http://www.php.net/manual/en/eventhttp.setdefaultcallback.php
	 * @param string $cb The callback
	 * callable
	 * . It should match the following prototype:
	 * <p><p>
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
	 * </p></p>
	 * <p>EventHttpRequest
	 * object.</p>
	 * <p>Custom data.</p>
	 * @param string $arg [optional] User custom data passed to the callback.
	 * @return void Returns true on success or false on failure.
	 */
	public function setDefaultCallback (string $cb, string $arg = null): void {}

	/**
	 * Sets the what HTTP methods are supported in requests accepted by this server, and passed to user callbacks
	 * @link http://www.php.net/manual/en/eventhttp.setallowedmethods.php
	 * @param int $methods A bit mask of
	 * EventHttpRequest::CMD_&#42;
	 * constants
	 * .
	 * @return void No value is returned.
	 */
	public function setAllowedMethods (int $methods): void {}

	/**
	 * Sets maximum request body size
	 * @link http://www.php.net/manual/en/eventhttp.setmaxbodysize.php
	 * @param int $value The body size in bytes.
	 * @return void No value is returned.
	 */
	public function setMaxBodySize (int $value): void {}

	/**
	 * Sets maximum HTTP header size
	 * @link http://www.php.net/manual/en/eventhttp.setmaxheaderssize.php
	 * @param int $value The header size in bytes.
	 * @return void No value is returned.
	 */
	public function setMaxHeadersSize (int $value): void {}

	/**
	 * Sets the timeout for an HTTP request
	 * @link http://www.php.net/manual/en/eventhttp.settimeout.php
	 * @param int $value The timeout in seconds.
	 * @return void No value is returned.
	 */
	public function setTimeout (int $value): void {}

	/**
	 * Adds a server alias to the HTTP server object
	 * @link http://www.php.net/manual/en/eventhttp.addserveralias.php
	 * @param string $alias The alias to add.
	 * @return bool Returns true on success or false on failure.
	 */
	public function addServerAlias (string $alias): bool {}

	/**
	 * Removes server alias
	 * @link http://www.php.net/manual/en/eventhttp.removeserveralias.php
	 * @param string $alias The alias to remove.
	 * @return bool Returns true on success or false on failure.
	 */
	public function removeServerAlias (string $alias): bool {}

}

/**
 * Represents an HTTP request.
 * @link http://www.php.net/manual/en/class.eventhttprequest.php
 */
final class EventHttpRequest  {
	/**
	 * GET method(command)
	const CMD_GET = 1;
	/**
	 * POST method(command)
	const CMD_POST = 2;
	/**
	 * HEAD method(command)
	const CMD_HEAD = 4;
	/**
	 * PUT method(command)
	const CMD_PUT = 8;
	/**
	 * DELETE command(method)
	const CMD_DELETE = 16;
	/**
	 * OPTIONS method(command)
	const CMD_OPTIONS = 32;
	/**
	 * TRACE method(command)
	const CMD_TRACE = 64;
	/**
	 * CONNECT method(command)
	const CMD_CONNECT = 128;
	/**
	 * PATCH method(command)
	const CMD_PATCH = 256;
	/**
	 * Request input header type.
	const INPUT_HEADER = 1;
	/**
	 * Request output header type.
	const OUTPUT_HEADER = 2;


	/**
	 * Constructs EventHttpRequest object
	 * @link http://www.php.net/manual/en/eventhttprequest.construct.php
	 * @param callable $callback Gets invoked on requesting path. Should match the following prototype:
	 * @param mixed $data [optional] User custom data passed to the callback.
	 * @return callable Returns EventHttpRequest object.
	 */
	public function __construct (callable $callback, mixed $data = null): callable {}

	/**
	 * {@inheritdoc}
	 */
	final public function __sleep (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function __wakeup (): void {}

	/**
	 * Frees the object and removes associated events
	 * @link http://www.php.net/manual/en/eventhttprequest.free.php
	 * @return void No value is returned.
	 */
	public function free (): void {}

	/**
	 * Returns the request command(method)
	 * @link http://www.php.net/manual/en/eventhttprequest.getcommand.php
	 * @return void Returns the request command, one of
	 * EventHttpRequest::CMD_&#42;
	 * constants.
	 */
	public function getCommand (): void {}

	/**
	 * Returns the request host
	 * @link http://www.php.net/manual/en/eventhttprequest.gethost.php
	 * @return string Returns the request host.
	 */
	public function getHost (): string {}

	/**
	 * Returns the request URI
	 * @link http://www.php.net/manual/en/eventhttprequest.geturi.php
	 * @return string Returns the request URI
	 */
	public function getUri (): string {}

	/**
	 * Returns the response code
	 * @link http://www.php.net/manual/en/eventhttprequest.getresponsecode.php
	 * @return int Returns the response code of the request.
	 */
	public function getResponseCode (): int {}

	/**
	 * Returns associative array of the input headers
	 * @link http://www.php.net/manual/en/eventhttprequest.getinputheaders.php
	 * @return array Returns associative array of the input headers.
	 */
	public function getInputHeaders (): array {}

	/**
	 * Returns associative array of the output headers
	 * @link http://www.php.net/manual/en/eventhttprequest.getoutputheaders.php
	 * @return void 
	 */
	public function getOutputHeaders (): void {}

	/**
	 * Returns the input buffer
	 * @link http://www.php.net/manual/en/eventhttprequest.getinputbuffer.php
	 * @return EventBuffer Returns the input buffer.
	 */
	public function getInputBuffer (): EventBuffer {}

	/**
	 * Returns the output buffer of the request
	 * @link http://www.php.net/manual/en/eventhttprequest.getoutputbuffer.php
	 * @return EventBuffer Returns the output buffer of the request.
	 */
	public function getOutputBuffer (): EventBuffer {}

	/**
	 * Returns EventBufferEvent object
	 * @link http://www.php.net/manual/en/eventhttprequest.getbufferevent.php
	 * @return EventBufferEvent Returns
	 * EventBufferEvent
	 * object.
	 */
	public function getBufferEvent (): EventBufferEvent {}

	/**
	 * Returns EventHttpConnection object
	 * @link http://www.php.net/manual/en/eventhttprequest.getconnection.php
	 * @return EventHttpConnection Returns
	 * EventHttpConnection
	 * object.
	 */
	public function getConnection (): EventHttpConnection {}

	/**
	 * Closes associated HTTP connection
	 * @link http://www.php.net/manual/en/eventhttprequest.closeconnection.php
	 * @return void No value is returned.
	 */
	public function closeConnection (): void {}

	/**
	 * Send an HTML error message to the client
	 * @link http://www.php.net/manual/en/eventhttprequest.senderror.php
	 * @param int $error The HTTP error code.
	 * @param string $reason [optional] A brief explanation ofthe error. If null, the standard meaning of the
	 * error code will be used.
	 * @return void No value is returned.
	 */
	public function sendError (int $error, string $reason = null): void {}

	/**
	 * Send an HTML reply to the client
	 * @link http://www.php.net/manual/en/eventhttprequest.sendreply.php
	 * @param int $code The HTTP response code to send.
	 * @param string $reason A brief message to send with the response code.
	 * @param EventBuffer $buf [optional] The body of the response.
	 * @return void No value is returned.
	 */
	public function sendReply (int $code, string $reason, EventBuffer $buf = null): void {}

	/**
	 * Send another data chunk as part of an ongoing chunked reply
	 * @link http://www.php.net/manual/en/eventhttprequest.sendreplychunk.php
	 * @param EventBuffer $buf The data chunk to send as part of the reply.
	 * @return void No value is returned.
	 */
	public function sendReplyChunk (EventBuffer $buf): void {}

	/**
	 * Complete a chunked reply, freeing the request as appropriate
	 * @link http://www.php.net/manual/en/eventhttprequest.sendreplyend.php
	 * @return void No value is returned.
	 */
	public function sendReplyEnd (): void {}

	/**
	 * Initiate a chunked reply
	 * @link http://www.php.net/manual/en/eventhttprequest.sendreplystart.php
	 * @param int $code The HTTP response code to send.
	 * @param string $reason A brief message to send with the response code.
	 * @return void No value is returned.
	 */
	public function sendReplyStart (int $code, string $reason): void {}

	/**
	 * Cancels a pending HTTP request
	 * @link http://www.php.net/manual/en/eventhttprequest.cancel.php
	 * @return void No value is returned.
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
	 * @return bool Returns true on success or false on failure.
	 */
	public function addHeader (string $key, string $value, int $type): bool {}

	/**
	 * Removes all output headers from the header list of the request
	 * @link http://www.php.net/manual/en/eventhttprequest.clearheaders.php
	 * @return void No value is returned.
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
	public function removeHeader (string $key, string $type): void {}

	/**
	 * Finds the value belonging a header
	 * @link http://www.php.net/manual/en/eventhttprequest.findheader.php
	 * @param string $key The header name.
	 * @param string $type One of
	 * EventHttpRequest::&#42;_HEADER constants
	 * .
	 * @return void Returns null if header not found.
	 */
	public function findHeader (string $key, string $type): void {}

}

/**
 * EventUtil
 * is a singleton with supplimentary methods and constants.
 * @link http://www.php.net/manual/en/class.eventutil.php
 */
final class EventUtil  {
	/**
	 * IPv4 address family
	const AF_INET = 2;
	/**
	 * IPv6 address family
	const AF_INET6 = 30;
	const AF_UNIX = 1;
	/**
	 * Unspecified IP address family
	const AF_UNSPEC = 0;
	/**
	 * Socket option. Enable socket debugging. Only allowed for processes with
	 * the
	 * CAP_NET_ADMIN
	 * capability or an effective user ID of
	 * 0
	 * . (Added in event-1.6.0.)
	const SO_DEBUG = 1;
	/**
	 * Socket option. Indicates that the rules used in validating addresses
	 * supplied in a
	 * bind(2)
	 * call should allow reuse of local addresses. See the
	 * socket(7)
	 * manual page. (Added in event-1.6.0.)
	const SO_REUSEADDR = 4;
	/**
	 * Socket option. Enable sending of keep-alive messages on
	 * connection-oriented sockets. Expects an integer boolean flag. See the
	 * socket(7)
	 * manual page. (Added in event-1.6.0.)
	const SO_KEEPALIVE = 8;
	/**
	 * Socket option. See the
	 * socket(7)
	 * manual page. (Added in event-1.6.0.)
	const SO_DONTROUTE = 16;
	/**
	 * Socket option. When enabled, a
	 * close(2)
	 * or
	 * shutdown(2)
	 * will not return until all queued messages for the socket have been
	 * successfully sent or the linger timeout has been reached. Otherwise,
	 * the call returns immediately and the closing is done in the background.
	 * See the
	 * socket(7)
	 * manual page. (Added in event-1.6.0.)
	const SO_LINGER = 128;
	/**
	 * Socket option. Reports whether transmission of broadcast messages is
	 * supported. See the
	 * socket(7)
	 * manual page. (Added in event-1.6.0.)
	const SO_BROADCAST = 32;
	/**
	 * Socket option. See the
	 * socket(7)
	 * manual page. (Added in event-1.6.0.)
	const SO_OOBINLINE = 256;
	/**
	 * Socket option. See the
	 * socket(7)
	 * manual page. (Added in event-1.6.0.)
	const SO_SNDBUF = 4097;
	/**
	 * Socket option. See the
	 * socket(7)
	 * manual page. (Added in event-1.6.0.)
	const SO_RCVBUF = 4098;
	/**
	 * Socket option. See the
	 * socket(7)
	 * manual page. (Added in event-1.6.0.)
	const SO_SNDLOWAT = 4099;
	/**
	 * Socket option. See the
	 * socket(7)
	 * manual page. (Added in event-1.6.0.)
	const SO_RCVLOWAT = 4100;
	/**
	 * Socket option. See the
	 * socket(7)
	 * manual page. (Added in event-1.6.0.)
	const SO_SNDTIMEO = 4101;
	/**
	 * Socket option. See the
	 * socket(7)
	 * manual page. (Added in event-1.6.0.)
	const SO_RCVTIMEO = 4102;
	/**
	 * Socket option. See the
	 * socket(7)
	 * manual page. (Added in event-1.6.0.)
	const SO_TYPE = 4104;
	/**
	 * Socket option. See the
	 * socket(7)
	 * manual page. (Added in event-1.6.0.)
	const SO_ERROR = 4103;
	const TCP_NODELAY = 1;
	/**
	 * Socket option level. See the
	 * socket(7)
	 * manual page. (Added in event-1.6.0.)
	const SOL_SOCKET = 65535;
	/**
	 * Socket option level. See the
	 * socket(7)
	 * manual page. (Added in event-1.6.0.)
	const SOL_TCP = 6;
	/**
	 * Socket option level. See the
	 * socket(7)
	 * manual page. (Added in event-1.6.0.)
	const SOL_UDP = 17;
	const SOCK_RAW = 3;
	/**
	 * See the
	 * socket(7)
	 * manual page. (Added in event-1.6.0.)
	const IPPROTO_IP = 0;
	/**
	 * See the
	 * socket(7)
	 * manual page. (Added in event-1.6.0.)
	const IPPROTO_IPV6 = 41;
	/**
	 * Libevent' version number at the time when Event extension had been
	 * compiled with the library.
	const LIBEVENT_VERSION_NUMBER = 33623040;


	/**
	 * The abstract constructor
	 * @link http://www.php.net/manual/en/eventutil.construct.php
	 * @return void No value is returned.
	 */
	private function __construct (): void {}

	/**
	 * Returns the most recent socket error number
	 * @link http://www.php.net/manual/en/eventutil.getlastsocketerrno.php
	 * @param mixed $socket [optional] Socket resource, stream or a file descriptor of a socket.
	 * @return int Returns the most recent socket error number(
	 * errno
	 * ).
	 */
	public static function getLastSocketErrno (mixed $socket = null): int {}

	/**
	 * Returns the most recent socket error
	 * @link http://www.php.net/manual/en/eventutil.getlastsocketerror.php
	 * @param mixed $socket [optional] Socket resource, stream or a file descriptor of a socket.
	 * @return string Returns the most recent socket error.
	 */
	public static function getLastSocketError (mixed $socket = null): string {}

	/**
	 * Generates entropy by means of OpenSSL's RAND_poll()
	 * @link http://www.php.net/manual/en/eventutil.sslrandpoll.php
	 * @return void No value is returned.
	 */
	public static function sslRandPoll (): void {}

	/**
	 * Retreives the current address to which the
	 * socket is bound
	 * @link http://www.php.net/manual/en/eventutil.getsocketname.php
	 * @param mixed $socket Socket resource, stream or a file descriptor of a socket.
	 * @param string $address Output parameter. IP-address, or the UNIX domain socket path depending
	 * on the socket address family.
	 * @param mixed $port [optional] Output parameter. The port the socket is bound to. Has no meaning for
	 * UNIX domain sockets.
	 * @return bool Returns true on success or false on failure.
	 */
	public static function getSocketName (mixed $socket, string &$address, mixed &$port = null): bool {}

	/**
	 * Returns numeric file descriptor of a socket, or stream
	 * @link http://www.php.net/manual/en/eventutil.getsocketfd.php
	 * @param mixed $socket Socket resource or stream.
	 * @return int Returns numeric file descriptor of a socket, or stream.
	 * EventUtil::getSocketFd
	 * returns false in case if it is whether failed to recognize the type of
	 * the underlying file, or detected that the file descriptor associated with
	 * socket
	 * is not valid.
	 */
	public static function getSocketFd (mixed $socket): int {}

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
	 * @return bool Returns true on success or false on failure.
	 */
	public static function setSocketOption (mixed $socket, int $level, int $optname, mixed $optval): bool {}

	/**
	 * {@inheritdoc}
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
	/**
	 * TLS client method. See
	 * SSL_CTX_new(3)
	 * man page.
	const TLS_CLIENT_METHOD = 4;
	/**
	 * TLS server method. See
	 * SSL_CTX_new(3)
	 * man page.
	const TLS_SERVER_METHOD = 8;
	const TLSv11_CLIENT_METHOD = 9;
	const TLSv11_SERVER_METHOD = 10;
	const TLSv12_CLIENT_METHOD = 11;
	const TLSv12_SERVER_METHOD = 12;
	/**
	 * Key for an item of the options' array used in
	 * EventSslContext::__construct
	 * . The option points to path of local certificate.
	const OPT_LOCAL_CERT = 1;
	/**
	 * Key for an item of the options' array used in
	 * EventSslContext::__construct
	 * . The option points to path of the private key.
	const OPT_LOCAL_PK = 2;
	/**
	 * Key for an item of the options' array used in
	 * EventSslContext::__construct
	 * . Represents passphrase of the certificate.
	const OPT_PASSPHRASE = 3;
	/**
	 * Key for an item of the options' array used in
	 * EventSslContext::__construct
	 * . Represents path of the certificate authority file.
	const OPT_CA_FILE = 4;
	/**
	 * Key for an item of the options' array used in
	 * EventSslContext::__construct
	 * . Represents path where the certificate authority file should be
	 * searched for.
	const OPT_CA_PATH = 5;
	/**
	 * Key for an item of the options' array used in
	 * EventSslContext::__construct
	 * . Represents option that allows self-signed certificates.
	const OPT_ALLOW_SELF_SIGNED = 6;
	/**
	 * Key for an item of the options' array used in
	 * EventSslContext::__construct
	 * . Represents option that tells Event to verify peer.
	const OPT_VERIFY_PEER = 7;
	/**
	 * Key for an item of the options' array used in
	 * EventSslContext::__construct
	 * . Represents maximum depth for the certificate chain verification that
	 * shall be allowed for the SSL context.
	const OPT_VERIFY_DEPTH = 8;
	/**
	 * Key for an item of the options' array used in
	 * EventSslContext::__construct
	 * . Represents the cipher list for the SSL context.
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


	/**
	 * Path to local certificate file on filesystem. It must be a PEM-encoded
	 * file which contains certificate. It can optionally contain the
	 * certificate chain of issuers.
	 * @var string
	 * @link http://www.php.net/manual/en/class.eventsslcontext.php#eventsslcontext.props.local_cert
	 */
	public string $local_cert;

	/**
	 * Path to local private key file
	 * @var string
	 * @link http://www.php.net/manual/en/class.eventsslcontext.php#eventsslcontext.props.local_pk
	 */
	public string $local_pk;

	/**
	 * Constructs an OpenSSL context for use with Event classes
	 * @link http://www.php.net/manual/en/eventsslcontext.construct.php
	 * @param string $method One of
	 * EventSslContext::&#42;_METHOD constants
	 * .
	 * @param string $options Associative array of SSL context options One of
	 * EventSslContext::OPT_&#42; constants
	 * .
	 * @return string Returns
	 * EventSslContext
	 * object.
	 */
	public function __construct (string $method, string $options): string {}

	/**
	 * {@inheritdoc}
	 * @param int $proto
	 */
	public function setMinProtoVersion (int $proto): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $proto
	 */
	public function setMaxProtoVersion (int $proto): bool {}

}

class EventException extends RuntimeException implements Stringable, Throwable {

	public $errorInfo;

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
define ('EVENT_NS', "");

// End of event v.3.0.8
