<?php

// Start of amqp v.1.11.0

class AMQPConnection  {

	/**
	 * {@inheritdoc}
	 * @param array $credentials [optional]
	 */
	public function __construct (array $credentials = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function isConnected () {}

	/**
	 * {@inheritdoc}
	 */
	public function connect () {}

	/**
	 * {@inheritdoc}
	 */
	public function pconnect () {}

	/**
	 * {@inheritdoc}
	 */
	public function pdisconnect () {}

	/**
	 * {@inheritdoc}
	 */
	public function disconnect () {}

	/**
	 * {@inheritdoc}
	 */
	public function reconnect () {}

	/**
	 * {@inheritdoc}
	 */
	public function preconnect () {}

	/**
	 * {@inheritdoc}
	 */
	public function getLogin () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $login
	 */
	public function setLogin ($login = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getPassword () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $password
	 */
	public function setPassword ($password = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getHost () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $host
	 */
	public function setHost ($host = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getPort () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $port
	 */
	public function setPort ($port = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getVhost () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $vhost
	 */
	public function setVhost ($vhost = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getTimeout () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $timeout
	 */
	public function setTimeout ($timeout = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getReadTimeout () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $timeout
	 */
	public function setReadTimeout ($timeout = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getWriteTimeout () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $timeout
	 */
	public function setWriteTimeout ($timeout = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getRpcTimeout () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $timeout
	 */
	public function setRpcTimeout ($timeout = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getUsedChannels () {}

	/**
	 * {@inheritdoc}
	 */
	public function getMaxChannels () {}

	/**
	 * {@inheritdoc}
	 */
	public function isPersistent () {}

	/**
	 * {@inheritdoc}
	 */
	public function getHeartbeatInterval () {}

	/**
	 * {@inheritdoc}
	 */
	public function getMaxFrameSize () {}

	/**
	 * {@inheritdoc}
	 */
	public function getCACert () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $cacert
	 */
	public function setCACert ($cacert = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getCert () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $cert
	 */
	public function setCert ($cert = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getKey () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function setKey ($key = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getVerify () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $verify
	 */
	public function setVerify ($verify = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getSaslMethod () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $sasl_method
	 */
	public function setSaslMethod ($sasl_method = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getConnectionName () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $connection_name
	 */
	public function setConnectionName ($connection_name = null) {}

}

class AMQPChannel  {

	/**
	 * {@inheritdoc}
	 * @param AMQPConnection $amqp_connection
	 */
	public function __construct (AMQPConnection $amqp_connection) {}

	/**
	 * {@inheritdoc}
	 */
	public function isConnected () {}

	/**
	 * {@inheritdoc}
	 */
	public function close () {}

	/**
	 * {@inheritdoc}
	 */
	public function getChannelId () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $size
	 */
	public function setPrefetchSize ($size = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getPrefetchSize () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $count
	 */
	public function setPrefetchCount ($count = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getPrefetchCount () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $size
	 */
	public function setGlobalPrefetchSize ($size = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getGlobalPrefetchSize () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $count
	 */
	public function setGlobalPrefetchCount ($count = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getGlobalPrefetchCount () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $size
	 * @param mixed $count
	 * @param mixed $global [optional]
	 */
	public function qos ($size = null, $count = null, $global = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function startTransaction () {}

	/**
	 * {@inheritdoc}
	 */
	public function commitTransaction () {}

	/**
	 * {@inheritdoc}
	 */
	public function rollbackTransaction () {}

	/**
	 * {@inheritdoc}
	 */
	public function getConnection () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $requeue [optional]
	 */
	public function basicRecover ($requeue = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function confirmSelect () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $timeout [optional]
	 */
	public function waitForConfirm ($timeout = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $ack_callback
	 * @param mixed $nack_callback [optional]
	 */
	public function setConfirmCallback ($ack_callback = null, $nack_callback = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $return_callback
	 */
	public function setReturnCallback ($return_callback = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $timeout [optional]
	 */
	public function waitForBasicReturn ($timeout = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getConsumers () {}

}

class AMQPQueue  {

	/**
	 * {@inheritdoc}
	 * @param AMQPChannel $amqp_channel
	 */
	public function __construct (AMQPChannel $amqp_channel) {}

	/**
	 * {@inheritdoc}
	 */
	public function getName () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $queue_name
	 */
	public function setName ($queue_name = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getFlags () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $flags
	 */
	public function setFlags ($flags = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $argument
	 */
	public function getArgument ($argument = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getArguments () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function setArgument ($key = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param array $arguments
	 */
	public function setArguments (array $arguments) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 */
	public function hasArgument ($key = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function declareQueue () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $exchange_name
	 * @param mixed $routing_key [optional]
	 * @param mixed $arguments [optional]
	 */
	public function bind ($exchange_name = null, $routing_key = NULL, $arguments = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $flags [optional]
	 */
	public function get ($flags = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $callback
	 * @param mixed $flags [optional]
	 * @param mixed $consumer_tag [optional]
	 */
	public function consume ($callback = null, $flags = NULL, $consumer_tag = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $delivery_tag
	 * @param mixed $flags [optional]
	 */
	public function ack ($delivery_tag = null, $flags = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $delivery_tag
	 * @param mixed $flags [optional]
	 */
	public function nack ($delivery_tag = null, $flags = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $delivery_tag
	 * @param mixed $flags [optional]
	 */
	public function reject ($delivery_tag = null, $flags = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function purge () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $consumer_tag [optional]
	 */
	public function cancel ($consumer_tag = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $flags [optional]
	 */
	public function delete ($flags = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $exchange_name
	 * @param mixed $routing_key [optional]
	 * @param mixed $arguments [optional]
	 */
	public function unbind ($exchange_name = null, $routing_key = NULL, $arguments = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getChannel () {}

	/**
	 * {@inheritdoc}
	 */
	public function getConnection () {}

	/**
	 * {@inheritdoc}
	 */
	public function getConsumerTag () {}

	/**
	 * {@inheritdoc}
	 * @deprecated 
	 */
	public function declare () {}

}

class AMQPExchange  {

	/**
	 * {@inheritdoc}
	 * @param AMQPChannel $amqp_channel
	 */
	public function __construct (AMQPChannel $amqp_channel) {}

	/**
	 * {@inheritdoc}
	 */
	public function getName () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $exchange_name
	 */
	public function setName ($exchange_name = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getFlags () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $flags
	 */
	public function setFlags ($flags = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getType () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $exchange_type
	 */
	public function setType ($exchange_type = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $argument
	 */
	public function getArgument ($argument = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getArguments () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $key
	 * @param mixed $value
	 */
	public function setArgument ($key = null, $value = null) {}

	/**
	 * {@inheritdoc}
	 * @param array $arguments
	 */
	public function setArguments (array $arguments) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $argument
	 */
	public function hasArgument ($argument = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function declareExchange () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $exchange_name
	 * @param mixed $routing_key
	 * @param mixed $flags [optional]
	 */
	public function bind ($exchange_name = null, $routing_key = null, $flags = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $exchange_name
	 * @param mixed $routing_key
	 * @param mixed $flags [optional]
	 */
	public function unbind ($exchange_name = null, $routing_key = null, $flags = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $exchange_name [optional]
	 * @param mixed $flags [optional]
	 */
	public function delete ($exchange_name = NULL, $flags = NULL) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $message
	 * @param mixed $routing_key [optional]
	 * @param mixed $flags [optional]
	 * @param array $headers [optional]
	 */
	public function publish ($message = null, $routing_key = NULL, $flags = NULL, array $headers = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getChannel () {}

	/**
	 * {@inheritdoc}
	 */
	public function getConnection () {}

	/**
	 * {@inheritdoc}
	 * @deprecated 
	 */
	public function declare () {}

}

class AMQPBasicProperties  {

	/**
	 * {@inheritdoc}
	 */
	public function __construct () {}

	/**
	 * {@inheritdoc}
	 */
	public function getContentType () {}

	/**
	 * {@inheritdoc}
	 */
	public function getContentEncoding () {}

	/**
	 * {@inheritdoc}
	 */
	public function getHeaders () {}

	/**
	 * {@inheritdoc}
	 */
	public function getDeliveryMode () {}

	/**
	 * {@inheritdoc}
	 */
	public function getPriority () {}

	/**
	 * {@inheritdoc}
	 */
	public function getCorrelationId () {}

	/**
	 * {@inheritdoc}
	 */
	public function getReplyTo () {}

	/**
	 * {@inheritdoc}
	 */
	public function getExpiration () {}

	/**
	 * {@inheritdoc}
	 */
	public function getMessageId () {}

	/**
	 * {@inheritdoc}
	 */
	public function getTimestamp () {}

	/**
	 * {@inheritdoc}
	 */
	public function getType () {}

	/**
	 * {@inheritdoc}
	 */
	public function getUserId () {}

	/**
	 * {@inheritdoc}
	 */
	public function getAppId () {}

	/**
	 * {@inheritdoc}
	 */
	public function getClusterId () {}

}

class AMQPEnvelope extends AMQPBasicProperties  {

	/**
	 * {@inheritdoc}
	 */
	public function __construct () {}

	/**
	 * {@inheritdoc}
	 */
	public function getBody () {}

	/**
	 * {@inheritdoc}
	 */
	public function getRoutingKey () {}

	/**
	 * {@inheritdoc}
	 */
	public function getConsumerTag () {}

	/**
	 * {@inheritdoc}
	 */
	public function getDeliveryTag () {}

	/**
	 * {@inheritdoc}
	 */
	public function getExchangeName () {}

	/**
	 * {@inheritdoc}
	 */
	public function isRedelivery () {}

	/**
	 * {@inheritdoc}
	 * @param mixed $name
	 */
	public function getHeader ($name = null) {}

	/**
	 * {@inheritdoc}
	 * @param mixed $name
	 */
	public function hasHeader ($name = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getContentType () {}

	/**
	 * {@inheritdoc}
	 */
	public function getContentEncoding () {}

	/**
	 * {@inheritdoc}
	 */
	public function getHeaders () {}

	/**
	 * {@inheritdoc}
	 */
	public function getDeliveryMode () {}

	/**
	 * {@inheritdoc}
	 */
	public function getPriority () {}

	/**
	 * {@inheritdoc}
	 */
	public function getCorrelationId () {}

	/**
	 * {@inheritdoc}
	 */
	public function getReplyTo () {}

	/**
	 * {@inheritdoc}
	 */
	public function getExpiration () {}

	/**
	 * {@inheritdoc}
	 */
	public function getMessageId () {}

	/**
	 * {@inheritdoc}
	 */
	public function getTimestamp () {}

	/**
	 * {@inheritdoc}
	 */
	public function getType () {}

	/**
	 * {@inheritdoc}
	 */
	public function getUserId () {}

	/**
	 * {@inheritdoc}
	 */
	public function getAppId () {}

	/**
	 * {@inheritdoc}
	 */
	public function getClusterId () {}

}

final class AMQPTimestamp implements Stringable {
	const MAX = 18446744073709551616;
	const MIN = 0;


	/**
	 * {@inheritdoc}
	 * @param mixed $timestamp [optional]
	 */
	public function __construct ($timestamp = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getTimestamp () {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

}

final class AMQPDecimal  {
	const EXPONENT_MIN = 0;
	const EXPONENT_MAX = 255;
	const SIGNIFICAND_MIN = 0;
	const SIGNIFICAND_MAX = 4294967295;


	/**
	 * {@inheritdoc}
	 * @param mixed $exponent
	 * @param mixed $significand
	 */
	public function __construct ($exponent = null, $significand = null) {}

	/**
	 * {@inheritdoc}
	 */
	public function getExponent () {}

	/**
	 * {@inheritdoc}
	 */
	public function getSignificand () {}

}

class AMQPException extends Exception implements Throwable, Stringable {

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

class AMQPConnectionException extends AMQPException implements Stringable, Throwable {

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

class AMQPChannelException extends AMQPException implements Stringable, Throwable {

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

class AMQPQueueException extends AMQPException implements Stringable, Throwable {

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

class AMQPEnvelopeException extends AMQPException implements Stringable, Throwable {

	public $envelope;

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

class AMQPExchangeException extends AMQPException implements Stringable, Throwable {

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

class AMQPValueException extends AMQPException implements Stringable, Throwable {

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
