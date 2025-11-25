<?php

// Start of amqp v.2.1.1

class AMQPException extends Exception implements Throwable, Stringable {

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

class AMQPConnectionException extends AMQPException implements Stringable, Throwable {

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

class AMQPChannelException extends AMQPException implements Stringable, Throwable {

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

class AMQPQueueException extends AMQPException implements Stringable, Throwable {

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

class AMQPExchangeException extends AMQPException implements Stringable, Throwable {

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

class AMQPValueException extends AMQPException implements Stringable, Throwable {

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

class AMQPConnection  {

	/**
	 * {@inheritdoc}
	 * @param array $credentials [optional]
	 */
	public function __construct (array $credentials = array (
)) {}

	/**
	 * {@inheritdoc}
	 */
	public function isConnected (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function connect (): void {}

	/**
	 * {@inheritdoc}
	 */
	public function pconnect (): void {}

	/**
	 * {@inheritdoc}
	 */
	public function pdisconnect (): void {}

	/**
	 * {@inheritdoc}
	 */
	public function disconnect (): void {}

	/**
	 * {@inheritdoc}
	 */
	public function reconnect (): void {}

	/**
	 * {@inheritdoc}
	 */
	public function preconnect (): void {}

	/**
	 * {@inheritdoc}
	 */
	public function getLogin (): string {}

	/**
	 * {@inheritdoc}
	 * @param string $login
	 */
	public function setLogin (string $login): void {}

	/**
	 * {@inheritdoc}
	 */
	public function getPassword (): string {}

	/**
	 * {@inheritdoc}
	 * @param string $password
	 */
	public function setPassword (string $password): void {}

	/**
	 * {@inheritdoc}
	 */
	public function getHost (): string {}

	/**
	 * {@inheritdoc}
	 * @param string $host
	 */
	public function setHost (string $host): void {}

	/**
	 * {@inheritdoc}
	 */
	public function getPort (): int {}

	/**
	 * {@inheritdoc}
	 * @param int $port
	 */
	public function setPort (int $port): void {}

	/**
	 * {@inheritdoc}
	 */
	public function getVhost (): string {}

	/**
	 * {@inheritdoc}
	 * @param string $vhost
	 */
	public function setVhost (string $vhost): void {}

	/**
	 * {@inheritdoc}
	 */
	public function getTimeout (): float {}

	/**
	 * {@inheritdoc}
	 * @param float $timeout
	 */
	public function setTimeout (float $timeout): void {}

	/**
	 * {@inheritdoc}
	 */
	public function getReadTimeout (): float {}

	/**
	 * {@inheritdoc}
	 * @param float $timeout
	 */
	public function setReadTimeout (float $timeout): void {}

	/**
	 * {@inheritdoc}
	 */
	public function getWriteTimeout (): float {}

	/**
	 * {@inheritdoc}
	 * @param float $timeout
	 */
	public function setWriteTimeout (float $timeout): void {}

	/**
	 * {@inheritdoc}
	 */
	public function getConnectTimeout (): float {}

	/**
	 * {@inheritdoc}
	 */
	public function getRpcTimeout (): float {}

	/**
	 * {@inheritdoc}
	 * @param float $timeout
	 */
	public function setRpcTimeout (float $timeout): void {}

	/**
	 * {@inheritdoc}
	 */
	public function getUsedChannels (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getMaxChannels (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function isPersistent (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getHeartbeatInterval (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getMaxFrameSize (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getCACert (): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $cacert
	 */
	public function setCACert (?string $cacert = null): void {}

	/**
	 * {@inheritdoc}
	 */
	public function getCert (): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $cert
	 */
	public function setCert (?string $cert = null): void {}

	/**
	 * {@inheritdoc}
	 */
	public function getKey (): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $key
	 */
	public function setKey (?string $key = null): void {}

	/**
	 * {@inheritdoc}
	 */
	public function getVerify (): bool {}

	/**
	 * {@inheritdoc}
	 * @param bool $verify
	 */
	public function setVerify (bool $verify): void {}

	/**
	 * {@inheritdoc}
	 */
	public function getSaslMethod (): int {}

	/**
	 * {@inheritdoc}
	 * @param int $saslMethod
	 */
	public function setSaslMethod (int $saslMethod): void {}

	/**
	 * {@inheritdoc}
	 */
	public function getConnectionName (): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $connectionName
	 */
	public function setConnectionName (?string $connectionName = null): void {}

}

class AMQPChannel  {

	/**
	 * {@inheritdoc}
	 * @param AMQPConnection $connection
	 */
	public function __construct (AMQPConnection $connection) {}

	/**
	 * {@inheritdoc}
	 */
	public function isConnected (): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function close (): void {}

	/**
	 * {@inheritdoc}
	 */
	public function getChannelId (): int {}

	/**
	 * {@inheritdoc}
	 * @param int $size
	 */
	public function setPrefetchSize (int $size): void {}

	/**
	 * {@inheritdoc}
	 */
	public function getPrefetchSize (): int {}

	/**
	 * {@inheritdoc}
	 * @param int $count
	 */
	public function setPrefetchCount (int $count): void {}

	/**
	 * {@inheritdoc}
	 */
	public function getPrefetchCount (): int {}

	/**
	 * {@inheritdoc}
	 * @param int $size
	 */
	public function setGlobalPrefetchSize (int $size): void {}

	/**
	 * {@inheritdoc}
	 */
	public function getGlobalPrefetchSize (): int {}

	/**
	 * {@inheritdoc}
	 * @param int $count
	 */
	public function setGlobalPrefetchCount (int $count): void {}

	/**
	 * {@inheritdoc}
	 */
	public function getGlobalPrefetchCount (): int {}

	/**
	 * {@inheritdoc}
	 * @param int $size
	 * @param int $count
	 * @param bool $global [optional]
	 */
	public function qos (int $size, int $count, bool $global = false): void {}

	/**
	 * {@inheritdoc}
	 */
	public function startTransaction (): void {}

	/**
	 * {@inheritdoc}
	 */
	public function commitTransaction (): void {}

	/**
	 * {@inheritdoc}
	 */
	public function rollbackTransaction (): void {}

	/**
	 * {@inheritdoc}
	 */
	public function getConnection (): AMQPConnection {}

	/**
	 * {@inheritdoc}
	 * @param bool $requeue [optional]
	 */
	public function basicRecover (bool $requeue = true): void {}

	/**
	 * {@inheritdoc}
	 */
	public function confirmSelect (): void {}

	/**
	 * {@inheritdoc}
	 * @param float $timeout [optional]
	 */
	public function waitForConfirm (float $timeout = 0.0): void {}

	/**
	 * {@inheritdoc}
	 * @param callable|null $ackCallback
	 * @param callable|null $nackCallback [optional]
	 */
	public function setConfirmCallback (?callable $ackCallback = null, ?callable $nackCallback = NULL): void {}

	/**
	 * {@inheritdoc}
	 * @param callable|null $returnCallback
	 */
	public function setReturnCallback (?callable $returnCallback = null): void {}

	/**
	 * {@inheritdoc}
	 * @param float $timeout [optional]
	 */
	public function waitForBasicReturn (float $timeout = 0.0): void {}

	/**
	 * {@inheritdoc}
	 */
	public function getConsumers (): array {}

}

class AMQPQueue  {

	/**
	 * {@inheritdoc}
	 * @param AMQPChannel $channel
	 */
	public function __construct (AMQPChannel $channel) {}

	/**
	 * {@inheritdoc}
	 */
	public function getName (): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string $name
	 */
	public function setName (string $name): void {}

	/**
	 * {@inheritdoc}
	 */
	public function getFlags (): int {}

	/**
	 * {@inheritdoc}
	 * @param int|null $flags
	 */
	public function setFlags (?int $flags = null): void {}

	/**
	 * {@inheritdoc}
	 * @param string $argumentName
	 */
	public function getArgument (string $argumentName) {}

	/**
	 * {@inheritdoc}
	 */
	public function getArguments (): array {}

	/**
	 * {@inheritdoc}
	 * @param string $argumentName
	 * @param mixed $argumentValue
	 */
	public function setArgument (string $argumentName, $argumentValue = null): void {}

	/**
	 * {@inheritdoc}
	 * @param string $argumentName
	 */
	public function removeArgument (string $argumentName): void {}

	/**
	 * {@inheritdoc}
	 * @param array $arguments
	 */
	public function setArguments (array $arguments): void {}

	/**
	 * {@inheritdoc}
	 * @param string $argumentName
	 */
	public function hasArgument (string $argumentName): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function declareQueue (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function declare (): int {}

	/**
	 * {@inheritdoc}
	 * @param string $exchangeName
	 * @param string|null $routingKey [optional]
	 * @param array $arguments [optional]
	 */
	public function bind (string $exchangeName, ?string $routingKey = NULL, array $arguments = array (
)): void {}

	/**
	 * {@inheritdoc}
	 * @param int|null $flags [optional]
	 */
	public function get (?int $flags = NULL): ?AMQPEnvelope {}

	/**
	 * {@inheritdoc}
	 * @param callable|null $callback [optional]
	 * @param int|null $flags [optional]
	 * @param string|null $consumerTag [optional]
	 */
	public function consume (?callable $callback = NULL, ?int $flags = NULL, ?string $consumerTag = NULL): void {}

	/**
	 * {@inheritdoc}
	 * @param int $deliveryTag
	 * @param int|null $flags [optional]
	 */
	public function ack (int $deliveryTag, ?int $flags = NULL): void {}

	/**
	 * {@inheritdoc}
	 * @param int $deliveryTag
	 * @param int|null $flags [optional]
	 */
	public function nack (int $deliveryTag, ?int $flags = NULL): void {}

	/**
	 * {@inheritdoc}
	 * @param int $deliveryTag
	 * @param int|null $flags [optional]
	 */
	public function reject (int $deliveryTag, ?int $flags = NULL): void {}

	/**
	 * {@inheritdoc}
	 * @param bool $requeue [optional]
	 */
	public function recover (bool $requeue = true): void {}

	/**
	 * {@inheritdoc}
	 */
	public function purge (): int {}

	/**
	 * {@inheritdoc}
	 * @param string $consumerTag [optional]
	 */
	public function cancel (string $consumerTag = ''): void {}

	/**
	 * {@inheritdoc}
	 * @param int|null $flags [optional]
	 */
	public function delete (?int $flags = NULL): int {}

	/**
	 * {@inheritdoc}
	 * @param string $exchangeName
	 * @param string|null $routingKey [optional]
	 * @param array $arguments [optional]
	 */
	public function unbind (string $exchangeName, ?string $routingKey = NULL, array $arguments = array (
)): void {}

	/**
	 * {@inheritdoc}
	 */
	public function getChannel (): AMQPChannel {}

	/**
	 * {@inheritdoc}
	 */
	public function getConnection (): AMQPConnection {}

	/**
	 * {@inheritdoc}
	 */
	public function getConsumerTag (): ?string {}

}

class AMQPExchange  {

	/**
	 * {@inheritdoc}
	 * @param AMQPChannel $channel
	 */
	public function __construct (AMQPChannel $channel) {}

	/**
	 * {@inheritdoc}
	 */
	public function getName (): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $exchangeName
	 */
	public function setName (?string $exchangeName = null): void {}

	/**
	 * {@inheritdoc}
	 */
	public function getFlags (): int {}

	/**
	 * {@inheritdoc}
	 * @param int|null $flags
	 */
	public function setFlags (?int $flags = null): void {}

	/**
	 * {@inheritdoc}
	 */
	public function getType (): ?string {}

	/**
	 * {@inheritdoc}
	 * @param string|null $exchangeType
	 */
	public function setType (?string $exchangeType = null): void {}

	/**
	 * {@inheritdoc}
	 * @param string $argumentName
	 */
	public function getArgument (string $argumentName) {}

	/**
	 * {@inheritdoc}
	 */
	public function getArguments (): array {}

	/**
	 * {@inheritdoc}
	 * @param string $argumentName
	 * @param mixed $argumentValue
	 */
	public function setArgument (string $argumentName, $argumentValue = null): void {}

	/**
	 * {@inheritdoc}
	 * @param string $argumentName
	 */
	public function removeArgument (string $argumentName): void {}

	/**
	 * {@inheritdoc}
	 * @param array $arguments
	 */
	public function setArguments (array $arguments): void {}

	/**
	 * {@inheritdoc}
	 * @param string $argumentName
	 */
	public function hasArgument (string $argumentName): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function declareExchange (): void {}

	/**
	 * {@inheritdoc}
	 */
	public function declare (): void {}

	/**
	 * {@inheritdoc}
	 * @param string $exchangeName
	 * @param string|null $routingKey [optional]
	 * @param array $arguments [optional]
	 */
	public function bind (string $exchangeName, ?string $routingKey = NULL, array $arguments = array (
)): void {}

	/**
	 * {@inheritdoc}
	 * @param string $exchangeName
	 * @param string|null $routingKey [optional]
	 * @param array $arguments [optional]
	 */
	public function unbind (string $exchangeName, ?string $routingKey = NULL, array $arguments = array (
)): void {}

	/**
	 * {@inheritdoc}
	 * @param string|null $exchangeName [optional]
	 * @param int|null $flags [optional]
	 */
	public function delete (?string $exchangeName = NULL, ?int $flags = NULL): void {}

	/**
	 * {@inheritdoc}
	 * @param string $message
	 * @param string|null $routingKey [optional]
	 * @param int|null $flags [optional]
	 * @param array $headers [optional]
	 */
	public function publish (string $message, ?string $routingKey = NULL, ?int $flags = NULL, array $headers = array (
)): void {}

	/**
	 * {@inheritdoc}
	 */
	public function getChannel (): AMQPChannel {}

	/**
	 * {@inheritdoc}
	 */
	public function getConnection (): AMQPConnection {}

}

class AMQPBasicProperties  {

	/**
	 * {@inheritdoc}
	 * @param string|null $contentType [optional]
	 * @param string|null $contentEncoding [optional]
	 * @param array $headers [optional]
	 * @param int $deliveryMode [optional]
	 * @param int $priority [optional]
	 * @param string|null $correlationId [optional]
	 * @param string|null $replyTo [optional]
	 * @param string|null $expiration [optional]
	 * @param string|null $messageId [optional]
	 * @param int|null $timestamp [optional]
	 * @param string|null $type [optional]
	 * @param string|null $userId [optional]
	 * @param string|null $appId [optional]
	 * @param string|null $clusterId [optional]
	 */
	public function __construct (?string $contentType = NULL, ?string $contentEncoding = NULL, array $headers = array (
), int $deliveryMode = 1, int $priority = 0, ?string $correlationId = NULL, ?string $replyTo = NULL, ?string $expiration = NULL, ?string $messageId = NULL, ?int $timestamp = NULL, ?string $type = NULL, ?string $userId = NULL, ?string $appId = NULL, ?string $clusterId = NULL) {}

	/**
	 * {@inheritdoc}
	 */
	public function getContentType (): ?string {}

	/**
	 * {@inheritdoc}
	 */
	public function getContentEncoding (): ?string {}

	/**
	 * {@inheritdoc}
	 */
	public function getHeaders (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function getDeliveryMode (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getPriority (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getCorrelationId (): ?string {}

	/**
	 * {@inheritdoc}
	 */
	public function getReplyTo (): ?string {}

	/**
	 * {@inheritdoc}
	 */
	public function getExpiration (): ?string {}

	/**
	 * {@inheritdoc}
	 */
	public function getMessageId (): ?string {}

	/**
	 * {@inheritdoc}
	 */
	public function getTimestamp (): ?int {}

	/**
	 * {@inheritdoc}
	 */
	public function getType (): ?string {}

	/**
	 * {@inheritdoc}
	 */
	public function getUserId (): ?string {}

	/**
	 * {@inheritdoc}
	 */
	public function getAppId (): ?string {}

	/**
	 * {@inheritdoc}
	 */
	public function getClusterId (): ?string {}

}

class AMQPEnvelope extends AMQPBasicProperties  {

	/**
	 * {@inheritdoc}
	 */
	public function __construct () {}

	/**
	 * {@inheritdoc}
	 */
	public function getBody (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function getRoutingKey (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function getConsumerTag (): ?string {}

	/**
	 * {@inheritdoc}
	 */
	public function getDeliveryTag (): ?int {}

	/**
	 * {@inheritdoc}
	 */
	public function getExchangeName (): ?string {}

	/**
	 * {@inheritdoc}
	 */
	public function isRedelivery (): bool {}

	/**
	 * {@inheritdoc}
	 * @param string $headerName
	 */
	public function getHeader (string $headerName): mixed {}

	/**
	 * {@inheritdoc}
	 * @param string $headerName
	 */
	public function hasHeader (string $headerName): bool {}

	/**
	 * {@inheritdoc}
	 */
	public function getContentType (): ?string {}

	/**
	 * {@inheritdoc}
	 */
	public function getContentEncoding (): ?string {}

	/**
	 * {@inheritdoc}
	 */
	public function getHeaders (): array {}

	/**
	 * {@inheritdoc}
	 */
	public function getDeliveryMode (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getPriority (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getCorrelationId (): ?string {}

	/**
	 * {@inheritdoc}
	 */
	public function getReplyTo (): ?string {}

	/**
	 * {@inheritdoc}
	 */
	public function getExpiration (): ?string {}

	/**
	 * {@inheritdoc}
	 */
	public function getMessageId (): ?string {}

	/**
	 * {@inheritdoc}
	 */
	public function getTimestamp (): ?int {}

	/**
	 * {@inheritdoc}
	 */
	public function getType (): ?string {}

	/**
	 * {@inheritdoc}
	 */
	public function getUserId (): ?string {}

	/**
	 * {@inheritdoc}
	 */
	public function getAppId (): ?string {}

	/**
	 * {@inheritdoc}
	 */
	public function getClusterId (): ?string {}

}

class AMQPEnvelopeException extends AMQPException implements Stringable, Throwable {

	/**
	 * {@inheritdoc}
	 */
	public function getEnvelope (): AMQPEnvelope {}

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

interface AMQPValue  {

	/**
	 * {@inheritdoc}
	 */
	abstract public function toAmqpValue ();

}

final readonly class AMQPTimestamp implements Stringable, AMQPValue {
	const MAX = 1.844674407371E+19;
	const MIN = 0;


	/**
	 * {@inheritdoc}
	 * @param float $timestamp
	 */
	public function __construct (float $timestamp) {}

	/**
	 * {@inheritdoc}
	 */
	public function getTimestamp (): float {}

	/**
	 * {@inheritdoc}
	 */
	public function __toString (): string {}

	/**
	 * {@inheritdoc}
	 */
	public function toAmqpValue () {}

}

final readonly class AMQPDecimal implements AMQPValue {
	const EXPONENT_MIN = 0;
	const EXPONENT_MAX = 255;
	const SIGNIFICAND_MIN = 0;
	const SIGNIFICAND_MAX = 4294967295;


	/**
	 * {@inheritdoc}
	 * @param int $exponent
	 * @param int $significand
	 */
	public function __construct (int $exponent, int $significand) {}

	/**
	 * {@inheritdoc}
	 */
	public function getExponent (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function getSignificand (): int {}

	/**
	 * {@inheritdoc}
	 */
	public function toAmqpValue () {}

}
define ('AMQP_EXTENSION_VERSION', "2.1.1");
define ('AMQP_EXTENSION_VERSION_MAJOR', 2);
define ('AMQP_EXTENSION_VERSION_MINOR', 1);
define ('AMQP_EXTENSION_VERSION_PATCH', 1);
define ('AMQP_EXTENSION_VERSION_EXTRA', "");
define ('AMQP_EXTENSION_VERSION_ID', 20101);
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
define ('AMQP_DELIVERY_MODE_TRANSIENT', 1);
define ('AMQP_DELIVERY_MODE_PERSISTENT', 2);

// End of amqp v.2.1.1
