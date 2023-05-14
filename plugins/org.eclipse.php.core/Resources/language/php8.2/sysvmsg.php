<?php

// Start of sysvmsg v.8.2.6

/**
 * A fully opaque class which replaces a sysvmsg queue resource as of PHP 8.0.0.
 * @link http://www.php.net/manual/en/class.sysvmessagequeue.php
 */
final class SysvMessageQueue  {
}

/**
 * Create or attach to a message queue
 * @link http://www.php.net/manual/en/function.msg-get-queue.php
 * @param int $key Message queue numeric ID
 * @param int $permissions [optional] Queue permissions. Default to 0666. If the message queue already
 * exists, the permissions will be ignored.
 * @return mixed SysvMessageQueue instance that can be used to access the System V message queue,
 * or false on failure.
 */
function msg_get_queue (int $key, int $permissions = null): SysvMessageQueue|false {}

/**
 * Send a message to a message queue
 * @link http://www.php.net/manual/en/function.msg-send.php
 * @param SysvMessageQueue $queue The message queue.
 * @param int $message_type The type of the message (MUST be greater than 0)
 * @param mixed $message <p>
 * The body of the message.
 * </p>
 * <p>
 * If serialize set to false is supplied,
 * MUST be of type: string, int, float 
 * or bool. In other case a warning will be issued.
 * </p>
 * @param bool $serialize [optional] The optional serialize controls how the
 * message is sent. serialize
 * defaults to true which means that the message is
 * serialized using the same mechanism as the session module before being
 * sent to the queue. This allows complex arrays and objects to be sent to
 * other PHP scripts, or if you are using the WDDX serializer, to any WDDX
 * compatible client.
 * @param bool $blocking [optional] If the message is too large to fit in the queue, your script will wait
 * until another process reads messages from the queue and frees enough
 * space for your message to be sent.
 * This is called blocking; you can prevent blocking by setting the
 * optional blocking parameter to false, in which
 * case msg_send will immediately return false if the
 * message is too big for the queue, and set the optional
 * error_code to MSG_EAGAIN,
 * indicating that you should try to send your message again a little
 * later on.
 * @param int $error_code [optional] If the function fails, the optional errorcode will be set to the value of the system errno variable.
 * @return bool true on success or false on failure
 * <p>
 * Upon successful completion the message queue data structure is updated as
 * follows: msg_lspid is set to the process-ID of the
 * calling process, msg_qnum is incremented by 1 and
 * msg_stime is set to the current time.
 * </p>
 */
function msg_send (SysvMessageQueue $queue, int $message_type, $message, bool $serialize = null, bool $blocking = null, int &$error_code = null): bool {}

/**
 * Receive a message from a message queue
 * @link http://www.php.net/manual/en/function.msg-receive.php
 * @param SysvMessageQueue $queue The message queue.
 * @param int $desired_message_type If desired_message_type is 0, the message from the front
 * of the queue is returned. If desired_message_type is
 * greater than 0, then the first message of that type is returned.
 * If desired_message_type is less than 0, the first
 * message on the queue with a type less than or equal to the
 * absolute value of desired_message_type will be read.
 * If no messages match the criteria, your script will wait until a suitable
 * message arrives on the queue. You can prevent the script from blocking
 * by specifying MSG_IPC_NOWAIT in the
 * flags parameter.
 * @param int $received_message_type The type of the message that was received will be stored in this
 * parameter.
 * @param int $max_message_size The maximum size of message to be accepted is specified by the
 * max_message_size; if the message in the queue is larger
 * than this size the function will fail (unless you set
 * flags as described below).
 * @param mixed $message The received message will be stored in message,
 * unless there were errors receiving the message.
 * @param bool $unserialize [optional] <p>
 * If set to
 * true, the message is treated as though it was serialized using the
 * same mechanism as the session module. The message will be unserialized
 * and then returned to your script. This allows you to easily receive
 * arrays or complex object structures from other PHP scripts, or if you
 * are using the WDDX serializer, from any WDDX compatible source.
 * </p>
 * <p>
 * If unserialize is false, the message will be
 * returned as a binary-safe string.
 * </p>
 * @param int $flags [optional] The optional flags allows you to pass flags to the
 * low-level msgrcv system call. It defaults to 0, but you may specify one
 * or more of the following values (by adding or ORing them together).
 * <table>
 * Flag values for msg_receive
 * <table>
 * <tr valign="top">
 * <td>MSG_IPC_NOWAIT</td>
 * <td>If there are no messages of the
 * desired_message_type, return immediately and do not
 * wait. The function will fail and return an integer value
 * corresponding to MSG_ENOMSG.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>MSG_EXCEPT</td>
 * <td>Using this flag in combination with a
 * desired_message_type greater than 0 will cause the
 * function to receive the first message that is not equal to
 * desired_message_type.</td>
 * </tr>
 * <tr valign="top">
 * <td>MSG_NOERROR</td>
 * <td>
 * If the message is longer than max_message_size,
 * setting this flag will truncate the message to
 * max_message_size and will not signal an error.
 * </td>
 * </tr>
 * </table>
 * </table>
 * @param int $error_code [optional] If the function fails, the optional error_code
 * will be set to the value of the system errno variable.
 * @return bool true on success or false on failure
 * <p>
 * Upon successful completion the message queue data structure is updated as
 * follows: msg_lrpid is set to the process-ID of the
 * calling process, msg_qnum is decremented by 1 and
 * msg_rtime is set to the current time.
 * </p>
 */
