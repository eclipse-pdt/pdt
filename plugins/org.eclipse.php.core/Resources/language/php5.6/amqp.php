<?php

// Start of amqp v.1.2.0

class AMQPConnection  {

	/**
	 * Create an instance of AMQPConnection
	 * @link http://www.php.net/manual/en/amqpconnection.construct.php
	 * @param credentials[optional]
	 */
	public function __construct (array $credentials) {}

	/**
	 * Determine if the AMQPConnection object is connected to the broker.
	 * @link http://www.php.net/manual/en/amqpconnection.isconnected.php
	 * @return bool true if connected, false otherwise
	 */
	public function isConnected () {}

	/**
	 * Establish a connection with the AMQP broker.
	 * @link http://www.php.net/manual/en/amqpconnection.connect.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function connect () {}

	public function pconnect () {}

	public function pdisconnect () {}

	/**
	 * Closes the connection with the AMQP broker.
	 * @link http://www.php.net/manual/en/amqpconnection.disconnect.php
	 * @return bool true if connection was successfully closed, false otherwise.
	 */
	public function disconnect () {}

	/**
	 * Closes any open connection and creates a new connection with the AMQP broker.
	 * @link http://www.php.net/manual/en/amqpconnection.reconnect.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function reconnect () {}

	/**
	 * Get the configured login
	 * @link http://www.php.net/manual/en/amqpconnection.getlogin.php
	 * @return string The configured login as a string.
	 */
	public function getLogin () {}

	/**
	 * Set the login.
	 * @link http://www.php.net/manual/en/amqpconnection.setlogin.php
	 * @param login string <p>
	 * The login string used to authenticate with the AMQP broker.
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 */
	public function setLogin ($login) {}

	/**
	 * Get the configured password
	 * @link http://www.php.net/manual/en/amqpconnection.getpassword.php
	 * @return string The configured password as a string.
	 */
	public function getPassword () {}

	/**
	 * Set the password.
	 * @link http://www.php.net/manual/en/amqpconnection.setpassword.php
	 * @param password string <p>
	 * The password string used to authenticate with the AMQP broker.
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 */
	public function setPassword ($password) {}

	/**
	 * Get the configured host
	 * @link http://www.php.net/manual/en/amqpconnection.gethost.php
	 * @return string The configured host as a string.
	 */
	public function getHost () {}

	/**
	 * Set the amqp host.
	 * @link http://www.php.net/manual/en/amqpconnection.sethost.php
	 * @param host string <p>
	 * The hostname of the AMQP broker.
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 */
	public function setHost ($host) {}

	/**
	 * Get the configured port
	 * @link http://www.php.net/manual/en/amqpconnection.getport.php
	 * @return int The configured port as an integer.
	 */
	public function getPort () {}

	/**
	 * Set the port.
	 * @link http://www.php.net/manual/en/amqpconnection.setport.php
	 * @param port int <p>
	 * The port used to connect to the AMQP broker.
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 */
	public function setPort ($port) {}

	/**
	 * Get the configured vhost
	 * @link http://www.php.net/manual/en/amqpconnection.getvhost.php
	 * @return string The configured virtual host as a string.
	 */
	public function getVhost () {}

	/**
	 * Set the amqp virtual host
	 * @link http://www.php.net/manual/en/amqpconnection.setvhost.php
	 * @param vhost string 
	 * @return bool Returns true on success or false on failure.
	 */
	public function setVhost ($vhost) {}

	/**
	 * Get the configured timeout
	 * @link http://www.php.net/manual/en/amqpconnection.gettimeout.php
	 * @return int The configured timeout as an integer.
	 */
	public function getTimeout () {}

	/**
	 * Set the timeout.
	 * @link http://www.php.net/manual/en/amqpconnection.settimeout.php
	 * @param timeout float <p>
	 * The timeout used to connect to the AMQP broker.
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 */
	public function setTimeout ($timeout) {}

	public function getReadTimeout () {}

	/**
	 * @param timeout
	 */
	public function setReadTimeout ($timeout) {}

	public function getWriteTimeout () {}

	/**
	 * @param timeout
	 */
	public function setWriteTimeout ($timeout) {}

}

