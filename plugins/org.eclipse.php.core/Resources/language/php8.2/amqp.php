<?php

// Start of amqp v.1.11.0

class AMQPConnection  {
	private $login;
	private $password;
	private $host;
	private $vhost;
	private $port;
	private $read_timeout;
	private $write_timeout;
	private $connect_timeout;
	private $rpc_timeout;
	private $channel_max;
	private $frame_max;
	private $heartbeat;
	private $cacert;
	private $key;
	private $cert;
	private $verify;
	private $sasl_method;
	private $connection_name;


	/**
	 * @param array[] $credentials [optional]
	 */
	public function __construct (array $credentials = null) {}

	public function isConnected () {}

	public function connect () {}

	public function pconnect () {}

	public function pdisconnect () {}

	public function disconnect () {}

	public function reconnect () {}

	public function preconnect () {}

	public function getLogin () {}

	/**
	 * @param mixed $login
	 */
	public function setLogin ($login = null) {}

	public function getPassword () {}

	/**
	 * @param mixed $password
	 */
	public function setPassword ($password = null) {}

	public function getHost () {}

	/**
	 * @param mixed $host
	 */
	public function setHost ($host = null) {}

	public function getPort () {}

	/**
	 * @param mixed $port
	 */
	public function setPort ($port = null) {}

	public function getVhost () {}

	/**
	 * @param mixed $vhost
	 */
	public function setVhost ($vhost = null) {}

	public function getTimeout () {}

	/**
	 * @param mixed $timeout
	 */
	public function setTimeout ($timeout = null) {}

	public function getReadTimeout () {}

	/**
	 * @param mixed $timeout
	 */
	public function setReadTimeout ($timeout = null) {}

	public function getWriteTimeout () {}

	/**
	 * @param mixed $timeout
	 */
	public function setWriteTimeout ($timeout = null) {}

	public function getRpcTimeout () {}

	/**
	 * @param mixed $timeout
	 */
	public function setRpcTimeout ($timeout = null) {}

	public function getUsedChannels () {}

	public function getMaxChannels () {}

	public function isPersistent () {}

	public function getHeartbeatInterval () {}

	public function getMaxFrameSize () {}

	public function getCACert () {}

	/**
	 * @param mixed $cacert
	 */
	public function setCACert ($cacert = null) {}

	public function getCert () {}

	/**
	 * @param mixed $cert
	 */
	public function setCert ($cert = null) {}

	public function getKey () {}

	/**
	 * @param mixed $key
	 */
	public function setKey ($key = null) {}

	public function getVerify () {}

	/**
	 * @param mixed $verify
	 */
	public function setVerify ($verify = null) {}

	public function getSaslMethod () {}

	/**
	 * @param mixed $sasl_method
	 */
	public function setSaslMethod ($sasl_method = null) {}

	public function getConnectionName () {}

	/**
	 * @param mixed $connection_name
	 */
	public function setConnectionName ($connection_name = null) {}

}

class AMQPChannel  {
	private $connection;
	private $prefetch_count;
	private $prefetch_size;
	private $global_prefetch_count;
	private $global_prefetch_size;
	private $consumers;


	/**
	 * @param AMQPConnection $amqp_connection
	 */
	public function __construct (AMQPConnection $amqp_connection) {}

	public function isConnected () {}

	public function close () {}

	public function getChannelId () {}

	/**
	 * @param mixed $size
	 */
	public function setPrefetchSize ($size = null) {}

	public function getPrefetchSize () {}

	/**
	 * @param mixed $count
	 */
	public function setPrefetchCount ($count = null) {}

	public function getPrefetchCount () {}

	/**
	 * @param mixed $size
	 */
	public function setGlobalPrefetchSize ($size = null) {}

	public function getGlobalPrefetchSize () {}

	/**
	 * @param mixed $count
	 */
	public function setGlobalPrefetchCount ($count = null) {}

	public function getGlobalPrefetchCount () {}

	/**
	 * @param mixed $size
	 * @param mixed $count
	 * @param mixed $global [optional]
	 */
	public function qos ($size = null, $count = null, $global = null) {}

	public function startTransaction () {}

	public function commitTransaction () {}

	public function rollbackTransaction () {}

	public function getConnection () {}

	/**
	 * @param mixed $requeue [optional]
	 */
	public function basicRecover ($requeue = null) {}

	public function confirmSelect () {}

	/**
	 * @param mixed $timeout [optional]
	 */
	public function waitForConfirm ($timeout = null) {}

	/**
	 * @param mixed $ack_callback
	 * @param mixed $nack_callback [optional]
	 */
	public function setConfirmCallback ($ack_callback = null, $nack_callback = null) {}

	/**
	 * @param mixed $return_callback
	 */
	public function setReturnCallback ($return_callback = null) {}

