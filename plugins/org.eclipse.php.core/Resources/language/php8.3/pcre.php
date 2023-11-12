<?php

// Start of pcre v.8.2.6

/**
 * Perform a regular expression match
 * @link http://www.php.net/manual/en/function.preg-match.php
 * @param string $pattern 
 * @param string $subject 
 * @param array $matches [optional] 
 * @param int $flags [optional] 
 * @param int $offset [optional] 
 * @return int|false preg_match returns 1 if the pattern
 * matches given subject, 0 if it does not, or false on failure.
 */
function preg_match (string $pattern, string $subject, array &$matches = null, int $flags = null, int $offset = null): int|false {}

/**
 * Perform a global regular expression match
 * @link http://www.php.net/manual/en/function.preg-match-all.php
 * @param string $pattern 
 * @param string $subject 
 * @param array $matches [optional] 
 * @param int $flags [optional] 
 * @param int $offset [optional] 
 * @return int|false Returns the number of full pattern matches (which might be zero), or false on failure.
 */
function preg_match_all (string $pattern, string $subject, array &$matches = null, int $flags = null, int $offset = null): int|false {}

/**
 * Perform a regular expression search and replace
 * @link http://www.php.net/manual/en/function.preg-replace.php
 * @param string|array $pattern 
 * @param string|array $replacement 
 * @param string|array $subject 
 * @param int $limit [optional] 
 * @param int $count [optional] 
 * @return string|array|null preg_replace returns an array if the
 * subject parameter is an array, or a string
 * otherwise.
 * <p>If matches are found, the new subject will
 * be returned, otherwise subject will be
 * returned unchanged or null if an error occurred.</p>
 */
function preg_replace (string|array $pattern, string|array $replacement, string|array $subject, int $limit = -1, int &$count = null): string|array|null {}

/**
 * Perform a regular expression search and replace
 * @link http://www.php.net/manual/en/function.preg-filter.php
 * @param string|array $pattern 
 * @param string|array $replacement 
 * @param string|array $subject 
 * @param int $limit [optional] 
 * @param int $count [optional] 
 * @return string|array|null Returns an array if the subject
 * parameter is an array, or a string otherwise.
 * <p>If no matches are found or an error occurred, an empty array 
 * is returned when subject is an array
 * or null otherwise.</p>
 */
function preg_filter (string|array $pattern, string|array $replacement, string|array $subject, int $limit = -1, int &$count = null): string|array|null {}

/**
 * Perform a regular expression search and replace using a callback
 * @link http://www.php.net/manual/en/function.preg-replace-callback.php
 * @param string|array $pattern 
 * @param callable $callback 
 * @param string|array $subject 
 * @param int $limit [optional] 
 * @param int $count [optional] 
 * @param int $flags [optional] 
 * @return string|array|null preg_replace_callback returns an array if the
 * subject parameter is an array, or a string
 * otherwise. On errors the return value is null
 * <p>If matches are found, the new subject will be returned, otherwise
 * subject will be returned unchanged.</p>
 */
function preg_replace_callback (string|array $pattern, callable $callback, string|array $subject, int $limit = -1, int &$count = null, int $flags = null): string|array|null {}

/**
 * Perform a regular expression search and replace using callbacks
 * @link http://www.php.net/manual/en/function.preg-replace-callback-array.php
 * @param array $pattern 
 * @param string|array $subject 
 * @param int $limit [optional] 
 * @param int $count [optional] 
 * @param int $flags [optional] 
 * @return string|array|null preg_replace_callback_array returns an array if the
 * subject parameter is an array, or a string
 * otherwise. On errors the return value is null
 * <p>If matches are found, the new subject will be returned, otherwise
 * subject will be returned unchanged.</p>
 */
function preg_replace_callback_array (array $pattern, string|array $subject, int $limit = -1, int &$count = null, int $flags = null): string|array|null {}

/**
 * Split string by a regular expression
 * @link http://www.php.net/manual/en/function.preg-split.php
 * @param string $pattern 
 * @param string $subject 
 * @param int $limit [optional] 
 * @param int $flags [optional] 
 * @return array|false Returns an array containing substrings of subject
 * split along boundaries matched by pattern, or false on failure.
 */
function preg_split (string $pattern, string $subject, int $limit = -1, int $flags = null): array|false {}

/**
 * Quote regular expression characters
 * @link http://www.php.net/manual/en/function.preg-quote.php
 * @param string $str 
 * @param string|null $delimiter [optional] 
 * @return string Returns the quoted (escaped) string.
 */
function preg_quote (string $str, ?string $delimiter = null): string {}

/**
 * Return array entries that match the pattern
 * @link http://www.php.net/manual/en/function.preg-grep.php
 * @param string $pattern 
 * @param array $array 
 * @param int $flags [optional] 
 * @return array|false Returns an array indexed using the keys from the
 * array array, or false on failure.
 */
function preg_grep (string $pattern, array $array, int $flags = null): array|false {}

/**
 * Returns the error code of the last PCRE regex execution
 * @link http://www.php.net/manual/en/function.preg-last-error.php
 * @return int Returns one of the following constants (explained on their own page):
 * <p>
 * PREG_NO_ERROR
 * PREG_INTERNAL_ERROR
 * PREG_BACKTRACK_LIMIT_ERROR (see also pcre.backtrack_limit)
 * PREG_RECURSION_LIMIT_ERROR (see also pcre.recursion_limit)
 * PREG_BAD_UTF8_ERROR
 * PREG_BAD_UTF8_OFFSET_ERROR
 * PREG_JIT_STACKLIMIT_ERROR
 * </p>
 */
function preg_last_error (): int {}

/**
 * Returns the error message of the last PCRE regex execution
 * @link http://www.php.net/manual/en/function.preg-last-error-msg.php
 * @return string Returns the error message on success, or "No error" if no
 * error has occurred.
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

// End of pcre v.8.2.6