class AMQPChannel  {

	/**
	 * Create an instance of an AMQPChannel object
	 * @link http://www.php.net/manual/en/amqpchannel.construct.php
	 * @param amqp_connection
	 */
	public function __construct ($amqp_connection) {}

	/**
	 * Check the channel connection
	 * @link http://www.php.net/manual/en/amqpchannel.isconnected.php
	 * @return void 
	 */
	public function isConnected () {}

	public function getChannelId () {}

	/**
	 * Set the window size to prefetch from the broker
	 * @link http://www.php.net/manual/en/amqpchannel.setprefetchsize.php
	 * @param size int <p>
	 * The window size, in octets, to prefetch.
	 * </p>
	 * @return void Returns true on success or false on failure.
	 */
	public function setPrefetchSize ($size) {}

	public function getPrefetchSize () {}

	/**
	 * Set the number of messages to prefetch from the broker
	 * @link http://www.php.net/manual/en/amqpchannel.setprefetchcount.php
	 * @param count int <p>
	 * The number of messages to prefetch.
	 * </p>
	 * @return void Returns true on success or false on failure.
	 */
	public function setPrefetchCount ($count) {}

	public function getPrefetchCount () {}

	/**
	 * Set the Quality Of Service settings for the given channel
	 * @link http://www.php.net/manual/en/amqpchannel.qos.php
	 * @param size int <p>
	 * The window size, in octets, to prefetch.
	 * </p>
	 * @param count int <p>
	 * The number of messages to prefetch.
	 * </p>
	 * @return void Returns true on success or false on failure.
	 */
	public function qos ($size, $count) {}

	/**
	 * Start a transaction
	 * @link http://www.php.net/manual/en/amqpchannel.starttransaction.php
	 * @return void Returns true on success or false on failure.
	 */
	public function startTransaction () {}

	/**
	 * Commit a pending transaction
	 * @link http://www.php.net/manual/en/amqpchannel.committransaction.php
	 * @return void Returns true on success or false on failure.
	 */
	public function commitTransaction () {}

	/**
	 * Rollback a transaction
	 * @link http://www.php.net/manual/en/amqpchannel.rollbacktransaction.php
	 * @return void Returns true on success or false on failure.
	 */
	public function rollbackTransaction () {}

}

class AMQPQueue  {

	/**
	 * Create an instance of an AMQPQueue object
	 * @link http://www.php.net/manual/en/amqpqueue.construct.php
	 * @param amqp_channel
	 */
	public function __construct ($amqp_channel) {}

	/**
	 * Get the configured name
	 * @link http://www.php.net/manual/en/amqpqueue.getname.php
	 * @return string The configured name as a string.
	 */
	public function getName () {}

	/**
	 * Set the queue name
	 * @link http://www.php.net/manual/en/amqpqueue.setname.php
	 * @param queue_name string <p>
	 * The name of the queue as a string.
	 * </p>
	 * @return void Returns true on success or false on failure.
	 */
	public function setName ($queue_name) {}

	/**
	 * Get the flag bitmask
	 * @link http://www.php.net/manual/en/amqpqueue.getflags.php
	 * @return int An integer bitmask of all the flags currently set on this exchange object.
	 */
	public function getFlags () {}

	/**
	 * Set the queue flags
	 * @link http://www.php.net/manual/en/amqpqueue.setflags.php
	 * @param flags int <p>
	 * A bitmask of flags. This call currently only supports a bitmask of the following flags: AMQP_DURABLE, AMQP_PASSIVE, AMQP_EXCLUSIVE, AMQP_AUTODELETE.
	 * </p>
	 * @return void Returns true on success or false on failure.
	 */
	public function setFlags ($flags) {}

	/**
	 * Get the argument associated with the given key
	 * @link http://www.php.net/manual/en/amqpqueue.getargument.php
	 * @param key string <p>
	 * The key to look up.
	 * </p>
	 * @return mixed The string or integer value associated with the given key, or false if the key is not set.
	 */
	public function getArgument ($key) {}

