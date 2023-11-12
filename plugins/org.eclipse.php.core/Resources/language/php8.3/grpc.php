<?php

// Start of grpc v.1.54.0

namespace Grpc {

class Call  {

	protected $channel;

	/**
	 * {@inheritdoc}
	 * @param mixed $channel
	 * @param mixed $method
	 * @param mixed $deadline
	 * @param mixed $host_override [optional]
	 */
	public function __construct ($channel = null, $method = null, $deadline = null, $host_override = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $ops
	 */
	public function startBatch ($ops = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getPeer () {}

	/**
	 * {@inheritdoc}
	 */
	public function cancel () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $credentials
	 */
	public function setCredentials ($credentials = null) {}

}

class Channel  {

	/**
	 * {@inheritdoc}
	 * @param mixed $target
	 * @param mixed $args
	 */
	public function __construct ($target = null, $args = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getTarget () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $try_to_connect [optional]
	 */
	public function getConnectivityState ($try_to_connect = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $last_state
	 * @param mixed $deadline
	 */
	public function watchConnectivityState ($last_state = null, $deadline = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function close () {}

}

class Server  {

	/**
	 * {@inheritdoc}
	 * @param mixed $args [optional]
	 */
	public function __construct ($args = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function requestCall () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $addr
	 */
	public function addHttp2Port ($addr = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $addr
	 * @param mixed $server_creds
	 */
	public function addSecureHttp2Port ($addr = null, $server_creds = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function start () {}

}

class Timeval  {

	/**
	 * {@inheritdoc}
	 * @param mixed $microseconds
	 */
	public function __construct ($microseconds = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $timeval
	 */
	public function add ($timeval = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $a_timeval
	 * @param mixed $b_timeval
	 */
	public static function compare ($a_timeval = null, $b_timeval = null) {}

	/**
	 * {@inheritdoc}
	 */
	public static function infFuture () {}

	/**
	 * {@inheritdoc}
	 */
	public static function infPast () {}

	/**
	 * {@inheritdoc}
	 */
	public static function now () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $a_timeval
	 * @param mixed $b_timeval
	 * @param mixed $threshold_timeval
	 */
	public static function similar ($a_timeval = null, $b_timeval = null, $threshold_timeval = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function sleepUntil () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $timeval
	 */
	public function subtract ($timeval = null) {}

	/**
	 * {@inheritdoc}
	 */
	public static function zero () {}

}

class ChannelCredentials  {

	/**
	 * {@inheritdoc}
	 * @param mixed $pem_roots
	 */
	public static function setDefaultRootsPem ($pem_roots = null) {}

	/**
	 * {@inheritdoc}
	 */
	public static function isDefaultRootsPemSet () {}

	/**
	 * {@inheritdoc}
	 */
	public static function invalidateDefaultRootsPem () {}

	/**
	 * {@inheritdoc}
	 */
	public static function createDefault () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $pem_root_certs [optional]
	 * @param mixed $pem_private_key [optional]
	 * @param mixed $pem_cert_chain [optional]
	 */
	public static function createSsl ($pem_root_certs = NULL, $pem_private_key = NULL, $pem_cert_chain = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $channel_creds
	 * @param mixed $call_creds
	 */
	public static function createComposite ($channel_creds = null, $call_creds = null) {}

	/**
	 * {@inheritdoc}
	 */
	public static function createInsecure () {}

	/**
	 * {@inheritdoc}
	 * @param \Grpc\ChannelCredentials|null $fallback_creds
	 */
	public static function createXds (?\Grpc\ChannelCredentials $fallback_creds = null) {}

}

class CallCredentials  {

	/**
	 * {@inheritdoc}
	 * @param mixed $creds1
	 * @param mixed $creds2
	 */
	public static function createComposite ($creds1 = null, $creds2 = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $callback
	 */
	public static function createFromPlugin ($callback = null) {}

}

class ServerCredentials  {

	/**
	 * {@inheritdoc}
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
