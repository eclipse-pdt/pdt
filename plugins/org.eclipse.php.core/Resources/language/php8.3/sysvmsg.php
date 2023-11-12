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
 * @param int $key 
 * @param int $permissions [optional] 
 * @return SysvMessageQueue|false Returns SysvMessageQueue instance that can be used to access the System V message queue,
 * or false on failure.
 */
function msg_get_queue (int $key, int $permissions = 0666): SysvMessageQueue|false {}

/**
 * Send a message to a message queue
 * @link http://www.php.net/manual/en/function.msg-send.php
 * @param SysvMessageQueue $queue 
 * @param int $message_type 
 * @param string|int|float|bool $message 
 * @param bool $serialize [optional] 
 * @param bool $blocking [optional] 
 * @param int $error_code [optional] 
 * @return bool Returns true on success or false on failure.
 * <p>Upon successful completion the message queue data structure is updated as
 * follows: msg_lspid is set to the process-ID of the
 * calling process, msg_qnum is incremented by 1 and
 * msg_stime is set to the current time.</p>
 */
function msg_send (SysvMessageQueue $queue, int $message_type, string|int|float|bool $message, bool $serialize = true, bool $blocking = true, int &$error_code = null): bool {}

/**
 * Receive a message from a message queue
 * @link http://www.php.net/manual/en/function.msg-receive.php
 * @param SysvMessageQueue $queue 
 * @param int $desired_message_type 
 * @param int $received_message_type 
 * @param int $max_message_size 
 * @param mixed $message 
 * @param bool $unserialize [optional] 
 * @param int $flags [optional] 
 * @param int $error_code [optional] 
 * @return bool Returns true on success or false on failure.
 * <p>Upon successful completion the message queue data structure is updated as
 * follows: msg_lrpid is set to the process-ID of the
 * calling process, msg_qnum is decremented by 1 and
 * msg_rtime is set to the current time.</p>
 */
function msg_receive (SysvMessageQueue $queue, int $desired_message_type, int &$received_message_type, int $max_message_size, mixed &$message, bool $unserialize = true, int $flags = null, int &$error_code = null): bool {}

/**
 * Destroy a message queue
 * @link http://www.php.net/manual/en/function.msg-remove-queue.php
 * @param SysvMessageQueue $queue 
 * @return bool Returns true on success or false on failure.
 */
function msg_remove_queue (SysvMessageQueue $queue): bool {}

/**
 * Returns information from the message queue data structure
 * @link http://www.php.net/manual/en/function.msg-stat-queue.php
 * @param SysvMessageQueue $queue 
 * @return array|false On success, the return value is an array whose keys and values have the following
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
 * <p>Returns false on failure.</p>
 */
function msg_stat_queue (SysvMessageQueue $queue): array|false {}

/**
 * Set information in the message queue data structure
 * @link http://www.php.net/manual/en/function.msg-set-queue.php
 * @param SysvMessageQueue $queue 
 * @param array $data 
 * @return bool Returns true on success or false on failure.
 */
function msg_set_queue (SysvMessageQueue $queue, array $data): bool {}

/**
 * Check whether a message queue exists
 * @link http://www.php.net/manual/en/function.msg-queue-exists.php
 * @param int $key 
 * @return bool Returns true on success or false on failure.
 */
function msg_queue_exists (int $key): bool {}


/**
 * 
 * @link http://www.php.net/manual/en/sem.constants.php
 * @var int
 */
define ('MSG_IPC_NOWAIT', 1);

/**
 * 
 * @link http://www.php.net/manual/en/sem.constants.php
 * @var int
 */
define ('MSG_EAGAIN', 35);

/**
 * 
 * @link http://www.php.net/manual/en/sem.constants.php
 * @var int
 */
define ('MSG_ENOMSG', 91);

/**
 * 
 * @link http://www.php.net/manual/en/sem.constants.php
 * @var int
 */
define ('MSG_NOERROR', 2);

/**
 * 
 * @link http://www.php.net/manual/en/sem.constants.php
 * @var int
 */
define ('MSG_EXCEPT', 4);

// End of sysvmsg v.8.2.6