	/**
	 * Get all arguments set on the given queue
	 * @link http://www.php.net/manual/en/amqpqueue.getarguments.php
	 * @return array An array containing all of the set key/value pairs.
	 */
	public function getArguments () {}

	/**
	 * Set the value for the given key
	 * @link http://www.php.net/manual/en/amqpqueue.setargument.php
	 * @param key string <p>
	 * The key to set.
	 * </p>
	 * @param value mixed <p>
	 * The value to set.
	 * </p>
	 * @return void Returns true on success or false on failure.
	 */
	public function setArgument ($key, $value) {}

	/**
	 * Set all arguments on the queue
	 * @link http://www.php.net/manual/en/amqpqueue.setarguments.php
	 * @param arguments array <p>
	 * An array of key/value pairs of arguments.
	 * </p>
	 * @return void Returns true on success or false on failure.
	 */
	public function setArguments (array $arguments) {}

	public function declareQueue () {}

	/**
	 * Bind the given queue to a routing key on an exchange.
	 * @link http://www.php.net/manual/en/amqpqueue.bind.php
	 * @param exchange_name string <p>
	 * The exchange name on which to bind.
	 * </p>
	 * @param routing_key string <p>
	 * The routing key to which to bind.
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 */
	public function bind ($exchange_name, $routing_key) {}

	/**
	 * Retrieve the next message from the queue.
	 * @link http://www.php.net/manual/en/amqpqueue.get.php
	 * @param flags int[optional] <p>
	 * A bitmask of supported flags for the method call. Currently, the only the supported flag is AMQP_AUTOACK. If this value is not provided, it will use the value of amqp.auto_ack.
	 * </p>
	 * @return mixed An instance of AMQPEnvelope representing the message pulled from the queue, or false.
	 */
	public function get ($flags = null) {}

	/**
	 * Consume messages from a queue
	 * @link http://www.php.net/manual/en/amqpqueue.consume.php
	 * @param callback callable <p>
	 * A callback function to which the consumed message will be passed. The function must accept at a minimum one parameter, an AMQPEnvelope object,
	 * and an optional second parameter the AMQPQueue from which the message was consumed.
	 * </p>
	 * <p>
	 * The AMQPQueue::consume will not return the processing thread back to the PHP script until the callback function returns false.
	 * </p>
	 * @param flags int[optional] <p>
	 * A bitmask of any of the flags: AMQP_NOACK.
	 * </p>
	 * @return void 
	 */
	public function consume ($callback, $flags = null) {}

	/**
	 * Acknowledge the receipt of a message
	 * @link http://www.php.net/manual/en/amqpqueue.ack.php
	 * @param delivery_tag int <p>
	 * The message delivery tag of which to acknowledge receipt.
	 * </p>
	 * @param flags int[optional] <p>
	 * The only valid flag that can be passed is AMQP_MULTIPLE.
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 */
	public function ack ($delivery_tag, $flags = null) {}

	/**
	 * Mark a message as explicitly not acknowledged.
	 * @link http://www.php.net/manual/en/amqpqueue.nack.php
	 * @param delivery_tag string <p>
	 * The delivery tag by which to identify the message.
	 * </p>
	 * @param flags string[optional] <p>
	 * A bitmask of flags.
	 * </p>
	 * @return void Returns true on success or false on failure.
	 */
	public function nack ($delivery_tag, $flags = null) {}

	/**
	 * @param delivery_tag
	 * @param flags[optional]
	 */
	public function reject ($delivery_tag, $flags) {}

	/**
	 * Purge the contents of a queue
	 * @link http://www.php.net/manual/en/amqpqueue.purge.php
	 * @return bool Returns true on success or false on failure.
	 */
	public function purge () {}

	/**
	 * Cancel a queue binding.
	 * @link http://www.php.net/manual/en/amqpqueue.cancel.php
	 * @param consumer_tag string[optional] <p>
	 * The queue name to cancel, if the queue object is not already representative of a queue.
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 */
	public function cancel ($consumer_tag = null) {}

	/**
	 * Delete a queue and its contents.
	 * @link http://www.php.net/manual/en/amqpqueue.delete.php
	 * @param flags[optional]
	 * @return bool Returns true on success or false on failure.
	 */
	public function delete ($flags) {}