	/**
	 * @param mixed $timeout [optional]
	 */
	public function waitForBasicReturn ($timeout = null) {}

	public function getConsumers () {}

}

class AMQPQueue  {
	private $connection;
	private $channel;
	private $name;
	private $consumer_tag;
	private $passive;
	private $durable;
	private $exclusive;
	private $auto_delete;
	private $arguments;


	/**
	 * @param AMQPChannel $amqp_channel
	 */
	public function __construct (AMQPChannel $amqp_channel) {}

	public function getName () {}

	/**
	 * @param mixed $queue_name
	 */
	public function setName ($queue_name = null) {}

	public function getFlags () {}

	/**
	 * @param mixed $flags
	 */
	public function setFlags ($flags = null) {}

	/**
	 * @param mixed $argument
	 */
	public function getArgument ($argument = null) {}

	public function getArguments () {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function setArgument ($key = null, $value = null) {}

	/**
	 * @param array[] $arguments
	 */
	public function setArguments (array $arguments) {}

	/**
	 * @param mixed $key
	 */
	public function hasArgument ($key = null) {}

	public function declareQueue () {}

	/**
	 * @param mixed $exchange_name
	 * @param mixed $routing_key [optional]
	 * @param mixed $arguments [optional]
	 */
	public function bind ($exchange_name = null, $routing_key = null, $arguments = null) {}

	/**
	 * @param mixed $flags [optional]
	 */
	public function get ($flags = null) {}

	/**
	 * @param mixed $callback
	 * @param mixed $flags [optional]
	 * @param mixed $consumer_tag [optional]
	 */
	public function consume ($callback = null, $flags = null, $consumer_tag = null) {}

	/**
	 * @param mixed $delivery_tag
	 * @param mixed $flags [optional]
	 */
	public function ack ($delivery_tag = null, $flags = null) {}

	/**
	 * @param mixed $delivery_tag
	 * @param mixed $flags [optional]
	 */
	public function nack ($delivery_tag = null, $flags = null) {}

	/**
	 * @param mixed $delivery_tag
	 * @param mixed $flags [optional]
	 */
	public function reject ($delivery_tag = null, $flags = null) {}

	public function purge () {}

	/**
	 * @param mixed $consumer_tag [optional]
	 */
	public function cancel ($consumer_tag = null) {}

	/**
	 * @param mixed $flags [optional]
	 */
	public function delete ($flags = null) {}

	/**
	 * @param mixed $exchange_name
	 * @param mixed $routing_key [optional]
	 * @param mixed $arguments [optional]
	 */
	public function unbind ($exchange_name = null, $routing_key = null, $arguments = null) {}

	public function getChannel () {}

	public function getConnection () {}

	public function getConsumerTag () {}

	public function declare () {}

}

class AMQPExchange  {
	private $connection;
	private $channel;
	private $name;
	private $type;
	private $passive;
	private $durable;
	private $auto_delete;
	private $internal;
	private $arguments;


	/**
	 * @param AMQPChannel $amqp_channel
	 */
	public function __construct (AMQPChannel $amqp_channel) {}

	public function getName () {}

	/**
	 * @param mixed $exchange_name
	 */
	public function setName ($exchange_name = null) {}

	public function getFlags () {}

	/**
	 * @param mixed $flags
	 */
	public function setFlags ($flags = null) {}

	public function getType () {}

	/**
	 * @param mixed $exchange_type
	 */
	public function setType ($exchange_type = null) {}

	/**
	 * @param mixed $argument
	 */
	public function getArgument ($argument = null) {}

	public function getArguments () {}

	/**
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function setArgument ($key = null, $value = null) {}

	/**
	 * @param array[] $arguments
	 */
	public function setArguments (array $arguments) {}

	/**
	 * @param mixed $argument
	 */
	public function hasArgument ($argument = null) {}

	public function declareExchange () {}

	/**
	 * @param mixed $exchange_name
	 * @param mixed $routing_key
	 * @param mixed $flags [optional]
	 */
	public function bind ($exchange_name = null, $routing_key = null, $flags = null) {}

	/**
	 * @param mixed $exchange_name
	 * @param mixed $routing_key
	 * @param mixed $flags [optional]
	 */
	public function unbind ($exchange_name = null, $routing_key = null, $flags = null) {}

	/**
	 * @param mixed $exchange_name [optional]
	 * @param mixed $flags [optional]
	 */
	public function delete ($exchange_name = null, $flags = null) {}

	/**
	 * @param mixed $message
	 * @param mixed $routing_key [optional]
	 * @param mixed $flags [optional]
	 * @param array[] $headers [optional]
	 */
	public function publish ($message = null, $routing_key = null, $flags = null, array $headers = null) {}

