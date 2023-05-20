<?php

// Start of grpc v.1.54.0

namespace Grpc {

class Call  {
	protected $channel;


	/**
	 * @param mixed $channel
	 * @param mixed $method
	 * @param mixed $deadline
	 * @param mixed $host_override [optional]
	 */
	public function __construct ($channel = null, $method = null, $deadline = null, $host_override = null) {}

	/**
	 * @param mixed $ops
	 */
	public function startBatch ($ops = null) {}

	public function getPeer () {}

	public function cancel () {}

	/**
	 * @param mixed $credentials
	 */
	public function setCredentials ($credentials = null) {}

}

class Channel  {

	/**
	 * @param mixed $target
	 * @param mixed $args
	 */
	public function __construct ($target = null, $args = null) {}

	public function getTarget () {}

	/**
	 * @param mixed $try_to_connect [optional]
	 */
	public function getConnectivityState ($try_to_connect = null) {}

	/**
	 * @param mixed $last_state
	 * @param mixed $deadline
	 */
	public function watchConnectivityState ($last_state = null, $deadline = null) {}

	public function close () {}

}

class Server  {

	/**
	 * @param mixed $args [optional]
	 */
	public function __construct ($args = null) {}

	public function requestCall () {}

	/**
	 * @param mixed $addr
	 */
	public function addHttp2Port ($addr = null) {}

	/**
	 * @param mixed $addr
	 * @param mixed $server_creds
	 */
	public function addSecureHttp2Port ($addr = null, $server_creds = null) {}

	public function start () {}

}

class Timeval  {

	/**
	 * @param mixed $microseconds
	 */
	public function __construct ($microseconds = null) {}

	/**
	 * @param mixed $timeval
	 */
	public function add ($timeval = null) {}

	/**
	 * @param mixed $a_timeval
	 * @param mixed $b_timeval
	 */
	public static function compare ($a_timeval = null, $b_timeval = null) {}

	public static function infFuture () {}

	public static function infPast () {}

	public static function now () {}

	/**
	 * @param mixed $a_timeval
	 * @param mixed $b_timeval
	 * @param mixed $threshold_timeval
	 */
	public static function similar ($a_timeval = null, $b_timeval = null, $threshold_timeval = null) {}

	public function sleepUntil () {}

	/**
	 * @param mixed $timeval
	 */
	public function subtract ($timeval = null) {}

	public static function zero () {}

}

class ChannelCredentials  {

	/**
	 * @param mixed $pem_roots
	 */
	public static function setDefaultRootsPem ($pem_roots = null) {}

	public static function isDefaultRootsPemSet () {}

	public static function invalidateDefaultRootsPem () {}

	public static function createDefault () {}

	/**
	 * @param mixed $pem_root_certs [optional]
	 * @param mixed $pem_private_key [optional]
	 * @param mixed $pem_cert_chain [optional]
	 */
	public static function createSsl ($pem_root_certs = null, $pem_private_key = null, $pem_cert_chain = null) {}

	/**
	 * @param mixed $channel_creds
	 * @param mixed $call_creds
	 */
	public static function createComposite ($channel_creds = null, $call_creds = null) {}

	public static function createInsecure () {}

	/**
	 * @param \Grpc\ChannelCredentials|null $fallback_creds
	 */
	public static function createXds (\Grpc\ChannelCredentials|null $fallback_creds = null) {}

}

class CallCredentials  {

	/**
	 * @param mixed $creds1
	 * @param mixed $creds2
	 */
	public static function createComposite ($creds1 = null, $creds2 = null) {}

	/**
	 * @param mixed $callback
	 */
	public static function createFromPlugin ($callback = null) {}

}

class ServerCredentials  {

	/**
	 * @param mixed $pem_root_certs
	 * @param mixed $pem_private_key
	 * @param mixed $pem_cert_chain
	 */
	public static function createSsl ($pem_root_certs = null, $pem_private_key = null, $pem_cert_chain = null) {}

}

}


namespace {

define ('Grpc\CALL_OK', 0);
define ('Grpc\CALL_ERROR', 1);
define ('Grpc\CALL_ERROR_NOT_ON_SERVER', 2);
define ('Grpc\CALL_ERROR_NOT_ON_CLIENT', 3);
define ('Grpc\CALL_ERROR_ALREADY_INVOKED', 5);
define ('Grpc\CALL_ERROR_NOT_INVOKED', 6);
define ('Grpc\CALL_ERROR_ALREADY_FINISHED', 7);
define ('Grpc\CALL_ERROR_TOO_MANY_OPERATIONS', 8);
define ('Grpc\CALL_ERROR_INVALID_FLAGS', 9);
define ('Grpc\WRITE_BUFFER_HINT', 1);
define ('Grpc\WRITE_NO_COMPRESS', 2);
define ('Grpc\STATUS_OK', 0);
define ('Grpc\STATUS_CANCELLED', 1);
define ('Grpc\STATUS_UNKNOWN', 2);
define ('Grpc\STATUS_INVALID_ARGUMENT', 3);
define ('Grpc\STATUS_DEADLINE_EXCEEDED', 4);
define ('Grpc\STATUS_NOT_FOUND', 5);
define ('Grpc\STATUS_ALREADY_EXISTS', 6);
define ('Grpc\STATUS_PERMISSION_DENIED', 7);
define ('Grpc\STATUS_UNAUTHENTICATED', 16);
define ('Grpc\STATUS_RESOURCE_EXHAUSTED', 8);
define ('Grpc\STATUS_FAILED_PRECONDITION', 9);
define ('Grpc\STATUS_ABORTED', 10);
define ('Grpc\STATUS_OUT_OF_RANGE', 11);
define ('Grpc\STATUS_UNIMPLEMENTED', 12);
define ('Grpc\STATUS_INTERNAL', 13);
define ('Grpc\STATUS_UNAVAILABLE', 14);
define ('Grpc\STATUS_DATA_LOSS', 15);
define ('Grpc\OP_SEND_INITIAL_METADATA', 0);
define ('Grpc\OP_SEND_MESSAGE', 1);
define ('Grpc\OP_SEND_CLOSE_FROM_CLIENT', 2);
define ('Grpc\OP_SEND_STATUS_FROM_SERVER', 3);
define ('Grpc\OP_RECV_INITIAL_METADATA', 4);
define ('Grpc\OP_RECV_MESSAGE', 5);
define ('Grpc\OP_RECV_STATUS_ON_CLIENT', 6);
define ('Grpc\OP_RECV_CLOSE_ON_SERVER', 7);
define ('Grpc\CHANNEL_IDLE', 0);
define ('Grpc\CHANNEL_CONNECTING', 1);
define ('Grpc\CHANNEL_READY', 2);
define ('Grpc\CHANNEL_TRANSIENT_FAILURE', 3);
define ('Grpc\CHANNEL_FATAL_FAILURE', 4);
define ('Grpc\VERSION', "1.54.0");


}

// End of grpc v.1.54.0