	/**
	 * Unbind the queue from a routing key.
	 * @link http://www.php.net/manual/en/amqpqueue.unbind.php
	 * @param exchange_name string <p>
	 * The name of the exchange on which the queue is bound.
	 * </p>
	 * @param routing_key string <p>
	 * The binding routing key used by the queue.
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 */
	public function unbind ($exchange_name, $routing_key) {}

	/**
	 * Declare a new queue
	 * @link http://www.php.net/manual/en/amqpqueue.declare.php
	 * @return int the message count.
	 * @deprecated 
	 */
	public function declare () {}

}

class AMQPExchange  {

	/**
	 * Create an instance of AMQPExchange
	 * @link http://www.php.net/manual/en/amqpexchange.construct.php
	 * @param amqp_channel
	 */
	public function __construct ($amqp_channel) {}

	/**
	 * Get the configured name
	 * @link http://www.php.net/manual/en/amqpexchange.getname.php
	 * @return string The configured name as a string.
	 */
	public function getName () {}

	/**
	 * Set the name of the exchange
	 * @link http://www.php.net/manual/en/amqpexchange.setname.php
	 * @param exchange_name string <p>
	 * The name of the exchange to set as string.
	 * </p>
	 * @return void Returns true on success or false on failure.
	 */
	public function setName ($exchange_name) {}

	/**
	 * Get the flag bitmask
	 * @link http://www.php.net/manual/en/amqpexchange.getflags.php
	 * @return int An integer bitmask of all the flags currently set on this exchange object.
	 */
	public function getFlags () {}

	/**
	 * Set the flags on an exchange
	 * @link http://www.php.net/manual/en/amqpexchange.setflags.php
	 * @param flags int <p>
	 * A bitmask of flags. This call currently only considers the following
	 * flags: AMQP_DURABLE,
	 * AMQP_PASSIVE.
	 * </p>
	 * @return void Returns true on success or false on failure.
	 */
	public function setFlags ($flags) {}

	/**
	 * Get the configured type
	 * @link http://www.php.net/manual/en/amqpexchange.gettype.php
	 * @return string The configured type as a string.
	 */
	public function getType () {}

	/**
	 * Set the type of the exchange
	 * @link http://www.php.net/manual/en/amqpexchange.settype.php
	 * @param exchange_type string <p>
	 * The type of exchange as a string.
	 * </p>
	 * @return string Returns true on success or false on failure.
	 */
	public function setType ($exchange_type) {}

	/**
	 * Get the argument associated with the given key
	 * @link http://www.php.net/manual/en/amqpexchange.getargument.php
	 * @param key string <p>
	 * The key to look up.
	 * </p>
	 * @return mixed The string or integer value associated with the given key, or false if the key is not set.
	 */
	public function getArgument ($key) {}

	/**
	 * Get all arguments set on the given exchange
	 * @link http://www.php.net/manual/en/amqpexchange.getarguments.php
	 * @return array An array containing all of the set key/value pairs.
	 */
	public function getArguments () {}

	/**
	 * Set the value for the given key
	 * @link http://www.php.net/manual/en/amqpexchange.setargument.php
	 * @param key string <p>
	 * The key to set.
	 * </p>
	 * @param value mixed <p>
	 * The value to set.
	 * </p>
	 * @return void Returns true on success or false on failure.
	 */
	public function setArgument ($key, $value) {}

	/**
	 * Set all arguments on the exchange
	 * @link http://www.php.net/manual/en/amqpexchange.setarguments.php
	 * @param arguments array <p>
	 * An array of key/value pairs of arguments.
	 * </p>
	 * @return void Returns true on success or false on failure.
	 */
	public function setArguments (array $arguments) {}

	public function declareExchange () {}

	/**
	 * Bind to another exchange
	 * @link http://www.php.net/manual/en/amqpexchange.bind.php
	 * @param destination_exchange_name string <p>
	 * The name of the destination exchange in the binding.
	 * </p>
	 * @param source_exchange_name string <p>
	 * The name of the source exchange in the binding.
	 * </p>
	 * @param routing_key string <p>
	 * The routing key to use as a binding.
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 */
	public function bind ($destination_exchange_name, $source_exchange_name, $routing_key) {}