	public function getChannel () {}

	public function getConnection () {}

	public function declare () {}

}

class AMQPBasicProperties  {
	private $content_type;
	private $content_encoding;
	private $headers;
	private $delivery_mode;
	private $priority;
	private $correlation_id;
	private $reply_to;
	private $expiration;
	private $message_id;
	private $timestamp;
	private $type;
	private $user_id;
	private $app_id;
	private $cluster_id;


	public function __construct () {}

	public function getContentType () {}

	public function getContentEncoding () {}

	public function getHeaders () {}

	public function getDeliveryMode () {}

	public function getPriority () {}

	public function getCorrelationId () {}

	public function getReplyTo () {}

	public function getExpiration () {}

	public function getMessageId () {}

	public function getTimestamp () {}

	public function getType () {}

	public function getUserId () {}

	public function getAppId () {}

	public function getClusterId () {}

}

class AMQPEnvelope extends AMQPBasicProperties  {
	private $body;
	private $consumer_tag;
	private $delivery_tag;
	private $is_redelivery;
	private $exchange_name;
	private $routing_key;


	public function __construct () {}

	public function getBody () {}

	public function getRoutingKey () {}

	public function getConsumerTag () {}

	public function getDeliveryTag () {}

	public function getExchangeName () {}

	public function isRedelivery () {}

	/**
	 * @param mixed $name
	 */
	public function getHeader ($name = null) {}

	/**
	 * @param mixed $name
	 */
	public function hasHeader ($name = null) {}

	public function getContentType () {}

	public function getContentEncoding () {}

	public function getHeaders () {}

	public function getDeliveryMode () {}

	public function getPriority () {}

	public function getCorrelationId () {}

	public function getReplyTo () {}

	public function getExpiration () {}

	public function getMessageId () {}

	public function getTimestamp () {}

	public function getType () {}

	public function getUserId () {}

	public function getAppId () {}

	public function getClusterId () {}

}

final class AMQPTimestamp implements Stringable {
	const MAX = 18446744073709551616;
	const MIN = 0;

	private $timestamp;


	/**
	 * @param mixed $timestamp [optional]
	 */
	public function __construct ($timestamp = null) {}

	public function getTimestamp () {}

	public function __toString (): string {}

}

final class AMQPDecimal  {
	const EXPONENT_MIN = 0;
	const EXPONENT_MAX = 255;
	const SIGNIFICAND_MIN = 0;
	const SIGNIFICAND_MAX = 4294967295;

	private $exponent;
	private $significand;


	/**
	 * @param mixed $exponent
	 * @param mixed $significand
	 */
	public function __construct ($exponent = null, $significand = null) {}

	public function getExponent () {}

	public function getSignificand () {}

}

class AMQPException extends Exception implements Throwable, Stringable {
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

class AMQPConnectionException extends AMQPException implements Stringable, Throwable {
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

class AMQPChannelException extends AMQPException implements Stringable, Throwable {
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

class AMQPQueueException extends AMQPException implements Stringable, Throwable {
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

class AMQPEnvelopeException extends AMQPException implements Stringable, Throwable {
	protected $message;
	protected $code;
	protected $file;
	protected $line;
	public $envelope;


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

class AMQPExchangeException extends AMQPException implements Stringable, Throwable {
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

class AMQPValueException extends AMQPException implements Stringable, Throwable {
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
define ('AMQP_NOPARAM', 0);
define ('AMQP_JUST_CONSUME', 1);
define ('AMQP_DURABLE', 2);
define ('AMQP_PASSIVE', 4);
define ('AMQP_EXCLUSIVE', 8);
define ('AMQP_AUTODELETE', 16);
define ('AMQP_INTERNAL', 32);
define ('AMQP_NOLOCAL', 64);
define ('AMQP_AUTOACK', 128);
define ('AMQP_IFEMPTY', 256);
define ('AMQP_IFUNUSED', 512);
define ('AMQP_MANDATORY', 1024);
define ('AMQP_IMMEDIATE', 2048);
define ('AMQP_MULTIPLE', 4096);
define ('AMQP_NOWAIT', 8192);
define ('AMQP_REQUEUE', 16384);
define ('AMQP_EX_TYPE_DIRECT', "direct");
define ('AMQP_EX_TYPE_FANOUT', "fanout");
define ('AMQP_EX_TYPE_TOPIC', "topic");
define ('AMQP_EX_TYPE_HEADERS', "headers");
define ('AMQP_OS_SOCKET_TIMEOUT_ERRNO', 536870947);
define ('PHP_AMQP_MAX_CHANNELS', 256);
define ('AMQP_SASL_METHOD_PLAIN', 0);
define ('AMQP_SASL_METHOD_EXTERNAL', 1);

// End of amqp v.1.11.0
