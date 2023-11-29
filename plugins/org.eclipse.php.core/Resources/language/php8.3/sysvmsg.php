<?php

// Start of sysvmsg v.8.3.0

final class SysvMessageQueue  {
}

/**
 * {@inheritdoc}
 * @param int $key
 * @param int $permissions [optional]
 */
function msg_get_queue (int $key, int $permissions = 438): SysvMessageQueue|false {}

/**
 * {@inheritdoc}
 * @param SysvMessageQueue $queue
 * @param int $message_type
 * @param mixed $message
 * @param bool $serialize [optional]
 * @param bool $blocking [optional]
 * @param mixed $error_code [optional]
 */
function msg_send (SysvMessageQueue $queue, int $message_type, $message = null, bool $serialize = true, bool $blocking = true, &$error_code = NULL): bool {}

/**
 * {@inheritdoc}
 * @param SysvMessageQueue $queue
 * @param int $desired_message_type
 * @param mixed $received_message_type
 * @param int $max_message_size
 * @param mixed $message
 * @param bool $unserialize [optional]
 * @param int $flags [optional]
 * @param mixed $error_code [optional]
 */
function msg_receive (SysvMessageQueue $queue, int $desired_message_type, &$received_message_type = null, int $max_message_size, mixed &$message = null, bool $unserialize = true, int $flags = 0, &$error_code = NULL): bool {}

/**
 * {@inheritdoc}
 * @param SysvMessageQueue $queue
 */
function msg_remove_queue (SysvMessageQueue $queue): bool {}

/**
 * {@inheritdoc}
 * @param SysvMessageQueue $queue
 */
function msg_stat_queue (SysvMessageQueue $queue): array|false {}

/**
 * {@inheritdoc}
 * @param SysvMessageQueue $queue
 * @param array $data
 */
function msg_set_queue (SysvMessageQueue $queue, array $data): bool {}

/**
 * {@inheritdoc}
 * @param int $key
 */
function msg_queue_exists (int $key): bool {}

define ('MSG_IPC_NOWAIT', 1);
define ('MSG_EAGAIN', 35);
define ('MSG_ENOMSG', 91);
define ('MSG_NOERROR', 2);
define ('MSG_EXCEPT', 4);

// End of sysvmsg v.8.3.0