	/**
	 * Delete the exchange from the broker.
	 * @link http://www.php.net/manual/en/amqpexchange.delete.php
	 * @param flags int[optional] <p>
	 * Optionally AMQP_IFUNUSED can be specified to indicate the exchange
	 * should not be deleted until no clients are connected to it.
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 */
	public function delete ($flags = null) {}

	/**
	 * Publish a message to an exchange.
	 * @link http://www.php.net/manual/en/amqpexchange.publish.php
	 * @param message string <p>
	 * The message to publish.
	 * </p>
	 * @param routing_key string <p>
	 * The routing key to which to publish.
	 * </p>
	 * @param flags int[optional] <p>
	 * One or more of AMQP_MANDATORY and
	 * AMQP_IMMEDIATE.
	 * </p>
	 * @param attributes array[optional] <p>
	 * <table>
	 * Supported indexes
	 * <tr valign="top">
	 * <td>key</td>
	 * <td>Description</td>
	 * <td>Default value</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>content_type</td>
	 * <td></td>
	 * <td>text/plain</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>content_encoding</td>
	 * <td></td>
	 * <td>NULL</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>message_id</td>
	 * <td></td>
	 * <td>NULL</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>user_id</td>
	 * <td></td>
	 * <td>NULL</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>app_id</td>
	 * <td></td>
	 * <td>NULL</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>delivery_mode</td>
	 * <td></td>
	 * <td>NULL</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>priority</td>
	 * <td></td>
	 * <td>NULL</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>timestamp</td>
	 * <td></td>
	 * <td>NULL</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>expiration</td>
	 * <td>time in milliseconds</td>
	 * <td>NULL</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>type</td>
	 * <td></td>
	 * <td>NULL</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>reply_to</td>
	 * <td></td>
	 * <td>NULL</td>
	 * </tr>
	 * <tr valign="top">
	 * <td>headers</td>
	 * <td>custom attributes to pass along with message</td>
	 * <td>array</td>
	 * </tr> 
	 * </table>
	 * </p>
	 * @return bool Returns true on success or false on failure.
	 */
	public function publish ($message, $routing_key, $flags = null, array $attributes = null) {}

	/**
	 * Declare a new exchange on the broker.
	 * @link http://www.php.net/manual/en/amqpexchange.declare.php
	 * @return int Returns true on success or false on failure.
	 * @deprecated 
	 */
	public function declare () {}

}

class AMQPEnvelope  {

	public function __construct () {}

	/**
	 * Get the message body
	 * @link http://www.php.net/manual/en/amqpenvelope.getbody.php
	 * @return string The contents of the message body.
	 */
	public function getBody () {}

	/**
	 * Get the message routing key
	 * @link http://www.php.net/manual/en/amqpenvelope.getroutingkey.php
	 * @return string The message routing key.
	 */
	public function getRoutingKey () {}

	/**
	 * Get the message delivery tag
	 * @link http://www.php.net/manual/en/amqpenvelope.getdeliverytag.php
	 * @return string The delivery tag of the message.
	 */
	public function getDeliveryTag () {}

	public function getDeliveryMode () {}

	public function getExchangeName () {}

	/**
	 * Whether this is a redelivery of the message
	 * @link http://www.php.net/manual/en/amqpenvelope.isredelivery.php
	 * @return bool true if this is a redelivery, false otherwise.
	 */
	public function isRedelivery () {}

	/**
	 * Get the message contenttype
	 * @link http://www.php.net/manual/en/amqpenvelope.getcontenttype.php
	 * @return string The content type of the message.
	 */
	public function getContentType () {}

	/**
	 * Get the message contentencoding
	 * @link http://www.php.net/manual/en/amqpenvelope.getcontentencoding.php
	 * @return string The content encoding of the message.
	 */
	public function getContentEncoding () {}

	/**
	 * Get the message type
	 * @link http://www.php.net/manual/en/amqpenvelope.gettype.php
	 * @return string The message type.
	 */
	public function getType () {}