function msg_receive (SysvMessageQueue $queue, int $desired_message_type, int &$received_message_type, int $max_message_size, &$message, bool $unserialize = null, int $flags = null, int &$error_code = null): bool {}

/**
 * Destroy a message queue
 * @link http://www.php.net/manual/en/function.msg-remove-queue.php
 * @param SysvMessageQueue $queue The message queue.
 * @return bool true on success or false on failure
 */
function msg_remove_queue (SysvMessageQueue $queue): bool {}

/**
 * Returns information from the message queue data structure
 * @link http://www.php.net/manual/en/function.msg-stat-queue.php
 * @param SysvMessageQueue $queue The message queue.
 * @return mixed On success, the return value is an array whose keys and values have the following
 * meanings:
 * <table>
 * Array structure for msg_stat_queue
 * <table>
 * <tr valign="top">
 * <td>msg_perm.uid</td>
 * <td>
 * The uid of the owner of the queue.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>msg_perm.gid</td>
 * <td>
 * The gid of the owner of the queue.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>msg_perm.mode</td>
 * <td>
 * The file access mode of the queue.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>msg_stime</td>
 * <td>
 * The time that the last message was sent to the queue.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>msg_rtime</td>
 * <td>
 * The time that the last message was received from the queue.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>msg_ctime</td>
 * <td>
 * The time that the queue was last changed.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>msg_qnum</td>
 * <td>
 * The number of messages waiting to be read from the queue.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>msg_qbytes</td>
 * <td>
 * The maximum number of bytes allowed in one message queue. On
 * Linux, this value may be read and modified via
 * /proc/sys/kernel/msgmnb.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>msg_lspid</td>
 * <td>
 * The pid of the process that sent the last message to the queue.
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>msg_lrpid</td>
 * <td>
 * The pid of the process that received the last message from the queue.
 * </td>
 * </tr>
 * </table>
 * </table>
 * <p>
 * Returns false on failure.
 * </p>
 */
function msg_stat_queue (SysvMessageQueue $queue): array|false {}

/**
 * Set information in the message queue data structure
 * @link http://www.php.net/manual/en/function.msg-set-queue.php
 * @param SysvMessageQueue $queue The message queue.
 * @param array $data You specify the values you require by setting the value of the keys
 * that you require in the data array.
 * @return bool true on success or false on failure
 */
function msg_set_queue (SysvMessageQueue $queue, array $data): bool {}

/**
 * Check whether a message queue exists
 * @link http://www.php.net/manual/en/function.msg-queue-exists.php
 * @param int $key Queue key.
 * @return bool true on success or false on failure
 */
function msg_queue_exists (int $key): bool {}


/**
 * 
 * @link http://www.php.net/manual/en/sem.constants.php
 */
define ('MSG_IPC_NOWAIT', 1);

/**
 * 
 * @link http://www.php.net/manual/en/sem.constants.php
 */
define ('MSG_EAGAIN', 35);

/**
 * 
 * @link http://www.php.net/manual/en/sem.constants.php
 */
define ('MSG_ENOMSG', 91);

/**
 * 
 * @link http://www.php.net/manual/en/sem.constants.php
 */
define ('MSG_NOERROR', 2);

/**
 * 
 * @link http://www.php.net/manual/en/sem.constants.php
 */
define ('MSG_EXCEPT', 4);

// End of sysvmsg v.8.2.6
