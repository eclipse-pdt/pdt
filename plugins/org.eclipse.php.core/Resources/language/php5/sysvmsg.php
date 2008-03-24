<?php

// Start of sysvmsg v.

/**
 * Create or attach to a message queue
 * @link http://php.net/manual/en/function.msg-get-queue.php
 * @param key int
 * @param perms int[optional]
 * @return resource a resource handle that can be used to access the System V message queue.
 */
function msg_get_queue ($key, $perms = null) {}

/**
 * Send a message to a message queue
 * @link http://php.net/manual/en/function.msg-send.php
 * @param queue resource
 * @param msgtype int
 * @param message mixed
 * @param serialize bool[optional]
 * @param blocking bool[optional]
 * @param errorcode int[optional]
 * @return bool 
 */
function msg_send ($queue, $msgtype, $message, $serialize = null, $blocking = null, &$errorcode = null) {}

/**
 * Receive a message from a message queue
 * @link http://php.net/manual/en/function.msg-receive.php
 * @param queue resource
 * @param desiredmsgtype int
 * @param msgtype int
 * @param maxsize int
 * @param message mixed
 * @param unserialize bool[optional]
 * @param flags int[optional]
 * @param errorcode int[optional]
 * @return bool 
 */
function msg_receive ($queue, $desiredmsgtype, &$msgtype, $maxsize, &$message, $unserialize = null, $flags = null, &$errorcode = null) {}

/**
 * Destroy a message queue
 * @link http://php.net/manual/en/function.msg-remove-queue.php
 * @param queue resource
 * @return bool 
 */
function msg_remove_queue ($queue) {}

/**
 * Returns information from the message queue data structure
 * @link http://php.net/manual/en/function.msg-stat-queue.php
 * @param queue resource
 * @return array 
 */
function msg_stat_queue ($queue) {}

/**
 * Set information in the message queue data structure
 * @link http://php.net/manual/en/function.msg-set-queue.php
 * @param queue resource
 * @param data array
 * @return bool 
 */
function msg_set_queue ($queue, array $data) {}

define ('MSG_IPC_NOWAIT', 1);
define ('MSG_EAGAIN', 11);
define ('MSG_ENOMSG', 42);
define ('MSG_NOERROR', 2);
define ('MSG_EXCEPT', 4);

// End of sysvmsg v.
?>