	/**
	 * Get the message timestamp
	 * @link http://www.php.net/manual/en/amqpenvelope.gettimestamp.php
	 * @return string The message timestamp.
	 */
	public function getTimestamp () {}

	/**
	 * Get the message priority
	 * @link http://www.php.net/manual/en/amqpenvelope.getpriority.php
	 * @return string The message priority.
	 */
	public function getPriority () {}

	/**
	 * Get the message expiration
	 * @link http://www.php.net/manual/en/amqpenvelope.getexpiration.php
	 * @return string The message expiration.
	 */
	public function getExpiration () {}

	/**
	 * Get the message user id
	 * @link http://www.php.net/manual/en/amqpenvelope.getuserid.php
	 * @return string The message user id.
	 */
	public function getUserId () {}

	/**
	 * Get the message appid
	 * @link http://www.php.net/manual/en/amqpenvelope.getappid.php
	 * @return string The application id of the message.
	 */
	public function getAppId () {}

	/**
	 * Get the message id
	 * @link http://www.php.net/manual/en/amqpenvelope.getmessageid.php
	 * @return string The message id.
	 */
	public function getMessageId () {}

	/**
	 * Get the message replyto
	 * @link http://www.php.net/manual/en/amqpenvelope.getreplyto.php
	 * @return string The contents of the reply to field.
	 */
	public function getReplyTo () {}

	/**
	 * Get the message correlation id
	 * @link http://www.php.net/manual/en/amqpenvelope.getcorrelationid.php
	 * @return string The correlation id of the message.
	 */
	public function getCorrelationId () {}

	/**
	 * Get the message headers
	 * @link http://www.php.net/manual/en/amqpenvelope.getheaders.php
	 * @return array An array of key value pairs associated with the message.
	 */
	public function getHeaders () {}

	/**
	 * Get a specific message header
	 * @link http://www.php.net/manual/en/amqpenvelope.getheader.php
	 * @param header_key string 
	 * @return string The contents of the specified header or false if not set.
	 */
	public function getHeader ($header_key) {}

}

class AMQPException extends Exception  {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	final private function __clone () {}

