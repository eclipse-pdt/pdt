<?php

// Start of event v.3.0.8

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
	 * {@inheritdoc}
	 * @param EventBase $base
	 * @param mixed $fd
	 * @param int $what
	 * @param callable $cb
	 * @param mixed $arg [optional]
	 */
	public function __construct (EventBase $base, mixed $fd = null, int $what, callable $cb, mixed $arg = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function free (): void {}

	/**
	 * {@inheritdoc}
	 * @param EventBase $base
	 * @param mixed $fd
	 * @param int $what [optional]
	 * @param callable|null $cb [optional]
	 * @param mixed $arg [optional]
	 */
	public function set (EventBase $base, mixed $fd = null, int $what = NULL, ?callable $cb = NULL, mixed $arg = NULL): bool {}

	/**
	 * {@inheritdoc}
	 */
	public static function getSupportedMethods (): array {}

	/**
	 * {@inheritdoc}
	 * @param float $timeout [optional]
	 */
	public function add (float $timeout = -1): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function del (): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $priority
	 */
	public function setPriority (int $priority): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $flags
	 */
	public function pending (int $flags): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function removeTimer (): bool {}

	/**
	 * {@inheritdoc}
	 * @param EventBase $base
	 * @param callable $cb
	 * @param mixed $arg [optional]
	 */
	public static function timer (EventBase $base, callable $cb, mixed $arg = NULL): Event {}

	/**
	 * {@inheritdoc}
	 * @param EventBase $base
	 * @param callable $cb
	 * @param mixed $arg [optional]
	 */
	public function setTimer (EventBase $base, callable $cb, mixed $arg = NULL): bool {}

	/**
	 * {@inheritdoc}
	 * @param EventBase $base
	 * @param int $signum
	 * @param callable $cb
	 * @param mixed $arg [optional]
	 */
	public static function signal (EventBase $base, int $signum, callable $cb, mixed $arg = NULL): Event {}

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
	 * {@inheritdoc}
	 * @param EventConfig|null $cfg [optional]
	 */
	public function __construct (?EventConfig $cfg = NULL) {}

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
	public function getMethod (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function getFeatures (): int {}

	/**
	 * {@inheritdoc}
	 * @param int $n_priorities
	 */
	public function priorityInit (int $n_priorities): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $flags [optional]
	 */
	public function loop (int $flags = -1): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function dispatch (): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $timeout [optional]
	 */
	public function exit (float $timeout = 0.0): bool {}

	/**
	 * {@inheritdoc}
	 * @param Event $event
	 */
	public function set (Event $event): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function stop (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function gotStop (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function gotExit (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getTimeOfDayCached (): float {}

	/**
	 * {@inheritdoc}
	 */
	public function reInit (): bool {}

	/**
	 * {@inheritdoc}
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

final class EventConfig  {
	const FEATURE_ET = 1;
	const FEATURE_O1 = 2;
	const FEATURE_FDS = 4;


	/**
	 * {@inheritdoc}
	 */
	public function __construct () {}

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
	 * @param string $method
	 */
	public function avoidMethod (string $method): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $feature
	 */
	public function requireFeatures (int $feature): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $max_interval
	 * @param int $max_callbacks
	 * @param int $min_priority
	 */
	public function setMaxDispatchInterval (int $max_interval, int $max_callbacks, int $min_priority): void {}

	/**
	 * {@inheritdoc}
	 * @param int $flags
	 */
	public function setFlags (int $flags): bool {}

}

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
	 * {@inheritdoc}
	 * @param EventBase $base
	 * @param mixed $socket [optional]
	 * @param int $options [optional]
	 * @param callable|null $readcb [optional]
	 * @param callable|null $writecb [optional]
	 * @param callable|null $eventcb [optional]
	 * @param mixed $arg [optional]
	 */
	public function __construct (EventBase $base, mixed $socket = NULL, int $options = 0, ?callable $readcb = NULL, ?callable $writecb = NULL, ?callable $eventcb = NULL, mixed $arg = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function free (): void {}

	/**
	 * {@inheritdoc}
	 */
	public function close (): void {}

	/**
	 * {@inheritdoc}
	 * @param string $addr
	 */
	public function connect (string $addr): bool {}

	/**
	 * {@inheritdoc}
	 * @param EventDnsBase|null $dns_base
	 * @param string $hostname
	 * @param int $port
	 * @param int $family [optional]
	 */
	public function connectHost (?EventDnsBase $dns_base = null, string $hostname, int $port, int $family = 0): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getDnsErrorString (): string {}

	/**
	 * {@inheritdoc}
	 * @param callable|null $readcb
	 * @param callable|null $writecb
	 * @param callable|null $eventcb
	 * @param mixed $arg [optional]
	 */
	public function setCallbacks (?callable $readcb = null, ?callable $writecb = null, ?callable $eventcb = null, mixed $arg = NULL): void {}

	/**
	 * {@inheritdoc}
	 * @param int $events
	 */
	public function enable (int $events): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $events
	 */
	public function disable (int $events): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getEnabled (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getInput (): EventBuffer {}

	/**
	 * {@inheritdoc}
	 */
	public function getOutput (): EventBuffer {}

	/**
	 * {@inheritdoc}
	 * @param int $events
	 * @param int $lowmark
	 * @param int $highmark
	 */
	public function setWatermark (int $events, int $lowmark, int $highmark): void {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	public function write (string $data): bool {}

	/**
	 * {@inheritdoc}
	 * @param EventBuffer $buf
	 */
	public function writeBuffer (EventBuffer $buf): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $size
	 */
	public function read (int $size): ?string {}

	/**
	 * {@inheritdoc}
	 * @param EventBuffer $buf
	 */
	public function readBuffer (EventBuffer $buf): bool {}

	/**
	 * {@inheritdoc}
	 * @param EventBase $base
	 * @param int $options [optional]
	 */
	public static function createPair (EventBase $base, int $options = 0): array|false {}

	/**
	 * {@inheritdoc}
	 * @param int $priority
	 */
	public function setPriority (int $priority): bool {}

	/**
	 * {@inheritdoc}
	 * @param float $timeout_read
	 * @param float $timeout_write
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
	 * {@inheritdoc}
	 * @param EventBase $base
	 * @param mixed $socket
	 * @param EventSslContext $ctx
	 * @param int $state
	 * @param int $options [optional]
	 */
	public static function sslSocket (EventBase $base, mixed $socket = null, EventSslContext $ctx, int $state, int $options = 0): EventBufferEvent {}

	/**
	 * {@inheritdoc}
	 */
	public function sslError (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function sslRenegotiate (): void {}

	/**
	 * {@inheritdoc}
	 */
	public function sslGetCipherInfo (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function sslGetCipherName (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function sslGetCipherVersion (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function sslGetProtocol (): string {}

}

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
	 * {@inheritdoc}
	 */
	public function __construct () {}

	/**
	 * {@inheritdoc}
	 * @param bool $at_front
	 */
	public function freeze (bool $at_front): bool {}

	/**
	 * {@inheritdoc}
	 * @param bool $at_front
	 */
	public function unfreeze (bool $at_front): bool {}

	/**
	 * {@inheritdoc}
	 * @param bool $at_front
	 */
	public function lock (bool $at_front): void {}

	/**
	 * {@inheritdoc}
	 * @param bool $at_front
	 */
	public function unlock (bool $at_front): void {}

	/**
	 * {@inheritdoc}
	 */
	public function enableLocking (): void {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	public function add (string $data): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $max_bytes
	 */
	public function read (int $max_bytes): string {}

	/**
	 * {@inheritdoc}
	 * @param EventBuffer $buf
	 */
	public function addBuffer (EventBuffer $buf): bool {}

	/**
	 * {@inheritdoc}
	 * @param EventBuffer $buf
	 * @param int $len
	 */
	public function appendFrom (EventBuffer $buf, int $len): int {}

	/**
	 * {@inheritdoc}
	 * @param int $len
	 */
	public function expand (int $len): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 */
	public function prepend (string $data): bool {}

	/**
	 * {@inheritdoc}
	 * @param EventBuffer $buf
	 */
	public function prependBuffer (EventBuffer $buf): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $len
	 */
	public function drain (int $len): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $data
	 * @param int $max_bytes
	 */
	public function copyout (string &$data, int $max_bytes): int {}

	/**
	 * {@inheritdoc}
	 * @param int $eol_style
	 */
	public function readLine (int $eol_style): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string $what
	 * @param int $start [optional]
	 * @param int $end [optional]
	 */
	public function search (string $what, int $start = -1, int $end = -1): int|false {}

	/**
	 * {@inheritdoc}
	 * @param int $start [optional]
	 * @param int $eol_style [optional]
	 */
	public function searchEol (int $start = -1, int $eol_style = 0): int|false {}

	/**
	 * {@inheritdoc}
	 * @param int $size
	 */
	public function pullup (int $size): ?string {}

	/**
	 * {@inheritdoc}
	 * @param mixed $fd
	 * @param int $howmuch [optional]
	 */
	public function write (mixed $fd = null, int $howmuch = -1): int|false {}

	/**
	 * {@inheritdoc}
	 * @param mixed $fd
	 * @param int $howmuch [optional]
	 */
	public function readFrom (mixed $fd = null, int $howmuch = -1): int|false {}

	/**
	 * {@inheritdoc}
	 * @param int $start
	 * @param int $length [optional]
	 */
	public function substr (int $start, int $length = -1): string|false {}

}

final class EventDnsBase  {
	const OPTION_SEARCH = 1;
	const OPTION_NAMESERVERS = 2;
	const OPTION_MISC = 4;
	const OPTION_HOSTSFILE = 8;
	const OPTIONS_ALL = 15;


	/**
	 * {@inheritdoc}
	 * @param EventBase $base
	 * @param bool $initialize
	 */
	public function __construct (EventBase $base, bool $initialize) {}

	/**
	 * {@inheritdoc}
	 * @param int $flags
	 * @param string $filename
	 */
	public function parseResolvConf (int $flags, string $filename): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $ip
	 */
	public function addNameserverIp (string $ip): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $hosts
	 */
	public function loadHosts (string $hosts): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function clearSearch (): void {}

	/**
	 * {@inheritdoc}
	 * @param string $domain
	 */
	public function addSearch (string $domain): void {}

	/**
	 * {@inheritdoc}
	 * @param int $ndots
	 */
	public function setSearchNdots (int $ndots): void {}

	/**
	 * {@inheritdoc}
	 * @param string $option
	 * @param string $value
	 */
	public function setOption (string $option, string $value): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function countNameservers (): int {}

}

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
	 * {@inheritdoc}
	 * @param EventBase $base
	 * @param callable $cb
	 * @param mixed $data
	 * @param int $flags
	 * @param int $backlog
	 * @param mixed $target
	 */
	public function __construct (EventBase $base, callable $cb, mixed $data = null, int $flags, int $backlog, mixed $target = null) {}

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
	 * {@inheritdoc}
	 */
	public function enable (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function disable (): bool {}

	/**
	 * {@inheritdoc}
	 * @param callable $cb
	 * @param mixed $arg [optional]
	 */
	public function setCallback (callable $cb, mixed $arg = NULL): void {}

	/**
	 * {@inheritdoc}
	 * @param callable $cb
	 */
	public function setErrorCallback (callable $cb): void {}

	/**
	 * {@inheritdoc}
	 */
	public function getBase (): EventBase {}

	/**
	 * {@inheritdoc}
	 * @param mixed $address
	 * @param mixed $port
	 */
	public function getSocketName (mixed &$address = null, mixed &$port = null): bool {}

}

final class EventHttpConnection  {

	/**
	 * {@inheritdoc}
	 * @param EventBase $base
	 * @param EventDnsBase|null $dns_base
	 * @param string $address
	 * @param int $port
	 * @param EventSslContext|null $ctx [optional]
	 */
	public function __construct (EventBase $base, ?EventDnsBase $dns_base = null, string $address, int $port, ?EventSslContext $ctx = NULL) {}

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
	public function getBase (): EventBase|false {}

	/**
	 * {@inheritdoc}
	 * @param mixed $address
	 * @param mixed $port
	 */
	public function getPeer (mixed &$address = null, mixed &$port = null): void {}

	/**
	 * {@inheritdoc}
	 * @param string $address
	 */
	public function setLocalAddress (string $address): void {}

	/**
	 * {@inheritdoc}
	 * @param int $port
	 */
	public function setLocalPort (int $port): void {}

	/**
	 * {@inheritdoc}
	 * @param int $timeout
	 */
	public function setTimeout (int $timeout): void {}

	/**
	 * {@inheritdoc}
	 * @param int $max_size
	 */
	public function setMaxHeadersSize (int $max_size): void {}

	/**
	 * {@inheritdoc}
	 * @param int $max_size
	 */
	public function setMaxBodySize (int $max_size): void {}

	/**
	 * {@inheritdoc}
	 * @param int $retries
	 */
	public function setRetries (int $retries): void {}

	/**
	 * {@inheritdoc}
	 * @param EventHttpRequest $req
	 * @param int $type
	 * @param string $uri
	 */
	public function makeRequest (EventHttpRequest $req, int $type, string $uri): ?bool {}

	/**
	 * {@inheritdoc}
	 * @param callable $callback
	 * @param mixed $data [optional]
	 */
	public function setCloseCallback (callable $callback, mixed $data = NULL): void {}

}

final class EventHttp  {

	/**
	 * {@inheritdoc}
	 * @param EventBase $base
	 * @param EventSslContext|null $ctx [optional]
	 */
	public function __construct (EventBase $base, ?EventSslContext $ctx = NULL) {}

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
	 * @param mixed $socket
	 */
	public function accept (mixed $socket = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $address
	 * @param int $port
	 */
	public function bind (string $address, int $port): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $path
	 * @param callable $cb
	 * @param mixed $arg [optional]
	 */
	public function setCallback (string $path, callable $cb, mixed $arg = NULL): bool {}

	/**
	 * {@inheritdoc}
	 * @param callable $cb
	 * @param mixed $arg [optional]
	 */
	public function setDefaultCallback (callable $cb, mixed $arg = NULL): void {}

	/**
	 * {@inheritdoc}
	 * @param int $methods
	 */
	public function setAllowedMethods (int $methods): void {}

	/**
	 * {@inheritdoc}
	 * @param int $value
	 */
	public function setMaxBodySize (int $value): void {}

	/**
	 * {@inheritdoc}
	 * @param int $value
	 */
	public function setMaxHeadersSize (int $value): void {}

	/**
	 * {@inheritdoc}
	 * @param int $value
	 */
	public function setTimeout (int $value): void {}

	/**
	 * {@inheritdoc}
	 * @param string $alias
	 */
	public function addServerAlias (string $alias): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $alias
	 */
	public function removeServerAlias (string $alias): bool {}

}

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
	 * {@inheritdoc}
	 * @param callable $callback
	 * @param mixed $data [optional]
	 */
	public function __construct (callable $callback, mixed $data = NULL) {}

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
	 * {@inheritdoc}
	 */
	public function getCommand (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getHost (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function getUri (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function getResponseCode (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getInputHeaders (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function getOutputHeaders (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function getInputBuffer (): EventBuffer {}

	/**
	 * {@inheritdoc}
	 */
	public function getOutputBuffer (): EventBuffer {}

	/**
	 * {@inheritdoc}
	 */
	public function getBufferEvent (): ?EventBufferEvent {}

	/**
	 * {@inheritdoc}
	 */
	public function getConnection (): ?EventHttpConnection {}

	/**
	 * {@inheritdoc}
	 */
	public function closeConnection (): void {}

	/**
	 * {@inheritdoc}
	 * @param int $error
	 * @param string|null $reason [optional]
	 */
	public function sendError (int $error, ?string $reason = NULL): void {}

	/**
	 * {@inheritdoc}
	 * @param int $code
	 * @param string $reason
	 * @param EventBuffer|null $buf [optional]
	 */
	public function sendReply (int $code, string $reason, ?EventBuffer $buf = NULL): void {}

	/**
	 * {@inheritdoc}
	 * @param EventBuffer $buf
	 */
	public function sendReplyChunk (EventBuffer $buf): void {}

	/**
	 * {@inheritdoc}
	 */
	public function sendReplyEnd (): void {}

	/**
	 * {@inheritdoc}
	 * @param int $code
	 * @param string $reason
	 */
	public function sendReplyStart (int $code, string $reason): void {}

	/**
	 * {@inheritdoc}
	 */
	public function cancel (): void {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param string $value
	 * @param int $type
	 */
	public function addHeader (string $key, string $value, int $type): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function clearHeaders (): void {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $type
	 */
	public function removeHeader (string $key, int $type): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $key
	 * @param int $type
	 */
	public function findHeader (string $key, int $type): ?string {}

}

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
	 * {@inheritdoc}
	 */
	private function __construct () {}

	/**
	 * {@inheritdoc}
	 * @param Socket|null $socket [optional]
	 */
	public static function getLastSocketErrno (?Socket $socket = NULL): int|false {}

	/**
	 * {@inheritdoc}
	 * @param mixed $socket [optional]
	 */
	public static function getLastSocketError (mixed $socket = NULL): string|false {}

	/**
	 * {@inheritdoc}
	 */
	public static function sslRandPoll (): bool {}

	/**
	 * {@inheritdoc}
	 * @param mixed $socket
	 * @param mixed $address
	 * @param mixed $port [optional]
	 */
	public static function getSocketName (mixed $socket = null, mixed &$address = null, mixed &$port = NULL): bool {}

	/**
	 * {@inheritdoc}
	 * @param mixed $socket
	 */
	public static function getSocketFd (mixed $socket = null): int {}

	/**
	 * {@inheritdoc}
	 * @param mixed $socket
	 * @param int $level
	 * @param int $optname
	 * @param mixed $optval
	 */
	public static function setSocketOption (mixed $socket = null, int $level, int $optname, mixed $optval = null): bool {}

	/**
	 * {@inheritdoc}
	 * @param int $fd
	 */
	public static function createSocket (int $fd): Socket|false {}

}

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
	const OPENSSL_VERSION_TEXT = "OpenSSL 3.1.2 1 Aug 2023";
	const OPENSSL_VERSION_NUMBER = 806354976;
	const SSL3_VERSION = 768;
	const TLS1_VERSION = 769;
	const TLS1_1_VERSION = 770;
	const TLS1_2_VERSION = 771;
	const DTLS1_VERSION = 65279;
	const DTLS1_2_VERSION = 65277;


	public $local_cert;

	public $local_pk;

	/**
	 * {@inheritdoc}
	 * @param int $method
	 * @param array $options
	 */
	public function __construct (int $method, array $options) {}

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
	 * {@inheritdoc}
	 * @param string $message [optional]
	 * @param int $code [optional]
	 * @param Throwable|null $previous [optional]
	 */
	public function __construct (string $message = '', int $code = 0, ?Throwable $previous = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function __wakeup () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getMessage (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getCode () {}

	/**
	 * {@inheritdoc}
	 */
	final public function getFile (): string {}

	/**
	 * {@inheritdoc}
	 */
	final public function getLine (): int {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTrace (): array {}

	/**
	 * {@inheritdoc}
	 */
	final public function getPrevious (): ?Throwable {}

	/**
	 * {@inheritdoc}
	 */
	final public function getTraceAsString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

}
define ('EVENT_NS', "");

// End of event v.3.0.8
