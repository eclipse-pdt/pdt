<?php

// Start of pcre v.8.3.0

/**
 * {@inheritdoc}
 * @param string $pattern
 * @param string $subject
 * @param mixed $matches [optional]
 * @param int $flags [optional]
 * @param int $offset [optional]
 */
function preg_match (string $pattern, string $subject, &$matches = NULL, int $flags = 0, int $offset = 0): int|false {}

/**
 * {@inheritdoc}
 * @param string $pattern
 * @param string $subject
 * @param mixed $matches [optional]
 * @param int $flags [optional]
 * @param int $offset [optional]
 */
function preg_match_all (string $pattern, string $subject, &$matches = NULL, int $flags = 0, int $offset = 0): int|false {}

/**
 * {@inheritdoc}
 * @param array|string $pattern
 * @param array|string $replacement
 * @param array|string $subject
 * @param int $limit [optional]
 * @param mixed $count [optional]
 */
function preg_replace (array|string $pattern, array|string $replacement, array|string $subject, int $limit = -1, &$count = NULL): array|string|null {}

/**
 * {@inheritdoc}
 * @param array|string $pattern
 * @param array|string $replacement
 * @param array|string $subject
 * @param int $limit [optional]
 * @param mixed $count [optional]
 */
function preg_filter (array|string $pattern, array|string $replacement, array|string $subject, int $limit = -1, &$count = NULL): array|string|null {}

/**
 * {@inheritdoc}
 * @param array|string $pattern
 * @param callable $callback
 * @param array|string $subject
 * @param int $limit [optional]
 * @param mixed $count [optional]
 * @param int $flags [optional]
 */
function preg_replace_callback (array|string $pattern, callable $callback, array|string $subject, int $limit = -1, &$count = NULL, int $flags = 0): array|string|null {}

/**
 * {@inheritdoc}
 * @param array $pattern
 * @param array|string $subject
 * @param int $limit [optional]
 * @param mixed $count [optional]
 * @param int $flags [optional]
 */
function preg_replace_callback_array (array $pattern, array|string $subject, int $limit = -1, &$count = NULL, int $flags = 0): array|string|null {}

/**
 * {@inheritdoc}
 * @param string $pattern
 * @param string $subject
 * @param int $limit [optional]
 * @param int $flags [optional]
 */
function preg_split (string $pattern, string $subject, int $limit = -1, int $flags = 0): array|false {}

/**
 * {@inheritdoc}
 * @param string $str
 * @param string|null $delimiter [optional]
 */
function preg_quote (string $str, ?string $delimiter = NULL): string {}

/**
 * {@inheritdoc}
 * @param string $pattern
 * @param array $array
 * @param int $flags [optional]
 */
function preg_grep (string $pattern, array $array, int $flags = 0): array|false {}

/**
 * {@inheritdoc}
 */
function preg_last_error (): int {}

/**
 * {@inheritdoc}
 */
function preg_last_error_msg (): string {}

define ('PREG_PATTERN_ORDER', 1);
define ('PREG_SET_ORDER', 2);
define ('PREG_OFFSET_CAPTURE', 256);
define ('PREG_UNMATCHED_AS_NULL', 512);
define ('PREG_SPLIT_NO_EMPTY', 1);
define ('PREG_SPLIT_DELIM_CAPTURE', 2);
define ('PREG_SPLIT_OFFSET_CAPTURE', 4);
define ('PREG_GREP_INVERT', 1);
define ('PREG_NO_ERROR', 0);
define ('PREG_INTERNAL_ERROR', 1);
define ('PREG_BACKTRACK_LIMIT_ERROR', 2);
define ('PREG_RECURSION_LIMIT_ERROR', 3);
define ('PREG_BAD_UTF8_ERROR', 4);
define ('PREG_BAD_UTF8_OFFSET_ERROR', 5);
define ('PREG_JIT_STACKLIMIT_ERROR', 6);
define ('PCRE_VERSION', "10.42 2022-12-11");
define ('PCRE_VERSION_MAJOR', 10);
define ('PCRE_VERSION_MINOR', 42);
define ('PCRE_JIT_SUPPORT', true);

// End of pcre v.8.3.0