	/**
	 * @param message[optional]
	 * @param code[optional]
	 * @param previous[optional]
	 */
	public function __construct ($message, $code, $previous) {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getPrevious () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

class AMQPConnectionException extends AMQPException  {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	final private function __clone () {}

	/**
	 * @param message[optional]
	 * @param code[optional]
	 * @param previous[optional]
	 */
	public function __construct ($message, $code, $previous) {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getPrevious () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

class AMQPChannelException extends AMQPException  {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	final private function __clone () {}

	/**
	 * @param message[optional]
	 * @param code[optional]
	 * @param previous[optional]
	 */
	public function __construct ($message, $code, $previous) {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getPrevious () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

class AMQPQueueException extends AMQPException  {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	final private function __clone () {}

	/**
	 * @param message[optional]
	 * @param code[optional]
	 * @param previous[optional]
	 */
	public function __construct ($message, $code, $previous) {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getPrevious () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

class AMQPExchangeException extends AMQPException  {
	protected $message;
	protected $code;
	protected $file;
	protected $line;


	final private function __clone () {}

	/**
	 * @param message[optional]
	 * @param code[optional]
	 * @param previous[optional]
	 */
	public function __construct ($message, $code, $previous) {}

	final public function getMessage () {}

	final public function getCode () {}

	final public function getFile () {}

	final public function getLine () {}

	final public function getTrace () {}

	final public function getPrevious () {}

	final public function getTraceAsString () {}

	public function __toString () {}

}

/**
 * Passing in this constant as a flag will forcefully disable all other flags. Use this if you want to temporarily disable the amqp.auto_ack ini setting.
 * @link http://www.php.net/manual/en/amqp.constants.php
 */
define ('AMQP_NOPARAM', 0);

/**
 * Durable exchanges and queues will survive a broker restart, complete with all of their data.
 * @link http://www.php.net/manual/en/amqp.constants.php
 */
define ('AMQP_DURABLE', 2);

/**
 * Passive exchanges are queues will not be redeclared, but the broker will throw an error if the exchange or queue does not exist.
 * @link http://www.php.net/manual/en/amqp.constants.php
 */
define ('AMQP_PASSIVE', 4);

/**
 * Valid for queues only, this flag indicates that only one client can be listening to and consuming from this queue.
 * @link http://www.php.net/manual/en/amqp.constants.php
 */
define ('AMQP_EXCLUSIVE', 8);

/**
 * For exchanges, the auto delete flag indicates that the exchange will be deleted as soon as no more queues are bound to it.
 * If no queues were ever bound the exchange, the exchange will never be deleted.
 * For queues, the auto delete flag indicates that the queue will be deleted as soon as there are no more listeners subscribed to it.
 * If no subscription has ever been active, the queue will never be deleted.
 * Note: Exclusive queues will always be automatically deleted with the client disconnects.
 * @link http://www.php.net/manual/en/amqp.constants.php
 */
define ('AMQP_AUTODELETE', 16);

/**
 * Clients are not allowed to make specific queue bindings to exchanges defined with this flag.
 * @link http://www.php.net/manual/en/amqp.constants.php
 */
define ('AMQP_INTERNAL', 32);

/**
 * When passed to the consume method for a clustered environment, do not consume from the local node.
 * @link http://www.php.net/manual/en/amqp.constants.php
 */
define ('AMQP_NOLOCAL', 64);

/**
 * When passed to the AMQPQueue::get and
 * AMQPQueue::consume methods as a flag, the
 * messages will be immediately marked as acknowledged by the server
 * upon delivery.
 * @link http://www.php.net/manual/en/amqp.constants.php
 */
define ('AMQP_AUTOACK', 128);

/**
 * Passed on queue creation, this flag indicates that the queue should be deleted if it becomes empty.
 * @link http://www.php.net/manual/en/amqp.constants.php
 */
define ('AMQP_IFEMPTY', 256);

/**
 * Passed on queue or exchange creation, this flag indicates that the queue or exchange should be deleted when no clients are connected
 * to the given queue or exchange.
 * @link http://www.php.net/manual/en/amqp.constants.php
 */
define ('AMQP_IFUNUSED', 512);

/**
 * When publishing a message, the message must be routed to a valid queue. If it is not, an error will be returned.
 * @link http://www.php.net/manual/en/amqp.constants.php
 */
define ('AMQP_MANDATORY', 1024);

/**
 * When publishing a message, mark this message for immediate processing by the broker. (High priority message.)
 * @link http://www.php.net/manual/en/amqp.constants.php
 */
define ('AMQP_IMMEDIATE', 2048);

/**
 * If set during a call to AMQPQueue::ack, the delivery tag is treated as "up to and including", so that multiple messages can be acknowledged with a single method. If set to zero, the delivery tag refers to a single message. If the AMQP_MULTIPLE flag is set, and the delivery tag is zero, this indicates acknowledgement of all outstanding messages.
 * @link http://www.php.net/manual/en/amqp.constants.php
 */
define ('AMQP_MULTIPLE', 4096);

/**
 * If set during a call to AMQPExchange::bind, the server will not respond to the method. The client should not wait for a reply method. If the server could not complete the method it will raise a channel or connection exception.
 * @link http://www.php.net/manual/en/amqp.constants.php
 */
define ('AMQP_NOWAIT', 8192);
define ('AMQP_REQUEUE', 16384);

/**
 * A direct exchange type.
 * @link http://www.php.net/manual/en/amqp.constants.php
 */
define ('AMQP_EX_TYPE_DIRECT', "direct");

/**
 * A fanout exchange type.
 * @link http://www.php.net/manual/en/amqp.constants.php
 */
define ('AMQP_EX_TYPE_FANOUT', "fanout");

/**
 * A topic exchange type.
 * @link http://www.php.net/manual/en/amqp.constants.php
 */
define ('AMQP_EX_TYPE_TOPIC', "topic");
define ('AMQP_EX_TYPE_HEADERS', "headers");
define ('AMQP_OS_SOCKET_TIMEOUT_ERRNO', 536870947);

// End of amqp v.1.2.0
