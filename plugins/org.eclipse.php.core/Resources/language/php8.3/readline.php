<?php

// Start of readline v.8.3.0

/**
 * {@inheritdoc}
 * @param string|null $prompt [optional]
 */
function readline (?string $prompt = NULL): string|false {}

/**
 * {@inheritdoc}
 * @param string|null $var_name [optional]
 * @param mixed $value [optional]
 */
function readline_info (?string $var_name = NULL, $value = NULL): mixed {}

/**
 * {@inheritdoc}
 * @param string $prompt
 */
function readline_add_history (string $prompt): bool {}

/**
 * {@inheritdoc}
 */
function readline_clear_history (): bool {}

/**
 * {@inheritdoc}
 * @param string|null $filename [optional]
 */
function readline_read_history (?string $filename = NULL): bool {}

/**
 * {@inheritdoc}
 * @param string|null $filename [optional]
 */
function readline_write_history (?string $filename = NULL): bool {}

/**
 * {@inheritdoc}
 * @param callable $callback
 */
function readline_completion_function (callable $callback): bool {}

/**
 * {@inheritdoc}
 * @param string $prompt
 * @param callable $callback
 */
function readline_callback_handler_install (string $prompt, callable $callback): bool {}

/**
 * {@inheritdoc}
 */
function readline_callback_read_char (): void {}

/**
 * {@inheritdoc}
 */
function readline_callback_handler_remove (): bool {}

/**
 * {@inheritdoc}
 */
function readline_redisplay (): void {}

/**
 * {@inheritdoc}
 */
function readline_on_new_line (): void {}

define ('READLINE_LIB', "libedit");

// End of readline v.8.3.0
